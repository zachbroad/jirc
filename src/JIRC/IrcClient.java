package JIRC;

import java.net.Socket;
import java.nio.channels.Channel;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/*
 *  TODO: validate that username doesn't have illegal chars used for channels
 */

public class IrcClient {
    private final IrcClientManager clientManager; // reference to IrcServer singleton's clientManager obj
    private final IrcServer server = IrcServer.instance; // reference to IrcServer singleton
    private final List<IrcChannel> channels = new ArrayList<>(); // channels that user is conn. to

    // Network stuff
    private Channel channel;
    private Socket socket;
    private String ipAddress;

    // User profile stuff
    private String awayMessage;
    private String nickname;
    private String realname;
    private String username;

    // Status
    private boolean away;
    private LocalDateTime dateTimeWentAway; // TODO: Implement away since returns seconds since awayAt
    private boolean operator;
    private boolean registered;
    private boolean visible; // used for invisible users todo: implement this

    public IrcClient() {
        clientManager = server.clientManager;
        setVisible(true);
    }

    @Override
    public String toString() {
        return this.getMask();
//        return "[JIRC.IrcClient %s@%s]".formatted(nickname, socket.getInetAddress().getHostAddress());
    }

    /**
     * Provides the user identifier used in messages
     *
     * @return NICKNAME!USERNAME@IPADDRESS
     */
    public String getMask() {
        return "%s!%s@%s".formatted(nickname, username, socket.getInetAddress().getHostAddress());
    }

    /**
     * Give the client server-op
     */
    public void giveOperatorPerms() {
        this.operator = true;
    }

    /**
     * Check if the user is a server-op
     * TODO: accept param for channel to see if chanop, if server-op always TRUE
     *
     * @return client's operator status
     */
    public boolean isOperator() {
        return this.operator;
    }

    /**
     * Register client as user
     * Runs on USER command
     *
     * @param name that user wants to register with
     */
    public void register(String name) {
        this.clientManager.registerClientAsUser(this, name);
    }

    /**
     * Send raw message to client
     *
     * @param message raw message to send
     */
    public void sendMessage(String message) {
        server.sendMessageToClient(message, this);
    }

    /**
     * Remove channel to connected list
     *
     * @param channel to remove
     */
    public void removeChannelFromConnected(IrcChannel channel) {
        channels.remove(channel);
    }

    /**
     * Add channel to connected list
     *
     * @param channel to add
     */
    public void addChannelToConnected(IrcChannel channel) {
        channels.add(channel);
    }

    /**
     * Add client to JIRC.IrcChannel
     *
     * @param channel to join
     */
    public boolean joinChannel(IrcChannel channel) {
        if (!server.channelManager.channels.contains(channel)) {
            IrcServer.logger.warning("User attempted to join channel that doesn't exist: " + channel.toString());
            return false;
        }

        if (channel.getClients().contains(this)) {
            IrcServer.logger.warning("User attempted to join channel they're already in.");
            return false;
        }

        // Add to channel's client list
        channel.addClientToConnectedList(this);

        // Build JOIN message to broadcast to server & send to whole server
        String s = ":{0}@{1} JOIN {2}\r\n";
        String toBroadcast = MessageFormat.format(s, nickname, server.IRC_HOSTNAME, channel);
        server.broadcastMessage(toBroadcast);

        String joinMsgPre = ":{0} JOIN {1}\r\n";
        String joinMsgPost = MessageFormat.format(joinMsgPre, this.getMask(), channel.getName());

        this.sendMessage(joinMsgPost);


        // Send RPL_TOPIC if we have it, otherwise RPL_NOTOPIC
        if (channel.getTopic() != null) {
            Responses.sendTopicMessage(this, channel);
        } else {
            Responses.sendNoTopicMessage(this, channel);
        }

        return true;
    }

    /**
     * Remove client from JIRC.IrcChannel
     *
     * @param channel to leave
     */
    public void leaveChannel(IrcChannel channel) {
        if (channel.hasClient(this)) {
            channel.removeClientFromConnected(this);
        }
        removeChannelFromConnected(channel);
    }

    /**
     * Tries to join a channel from given channelName
     *
     * @param channelName name of channel to join, may or may not exist
     * @return t/f if the channel was successfully joined
     */
    public boolean attemptJoinChannelByName(String channelName) {
        IrcChannel channelToJoin = server.channelManager.getChannelByName(channelName);

        if (channelToJoin == null) {
            Responses.errorNoSuchChannel(this, channelName);
            return false;
        }

        return this.joinChannel(channelToJoin);
    }

    /**
     * Return all unique clients that are in a channel with this client
     *
     * @return a unique set of clients
     */
    public HashSet<IrcClient> getRelatedClients() {
        HashSet<IrcClient> clients = new HashSet<>();
        for (var c : channels) {
            clients.addAll(c.getClients());
        }

        return clients;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAwayMessage() {
        return awayMessage;
    }

    public void setAwayMessage(String awayMessage) {
        this.awayMessage = awayMessage;
    }

    public boolean isAway() {
        return this.away;
    }

    public void setAway(boolean status) {
        if (status) {
            dateTimeWentAway = LocalDateTime.now();
        } else {
            dateTimeWentAway = null;
        }

        this.away = status;
    }

    public long getSecondsIdle() {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), dateTimeWentAway);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Channel getNetworkChannel() {
        return channel;
    }

    public void setNetworkChannel(Channel channel) {
        this.channel = channel;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Get all channels that the client is currently connected to
     *
     * @return a list of unique channels
     */
    public List<IrcChannel> getChannels() {
        return this.channels;
    }

    public boolean hasVoiceInChannel(IrcChannel channel) {
        return true; // TODO: Immplement this
    }

    public String getNamePrefixChannel(IrcChannel channel) {
        String prefix = "";
        if (isOperator()) {
            prefix = "@";
        } else if (hasVoiceInChannel(channel)) {
            prefix = "+";
        } else {
            prefix = "";
        }

        return prefix + nickname;
    }

}

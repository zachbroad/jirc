package JIRC;

import java.net.Socket;
import java.nio.channels.Channel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IrcClient {
    private final IrcServer server = IrcServer.instance;
    private final IrcClientManager clientManager;
    private String nickname;
    private String username;
    private String realname;
    private String awayMessage;
    private boolean away;
    private Socket socket;
    private Channel channel;
    private String ipAddress;
    private boolean registered;
    private boolean visible;
    private boolean operator;
    private List<IrcChannel> channels = new ArrayList<>();

    public IrcClient() {
        clientManager = server.clientManager;
        setVisible(true);
    }

    @Override
    public String toString() {
        return "[JIRC.IrcClient %s@%s]".formatted(nickname, socket.getInetAddress().getHostAddress());
    }

    /**
     * Provides the user identifier used in messages
     *
     * @return NICKNAME!USERNAME@IPADDRESS
     */
    public String getMask() {
        return "%s!%s@%s".formatted(nickname, username, socket.getInetAddress().getHostAddress());
    }

    public void giveOperatorPerms() {
        this.operator = true;
    }

    public boolean isOperator() {
        // TODO: CHANNEL OPERATORS
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

    public void removeChannelFromConnected(IrcChannel channel) {
        channels.remove(channel);
    }

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

    // Return all unique clients that are in a channel with this client
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
        this.away = status;
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

    public List<IrcChannel> getChannels() {
        return this.channels;
    }
}

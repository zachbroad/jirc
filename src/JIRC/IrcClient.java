package JIRC;

import java.net.InetAddress;
import java.net.Socket;
import java.text.MessageFormat;

public class IrcClient {
    private final IrcServer server = IrcServer.instance;
    public String nickname;
    public String username; // name user registered under
    public String realname; // name user registered under
    public String awayMessage;
    public Socket socket;
    public String ipAddress;
    public boolean registered;
    public boolean visible;
    private IrcClientManager clientManager;

    public IrcClient() {
        clientManager = server.clientManager;
        visible = true;
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
    public String getPrefix() {
        return "%s!%s@%s".formatted(nickname, username, socket.getInetAddress().getHostAddress());
    }

    /**
     * Remove client from JIRC.IrcClientManager list
     */
    public void remove() {
        this.clientManager.removeClient(this);
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
     * Add client to JIRC.IrcChannel
     *
     * @param channel to join
     */
    public boolean joinChannel(IrcChannel channel) {
        if (!server.channelManager.channels.contains(channel)) {
            IrcServer.logger.warning("User attempted to join channel that doesn't exist: " + channel.toString());
            return false;
        }

        if (channel.clients.contains(this)) {
            IrcServer.logger.warning("User attempted to join channel they're already in.");
            return false;
        }

        // Add to channel's client list
        channel.addClient(this);

        // Build JOIN message to broadcast to server & send to whole server
        String s = ":{0}@{1} JOIN {2}\r\n";
        String toBroadcast = MessageFormat.format(s, this.nickname, server.IRC_HOSTNAME, channel);
        server.broadcastMessage(toBroadcast);

        String joinMsgPre = ":{0} JOIN {1}\r\n";
        String joinMsgPost = MessageFormat.format(joinMsgPre, this.getPrefix(), channel.name);
        this.sendMessage(joinMsgPost);



        // Send RPL_TOPIC if we have it, otherwise RPL_NOTOPIC
        if (channel.topic != null) {
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
        channel.removeClient(this);
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
            Responses.errorNoSuchMessage(this, channelName);
            return false;
        }

        return this.joinChannel(channelToJoin);
    }
}

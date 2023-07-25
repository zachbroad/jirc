import java.net.InetAddress;
import java.net.Socket;
import java.text.MessageFormat;

public class IrcClient {
    private final IrcServer server = IrcServer.serverInstance;
    public String nickname;
    public String username; // name user registered under
    public Socket socket;
    public InetAddress ipAddress;
    public boolean registered;
    private IrcClientManager clientManager;

    public IrcClient() {
        clientManager = server.clientManager;
    }

    @Override
    public String toString() {
        return "[IrcClient %s@%s]".formatted(this.nickname, this.socket.getRemoteSocketAddress().toString());
    }

    /**
     * @return
     */
    public String identifier() {
        return "%s!%s@%s".formatted(nickname, username, ipAddress);
    }

    /**
     * Remove client from IrcClientManager list
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
     * Add client to IrcChannel
     *
     * @param channel to join
     */
    public boolean joinChannel(IrcChannel channel) {
        if (channel.clients.contains(this)) {
            IrcServer.logger.warning("User attempted to join channel that doesn't exist: " + channel.toString());
            return false;
        }

        // Build JOIN message to broadcast to server & send to whole server
        String s = ":{0}@{1} JOIN {2}\r\n";
        String toBroadcast = MessageFormat.format(s, this.nickname, server.IRC_HOSTNAME, channel);
        server.broadcastMessage(toBroadcast);

        // Add to channel's client list
        channel.addClient(this);

        return true;
    }

    /**
     * Remove client from IrcChannel
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
            String preFormat = ":{0} {1}\r\n";
            String postFormat = MessageFormat.format(preFormat, server.IRC_HOSTNAME, Numerics.ERR_NOSUCHCHANNEL);
            this.sendMessage(postFormat);

            return false;
        }


        boolean joined = this.joinChannel(channelToJoin);
        if (joined) { // channel successfully joined
            String joinMsgPre = ":{0} JOIN {1}\r\n";
            String joinMsgPost = MessageFormat.format(joinMsgPre, this.identifier(), channelToJoin.name);
            this.sendMessage(joinMsgPost);

            String topicMsgPre = ":{0} {1} :{2}\r\n";
            String topicMsgPost = MessageFormat.format(topicMsgPre, server.IRC_HOSTNAME, Numerics.RPL_TOPIC, channelToJoin.topic);
            this.sendMessage(topicMsgPost);
            return true;
        }

        return false;
    }
}

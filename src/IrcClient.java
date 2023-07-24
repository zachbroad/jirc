import java.net.InetAddress;
import java.net.Socket;
import java.text.MessageFormat;

public class IrcClient {
    public String nickname;
    public String username; // name user registered under
    public Socket socket;
    public InetAddress ipAddress;
    public boolean registered;

    private IrcClientManager clientManager;

    public IrcClient() {
        clientManager = IrcServer.serverInstance.clientManager;
    }

    @Override
    public String toString() {
        return "[IrcClient %s@%s]".formatted(this.nickname,this.socket.getRemoteSocketAddress().toString());
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
        IrcServer.serverInstance.sendMessageToClient(message, this);
    }

    /**
     * Add client to IrcChannel
     *
     * @param channel to join
     */
    public void joinChannel(IrcChannel channel) {
        if (channel.clients.contains(this)) {
            IrcServer.logger.warning("User attempted to join channel that doesn't exist: " + channel.toString());
        }

        // Build JOIN message to broadcast to server & send to whole server
        String s = ":{0}@{1} JOIN {2}\r\n";
        String toBroadcast = MessageFormat.format(s, this.nickname, IrcServer.serverInstance.IRC_HOSTNAME, channel);
        IrcServer.serverInstance.broadcastMessage(toBroadcast);

        // Add to channel's client list
        channel.addClient(this);
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
        IrcChannel channelToJoin = IrcServer.serverInstance.channelManager.getChannelByName(channelName);
        if (channelToJoin == null) return false;

        this.joinChannel(channelToJoin);

        return true;
    }
}

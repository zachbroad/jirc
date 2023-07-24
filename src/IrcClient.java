import java.net.InetAddress;
import java.net.Socket;

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

    public void remove() {
        this.clientManager.removeClient(this);
    }

    public void register(String name) {
        this.clientManager.registerClientAsUser(this, name);
    }

    public void sendMessage(String message) {
        IrcServer.serverInstance.sendMessageToClient(message, this);
    }

    public void joinChannel(IrcChannel channel) {
        channel.addClient(this);
    }

    public void leaveChannel(IrcChannel channel) {
        channel.removeClient(this);
    }
}

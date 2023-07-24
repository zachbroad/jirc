import java.util.ArrayList;

public class IrcChannel {
    public String name;
    public String topic;
    public ArrayList<IrcClient> clients = new ArrayList<>();

    public IrcChannel() {
        this.name = "Channel";
        this.topic = "Topic";
    }

    public IrcChannel(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

    public void addClient(IrcClient client) {
        if (!this.clients.contains(client)) {
            clients.add(client);
        } else {
            IrcServer.logger.warning("User tried to join channel they're already in!");
        }
    }

    public void removeClient(IrcClient client) {
        if (this.clients.contains(client)) {
            clients.remove(client);
        } else {
            IrcServer.logger.warning("User tried to leave channel they're not in!");
        }
    }



}

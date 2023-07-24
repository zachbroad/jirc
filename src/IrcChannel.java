import java.util.ArrayList;

public class IrcChannel {
    public String name;
    public String topic;
    public ArrayList<IrcClient> clients = new ArrayList<>();

    public IrcChannel() {}

    public IrcChannel(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }

    /**
     * Add client to channel
     *
     * @param client to add
     */
    public void addClient(IrcClient client) {
        if (!this.clients.contains(client)) {
            clients.add(client);
        } else {
            IrcServer.logger.warning("User tried to join channel they're already in!");
        }
    }

    /**
     * Remove client from channel
     *
     * @param client to remove
     */
    public void removeClient(IrcClient client) {
        if (this.clients.contains(client)) {
            clients.remove(client);
        } else {
            IrcServer.logger.warning("User tried to leave channel they're not in!");
        }
    }
}

package JIRC;

import java.util.ArrayList;

public class IrcChannel {
    public String name;
    public String topic;
    public ArrayList<IrcClient> clients = new ArrayList<>();
    public int maxCapacity;

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
        clients.add(client);
//        if (!this.clients.contains(client) && currentCapacity() < maxCapacity) {
//        } else {
//            JIRC.IrcServer.logger.warning("User tried to join channel [%s] they're already in!".formatted(this.name));
//        }
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

    /**
     * Returns number of connected clients
     *
     * @return how many clients are currently connected to the channel
     */
    public int currentCapacity() {
        return this.clients.size();
    }
}

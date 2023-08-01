package JIRC;

import java.util.ArrayList;

public class IrcChannel {
    private String name;
    private String topic;
    private ArrayList<IrcClient> clients = new ArrayList<>();
    private int maxCapacity;

    public IrcChannel() {}

    public IrcChannel(String name, String topic) {
        this.setName(name);
        this.setTopic(topic);
    }


    @Override
    public String toString() {
        return "%s - %s".formatted(this.getName(), this.getTopic());
    }

    /**
     * Sends raw message to all clients connected to channel
     *
     * @param message raw message to send
     */
    public void sendMessageToClients(String message) {
        for (IrcClient client : this.getClients()) {
            client.sendMessage(message);
        }
    }

    /**
     * Add client to channel
     *
     * @param client to add
     */
    public void addClient(IrcClient client) {
        getClients().add(client);
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
        if (this.getClients().contains(client)) {
            getClients().remove(client);
        } else {
            IrcServer.logger.warning("User tried to leave channel they're not in!");
        }
    }

    public boolean hasClient(IrcClient client) {
        return this.clients.stream().anyMatch(c -> c.username.equals(client.username));
    }

    /**
     * Returns number of connected clients
     *
     * @return how many clients are currently connected to the channel
     */
    public int howManyClients() {
        return this.getClients().size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ArrayList<IrcClient> getClients() {
        return clients;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}

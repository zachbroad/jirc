package JIRC;

import java.util.ArrayList;

public class IrcChannel {
    private String name;
    private String topic;
    private ArrayList<IrcClient> connectedClients = new ArrayList<>();
    private int maxCapacity;

    public IrcChannel() {}

    public IrcChannel(String name, String topic) {
        this.setName(name);
        this.setTopic(topic);
    }


    @Override
    public String toString() {
        return "%s - %s".formatted(name, topic);
    }

    /**
     * Sends raw message to all clients connected to channel
     *
     * @param message raw message to send
     */
    public void sendMessageToClients(String message) {
        for (IrcClient client : connectedClients) {
            client.sendMessage(message);
        }
    }

    public void connectClient(IrcClient client) {
        client.addChannelToConnected(this);
        connectedClients.add(client);
    }

    /**
     * Add client to channel
     * Not used to add the client itself, use client.joinChannel()
     *
     * @param client to add
     */
    public void addClientToConnectedList(IrcClient client) {
        connectedClients.add(client);

        // TODO: max capacity
//        if (!this.clients.contains(client) && currentCapacity() < maxCapacity) {
//        } else {
//            JIRC.IrcServer.logger.warning("User tried to join channel [%s] they're already in!".formatted(this.name));
//        }
    }

    /**
     * Remove client from client connected list
     * Not used to remove the client itself, use client.leaveChannel()
     * @param client to remove
     */
    public void removeClientFromConnected(IrcClient client) {
        if (this.connectedClients.contains(client)) {
            connectedClients.remove(client);
        } else {
            IrcServer.logger.warning("User tried to leave channel they're not in!");
        }
    }

    public boolean hasClient(IrcClient client) {
        return this.connectedClients.stream().anyMatch(c -> c.getUsername().equals(client.getUsername()));
    }

    /**
     * Returns number of connected clients
     *
     * @return how many clients are currently connected to the channel
     */
    public int howManyClients() {
        return this.connectedClients.size();
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
        return connectedClients;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}

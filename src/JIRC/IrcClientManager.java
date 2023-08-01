package JIRC;

import java.util.concurrent.ConcurrentLinkedQueue;

public class IrcClientManager {
    ConcurrentLinkedQueue<IrcClient> clients;

    public IrcClientManager() {
        clients = new ConcurrentLinkedQueue<>();
    }

    public IrcClient getClientByNickname(String name) {
        return clients.stream()
                .filter(c -> c.username.equals(name) || c.nickname.equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a client to JIRC.IrcClientManager
     *
     * @param client to add
     */
    void createClient(IrcClient client) {
        this.clients.add(client);
    }

    /**
     * Remove a client from JIRC.IrcClientManager
     *
     * @param client to remove
     */
    public void removeClient(IrcClient client) {
        this.clients.remove(client);
    }

    /**
     * Register client with username / real name
     *
     * @param client   to register
     * @param username the client wants
     */
    void registerClientAsUser(IrcClient client, String username) {
        // TODO ADD MORE REG & broadcast to server
        client.registered = true;
    }
}

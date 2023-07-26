package JIRC;

import java.util.ArrayList;

public class IrcClientManager {
    ArrayList<IrcClient> clients;

    public IrcClientManager() {
        clients = new ArrayList<>();
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

import java.util.ArrayList;

public class IrcClientManager {
    ArrayList<IrcClient> clients;

    public IrcClientManager() {
        clients = new ArrayList<>();
    }

    void createClient(IrcClient client) {
        this.clients.add(client);
    }

    void removeClient(IrcClient client) {
        this.clients.remove(client);
    }

    void registerClientAsUser(IrcClient client, String username) {
        client.registered = true;
    }
}

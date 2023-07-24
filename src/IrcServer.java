import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.logging.*;


class IrcClient {
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
}

class IrcClientManager {
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

public class IrcServer {
    public static IrcServer serverInstance;
    final int IRC_PORT = 6667;
    final String IRC_HOSTNAME = "127.0.0.1";
    public LocalDateTime dateTimeCreated;
    public final int VERSION = 1;
    boolean isRunning = true;
    ServerSocket server;
    IrcClientManager clientManager = new IrcClientManager();

    public static final Logger logger = Logger.getLogger(IrcServer.class.getName());

    public IrcServer() {
        IrcServer.serverInstance = this;

        // TODO -v verbosity arg
        logger.setLevel(Level.ALL);

        // TODO: store in config
        dateTimeCreated = LocalDateTime.now();
    }

    void handleClient(IrcClient client) {
        if (!client.socket.isConnected()) return;

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        try {
            if (client.socket.isInputShutdown()) return;
            InputStream inputStream = client.socket.getInputStream();
            if (inputStream.available() == 0) return;

            bytesRead = inputStream.read(buffer);
            if (bytesRead == 0) return;
            if (bytesRead < 0) {
                clientManager.removeClient(client);
                IrcServer.logger.warning("Failed to read inputstream from client " + client.toString());
                return;
            }

            // Read buffer into String and print
            String recv = new String(buffer, 0, bytesRead);
            System.out.println(recv);

            String[] messages = recv.split("\r\n");
            IrcServer.logger.info("Got %d messages".formatted(messages.length));


            for (String message : messages) {
                Messages.processMessage(new IrcMessage(message), client);
            }

        } catch (IOException e) {
            System.err.println("Exception while reading from client: " + e.getMessage());
        }
    }

    void sendMessage(String message, IrcClient client) {
        if (!client.socket.isConnected()) return;

        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(client.socket.getOutputStream());
            for (char c : message.toCharArray()) {
                outputStream.write(c);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void startServer() {
        try {
            server = new ServerSocket(IRC_PORT);
            DataInputStream in = null;


            // create server socket
            // bind server socket
            // start listening

            // Try to accept new clients

//            new Thread(() -> {
//                while (this.isRunning) {
            try {
                System.out.println("Accepting new clients");
                Socket clientSocket = null;
                clientSocket = server.accept();
                if (clientSocket.isConnected()) {
                    IrcClient client = new IrcClient();
                    client.nickname = "";
                    client.socket = clientSocket;
                    client.ipAddress = clientSocket.getInetAddress();
                    System.out.println("Client connected " + client.ipAddress);
                    clientManager.createClient(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//                }
//            }).start();

            while (this.isRunning) {
                for (IrcClient client : clientManager.clients) {
                    handleClient(client);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void sendMessage() {

    }


}

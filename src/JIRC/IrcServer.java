package JIRC;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.logging.*;


public class IrcServer {
    public static final Logger logger = Logger.getLogger(IrcServer.class.getName());
    public static IrcServer instance;
    public final String IRC_HOSTNAME = "localhost";
    public final String name = "IRC";
    public final int IRC_PORT = 6667;
    public final int VERSION = 1;
    public final String password = "letmein";
    public LocalDateTime dateTimeCreated;
    public String motd;
    public IrcClientManager clientManager = new IrcClientManager();
    public IrcChannelManager channelManager = new IrcChannelManager();
    ServerSocket server;
    private boolean isRunning = true;

    public IrcServer() {
        IrcServer.instance = this;

        // TODO -v verbosity arg
        logger.setLevel(Level.ALL);

        // TODO: store in config
        dateTimeCreated = LocalDateTime.now();

        // TODO: load from file
        this.motd = "hello world";


        // Create initial channel
        this.channelManager.addChannel(new IrcChannel("#general", "lorem ipsum dolor"));
        this.channelManager.addChannel(new IrcChannel("#test", "test channel 123"));
        this.channelManager.addChannel(new IrcChannel("#channel", "another channel"));
    }

    public String getPrefix() {
        return "%s@%s".formatted(name, IRC_HOSTNAME);
    }

    /**
     * Handle data/messages sent to server from the client
     *
     * @param client who is sending the messages
     */
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

//            JIRC.IrcServer.logger.info("Got %d messages".formatted(messages.length));


            for (String message : messages) {
                Messages.processMessage(new IrcMessage(message), client);
            }

        } catch (IOException e) {
            System.err.println("Exception while reading from client: " + e.getMessage());
        }
    }

    /**
     * Sends a message to all connected clients
     *
     * @param message to send
     */
    public void broadcastMessage(String message) {
        for (IrcClient client : clientManager.clients) {
            sendMessageToClient(message, client);
        }
    }

    public void broadcastMessageFromClient(String message, IrcClient ignore) {
        for (IrcClient client : clientManager.clients) {
            if (client != ignore) {
                sendMessageToClient(message, client);
            }
        }
    }

    /**
     * Sends a message to a specific connected client
     *
     * @param message raw message to send
     * @param client  to send to
     */
    public void sendMessageToClient(String message, IrcClient client) {
        if (message.length() > 512) {
            IrcServer.logger.warning("Message from %s >512 characters!".formatted(client.getPrefix()));
            return;
        }

        if (!client.socket.isConnected()) {
            IrcServer.logger.warning("Client %s socket not connected.".formatted(client.getPrefix()));
        }

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

    /**
     * Set up the server & main loop
     */
    void startServer() {
        try {
            server = new ServerSocket(IRC_PORT);
            DataInputStream in = null;

//            Thread t = new Thread(() -> {
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
//            });
//            t.start();


            while (this.isRunning) {
                for (IrcClient client : clientManager.clients) {
                    handleClient(client);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

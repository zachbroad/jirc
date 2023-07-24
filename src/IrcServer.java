import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.*;


public class IrcServer {
    ServerSocket server;
    public final String IRC_HOSTNAME = "127.0.0.1";
    public final String name = "JIRC";
    public final int IRC_PORT = 6667;
    public final int VERSION = 1;

    public LocalDateTime dateTimeCreated;
    public String motd;

    public static IrcServer serverInstance;
    public IrcClientManager clientManager = new IrcClientManager();
    public IrcChannelManager channelManager = new IrcChannelManager();

    private boolean isRunning = true;


    public static final Logger logger = Logger.getLogger(IrcServer.class.getName());

    public IrcServer() {
        IrcServer.serverInstance = this;

        // TODO -v verbosity arg
        logger.setLevel(Level.ALL);

        // TODO: store in config
        dateTimeCreated = LocalDateTime.now();

        // TODO: load from file
        this.motd = "MOTD goes here";


        // Create initial channel
        this.channelManager.addChannel(new IrcChannel("Channel 1 ", "Anything & everything!"));
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

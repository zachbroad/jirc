package JIRC;

import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
import java.nio.*;


public class IrcServer {
    public static final Logger logger = Logger.getLogger(IrcServer.class.getName());
    public static IrcServer instance;
    public final String IRC_HOSTNAME = "localhost";
    public final String serverName = "IRC";
    public final int IRC_PORT = 6667;
    public final int VERSION = 1;
    public final String password = "letmein";

    public LocalDateTime dateTimeCreated;
    public List<String> motd;

    public IrcClientManager clientManager = new IrcClientManager();
    public IrcChannelManager channelManager = new IrcChannelManager();
    private boolean isRunning = true;

    public IrcServer() {
        IrcServer.instance = this;

        // TODO -v verbosity arg
        logger.setLevel(Level.ALL);

        // TODO: store in config
        dateTimeCreated = LocalDateTime.now();

        // TODO: load from file
        this.motd = IrcMotd.getMotd();


        // Create initial channel
        this.channelManager.addChannel(new IrcChannel("#general", "lorem ipsum dolor"));
        this.channelManager.addChannel(new IrcChannel("#test", "test channel 123"));
        this.channelManager.addChannel(new IrcChannel("#channel", "another channel"));

        IrcOperator.parseOperators();
    }

    public String getPrefix() {
        return "%s@%s".formatted(serverName, IRC_HOSTNAME);
    }

    void handleClient(IrcClient client) {
        if (!isClientConnected(client)) return;

        String receivedData = readFromClient(client);
        if (receivedData == null) return;

        String[] messages = receivedData.split("\r\n");
        for (String message : messages) {
            processMessageFromClient(message, client);
        }
    }

    boolean isClientConnected(IrcClient client) {
        if (!client.socket.isConnected() || client.socket.isInputShutdown()) {
            clientManager.removeClient(client);
            IrcServer.logger.warning("Failed to read inputstream from client " + client.toString());
            return false;
        }
        return true;
    }

    String readFromClient(IrcClient client) {
        if (!client.socket.isConnected()) return null;
        try {
            SocketChannel channel = client.socket.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(512);
            int bytesRead = channel.read(buffer);

            if (bytesRead == -1) {
                clientManager.removeClient(client);
            } else if (bytesRead > 0) {
                buffer.flip();

                String recvData = new String(buffer.array(), 0, bytesRead);
                System.out.println(recvData);

                return recvData;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    void processMessageFromClient(String message, IrcClient client) {
        IrcMessageProcessor.processMessage(new IrcMessage(message), client);
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
            return;
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
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(IRC_PORT));

            int numThreads = 16;
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            while (isRunning) {
                SocketChannel clientChannel = server.accept();

                if (clientChannel != null) {
                    // Set up the new client & add them to clientManager
                    IrcClient client = new IrcClient();
                    client.nickname = "";
                    client.socket = clientChannel.socket();
                    client.ipAddress = clientChannel.socket().getInetAddress().getHostAddress();
                    System.out.println("Client connected " + client.socket.getInetAddress().getHostAddress());
                    clientManager.clients.add(client);

                    executor.execute(() -> {
                        while (clientManager.clients.contains(client)) {
                            handleClient(client);
                        }
                    });
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    logger.warning("Client %s not connected?!".formatted(clientChannel));
                }
            }

            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}

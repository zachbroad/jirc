package JIRC;

import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
import java.nio.*;


/*
 * TODO: Move config stuff to IrcServerConfig class
 */

public class IrcServer {
    public static final Logger logger = Logger.getLogger(IrcServer.class.getName());
    public static IrcServer instance;
    public final IrcChannelManager channelManager = new IrcChannelManager();
    public final IrcClientManager clientManager = new IrcClientManager();
    public final String IRC_HOSTNAME = "localhost"; // TODO: config file
    public final String serverName = "IRC"; // TODO: config file
    public final int IRC_PORT = 6667;
    public final int VERSION = 1;
    public final String password = "letmein"; // TODO: config file
    private String dateTimeCreated;
    private String dateTimeServerStarted;
    private List<String> motd;

    private volatile boolean isRunning = true;
    private volatile boolean shouldRestart = false;

    public IrcServer() {
        IrcServer.instance = this;

        // TODO -v verbosity arg
        logger.setLevel(Level.ALL);

        // TODO: store in config
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        dateTimeCreated = dateTimeFormatter.format(LocalDateTime.now());

        // when server was started
        dateTimeServerStarted = dateTimeFormatter.format(LocalDateTime.now());

        // TODO: load from file
        this.motd = IrcMotd.getMotd();


        // Create initial channel
        this.channelManager.addChannel(new IrcChannel("#general", "lorem ipsum dolor"));
        this.channelManager.addChannel(new IrcChannel("#test", "test channel 123"));
        this.channelManager.addChannel(new IrcChannel("#channel", "another channel"));

        IrcOperator.parseOperators();
    }


    /**
     * Set up the server & main loop
     */

    void startServer() {
        restart:
        try {
            logger.info("Server is starting...");
            shouldRestart = false;
            isRunning = true;
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(IRC_PORT));
            logger.info("Bound to port " + IRC_PORT);

            int numThreads = 16;
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            while (isRunning) {
                SocketChannel clientChannel = server.accept();

                if (clientChannel != null) {
                    // Set up the new client & add them to clientManager
                    IrcClient client = new IrcClient();
                    client.setNickname("");
                    client.setSocket(clientChannel.socket());
                    client.setIpAddress(clientChannel.socket().getInetAddress().getHostAddress());
                    System.out.println("Client connected " + client.getSocket().getInetAddress().getHostAddress());
                    clientManager.addClient(client);

                    executor.execute(() -> {
                        while (clientManager.hasClient(client) && (isRunning && !shouldRestart)) {
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

                if (shouldRestart) {
                    logger.info("Should restart = true");
                    break restart;
                }
            }

            // TODO:  Send shutdown message to all clients here?
            logger.info("Server is shutting down...");

            executor.shutdownNow();
            server.close();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        logger.info("Server has shut down.");
    }

    public void restart() {
        shouldRestart = true;
    }

    public void shutdown() {
        isRunning = false;
    }

    public String getServerInfo() {
        return "SERVER INFO HERE TODO";
    }

    public String getRuntime() {
        return dateTimeServerStarted;
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
        if (!client.getSocket().isConnected() || client.getSocket().isInputShutdown()) {
            clientManager.removeClient(client);
            IrcServer.logger.warning("Failed to read inputstream from client " + client.toString());
            return false;
        }
        return true;
    }

    String readFromClient(IrcClient client) {
        if (!client.getSocket().isConnected()) return null;

        try {
            SocketChannel channel = client.getSocket().getChannel();
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
            clientManager.removeClient(client);
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
        for (IrcClient client : clientManager.getClients()) {
            sendMessageToClient(message, client);
        }
    }

    public void broadcastMessageFromClient(String message, IrcClient ignore) {
        for (IrcClient client : clientManager.getClients()) {
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
            IrcServer.logger.warning("Message from %s >512 characters!".formatted(client.getMask()));
            return;
        }

        if (!client.getSocket().isConnected()) {
            IrcServer.logger.warning("Client %s socket not connected.".formatted(client.getMask()));
            return;
        }

        new Thread(() -> {
            try {
                BufferedOutputStream outputStream = new BufferedOutputStream(client.getSocket().getOutputStream());
//            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
                for (char c : message.toCharArray()) {
                    outputStream.write(c);
                }
                outputStream.flush();
            } catch (IOException e) {
                clientManager.removeClient(client);
                // TODO: Figure out IOException: Broken pipe -- can it happen without user disconnecting?
                e.printStackTrace();
//                throw new RuntimeException(e);
            }
        }).start();
    }

    public List<String> getMotd() {
        return motd;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

}

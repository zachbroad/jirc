import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Queue;



/*
    Most of the messages sent to the server generate a reply of some
    sort.  The most common reply is the numeric reply, used for both
    errors and normal replies.  The numeric reply MUST be sent as one
    message consisting of the sender prefix, the three-digit numeric, and
    the target of the reply.  A numeric reply is not allowed to originate
    from a client. In all other respects, a numeric reply is just like a
    normal message, except that the keyword is made up of 3 numeric
    digits rather than a string of letters.  A list of different replies
    is supplied in section 5 (Replies).
 */

public class Messages {


    private static IrcServer server = IrcServer.serverInstance;


    /*
     */

    /**
     * 001 RPL_WELCOME
     * "Welcome to the Internet Relay Network
     * <nick>!<user>@<host>"
     *
     * @param client recipient of message
     */
    public static void sendWelcomeMessage(IrcClient client) {
        server.sendMessageToClient(MessageFormat.format(
                        ":{0} {1} {2} :Welcome to the Internet Relay Network {2}!{3}@{0}\r\n",
                        server.IRC_HOSTNAME, // 0
                        Numerics.RPL_YOURHOST, // 1
                        client.nickname,
                        client.username
                ), client
        );

        server.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :Your host is {0}, running version {3}\r\n",
                server.IRC_HOSTNAME, // 0
                Numerics.RPL_YOURHOST, // 1
                client.nickname, // 2
                server.VERSION // 3
        ), client);

        server.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :This server was created {3}\r\n",
                server.IRC_HOSTNAME, // 0
                Numerics.RPL_CREATED, // 1
                client.nickname, // 2
                server.dateTimeCreated.toString() // 3
        ), client);

        // TODO fix NULL
        server.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :{3} v1 NULL NULL\r\n",
                server.IRC_HOSTNAME, // 0
                Numerics.RPL_MYINFO, // 1
                client.nickname, // 2
                server.dateTimeCreated.toString(), // 3
                server.name // 4
        ), client);
    }

    /**
     * 372    RPL_MOTD
     * ":- <text>"     * @param client
     * 376    RPL_ENDOFMOTD
     * ":End of MOTD command"
     *
     * @param client recipient of message
     */
    public static void sendMotdMessage(IrcClient client) {
        server.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :- {3} Message of the day - \r\n",
                server.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDSTART, // 1
                client.nickname, // 2
                server.name // 3
        ), client);
        server.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :- {3}\r\n",
                server.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTD, // 1
                client.nickname, // 2
                server.motd // 3
        ), client);
        server.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :End of MOTD command\r\n",
                server.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDEND, // 1
                client.nickname, // 2
                server.motd // 3
        ), client);
    }

    /**
     * Send PING to client
     *
     * @param client recipient of message
     */
    public static void sendPongMessage(IrcClient client, String identifier) { // TODO: FIX ?
        server.sendMessageToClient(MessageFormat.format("PING :{0}\r\n", identifier), client);
    }

    /**
     * 322    RPL_LIST
     * "<channel> <# visible> :<topic>"
     * 323    RPL_LISTEND
     * ":End of LIST"
     *
     * @param client recipient of message
     */
    public static void sendListMessage(IrcClient client) {
        for (IrcChannel channel : server.channelManager.channels) {
            System.out.println("h");
            server.sendMessageToClient(
                    MessageFormat.format(
                            ":{0} {1} {2} {3} {4} :{5}\r\n",
                            server.IRC_HOSTNAME, // 0
                            Numerics.RPL_LIST, // 1
                            client.nickname, // 2
                            channel.name, // 3
                            channel.clients.size(), // 4
                            channel.topic // 5
                    ), client);
        }

        server.sendMessageToClient(
                MessageFormat.format(
                        ":{0} {1} {2} :End of LIST\r\n",
                        server.IRC_HOSTNAME, // 0
                        Numerics.RPL_LISTEND, // 1
                        client.nickname // 2
                ), client);
    }


    /**
     * Parse message & handle it
     * TODO: move message cases to their own files
     *
     * @param ircMessage incoming message to process
     * @param client     recipient of message
     */
    public static void processMessage(IrcMessage ircMessage, IrcClient client) {
        // Process all messages

        String[] arrOfMessageParts = ircMessage.raw.split(" ");

        Queue<String> partsOfMessage = new ArrayDeque<>();
        for (var m : arrOfMessageParts) {
            partsOfMessage.add(m);
        }

        String messageType = partsOfMessage.poll();
        if (messageType == null) return;

        switch (messageType.toUpperCase()) {
            case "CAP" -> {
                IrcServer.logger.info("TODO: CAP");
                break;
            }
            case "EXIT" -> {
                IrcServer.logger.info(MessageFormat.format("Client {0} disconnected", client.toString()));
                server.clientManager.removeClient(client);
            }
            case "NICK" -> {
                String oldName = client.nickname; // store old
                client.nickname = partsOfMessage.poll(); // set new
                server.broadcastMessage(MessageFormat.format(
//                        ":{0}@{1} NICK {2}\r\n",
                        ":{0} NICK {2}\r\n",
                        oldName,
                        server.IRC_HOSTNAME,
                        client.nickname
                ));

                IrcServer.logger.info("Client %s sent name %s".formatted(client.toString(), client.nickname));
            }
            case "USER" -> {
                IrcServer.logger.info(MessageFormat.format("Client {0} wants to register", client.toString()));
                String realName = ircMessage.raw.split(":")[1];
                IrcServer.logger.info(MessageFormat.format("Got real name {0}", realName));
                client.username = realName;
                Messages.sendWelcomeMessage(client);
                Messages.sendMotdMessage(client);
            }
            case "MOTD" -> {
                Messages.sendMotdMessage(client);
            }
            case "OPER" -> {
            }
            case "SERVICE" -> {
            }
            case "QUIT" -> {
                // Get quit message from user
                String quitMessage = ircMessage.raw.split(":")[1];

                /* broadcast their quit message to server
                 * 0 - user
                 * 1 - irc hostname
                 * 2 - quitMessage
                 */
                String formattedMessage = MessageFormat.format(
                        ":{0} QUIT :{1}\r\n",
                        client.identifier(), // todo: is it nickname or username
                        quitMessage
                );
                server.broadcastMessage(formattedMessage);

                // disconnect the user
                server.clientManager.removeClient(client);
            }
            case "SQUIT" -> {
            }
            case "JOIN" -> {
                // parse channels
                String after = ircMessage.afterMessageType();

                // parse keys
                String[] parts = after.split(" ");

                String[] channelsStr = null;
                String[] keysStr = null;

                channelsStr = parts[0].split(",");

                // TODO: Clean this up
                // optional key for join chan
                if (parts.length >= 2)
                    keysStr = parts[1].split(",");

                if (channelsStr == null) return;

                for (String channelName : channelsStr) {
                    client.attemptJoinChannelByName(channelName);
                }


            }
            case "PART" -> {
            }
            case "MODE" -> {
            }
            case "TOPIC" -> {
            }
            case "NAMES" -> {
            }
            case "LIST" -> {
                Messages.sendListMessage(client);
            }
            case "INVITE" -> {
            }
            case "KICK" -> {
            }
            case "PRIVMSG" -> {
            }
            case "NOTICE" -> {
            }
            case "LUSERS" -> {
            }
            case "VERSION" -> {
            }
            case "STATS" -> {
            }
            case "LINKS" -> {
            }
            case "TIME" -> {
            }
            case "CONNECT" -> {
            }
            case "TRACE" -> {
            }
            case "ADMIN" -> {
            }
            case "INFO" -> {
            }
            case "SERVLIST" -> {
            }
            case "SQUERY" -> {
            }

            // 3.6 User based queries
            case "WHO" -> {
            }
            case "WHOIS" -> {
            }
            case "WHOWAS" -> {
            }

            // 3.7 Misc. messages
            case "KILL" -> {
            }
            case "PING" -> {
                String after = ircMessage.afterMessageType();
                Messages.sendPongMessage(client, after);
            }
            case "PONG" -> {
//                Messages.sendPongMessage(client); // todo figure this out
            }
            case "ERROR" -> {
            }

            // 4 Optional features
            case "AWAY" -> {
            }
            case "REHASH" -> {
            }

            default -> {
            }
        }
    }
}


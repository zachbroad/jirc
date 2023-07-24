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
        001    RPL_WELCOME
        "Welcome to the Internet Relay Network
        <nick>!<user>@<host>"
     */
    public static void sendWelcomeMessage(IrcClient client) {
        server.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :Welcome to the Internet Relay Network {2}!{3}@{0}\r\n",
                        server.IRC_HOSTNAME,
                        Numerics.RPL_WELCOME,
                        client.nickname,
                        client.username
                ),
                client
        );

        server.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :Your host is {0}, running version {3}\r\n",
                        server.IRC_HOSTNAME,
                        Numerics.RPL_YOURHOST,
                        client.nickname,
                        server.VERSION
                ),
                client
        );

        server.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :This server was created {3}\r\n",
                        server.IRC_HOSTNAME,
                        Numerics.RPL_CREATED,
                        client.nickname,
                        server.dateTimeCreated.toString()
                ),
                client
        );

        server.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :{3} v1 NULL NULL\r\n", // todo
                        server.IRC_HOSTNAME,
                        Numerics.RPL_MYINFO,
                        client.nickname,
                        server.dateTimeCreated.toString(),
                        server.name
                ),
                client
        );
    }

    public static void sendMotdMessage(IrcClient client) {
        server.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :- {3} Message of the day - \r\n",
                        server.IRC_HOSTNAME,
                        Numerics.RPL_MOTDSTART,
                        client.nickname,
                        server.name
                ),
                client
        );
        server.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :- {3}\r\n",
                        server.IRC_HOSTNAME,
                        Numerics.RPL_MOTD,
                        client.nickname,
                        server.motd
                ),
                client
        );
        server.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :End of MOTD command\r\n",
                        server.IRC_HOSTNAME,
                        Numerics.RPL_MOTDEND,
                        client.nickname,
                        server.motd
                ),
                client
        );
    }

    public static void sendPongMessage(IrcClient client) {
        server.sendMessage(
                MessageFormat.format(
                        "PONG {0}",
                        server.IRC_HOSTNAME
                ),
                client
        );
    }


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
                client.nickname = partsOfMessage.poll();
                IrcServer.logger.info(MessageFormat.format("Client {0} sent name '{1}'", client.toString(), client.nickname));
            }
            case "USER" -> {
                IrcServer.logger.info(MessageFormat.format("Client {0} wants to register", client.toString()));
                Messages.sendWelcomeMessage(client);
                Messages.sendMotdMessage(client);
            }
            case "MOTD" -> {
                Messages.sendMotdMessage(client);
            }
            case "PING" -> {
                Messages.sendPongMessage(client);
            }

            default -> {
            }
        }

        // TODO
//        switch (ircMessage.type) {
//            case CAP -> {
//
//            }
//            case INFO -> {
//
//            }
//            case NICK -> {
//            }
//            case USER -> {
//
//            }
//        }
    }


}


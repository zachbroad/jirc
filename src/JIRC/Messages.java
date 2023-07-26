package JIRC;

import JIRC.MessageTypes.*;

import java.text.MessageFormat;



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
    private static IrcServer server = IrcServer.instance;


    /*
     */


    /**
     * Send PING to client
     *
     * @param client recipient of message
     */
    public static void sendPongMessage(IrcClient client, String identifier) { // TODO: FIX ?
        server.sendMessageToClient(MessageFormat.format(":{0} PING {1}\r\n", server.getPrefix(), identifier), client);
        server.sendMessageToClient(MessageFormat.format(":{0} PONG {1}\r\n", server.getPrefix(), identifier), client);
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

        if (ircMessage.getMessageType() == null) return;


        switch (ircMessage.getMessageType().toUpperCase()) {
            case "CAP" -> {
                IrcServer.logger.info("TODO: CAP");
            }
            case "EXIT" -> {
                new ExitMessage(ircMessage, client).handle();
            }
            case "NICK" -> {
                new NickMessage(ircMessage, client).handle();
            }
            case "USER" -> {
                new UserMessage(ircMessage, client).handle();
                new MotdMessage(ircMessage, client).handle();
            }
            case "MOTD" -> {
                new MotdMessage(ircMessage, client).handle();
            }
            case "OPER" -> {
            }
            case "SERVICE" -> {
            }
            case "QUIT" -> {
                new QuitMessage(ircMessage, client).handle();
            }
            case "SQUIT" -> {
            }
            case "JOIN" -> {
                new JoinMessage(ircMessage, client).handle();
            }
            case "PART" -> {
                new PartMessage(ircMessage, client).handle();
            }
            case "MODE" -> {}
            case "TOPIC" -> {}
            case "NAMES" -> {}
            case "LIST" -> {
                new ListMessage(ircMessage, client).handle();
            }
            case "INVITE" -> {}
            case "KICK" -> {}
            case "PRIVMSG" -> {
                new PrivMsgMessage(ircMessage, client).handle();
            }
            case "NOTICE" -> {}
            case "LUSERS" -> {}
            case "VERSION" -> {}
            case "STATS" -> {}
            case "LINKS" -> {}
            case "TIME" -> {}
            case "CONNECT" -> {}
            case "TRACE" -> {}
            case "ADMIN" -> {}
            case "INFO" -> {}
            case "SERVLIST" -> {}
            case "SQUERY" -> {}

            // 3.6 User based queries
            case "WHO" -> {
                new WhoMessage(ircMessage, client).handle();
            }
            case "WHOIS" -> {}
            case "WHOWAS" -> {}

            // 3.7 Misc. messages
            case "KILL" -> {}
            case "PING" -> {
                new PingMessage(ircMessage, client).handle();
            }
            case "PONG" -> {
//                JIRC.Messages.sendPongMessage(client); // todo figure this out
            }
            case "ERROR" -> {}
            // 4 Optional features
            case "AWAY" -> {}
            case "REHASH" -> {}
            default -> {}
        }
    }
}


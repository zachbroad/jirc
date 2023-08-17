package JIRC;

import JIRC.MessageTypes.*;

import java.util.HashMap;


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


public class IrcMessageProcessor {

    /**
     * Parse message & handle it
     * TODO: move message cases to their own files
     *
     * @param ircMessage incoming message to process
     * @param client     recipient of message
     */
    public static void processMessage(IrcMessage ircMessage, IrcClient client) {
        // Process all messages

        if (ircMessage.getCommand() == null) return;


        switch (ircMessage.getCommand().toUpperCase()) {
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
                new OperMessage(ircMessage, client).handle();
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
            case "MODE" -> {
                new ModeMessage(ircMessage, client).handle();
            }
            case "TOPIC" -> {
                new TopicMessage(ircMessage, client).handle();
            }
            case "NAMES" -> {
                new NamesMessage(ircMessage, client).handle();
            }
            case "LIST" -> {
                new ListMessage(ircMessage, client).handle();
            }
            case "INVITE" -> {}
            case "KICK" -> {
                new KickMessage(ircMessage, client).handle();
            }
            case "PRIVMSG" -> {
                new PrivMsgMessage(ircMessage, client).handle();
            }
            case "NOTICE" -> {
                new NoticeMessage(ircMessage, client).handle();
            }
            case "LUSERS" -> {}
            case "VERSION" -> {
                new VersionMessage(ircMessage, client).handle();
            }
            case "STATS" -> {}
            case "LINKS" -> {}
            case "TIME" -> {
                new TimeMessage(ircMessage, client).handle();
            }
            case "CONNECT" -> {}
            case "TRACE" -> {}
            case "ADMIN" -> {}
            case "INFO" -> {
                new InfoMessage(ircMessage, client).handle();
            }
            case "SERVLIST" -> {}
            case "SQUERY" -> {}

            // 3.6 User based queries
            case "WHO" -> {
                new WhoMessage(ircMessage, client).handle();
            }
            case "WHOIS" -> {
                new WhoIsMessage(ircMessage, client).handle();
            }
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
            case "AWAY" -> {
                new AwayMessage(ircMessage, client).handle();
            }
            case "ISON" -> {
                new IsonMessage(ircMessage, client).handle();
            }
            case "RESTART" -> {
                new RestartMessage(ircMessage, client).handle();
            }
            case "DIE" -> {
                new DieMessage(ircMessage, client).handle();
            }
            case "BACK" -> {
                new AwayMessage(new IrcMessage("BACK"), client).handle();
            }
            case "REHASH" -> {}
            default -> {}
        }
    }
}


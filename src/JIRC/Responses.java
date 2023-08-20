package JIRC;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class Responses {

    /**
     * 001 RPL_WELCOME
     * "Welcome to the Internet Relay Network <nick>!<user>@<host>"
     */
    public static void sendWelcomeMessage(IrcClient client) {
        client.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} :Welcome to the Internet Relay Network {2}!{3}@{0}\r\n",
                        IrcServer.instance.IRC_HOSTNAME, // 0
                        Numerics.RPL_YOURHOST, // 1
                        client.getNickname(), // 2
                        client.getUsername() // 3
                )
        );
    }

    /**
     * 002 RPL_YOURHOST
     * "Your host is <servername>, running version <ver>"
     */
    public static void sendVersionMessage(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :Your host is {0}, running version {3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_YOURHOST, // 1
                client.getNickname(), // 2
                IrcServer.instance.VERSION // 3
        ));
    }

    /**
     * 003 RPL_CREATED
     * "This server was created <date>"
     */
    public static void sendCreatedMessage(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :This server was created {3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_CREATED, // 1
                client.getNickname(), // 2
                IrcServer.instance.getDateTimeCreated() // 3
        ));
    }

    /**
     * 004 RPL_MYINFO
     * "<servername> <version> <available user modes> <available channel modes>"
     */
    public static void sendMyInfoMessage(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :{3} v1 NULL NULL\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MYINFO, // 1
                client.getNickname(), // 2
                IrcServer.instance.getDateTimeCreated(), // 3
                IrcServer.instance.serverName // 4
        ));
    }

    // TODO: TEST

    /**
     * 301 RPL_AWAY
     * "<nick> :<away message>"
     */
    public static void sendAwayMessage(IrcClient client, IrcClient awayClient) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :{4}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_AWAY, // 1
                client.getNickname(), // 2
                awayClient.getNickname(),
                awayClient.getAwayMessage()
        ));
    }

    /**
     * RPL_ISON
     * ":*1<nick> *( " " <nick> )"
     * - Reply format used by ISON to list replies to the
     * query list.
     */
    public static void sendIsonMessage(IrcClient client, String usersThatAreOn) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :{3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_ISON, // 1
                client.getNickname(), // 2
                usersThatAreOn
        ));
    }

    /**
     * 305 RPL_UNAWAY
     * ":You are no longer marked as being away"
     */
    public static void sendUnawayMessage(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :You are no longer marked as being away\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_UNAWAY, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 306 RPL_NOWAWAY
     * "You have been marked as being away"
     */
    public static void sendNowAwayMessage(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :You have been marked as being away\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_NOWAWAY, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 311 RPL_WHOISUSER
     * "<nick> <user> <host> * :<real name>"
     */
    public static void sendWhoIsUser(IrcClient client, IrcClient whoIsClient) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} {4} {5} * :{6}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_WHOISUSER, // 1
                client.getNickname(), // 2
                whoIsClient.getNickname(), // 3
                whoIsClient.getUsername(), // 4
                whoIsClient.getIpAddress(), // 5
                whoIsClient.getRealname() // 6
        ));
    }

    /**
     * 312 RPL_WHOISSERVER
     * "<nick> <server> :<server info>"
     */
    public static void sendWhoIsServer(IrcClient client, IrcClient whoIsClient) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} {4} :{5}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_WHOISSERVER, // 1
                client.getNickname(), // 2
                whoIsClient.getNickname(), // 3
                IrcServer.instance.IRC_HOSTNAME, // 4
                IrcServer.instance.getServerInfo() // 5
        ));
    }

    /**
     * 313 RPL_WHOISOPERATOR
     * "<nick> :is an IRC operator"
     */
    public static void sendWhoIsOperator(IrcClient client, IrcClient whoIsClient) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :is an IRC operator\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_WHOISOPERATOR, // 1
                client.getNickname(), // 2
                whoIsClient.getNickname() // 3
        ));
    }

    /**
     * 314    RPL_WHOWASUSER
     *               "<nick> <user> <host> * :<real name>"
     */

    /**
     * 313 RPL_WHOISIDLE
     * "<nick> :is an IRC operator"
     */
    public static void sendWhoIsIdle(IrcClient client, IrcClient whoIsClient) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} TODO :seconds idle\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_WHOISIDLE, // 1
                client.getNickname(), // 2
                client.getSecondsIdle() // 3
        ));
    }


    /**
     * 318 RPL_ENDOFWHOIS
     * "<nick> :End of WHOIS list"
     */
    public static void sendEndOfWhoIs(IrcClient client, IrcClient whoIsClient) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :End of WHOIS list\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_ENDOFWHOIS, // 1
                client.getNickname(), // 2
                whoIsClient.getNickname() // 3
        ));
    }

    /**
     * 331 RPL_NOTOPIC
     * "<channel> :No topic is set"
     */
    public static void sendNoTopicMessage(IrcClient client, IrcChannel channel) {
        String topicMsgPre = ":{0} {1} {2} {3} :No topic is set\r\n";
        String topicMsgPost = MessageFormat.format(
                topicMsgPre,
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_NOTOPIC, // 1
                client.getNickname(), // 2
                channel.getName() // 3
        );
        client.sendMessage(topicMsgPost);
    }

    /**
     * 332 RPL_TOPIC
     * "<channel> :<topic>"
     */
    public static void sendTopicMessage(IrcClient client, IrcChannel channel) {
        String topicMsgPre = ":{0} {1} {2} {3} :{4}\r\n";
        String topicMsgPost = MessageFormat.format(
                topicMsgPre,
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_TOPIC, // 1
                client.getNickname(), // 2
                channel.getName(), // 3
                channel.getTopic() // 4
        );
        client.sendMessage(topicMsgPost);
    }

    /**
     * 353 RPL_ENDOFNAMES
     * "<channel> :End of NAMES list"
     */
    public static void sendNameReply(IrcClient client, IrcChannel channel) {
        String namesInChanSepBySpace = channel.getClients().stream()
                .map(c -> c.getNamePrefixChannel(channel))
                .collect(Collectors.joining(" "));

        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :{4}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_NAMREPLY, // 1
                client.getNickname(), // 2
                channel.getNameWithType(), // 3
                namesInChanSepBySpace // 4
        ));
    }

    /**
     * 366 RPL_ENDOFNAMES
     * "<channel> :End of NAMES list"
     */
    public static void sendEndOfNames(IrcClient client, IrcChannel channel) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :End of NAMES list\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_ENDOFNAMES, // 1
                client.getNickname(), // 2
                channel.getName() // 3
        ));
    }

    /**
     * 369 RPL_ENDOFWHOWAS
     * "<nick> :End of WHOWAS"
     */
    public static void sendEndOfWhoWas(IrcClient client, IrcClient whoWasClient) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :End of WHOWAS\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_ENDOFWHOWAS, // 1
                client.getNickname(), // 2
                whoWasClient.getNickname() // 3
        ));
    }

    /**
     * 371 RPL_INFO
     * ":<string>"
     */
    public static void sendInfo(IrcClient client, String info) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :{3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_INFO, // 1
                client.getNickname(), // 2
                info // 3
        ));
    }

    /**
     * 374 RPL_ENDOFINFO
     * ":End of INFO list"
     * =====================================================
     * A server responding to an INFO message is required to
     * send all its 'info' in a series of RPL_INFO messages
     * with a RPL_ENDOFINFO reply to indicate the end of the
     * replies.
     */
    public static void sendEndOfInfo(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :End of INFO list\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_INFO, // 1
                client.getNickname() // 2
        ));
    }


    /**
     * 375 RPL_MOTDSTART
     * 372 RPL_MOTD
     * 376 RPL_ENDOFMOTD
     */
    public static void sendMotdMessage(IrcClient client) {
        // RPL_MOTDSTART
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :- {3} Message of the day - \r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDSTART, // 1
                client.getNickname(), // 2
                IrcServer.instance.serverName // 3
        ));

        // RPL_MOTD
        for (String motdLine : IrcServer.instance.getMotd()) {
            client.sendMessage(MessageFormat.format(
                    ":{0} {1} {2} :- {3}\r\n",
                    IrcServer.instance.IRC_HOSTNAME, // 0
                    Numerics.RPL_MOTD, // 1
                    client.getNickname(), // 2
                    motdLine // 3
            ));
        }

        // RPL_ENDOFMOTD
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :End of MOTD command\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDEND, // 1
                client.getNickname(), // 2
                IrcServer.instance.getMotd() // 3
        ));
    }

    /**
     * 381 RPL_YOUREOPER
     * ":You are not an IRC operator"
     */
    public static void sendOperYoureOper(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :You are now an IRC operator\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_YOUREOPER, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 391    RPL_TIME
     * "<server> :<string showing server's local time>"
     * <p>
     * - When replying to the TIME message, a server MUST send
     * the reply using the RPL_TIME format above.  The string
     * showing the time need only contain the correct day and
     * time there.  There is no further requirement for the
     * time string.
     */
    public static void sendTimeMessage(IrcClient client) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {3} {0} :{2}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_TIME, // 1
                dateTimeFormatter.format(LocalDateTime.now()), // 2
                client.getNickname() // 3
        ));
    }

    /**
     * 392 RPL_USERSSTART
     * ":UserID   Terminal  Host"
     */
    public static void sendUsersStart(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :UserID Terminal Host\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_USERSSTART, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 393 RPL_USERS
     * ":<username> <ttyline> <hostname>"
     */
    public static void sendUsers(IrcClient client) {
        // TODO: no users...? would this be for "registered" users vs unregistered? or to comm. with other servers?
        IrcServer.instance.clientManager.getClients().forEach(c -> {
            client.sendMessage(MessageFormat.format(
                    ":{0} {1} {2} :{3} {4} {5}\r\n",
                    IrcServer.instance.IRC_HOSTNAME, // 0
                    Numerics.RPL_USERS, // 1
                    client.getNickname(), // 2
                    c.getUsername(),
                    "N/A", // ttyline? todo:?!?!
                    c.getIpAddress()
            ));
        });
    }

    /**
     * 392 RPL_ENDOFUSERS
     * ":End of users"
     */
    public static void sendEndOfUsers(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :End of users\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_ENDOFUSERS, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 392 RPL_NOUSERS
     * ":Nobody logged in"
     */
    public static void sendNoUsers(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :Nobody logged in\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_NOUSERS, // 1
                client.getNickname() // 2
        ));
    }


    /****************************************************************************
     ***************************** ERRORS ***************************************
     ****************************************************************************/

    /**
     * 401 ERR_NOSUCHNICK
     * "<nickname> :No such nick/channel"
     */
    public static void errorNoSuchNick(IrcClient client, String nickname) {
        String message = MessageFormat.format(
                ":{0} {1} {2} {3}:No such nickname\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NOSUCHNICK, // 1
                client.getNickname(), // 2
                nickname // 3
        );
        client.sendMessage(message);
    }

    /**
     * 403 ERR_NOSUCHCHANNEL
     * "<channel name> :No such channel"
     */
    public static void errorNoSuchChannel(IrcClient client, String channelName) {
        String message = MessageFormat.format(
                ":{0} {1} {2} {3} :No such channel\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NOSUCHCHANNEL, // 1
                client.getNickname(), // 2
                channelName // 3
        );
        client.sendMessage(message);
    }

    /**
     * 431 ERR_NONICKNAMEGIVEN
     * ":No nickname given"
     */
    public static void errorNoNicknameGiven(IrcClient client) {
        String message = MessageFormat.format(
                ":{0} {1} {2} :No nickname given\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NOSUCHCHANNEL, // 1
                client.getNickname() // 2
        );
        client.sendMessage(message);
    }

    /**
     * 441 ERR_USERNOTINCHANNEL
     * "<nick> <channel> :They aren't on that channel"
     * Returned by the server to indicate that the target
     * user of the command is not on the given channel.
     */
    public static void errorUserNotInChannel(IrcClient client, String targetName, String channelName) {
        String message = MessageFormat.format(
                ":{0} {1} {2} {3} {4} :They aren''t on that channel\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NOTONCHANNEL, // 1
                client.getNickname(), // 2
                channelName, // 3
                targetName // 4
        );
        client.sendMessage(message);
    }

    /**
     * 442 ERR_NOTONCHANNEL
     * "<channel name> :You're not on that channel"
     * Returned by the server whenever a client tries to
     * perform a channel affecting command for which the
     * client isn't a member.
     */
    public static void errorNotOnChannel(IrcClient client, String channelName) {
        String postFormat = MessageFormat.format(
                ":{0} {1} {2} :You''re not on that channel\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NOTONCHANNEL, // 1
                channelName // 2
        );
        client.sendMessage(postFormat);
    }

    /**
     * 443 ERR_USERONCHANNEL
     * "<user> <channel> :is already on channel"
     */
    public static void errorUserOnChannel(IrcClient client, String username, String channelName) {
        String postFormat = MessageFormat.format(
                ":{0} {1} {2} {3} {4} :is already on channel\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_USERONCHANNEL, // 1
                client.getNickname(), // 2
                username, // 3
                channelName // 4
        );
        client.sendMessage(postFormat);
    }

    /**
     * 461 ERR_NEEDMOREPARAMS
     * "<command> :Not enough parameters"
     */
    public static void errorNeedMoreParams(IrcClient client, String command) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :Not enough parameters\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NEEDMOREPARAMS, // 1
                client.getNickname(), // 2
                command // 3
        ));
    }


    /**
     * 464 ERR_PASSWDMISMATCH
     * ":Password incorrect"
     */
    public static void errorPasswordMismatch(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :Password incorrect\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_PASSWDMISMATCH, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 476 ERR_BADCHANMASK
     * "<channel> :Bad Channel Mask"
     */
    public static void errorBadChanMask(IrcClient client, String channelBadMask) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :Bad Channel Mask\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_BADCHANMASK, // 1
                client.getNickname(), // 2
                channelBadMask // 3
        ));
    }

    /**
     * 481    ERR_NOPRIVILEGES
     * ":Permission Denied- You're not an IRC operator"
     * <p>
     * - Any command requiring operator privileges to operate
     * MUST return this error to indicate the attempt was
     * unsuccessful.
     */
    public static void errorNoPrivileges(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :Permission Denied- You''re not an IRC operator\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NOPRIVILEGES, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 482    ERR_CHANOPRIVSNEEDED
     * "<channel> :You're not channel operator"
     * <p>
     * - Any command requiring 'chanop' privileges (such as
     * MODE messages) MUST return this error if the client
     * making the attempt is not a chanop on the specified
     * channel.
     */
    public static void errorChanoPrivsNeeded(IrcClient client, String channel) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :You''re not channel operator\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_CHANOPRIVSNEEDED, // 1
                client.getNickname(), // 2
                channel // 3
        ));
    }

    /**
     * 483    ERR_CANTKILLSERVER
     * ":You can't kill a server!"
     * <p>
     * - Any attempts to use the KILL command on a server
     * are to be refused and this error returned directly
     * to the client.
     */
    public static void errorCantKillServer(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :You can''t kill a server!\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_CANTKILLSERVER, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 484    ERR_RESTRICTED
     * ":Your connection is restricted!"
     * <p>
     * - Sent by the server to a user upon connection to indicate
     * the restricted nature of the connection (user mode "+r").
     */
    public static void errorRestricted(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :Your connection is restricted!\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_RESTRICTED, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 485    ERR_UNIQOPPRIVSNEEDED
     * ":You're not the original channel operator"
     * <p>
     * - Any MODE requiring "channel creator" privileges MUST
     * return this error if the client making the attempt is not
     * a chanop on the specified channel.
     */
    public static void errorUniqOpPrivsNeeded(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :You''re not the original channel operator\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_UNIQOPPRIVSNEEDED, // 1
                client.getNickname() // 2
        ));
    }

    /**
     * 491    ERR_NOOPERHOST
     * ":No O-lines for your host"
     * <p>
     * - If a client sends an OPER message and the server has
     * not been configured to allow connections from the
     * client's host as an operator, this error MUST be
     * returned.
     */
    public static void errorNoOperHost(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :No O-lines for your host\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NOOPERHOST, // 1
                client.getNickname() // 2
        ));
    }


    /**        501    ERR_UMODEUNKNOWNFLAG
     *               ":Unknown MODE flag"
     *
     *          - Returned by the server to indicate that a MODE
     *            message was sent with a nickname parameter and that
     *            the a mode flag sent was not recognized.
     */

    /**        502    ERR_USERSDONTMATCH
     *               ":Cannot change mode for other users"
     *
     *          - Error sent to any user trying to view or change the
     *            user mode for a user other than themselves.
     */

}

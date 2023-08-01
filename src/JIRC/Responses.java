package JIRC;

import java.text.MessageFormat;

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
                        client.nickname,
                        client.username
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
                client.nickname, // 2
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
                client.nickname, // 2
                IrcServer.instance.dateTimeCreated.toString() // 3
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
                client.nickname, // 2
                IrcServer.instance.dateTimeCreated.toString(), // 3
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
                client.nickname, // 2
                awayClient.nickname,
                awayClient.awayMessage
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
                Numerics.RPL_AWAY, // 1
                client.nickname // 2
        ));
    }

    /**
     * 306 RPL_NOWAWAY
     * "You have been marked as being away"
     */
    public static void sendAwayMessage(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :You have been marked as being away\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_AWAY, // 1
                client.nickname // 2
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
                IrcServer.instance.IRC_HOSTNAME,
                Numerics.RPL_TOPIC,
                client.nickname,
                channel.getName()
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
                IrcServer.instance.IRC_HOSTNAME,
                Numerics.RPL_TOPIC,
                client.nickname,
                channel.getName(),
                channel.getTopic()
        );
        client.sendMessage(topicMsgPost);
    }


    /**
     * 375 RPL_MOTDSTART
     * 372 RPL_MOTD
     * 376 RPL_ENDOFMOTD
     */
    public static void sendMotdMessage(IrcClient client) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :- {3} Message of the day - \r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDSTART, // 1
                client.nickname, // 2
                IrcServer.instance.serverName // 3
        ));
        for (String motdLine : IrcServer.instance.motd) {
            client.sendMessage(MessageFormat.format(
                    ":{0} {1} {2} :- {3}\r\n",
                    IrcServer.instance.IRC_HOSTNAME, // 0
                    Numerics.RPL_MOTD, // 1
                    client.nickname, // 2
                    motdLine // 3
            ));
        }
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :End of MOTD command\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDEND, // 1
                client.nickname, // 2
                IrcServer.instance.motd // 3
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
                client.nickname // 2
        ));
    }


    /****************************************************************************
     ***************************** ERRORS ***************************************
     ****************************************************************************/

    /**
     * 403 ERR_NOSUCHCHANNEL
     * "<channel name> :No such channel"
     */
    public static void errorNoSuchChannel(IrcClient client, String channelName) {
        String postFormat = MessageFormat.format(
                ":{0} {1} {2} :No such channel\r\n",
                IrcServer.instance.IRC_HOSTNAME,
                Numerics.ERR_NOSUCHCHANNEL,
                channelName
        );
        client.sendMessage(postFormat);
    }

    /**
     * 441 ERR_USERNOTINCHANNEL
     * "<nick> <channel> :They aren't on that channel"
     * Returned by the server to indicate that the target
     * user of the command is not on the given channel.
     */
    public static void errorUserNotInChannel(IrcClient client, String targetName, String channelName) {
        String postFormat = MessageFormat.format(
                ":{0} {1} {2} {3} {4} :They aren''t on that channel\r\n",
                IrcServer.instance.IRC_HOSTNAME,
                Numerics.ERR_NOTONCHANNEL,
                client.nickname,
                channelName,
                targetName
        );
        client.sendMessage(postFormat);
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
                IrcServer.instance.IRC_HOSTNAME,
                Numerics.ERR_NOTONCHANNEL,
                channelName
        );
        client.sendMessage(postFormat);
    }

    /**
     * 461 ERR_NEEDMOREPARAMS
     * ec<command> :Not enough parameters"
     */
    public static void errorNeedMoreParams(IrcClient client, String command) {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} {3} :Not enough parameters\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.ERR_NEEDMOREPARAMS, // 1
                client.nickname, // 2
                command
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
                client.nickname // 2
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
                client.nickname, // 2
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
                client.nickname // 2
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
                client.nickname, // 2
                channel
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
                client.nickname // 2
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
                client.nickname // 2
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
                client.nickname // 2
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
                client.nickname // 2
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

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
                IrcServer.instance.name // 4
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
                channel.name
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
                channel.name,
                channel.topic
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
                IrcServer.instance.name // 3
        ));
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :- {3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTD, // 1
                client.nickname, // 2
                IrcServer.instance.motd // 3
        ));
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :End of MOTD command\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDEND, // 1
                client.nickname, // 2
                IrcServer.instance.motd // 3
        ));
    }

    /****************************************************************************
     ***************************** ERRORS ***************************************
     ****************************************************************************/
    public static void errorNoSuchMessage(IrcClient client, String channelName) {
        String preFormat = ":{0} {1} {2} :No such channel\r\n";
        String postFormat = MessageFormat.format(preFormat, IrcServer.instance.IRC_HOSTNAME, Numerics.ERR_NOSUCHCHANNEL, channelName);
        client.sendMessage(postFormat);
    }


}

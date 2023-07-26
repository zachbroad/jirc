package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;


public class UserMessage extends BaseMessage {

    public UserMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }


    /**
     * 001 RPL_WELCOME
     * "Welcome to the Internet Relay Network
     * <nick>!<user>@<host>"
     *
     * @param client recipient of message
     */
    private static void sendWelcomeMessage(IrcClient client) {
        IrcServer.instance.sendMessageToClient(MessageFormat.format(
                        ":{0} {1} {2} :Welcome to the Internet Relay Network {2}!{3}@{0}\r\n",
                        IrcServer.instance.IRC_HOSTNAME, // 0
                        Numerics.RPL_YOURHOST, // 1
                        client.nickname,
                        client.username
                ), client
        );

        IrcServer.instance.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :Your host is {0}, running version {3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_YOURHOST, // 1
                client.nickname, // 2
                IrcServer.instance.VERSION // 3
        ), client);

        IrcServer.instance.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :This IrcServer.instance was created {3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_CREATED, // 1
                client.nickname, // 2
                IrcServer.instance.dateTimeCreated.toString() // 3
        ), client);

        // TODO fix NULL
        IrcServer.instance.sendMessageToClient(MessageFormat.format(
                ":{0} {1} {2} :{3} v1 NULL NULL\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MYINFO, // 1
                client.nickname, // 2
                IrcServer.instance.dateTimeCreated.toString(), // 3
                IrcServer.instance.name // 4
        ), client);
    }

    @Override
    public void handle() {
        IrcServer.logger.info(MessageFormat.format("Client {0} wants to register", client.toString()));
        String username = message.afterMessageType().split(" ")[0];
        String realname = message.raw.split(":")[1];
        IrcServer.logger.info(MessageFormat.format("Got username {0}", username));
        IrcServer.logger.info(MessageFormat.format("Got realname {0}", realname));
        client.username = username;
        client.realname = realname;

        sendWelcomeMessage(client);
    }
}
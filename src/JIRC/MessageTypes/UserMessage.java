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
        Responses.sendWelcomeMessage(client);
        Responses.sendVersionMessage(client);
        Responses.sendCreatedMessage(client);
        Responses.sendMyInfoMessage(client);
    }

    @Override
    public void handle() {
        IrcServer.logger.info(MessageFormat.format("Client {0} wants to register", client.toString()));
        String username = message.getParams().get(0);
        String realname = message.getParams().get(3);
        IrcServer.logger.info(MessageFormat.format("Got username {0}", username));
        IrcServer.logger.info(MessageFormat.format("Got realname {0}", realname));
        client.username = username;
        client.realname = realname;

        sendWelcomeMessage(client);
    }
}
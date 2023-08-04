package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;


public class UserMessage extends BaseMessage {

    public UserMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    public String getUsername() {
        return message.getParams().get(0);
    }

    public String getRealname() {
        return message.getParams().get(3);
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
        IrcServer.logger.info(MessageFormat.format("Client {0} wants to register", sender.toString()));
        IrcServer.logger.info(MessageFormat.format("Got username {0}", getUsername()));
        IrcServer.logger.info(MessageFormat.format("Got realname {0}", getRealname()));
        sender.setUsername(getUsername());
        sender.setRealname(getRealname());

        sendWelcomeMessage(sender);
    }
}
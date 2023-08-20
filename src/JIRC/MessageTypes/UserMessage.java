package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;


public class UserMessage extends BaseMessage {

    public UserMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
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

    public String getUsername() {
        return message.getParams().get(0);
    }

    public String getRealname() {
        return message.getParams().get(3);
    }

    @Override
    public void handle() {
        IrcServer.logger.info(MessageFormat.format("Client {0} wants to register", sender.toString()));
        IrcServer.logger.info(MessageFormat.format("Got username {0}", getUsername()));
        IrcServer.logger.info(MessageFormat.format("Got realname {0}", getRealname()));
        // TODO: Implement mode
        /*
           The <mode> parameter should be a numeric, and can be used to
           automatically set user modes when registering with the server.  This
           parameter is a bitmask, with only 2 bits having any signification: if
           the bit 2 is set, the user mode 'w' will be set and if the bit 3 is
           set, the user mode 'i' will be set.  (See Section 3.1.5 "User
           Modes").
         */
        sender.setUsername(getUsername());
        sender.setRealname(getRealname());

        sendWelcomeMessage(sender);
    }
}
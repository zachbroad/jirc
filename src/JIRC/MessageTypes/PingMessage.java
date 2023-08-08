package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

import java.text.MessageFormat;

public class PingMessage extends BaseMessage {
    private String identifier;

    public PingMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        identifier = message.getParams().size() > 0 ? message.getParams().get(0) : null;

    }

    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public void handle() {
//        IrcServer.logger.info("Sending PING message %s".formatted(getIdentifier()));

//        server.sendMessageToClient(MessageFormat.format(":{0} PING {1}\r\n", server.IRC_HOSTNAME, getIdentifier()), sender);
        server.sendMessageToClient(MessageFormat.format(":{0} PONG {0} :{1}\r\n", server.IRC_HOSTNAME, identifier), sender);
    }
}

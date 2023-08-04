package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

import java.text.MessageFormat;

public class PingMessage extends BaseMessage {
    public PingMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    public String getIdentifier() {
        return message.getParams().get(0);
    }

    @Override
    public void handle() {
//        IrcServer.logger.info("Sending PING message %s".formatted(getIdentifier()));

        server.sendMessageToClient(MessageFormat.format(":{0} PING {1}\r\n", server.IRC_HOSTNAME, getIdentifier()), sender);
        server.sendMessageToClient(MessageFormat.format(":{0} PONG {1}\r\n", server.IRC_HOSTNAME, getIdentifier()), sender);
    }
}

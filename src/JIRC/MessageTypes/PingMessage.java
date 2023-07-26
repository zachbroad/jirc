package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;
import JIRC.Messages;

import java.text.MessageFormat;

public class PingMessage extends BaseMessage {
    public PingMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    String getIdentifier() {
        return message.afterMessageType();
    }

    @Override
    public void handle() {
        IrcServer.logger.info("Sending PING message %s".formatted(getIdentifier()));

        server.sendMessageToClient(MessageFormat.format(":{0} PING {1}\r\n", server.getPrefix(), getIdentifier()), client);
        server.sendMessageToClient(MessageFormat.format(":{0} PONG {1}\r\n", server.getPrefix(), getIdentifier()), client);
    }
}

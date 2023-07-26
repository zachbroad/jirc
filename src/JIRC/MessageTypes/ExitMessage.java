package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class ExitMessage extends BaseMessage {

    public ExitMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        IrcServer.logger.info(MessageFormat.format("Client {0} disconnected", client.toString()));
        IrcServer.instance.clientManager.removeClient(client);
    }

}

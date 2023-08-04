package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class ExitMessage extends BaseMessage {

    public ExitMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    @Override
    public void handle() {
        IrcServer.logger.info(MessageFormat.format("Client {0} disconnected", sender.toString()));
        IrcServer.instance.clientManager.removeClient(sender);
    }

}

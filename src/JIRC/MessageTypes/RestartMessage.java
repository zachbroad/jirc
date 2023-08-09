package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

public class RestartMessage extends BaseMessage {

    public RestartMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    @Override
    public void handle() {
        if (!sender.isOperator()) {
            Responses.errorNoPrivileges(sender);
            return;
        }

        // TODO: send message to all clients that server is restarting
        server.restartServer();
    }
}

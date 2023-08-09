package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;
import JIRC.Responses;

public class DieMessage extends BaseMessage {
    public DieMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    @Override
    public void handle() {
        if (!sender.isOperator()) {
            Responses.errorNoPrivileges(sender);
            return;
        }

        IrcServer.logger.info("User %s is shutting down the server.".formatted(sender));

        // TODO: Send message to clients that server is shutting down
        server.shutdown();
    }
}

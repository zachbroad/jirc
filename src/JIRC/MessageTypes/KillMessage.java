package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

public class KillMessage extends BaseMessage {
    private String nickname;
    private String comment;

    public KillMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        nickname = message.getParams().size() > 0 ? message.getParams().get(0) : null;
        comment = message.getParams().size() > 1 ? message.getParams().get(1) : null;
    }

    private boolean isValid() {
        return this.nickname != null;
    }

    private void doKick() {

//        client.
    }

    @Override
    public void handle() {
        if (!sender.isOperator()) {
            Responses.errorNoPrivileges(sender);
        }

        if (!isValid()) {
            Responses.errorNeedMoreParams(sender, "KILL");
        }


        // Send message to client being kicked
        // Send message to every client that is connected with them // TODO: HOW EFFICIENTLY

        /*
         * Get every channel client connected to
         * Then get every Client
         * Then remove duplicates
         */

        doKick();
    }
}

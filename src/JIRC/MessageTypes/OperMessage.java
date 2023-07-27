package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

public class OperMessage extends BaseMessage {

    public OperMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    public String getUsername() {
        return message.getParams().get(0);
    }

    public String getPassword() {
        return message.getParams().get(1);
    }

    @Override
    public void handle() {

    }
}

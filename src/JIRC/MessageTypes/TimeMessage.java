package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

public class TimeMessage extends BaseMessage {
    public TimeMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    @Override
    public void handle() {
        Responses.sendTimeMessage(sender);
    }
}

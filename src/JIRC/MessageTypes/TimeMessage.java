package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

public class TimeMessage extends BaseMessage {
    public TimeMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        Responses.sendTimeMessage(client);
    }
}

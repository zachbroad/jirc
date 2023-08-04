package JIRC.MessageTypes;

import JIRC.*;

public class MotdMessage extends BaseMessage {
    public MotdMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    @Override
    public void handle() {
        Responses.sendMotdMessage(sender);
    }
}

package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

public class MotdMessage extends BaseMessage {
    public MotdMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        Responses.sendMotdMessage(client);
    }
}

package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

public class VersionMessage extends BaseMessage {
    public VersionMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    @Override
    public void handle() {
        Responses.sendVersionMessage(sender);
    }
}

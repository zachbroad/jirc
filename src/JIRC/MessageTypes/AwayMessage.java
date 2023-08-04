package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

public class AwayMessage extends BaseMessage {

    private String awayMessage;

    public AwayMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        this.awayMessage = message.getParams().size() > 0 ? message.getParams().get(0) : null;
    }


    @Override
    public void handle() {
        // if no away message, we are going to set away false
        if (awayMessage == null) {
            sender.setAway(false);
            sender.setAwayMessage(null);
            Responses.sendUnawayMessage(sender);
            return;
        }

        sender.setAway(true);
        sender.setAwayMessage(awayMessage);
        Responses.sendNowAwayMessage(sender);
    }
}

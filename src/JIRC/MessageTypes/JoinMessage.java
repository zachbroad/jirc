package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

import java.util.List;

public class JoinMessage extends BaseMessage {

    public JoinMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    public List<String> getChannels() {
        return List.of(message.getParams().get(0).split(","));
    }

    @Override
    public void handle() {
        for (String channelName : getChannels()) {
            sender.attemptJoinChannelByName(channelName);
        }
    }
}

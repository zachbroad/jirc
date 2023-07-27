package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.util.List;

public class JoinMessage extends BaseMessage {

    public JoinMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    public List<String> getChannels() {
        return List.of(message.getParams().get(0).split(","));
    }

    @Override
    public void handle() {
        for (String channelName : getChannels()) {
            client.attemptJoinChannelByName(channelName);
        }
    }
}

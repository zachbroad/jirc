package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.util.List;

// TODO: channel key

public class JoinMessage extends BaseMessage {

    List<String> channels;

    public JoinMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    public List<String> getChannels() {
        return List.of(message.getParams().get(0).split(","));
    }

    @Override
    public void handle() {
        for (String channelName : getChannels()) {
            if (sender.attemptJoinChannelByName(channelName)) {
                new WhoMessage(new IrcMessage("WHO %s".formatted(channelName)), sender).handle();
            }
        }

        IrcServer.logger.info("%s joined %d channels".formatted(sender.getMask(), getChannels().size()));
    }
}

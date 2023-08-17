package JIRC.MessageTypes;

import JIRC.IrcChannel;
import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.util.List;

// TODO: channel key

public class JoinMessage extends BaseMessage {

    public JoinMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    public List<String> getChannels() {
        return List.of(message.getParams().get(0).split(","));
    }

    @Override
    public void handle() {
        int successfulJoinCount = 0;

        for (String channelName : getChannels()) {
            if (!server.channelManager.doesChannelExist(channelName)) {
                IrcServer.logger.info("Channel %s does not exist, %s is creating...".formatted(channelName, sender.toString()));
                if (server.channelManager.createChannelByName(channelName)) {
                    IrcServer.logger.info("Channel %s created.".formatted(channelName));
                    if (sender.attemptJoinChannelByName(channelName)) {
                        new NamesMessage(new IrcMessage("NAMES %s".formatted(channelName)), sender).handle();
                        new WhoMessage(new IrcMessage("WHO %s".formatted(channelName)), sender).handle();
                        successfulJoinCount += 1;
                    } else {
                        IrcServer.logger.severe("%s failed to join channel %s they just created!".formatted(sender, channelName));
                    }
                } else {
                    IrcServer.logger.warning("Channel %s failed to create.".formatted(channelName));
                    // TODO: Failed to create channel ? why? was the name too long?
                }
            } else {
                IrcServer.logger.info("%s wants to join %s".formatted(sender.toString(), channelName));
                if (sender.attemptJoinChannelByName(channelName)) {
                    new NamesMessage(new IrcMessage("NAMES %s".formatted(channelName)), sender).handle();
                    new WhoMessage(new IrcMessage("WHO %s".formatted(channelName)), sender).handle();
                    IrcServer.logger.warning("%s joined %s.".formatted(sender, channelName));
                    successfulJoinCount += 1;
                } else {
                    // TODO: Failed to join channel handle?
                }
            }

        }

        IrcServer.logger.info("%s joined %d channels".formatted(sender.getMask(), getChannels().size()));
    }
}

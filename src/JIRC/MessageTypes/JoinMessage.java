package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

public class JoinMessage extends BaseMessage {

    public JoinMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        // parse channels
        String[] parts = message.afterMessageType().split(" ");

        String[] channelsStr = null;
        // parse keys TODO
        String[] keysStr = null;

        channelsStr = parts[0].split(",");

        // TODO: Clean this up
        // optional key for join chan
        if (parts.length >= 2)
            keysStr = parts[1].split(",");

        if (channelsStr == null) {
            IrcServer.logger.info("Couldn't find channel user wants to join");
            return;
        }

        for (String channelName : channelsStr) {
            client.attemptJoinChannelByName(channelName);
        }

    }


}

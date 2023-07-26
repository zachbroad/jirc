package JIRC.MessageTypes;

import JIRC.IrcChannel;
import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class PartMessage extends BaseMessage {
    public PartMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        // Remove user from channel
        String channelStr = message.afterMessageType().split(" ")[0];
        IrcChannel channel = IrcServer.instance.channelManager.getChannelByName(channelStr);

        if (channel == null) {
            IrcServer.logger.info("User [%s] PART failed - can't find channel by name %s".formatted(client.toString(), channel.toString()));
            return;
        }

        client.leaveChannel(channel);

        /*
         * 0 - client identifier
         * 1 - channel they are leaving
         * 2 - leave message
         */
        String preFormat = ":{0} PART {1} :{2}\r\n";
        String msg = message.afterMessageType().substring(message.afterMessageType().indexOf(":"));
        String postFormat = MessageFormat.format(preFormat, client.getPrefix(), channelStr, msg);
        IrcServer.instance.broadcastMessage(postFormat);
    }
}

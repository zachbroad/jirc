package JIRC.MessageTypes;

import JIRC.IrcChannel;
import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class PartMessage extends BaseMessage {
    public PartMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }


    public String getChannel() {
        return message.getParams().get(0);
    }

    public String getLeaveMessage() {
        return message.getParams().get(1);
    }


    @Override
    public void handle() {
        // Get channel obj from name
        IrcChannel channel = IrcServer.instance.channelManager.getChannelByName(getChannel());

        if (channel == null) {
            IrcServer.logger.info("User [%s] PART failed - can't find channel by name %s".formatted(sender.toString(), channel.toString()));
            return;
        }

        sender.leaveChannel(channel);

        /*
         * 0 - client identifier
         * 1 - channel they are leaving
         * 2 - leave message
         */
        String preFormat = ":{0} PART {1} :{2}\r\n";
        String postFormat = MessageFormat.format(preFormat, sender.getMask(), getChannel(), getLeaveMessage());
        channel.sendMessageToClients(postFormat);
        IrcServer.instance.broadcastMessage(postFormat);
    }
}

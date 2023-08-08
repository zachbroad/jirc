package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;
import JIRC.Numerics;

import java.text.MessageFormat;

public class ModeMessage extends BaseMessage {

    String channel;

    public ModeMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        channel = message.getParams().size() > 0 ? message.getParams().get(0) : null;
    }

    @Override
    public void handle() {
        /*
          0 - server hostname
          1 - RPL_CHANNELMODEIS
          2 - sender username
          3 - channel
          4 -  mask
         */
        sender.sendMessage(MessageFormat.format(":{0} {1} {2} {3} +nt\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_CHANNELMODEIS, // 1
                sender.getUsername(), // 2
                channel // 3
        ));

    }
}

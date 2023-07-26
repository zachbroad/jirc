package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

public class WhoMessage extends BaseMessage {

    public WhoMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        String channel = message.afterMessageType();
        IrcChannel channelObj = server.channelManager.getChannelByName(channel);

        if (channelObj == null) {
            client.sendMessage(
                    MessageFormat.format(
                            ":{0} {1}\r\n",
                            server.getPrefix(),
                            Numerics.ERR_NOSUCHSERVER
                    )
            );
            return;
        }

        Messages.sendWhoMessage(client, channelObj);
    }
}

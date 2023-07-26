package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

import java.text.MessageFormat;

public class PrivMsgMessage extends BaseMessage {
    public PrivMsgMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        String preFormat = ":{0} PRIVMSG {1} {2}\r\n";
        String channel = message.afterMessageType().split(" ")[0];
        String msg = message.afterMessageType().substring(message.afterMessageType().indexOf(":"));
        String postFormat = MessageFormat.format(preFormat, client.getPrefix(), channel, msg);
        server.broadcastMessageFromClient(postFormat, client);
    }
}

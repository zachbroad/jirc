package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

import java.text.MessageFormat;

public class PrivMsgMessage extends BaseMessage {
    public PrivMsgMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    String getMessage() {
        return message.getTrailer();
    }

    @Override
    public void handle() {
        String preFormat = ":{0} PRIVMSG {1} {2}\r\n";
        String channel = message.afterMessageType().split(" ")[0];
        String postFormat = MessageFormat.format(preFormat, client.getPrefix(), channel, getMessage());
        server.broadcastMessageFromClient(postFormat, client);
    }
}

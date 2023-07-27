package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

import java.text.MessageFormat;

public class PrivMsgMessage extends BaseMessage {
    public PrivMsgMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    String getMessage() {
        return message.getParams().get(1);
    }

    String getChannel() {
        return message.getParams().get(0);
    }

    @Override
    public void handle() {
        String preFormat = ":{0} PRIVMSG {1} {2}\r\n";
        String postFormat = MessageFormat.format(preFormat, client.getPrefix(), getChannel(), getMessage());
        server.broadcastMessageFromClient(postFormat, client);
    }
}

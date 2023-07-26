package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class NickMessage extends BaseMessage {

    public NickMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    @Override
    public void handle() {
        String oldName = client.nickname; // store old
        client.nickname = message.afterMessageType(); // set new
        if (oldName != null) {
            IrcServer.instance.broadcastMessage(MessageFormat.format(
                    ":{0} NICK {2}\r\n",
                    oldName,
                    IrcServer.instance.IRC_HOSTNAME,
                    client.nickname
            ));
        } else {
            IrcServer.instance.broadcastMessage(MessageFormat.format(
                    ":{0} NICK {2}\r\n",
                    oldName,
                    IrcServer.instance.IRC_HOSTNAME,
                    client.nickname
            ));
        }

        IrcServer.logger.info("Client %s sent name %s".formatted(client.toString(), client.nickname));
    }
}

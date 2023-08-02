package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class NickMessage extends BaseMessage {

    public NickMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    public String getNickname() {
        return message.getParams().get(0);
    }

    @Override
    public void handle() {
        String oldName = client.getNickname(); // store old
        client.setNickname(getNickname());
        IrcServer.instance.broadcastMessage(MessageFormat.format(
                ":{0} NICK {2}\r\n",
                oldName,
                IrcServer.instance.IRC_HOSTNAME,
                client.getNickname()
        ));

        IrcServer.logger.info("Client %s sent nickname: %s".formatted(client.toString(), client.getNickname()));
    }
}

package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Numerics;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class MotdMessage extends BaseMessage {
    public MotdMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    /**
     * 372    RPL_MOTD
     * ":- <text>"     * @param client
     * 376    RPL_ENDOFMOTD
     * ":End of MOTD command"
     */
    @Override
    public void handle() {
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :- {3} Message of the day - \r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDSTART, // 1
                client.nickname, // 2
                IrcServer.instance.name // 3
        ));
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :- {3}\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTD, // 1
                client.nickname, // 2
                IrcServer.instance.motd // 3
        ));
        client.sendMessage(MessageFormat.format(
                ":{0} {1} {2} :End of MOTD command\r\n",
                IrcServer.instance.IRC_HOSTNAME, // 0
                Numerics.RPL_MOTDEND, // 1
                client.nickname, // 2
                IrcServer.instance.motd // 3
        ));
    }
}

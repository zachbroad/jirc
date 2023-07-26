package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

public class WhoMessage extends BaseMessage {

    public WhoMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    /**
     * :server 352 <user> <channel> <user> <host> <server> <nick> <H|G>[*][@|+] :<hopcount> <real name>
     */
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

        for (var c : channelObj.clients) {
            client.sendMessage(
                    MessageFormat.format(
                            ":{0} {1} {2} {3} {4} {5} {6} H :0 {7}\r\n",
                            server.IRC_HOSTNAME, // 0
                            Numerics.RPL_WHOREPLY, // 1
                            client.username, // 2
                            channelObj.name, // 3
                            c.username, //4
                            c.ipAddress, //5
                            c.nickname,// 6
                            c.username
                    )
            );
        }
        server.sendMessageToClient(
                MessageFormat.format(
                        ":{0} {1} {2} :End of WHO list\r\n",
                        server.IRC_HOSTNAME, // 0
                        Numerics.RPL_ENDOFWHO, // 1
                        client.nickname // 2
                ), client
        );
    }
}

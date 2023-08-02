package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

public class WhoMessage extends BaseMessage {

    public WhoMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    public String getChannel() {
        return message.getParams().size() > 0 ? message.getParams().get(0) : null;
    }

    /**
     * :server 352 <user> <channel> <user> <host> <server> <nick> <H|G>[*][@|+] :<hopcount> <real name>
     */
    @Override
    public void handle() {
        IrcChannel channelObj = server.channelManager.getChannelByName(getChannel());

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

        for (var c : channelObj.getClients()) {
            client.sendMessage(
                    MessageFormat.format(
                            ":{0} {1} {2} {3} {4} {5} {6} H :0 {7}\r\n",
                            server.IRC_HOSTNAME, // 0
                            Numerics.RPL_WHOREPLY, // 1
                            client.getUsername(), // 2
                            channelObj.getName(), // 3
                            c.getUsername(), //4
                            c.getIpAddress(), //5
                            c.getNickname(),// 6
                            c.getUsername()
                    )
            );
        }
        server.sendMessageToClient(
                MessageFormat.format(
                        ":{0} {1} {2} :End of WHO list\r\n",
                        server.IRC_HOSTNAME, // 0
                        Numerics.RPL_ENDOFWHO, // 1
                        client.getNickname() // 2
                ), client
        );
    }
}

package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

/*
 * TODO: o mask for "/WHO #chan o" to find operators
 * TODO: +i mask for invisible users
 */
public class WhoMessage extends BaseMessage {

    private String channelName;
    private IrcChannel channel;

    public WhoMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        channelName = message.getParams().size() > 0 ? message.getParams().get(0) : null;
        channel = server.channelManager.getChannelByName(channelName);
    }

    public String getChannelName() {
        return channelName;
    }

    /**
     * :server 352 <user> <channel> <user> <host> <server> <nick> <H|G>[*][@|+] :<hopcount> <real name>
     */
    @Override
    public void handle() {
        if (channelName == null) {
            Responses.errorNeedMoreParams(sender, "WHO");
            return;
        }

        if (channel == null) {
            sender.sendMessage(
                    MessageFormat.format(
                            ":{0} {1}\r\n",
                            server.getPrefix(),
                            Numerics.ERR_NOSUCHSERVER
                    )
            );
            IrcServer.logger.warning("channelObj was null %s".formatted(channelName));
            return;
        }

        for (var c : channel.getClients()) {
            sender.sendMessage(
                    MessageFormat.format(
                            ":{0} {1} {2} {3} {4} {5} {6} H :0 {7}\r\n",
                            server.IRC_HOSTNAME, // 0
                            Numerics.RPL_WHOREPLY, // 1
                            sender.getNickname(), // 2
                            channel.getName(), // 3
                            c.getUsername(), //4
                            c.getIpAddress(), //5
                            c.getNickname(),// 6
                            c.getRealname()
                    )
            );
        }
        server.sendMessageToClient(
                MessageFormat.format(
                        ":{0} {1} {2} {3} :End of /WHO list\r\n",
                        server.IRC_HOSTNAME, // 0
                        Numerics.RPL_ENDOFWHO, // 1
                        sender.getNickname(), // 2
                        channelName
                ), sender
        );
    }
}

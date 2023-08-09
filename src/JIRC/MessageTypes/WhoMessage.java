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
            IrcServer.logger.warning("channelName is null");
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


        IrcServer.logger.info("Sending WHO list");
        for (var c : channel.getClients()) {
            IrcServer.logger.info("Client %s".formatted(c.toString()));
            String hereOrGone = "";
            if (c.isAway()) {
                hereOrGone = hereOrGone.concat("G");
            } else {
                hereOrGone = hereOrGone.concat("H");
            }

            String privs = "";
            if (c.isOperator()) {
                privs = privs.concat("@");
            } else if (c.hasVoiceInChannel(channel)){
                privs = privs.concat("+");
            } else {
                continue;
            }

            sender.sendMessage(
                    MessageFormat.format(
                            ":{0} {1} {2} {3} {4} {5} {6} {7} {9} {10} :0 {8}\r\n",
                            server.IRC_HOSTNAME, // 0
                            Numerics.RPL_WHOREPLY, // 1
                            sender.getNickname(), // 2
                            channel.getName(), // 3
                            c.getUsername(), //4
                            c.getIpAddress(), //5
                            server.IRC_HOSTNAME, // 6
                            c.getNickname(),// 7
                            c.getRealname(), // 8
                            hereOrGone, // 9
                            privs
                    )
            );
        }
        sender.sendMessage(
                MessageFormat.format(
                        ":{0} {1} {2} {3} :End of /WHO list\r\n",
                        server.IRC_HOSTNAME, // 0
                        Numerics.RPL_ENDOFWHO, // 1
                        sender.getNickname(), // 2
                        channelName
                )
        );
    }
}

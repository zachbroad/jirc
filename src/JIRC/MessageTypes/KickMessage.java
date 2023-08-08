package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

public class KickMessage extends BaseMessage {

    public KickMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    public String getChannel() {
        return message.getParams().size() > 0 ? message.getParams().get(0) : null;
    }

    private IrcChannel getChannelObj() {
        if (getChannel() == null) return null;

        return IrcServer.instance.channelManager.getChannelByName(getChannel());
    }

    public String getUser() {
        return message.getParams().size() > 1 ? message.getParams().get(1) : null;
    }

    private IrcClient getClientObj() {
        if (getUser() == null) return null;

        return IrcServer.instance.clientManager.getClientByNickname(getUser());
    }

    public String getMessage() {
        return message.getParams().size() > 2 ? message.getParams().get(2) : null;
    }

    private boolean hasEnoughParams() {
        return getChannel() != null || getUser() != null;
    }

    @Override
    public void handle() {
        if (!sender.isOperator()) {
            Responses.errorChanoPrivsNeeded(sender, getChannel());
            return;
        }

        if (!hasEnoughParams()) {
            Responses.errorNeedMoreParams(sender, "KICK");
            return;
        }

        IrcChannel channel = getChannelObj();
        IrcClient clientToKick = getClientObj();

        /* First, let's make sure the channel exists, otherwise ERR_BADCHANMASK
         * The sender needs to be in the channel, otherwise ERR_NOTONCHANNEL
         * Then, let's make sure the channel has the user, otherwise ERR_USERNOTINCHANNEL
         */


        if (channel == null) {
            Responses.errorBadChanMask(sender, getChannel());
            return;
        }

        if (!channel.hasClient(sender)) {
            Responses.errorNotOnChannel(sender, getChannel());
        }

        if (clientToKick == null) {
            Responses.errorUserNotInChannel(sender, getUser(), getChannel());
            return;
        }

        if (channel.hasClient(clientToKick)) {
            if (getMessage() != null) {
                channel.sendMessageToClients(
                        MessageFormat.format(
                                ":{0} KICK {1} {2} :{3}\r\n",
                                sender.getMask(), // 0
                                getChannel(), // 1
                                clientToKick.getNickname(), // 2
                                getMessage() // 3
                        )
                );
            } else {
                channel.sendMessageToClients(
                        MessageFormat.format(
                                ":{0} KICK {1} {2}\r\n",
                                sender.getMask(), // 0
                                getChannel(), // 1
                                clientToKick.getNickname() // 2
                        )
                );
            }
            sender.leaveChannel(channel);
        } else {
            Responses.errorUserNotInChannel(sender, getUser(), getChannel());
        }


        // Does channel have user?

        // If yes, remove them and broadcast kick message on channel
        // :WiZ!jto@tolsun.oulu.fi KICK #Finnish John


    }
}

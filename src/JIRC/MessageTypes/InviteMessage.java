package JIRC.MessageTypes;

import JIRC.*;

public class InviteMessage extends BaseMessage {

    private String nickname;
    private String channelName;
    private IrcChannel channel;
    private IrcClient invitedClient;

    public InviteMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        nickname = message.getParams().size() > 0 ? message.getParams().get(0) : null;
        invitedClient = IrcServer.instance.clientManager.getClientByNickname(nickname);
        channelName = message.getParams().size() > 1 ? message.getParams().get(1) : null;
        channel = IrcServer.instance.channelManager.getChannelByName(channelName);
    }

    private boolean hasEnoughParams() {
        return nickname != null && channelName != null;
    }

    @Override
    public void handle() {
        // Didn't get enough params ERR_NEED
        if (!hasEnoughParams()) {
            Responses.errorNeedMoreParams(sender, "INVITE");
            return;
        }

        // Is sender OP, if not, ERR_CHANOPRIVSNEEDED
        if (sender.isOperator()) { // TODO: Channel OP
            Responses.errorChanoPrivsNeeded(sender, channelName);
            return;
        }

        // Does user exist? if not ERR_NOSUCHNICK
        if (invitedClient == null) {
            Responses.errorNoSuchNick(sender, nickname);
            return;
        }


        // If the channel doesn't exist, we don't have to be on the channel
        if (channel == null) {

            return;
        } else {
            // Is sender on channel? If not, ERR_NOTONCHANNEL
            if (!channel.hasClient(sender)) {
                Responses.errorNotOnChannel(sender, channelName);
                return;
            }

            // User already in channel ERR_USERONCHANNEL
            if (channel.hasClient(IrcServer.instance.clientManager.getClientByNickname(nickname))) {
                Responses.errorUserOnChannel(sender, nickname, channelName);
                return;
            }
        }


        // Ok, sender is on the channel and target is also in channel


        // Is User away?
    }
}

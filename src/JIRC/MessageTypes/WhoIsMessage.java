package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;
import JIRC.Responses;

public class WhoIsMessage extends BaseMessage {
    public IrcClient whoIsClient;
    private String serverStr;
    private String nicknameStr;


    public WhoIsMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        serverStr = message.getParams().size() > 0 ? message.getParams().get(0) : null;
        nicknameStr = message.getParams().size() > 1 ? message.getParams().get(1) : null;

        // If we only got one param, we set nickname to the first param (which in other cases would be the target)
        if (nicknameStr == null) {
            nicknameStr = serverStr;
            serverStr = null;
        }

        if (nicknameStr != null) {
            whoIsClient = IrcServer.instance.clientManager.getClientByNickname(getNickname());
        }
    }

    private boolean doesNameExist() {
        return whoIsClient != null;
    }

    private boolean doesTargetExist() {
        return false;
    }

    private boolean wasNameGiven() {
        return getNickname() != null;
    }

    private boolean wasTargetGiven() {
        return getTarget() != null;
    }

    public String getTarget() {
        return this.serverStr;
    }

    public String getNickname() {
        return this.nicknameStr;
    }

    @Override
    public void handle() {
        // ERR_NOSUCHSERVER

        // ERR_NONICKNAMEGIVEN 431
        if (!wasNameGiven()) {
            Responses.errorNoNicknameGiven(sender);
            return;
        }

        // ERR_NOSUCHNICK 401
        if (!doesNameExist()) {
            Responses.errorNoSuchNick(sender, getNickname());
            return;
        }


        // ok --> reply 311-313, 317-319

//        whoIsClient.getChannels();

        // RPL_WHOISUSER
        Responses.sendWhoIsUser(sender, whoIsClient);

        // RPL_WHOISOPERATOR
        if (whoIsClient.isOperator()) {
            Responses.sendWhoIsOperator(sender, whoIsClient);
        }


        // RPL_WHOISSERVER

//         RPL_WHOISIDLE
        // RPL_AWAY
        if (whoIsClient.isAway()) {
            Responses.sendWhoIsIdle(sender, whoIsClient);
        }

        // RPL_WHOISCHANNELS may appear more than once and @ and + are operator & whether have permission to speak

        // RPL_ENDOFWHOIS
        Responses.sendEndOfWhoIs(sender, whoIsClient);
    }
}

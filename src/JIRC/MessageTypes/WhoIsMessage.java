package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

public class WhoIsMessage extends BaseMessage {
    private String target;
    private String nickname;

    public WhoIsMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        target = message.getParams().size() > 0 ? message.getParams().get(0) : null;
        nickname = message.getParams().size() > 1 ? message.getParams().get(1) : null;

        // If we only got one param, we set nickname to the first param (which in other cases would be the target)
        if (nickname == null) {
            nickname = target;
            target = null;
        }
    }

    private boolean doesNameExist() {
        return getClientObj() != null;
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
        return this.target;
    }

    public String getNickname() {
        return this.nickname;
    }

    public IrcClient getClientObj() {
        return IrcServer.instance.clientManager.getClientByNickname(getNickname());
    }

    @Override
    public void handle() {
        if (!wasNameGiven()) {
            // ERR_NONICKNAMEGIVEN 431
            return;
        }

        if (!doesNameExist()) {
            // ERR_NOSUCHNICK 401
        }

    }
}

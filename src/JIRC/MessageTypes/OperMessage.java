package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcOperator;
import JIRC.Responses;

public class OperMessage extends BaseMessage {

    public OperMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    public String getUsername() {
        return message.getParams().size() >= 1 ? message.getParams().get(0) : null;
    }

    public String getPassword() {
        return message.getParams().size() >= 2 ? message.getParams().get(1) : null;
    }

    @Override
    public void handle() {
        /*
         *  ERR_NEEDMOREPARAMS              RPL_YOUREOPER
         *  ERR_NOOPERHOST                  ERR_PASSWDMISMATCH
         */

        if (getUsername() == null || getPassword() == null) {
            Responses.errorNeedMoreParams(sender, "OPER");
        }

        if (IrcOperator.isValidUsername(getUsername())) {
            if (IrcOperator.tryLogin(getUsername(), getPassword())) {
                sender.giveOperatorPerms();
                Responses.sendOperYoureOper(sender);
            } else {
                Responses.errorPasswordMismatch(sender);
            }
        } else {
            Responses.errorPasswordMismatch(sender);
        }
    }
}

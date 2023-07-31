package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcOperator;
import JIRC.Responses;

public class OperMessage extends BaseMessage {

    public OperMessage(IrcMessage message, IrcClient client) {
        super(message, client);
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
            Responses.errorNeedMoreParams(client, "OPER");
        }

        if (IrcOperator.isValidUsername(getUsername())) {
            if (IrcOperator.tryLogin(getUsername(), getPassword())) {
                client.giveOperatorPerms();
                Responses.sendOperYoureOper(client);
            } else {
                Responses.errorPasswordMismatch(client);
            }
        } else {
            Responses.errorPasswordMismatch(client);
        }
    }
}

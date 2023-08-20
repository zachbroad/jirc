package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

public class UsersMessage extends BaseMessage {

    private String target;

    public UsersMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        target = message.getParams().size() > 0 ? message.getParams().get(0) : null;
    }

    @Override
    public void handle() {

        // TODO request a list of users logged in on a server example.org
        Responses.sendUsersStart(sender);
        Responses.sendUsers(sender);
        Responses.sendEndOfUsers(sender);

    }
}

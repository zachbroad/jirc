package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

public abstract class BaseMessage {

    IrcMessage message = null;
    IrcClient sender = null;
    static IrcServer server = IrcServer.instance;

    public BaseMessage(IrcMessage message, IrcClient sender) {
        this.message = message;
        this.sender = sender;
    }

    public abstract void handle();
}
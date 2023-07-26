package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

public abstract class BaseMessage {

    IrcMessage message = null;
    IrcClient client = null;
    static IrcServer server = IrcServer.instance;

    public BaseMessage(IrcMessage message, IrcClient client) {
        this.message = message;
        this.client = client;
    }

    public abstract void handle();
}
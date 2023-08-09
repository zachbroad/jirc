package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.Responses;

import java.time.LocalDateTime;

public class InfoMessage extends BaseMessage {
    public InfoMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    @Override
    public void handle() {
        Responses.sendInfo(sender, "Server Hostname: " + server.IRC_HOSTNAME);
        Responses.sendInfo(sender, "Server Version: " + server.VERSION);
        Responses.sendInfo(sender, "Running Since: " + server.getRuntime());
        Responses.sendInfo(sender, "Current Time: " + LocalDateTime.now());
        Responses.sendInfo(sender, "Clients Connected: " + server.clientManager.howManyConnectedClients());
        Responses.sendEndOfInfo(sender);
    }
}

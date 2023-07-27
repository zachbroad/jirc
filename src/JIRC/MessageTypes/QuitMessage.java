package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;

import java.text.MessageFormat;

public class QuitMessage extends BaseMessage {

    public QuitMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    String getQuitMessage() {
        return message.getParams().get(1);
    }

    @Override
    public void handle() {
        // Get quit message from user

        /* broadcast their quit message to server
         * 0 - user
         * 1 - irc hostname
         * 2 - quitMessage
         */
        String formattedMessage = MessageFormat.format(
                ":{0} QUIT :{1}\r\n",
                client.getPrefix(), // todo: is it nickname or username
                getQuitMessage()
        );
        IrcServer.instance.broadcastMessage(formattedMessage);

        // disconnect the user
        IrcServer.instance.clientManager.removeClient(client);
    }
}

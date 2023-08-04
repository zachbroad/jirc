package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

public class ListMessage extends BaseMessage {

    public ListMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
    }

    /**
     * 322    RPL_LIST
     * "<channel> <# visible> :<topic>"
     * 323    RPL_LISTEND
     * ":End of LIST"
     *
     * @param client recipient of message
     */
    @Override
    public void handle() {
        //TODO: HANDLE ERR_TOOMANYMATCHES
        for (IrcChannel channel : IrcServer.instance.channelManager.channels) {
            System.out.println("h");
            IrcServer.instance.sendMessageToClient(
                    MessageFormat.format(
                            ":{0} {1} {2} {3} {4} :{5}\r\n",
                            IrcServer.instance.IRC_HOSTNAME, // 0
                            Numerics.RPL_LIST, // 1
                            sender.getNickname(), // 2
                            channel.getName(), // 3
                            channel.getClients().size(), // 4
                            channel.getTopic() // 5
                    ), sender
            );
        }

        IrcServer.instance.sendMessageToClient(
                MessageFormat.format(
                        ":{0} {1} {2} :End of LIST\r\n",
                        IrcServer.instance.IRC_HOSTNAME, // 0
                        Numerics.RPL_LISTEND, // 1
                        sender.getNickname() // 2
                ), sender
        );
    }
}

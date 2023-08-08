package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

public class PrivMsgMessage extends BaseMessage {

    private final String userMessage;
    private final String target;

    public PrivMsgMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        this.userMessage = message.getParams().size() > 1 ? message.getParams().get(1) : null;
        this.target = message.getParams().size() > 0 ? message.getParams().get(0) : null;
    }


    @Override
    public void handle() {
        String preFormat = ":{0} PRIVMSG {1} {2}\r\n";
        String postFormat = MessageFormat.format(preFormat, sender.getMask(), target, userMessage);

        if (IrcChannel.isValidChannelName(target)) {
            // send to channel
            IrcChannel targetChannel;
            targetChannel = IrcServer.instance.channelManager.getChannelByName(target);
            targetChannel.sendMessageToClients(postFormat, sender);
        } else {
            // send to user
            IrcClient targetClient;
            targetClient = IrcServer.instance.clientManager.getClientByNickname(target); // TODO: get by more than nickname? username?
            targetClient.sendMessage(postFormat);

            // Let the sender know that the target is away
            if (targetClient.isAway()) {
                Responses.sendAwayMessage(sender, targetClient);
            }
        }
    }
}

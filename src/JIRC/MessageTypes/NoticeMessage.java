package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;
import JIRC.Numerics;

import java.text.MessageFormat;

public class NoticeMessage extends BaseMessage {
    private String messageTargetNickname;
    private IrcClient target;
    private String text;

    public NoticeMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        messageTargetNickname = message.getParams().size() > 0 ? message.getParams().get(0) : null;
        target = server.clientManager.getClientByNickname(messageTargetNickname);
        text = message.getParams().size() > 1 ? message.getParams().get(1) : null;
    }

    @Override
    public void handle() {
        target.sendMessage(MessageFormat.format(
                ":{0} NOTICE {1} :{2}\r\n",
                sender.getMask(), // 0
                target.getNickname(), // 1
                text // 2
        ));
    }
}

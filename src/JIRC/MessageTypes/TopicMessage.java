package JIRC.MessageTypes;

import JIRC.*;

import java.text.MessageFormat;

/*
3.2.4 Topic message

Command: TOPIC
Parameters: <channel> [ <topic> ]

The TOPIC command is used to change or view the topic of a channel.
The topic for channel <channel> is returned if there is no <topic>
given.  If the <topic> parameter is present, the topic for that
channel will be changed, if this action is allowed for the user
requesting it.  If the <topic> parameter is an empty string, the
topic for that channel will be removed.

Numeric Replies:

        ERR_NEEDMOREPARAMS              ERR_NOTONCHANNEL
        RPL_NOTOPIC                     RPL_TOPIC
        ERR_CHANOPRIVSNEEDED            ERR_NOCHANMODES

Examples:

:WiZ!jto@tolsun.oulu.fi TOPIC #test :New topic ; User Wiz setting the
                                topic.

TOPIC #test :another topic      ; Command to set the topic on #test
                                to "another topic".

TOPIC #test :                   ; Command to clear the topic on
                                #test.

TOPIC #test                     ; Command to check the topic for
                                   #test.
 */

public class TopicMessage extends BaseMessage {

    private final IrcChannel channel;
    private final String topic;
    private final String channelName;

    public TopicMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        topic = message.getParams().size() > 1 ? message.getParams().get(1) : null;
        channelName = message.getParams().size() > 0 ? message.getParams().get(0) : null;
        channel = IrcServer.instance.channelManager.getChannelByName(channelName);
    }

    boolean isCheckingTopic() {
        return topic == null;
    }


    @Override
    public void handle() {
        // set topic
        if (channelName == null) {
            String msg = ":{0} {1} {2} {3} :No such channel\r\n";
            msg = MessageFormat.format(msg, server.IRC_HOSTNAME, Numerics.ERR_NOSUCHCHANNEL, sender.getNickname(), channelName);
            sender.sendMessage(msg);
            return;
        }
        if (isCheckingTopic()) {
            // server RPL_TOPIC channel
            String msg = ":{0} {1} {2} {3} :{4}\r\n";
            msg = MessageFormat.format(msg, server.IRC_HOSTNAME, Numerics.RPL_TOPIC, sender.getNickname(), channel.getName(), channel.getTopic());
            sender.sendMessage(msg);
        } else {
            channel.setTopic(topic);
            String msg = ":{0} TOPIC {1} :{2}\r\n";
            msg = MessageFormat.format(msg, sender.getPrefix(), channelName, topic);
            IrcServer.instance.broadcastMessage(msg);
        }

        // is topic blank / user want to clear?

        // does user want to check topic

        // channel broadcast msg
    }
}

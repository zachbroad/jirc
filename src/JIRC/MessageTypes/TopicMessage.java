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

    public TopicMessage(IrcMessage message, IrcClient client) {
        super(message, client);
    }

    String getChannelStr() {
        return message.getFirstWord();
    }

    IrcChannel getChannel() {
        return IrcServer.instance.channelManager.getChannelByName(getChannelStr());
    }

    String getTopic() {
        return message.getTrailer();
    }

    boolean isCheckingTopic() {
        return !message.raw.contains(":");
    }


    @Override
    public void handle() {
        // set topic
        if (getChannel() == null) {
            String msg = ":{0} {1} {2} {3} :No such channel\r\n";
            msg = MessageFormat.format(msg, server.IRC_HOSTNAME, Numerics.ERR_NOSUCHCHANNEL, client.nickname, getChannelStr());
            client.sendMessage(msg);
            return;
        }
        if (isCheckingTopic()) {
            // server RPL_TOPIC channel
            String msg = ":{0} {1} {2} {3} :{4}\r\n";
            msg = MessageFormat.format(msg, server.IRC_HOSTNAME, Numerics.RPL_TOPIC, client.nickname, getChannel().name, getChannel().topic);
            client.sendMessage(msg);
        }

        // is topic blank / user want to clear?

        // does user want to check topic

        // channel broadcast msg
    }
}

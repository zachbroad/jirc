package JIRC.MessageTypes;

import JIRC.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NamesMessage extends BaseMessage {

    List<String> channelStrs;
    List<IrcChannel> channels;

    public NamesMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);
        channelStrs = new LinkedList<>();
        channels = new LinkedList<>();

        if (message.getParams().size() > 0) {
            channelStrs.addAll(Arrays.asList(message.getParams().get(0).split(",")));
            channelStrs.stream().forEach(c -> {
                IrcChannel temp = server.channelManager.getChannelByName(c);
                if (temp != null) channels.add(temp);
            });
        }
    }

    public boolean anyValidChannels() {
        return channels.size() > 0;
    }

    public boolean wantsToGetAll() {
        return !anyValidChannels() && message.getParams().size() == 0;
    }

    private void nameReplyForChannel(IrcChannel channel) {
        Responses.sendNameReply(sender, channel);
        Responses.sendEndOfNames(sender, channel);
    }

    @Override
    public void handle() {
        if (wantsToGetAll()) {
            // :Users on <channel>: user sep by space
        }

        for (IrcChannel channel : channels) {
            IrcServer.logger.info("%s wants NAMES on %s".formatted(sender.getMask(), channel.toString()));
            nameReplyForChannel(channel);
        }
    }
}

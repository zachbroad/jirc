package JIRC;

import java.util.ArrayList;

public class IrcChannelManager {
    public ArrayList<IrcChannel> channels = new ArrayList<>();

    public IrcChannelManager() {
    }

    /**
     * Adds channel to server's channel list
     *
     * @param channel the JIRC.IrcChannel object we want to add to channel list
     */
    public void addChannel(IrcChannel channel) {
        this.channels.add(channel);
        IrcServer.logger.info(String.format("Created channel [%s]".formatted(channel.toString())));
    }

    /**
     * Deletes channel from server's channel list
     *
     * @param channel the JIRC.IrcChannel object we want to remove
     */
    public void removeChannel(IrcChannel channel) {
        if (this.channels.contains(channel))
            this.channels.remove(channel);
        else
            IrcServer.logger.warning("Attempted to remove channel that doesn't exist.");
    }

    /**
     * Find channel by name. Used for client.attemptJoinChannelByName()
     *
     * @param channelName the name of the channel
     * @return the IRC channel with the name given
     * @see IrcClient
     */
    public IrcChannel getChannelByName(String channelName) {
        for (var c : channels) {
            if (c.name.equalsIgnoreCase(channelName)) {
                return c;
            }
        }

        return null;
    }
}

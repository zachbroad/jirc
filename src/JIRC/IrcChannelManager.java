package JIRC;

import java.util.ArrayList;

public class IrcChannelManager {
    public ArrayList<IrcChannel> channels = new ArrayList<>();

    public IrcChannelManager() {}

    /**
     * Adds channel to server's channel list
     *
     * @param channel the JIRC.IrcChannel object we want to add to channel list
     */
    public void addChannel(IrcChannel channel) {
        this.channels.add(channel);
        IrcServer.logger.info(String.format("Added channel %s to channelManager".formatted(channel.toString())));
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
            if (c.getName().equalsIgnoreCase(channelName)) {
                return c;
            }
        }

        return null;
    }

    /**
     * Checks whether a channel with channelName exists
     *
     * @param channelName name to check
     * @return true if exists, false if not
     */
    public boolean doesChannelExist(String channelName) {
        IrcChannel channel = getChannelByName(channelName);
        return channel != null;
    }

    public boolean createChannelByName(String channelName) {
        // Can't create a channel that already exists!
        if (this.doesChannelExist(channelName)) {
            return false;
        }

        // Will return false if too long or contains invalid characters
        IrcChannel channel = new IrcChannel(channelName);
        if (!channel.isValid()) {
            return false;
        }

        // TODO: does user have permission to create a channel? Everyone can right now, probably keep it that way.
        // ok we can create the channel
        addChannel(channel);
        return true;
    }
}

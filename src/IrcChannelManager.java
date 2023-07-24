import java.util.ArrayList;

public class IrcChannelManager {
    ArrayList<IrcChannel> channels = new ArrayList<>();

    public IrcChannelManager() {
    }

    public void addChannel(IrcChannel channel) {
        this.channels.add(channel);
        IrcServer.logger.info(String.format("Created channel [%s]".formatted(channel.toString())));
    }

    public void removeChannel(IrcChannel channel) {
        if (this.channels.contains(channel))
            this.channels.remove(channel);
        else
            IrcServer.logger.warning("Attempted to remove channel that doesn't exist.");
    }

}

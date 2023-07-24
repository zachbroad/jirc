import java.util.ArrayList;

public class IrcChannel {
    public String name;
    public String topic;
    public ArrayList<IrcClient> clients = new ArrayList<>();

    public IrcChannel() {
        this.name = "Channel";
        this.topic = "Topic";
    }

    public IrcChannel(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }



}

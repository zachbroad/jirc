package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;

public class StatsMessage extends BaseMessage {

    private String query;

    public StatsMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);

        query = message.getParams().size() > 0 ? message.getParams().get(0).toLowerCase() : null;
    }

    @Override
    public void handle() {
        // ERR_NOSUCHSERVER

        switch (query) {
            case "l" -> {
                // l - RPL_STATSLINKINFO
            }
            case "m" -> {
                // m - RPL_STATSCOMMANDS
            }
            case "o" -> {
                // o - RPL_STATSOLINE
            }
            case "u" -> {
                // u - RPL_STATSUPTIME
            }
        }


        // RPL_ENDOFSTATS
    }
}

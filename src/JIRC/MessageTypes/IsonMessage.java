package JIRC.MessageTypes;

import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;
import JIRC.Responses;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class IsonMessage extends BaseMessage {

    List<String> nicknameList = new LinkedList<>();
    List<IrcClient> clientList = new LinkedList<>();
    String onStr = "";

    public IsonMessage(IrcMessage message, IrcClient sender) {
        super(message, sender);

        nicknameList.addAll(message.getParams());
        nicknameList.forEach(name -> {
            IrcClient c = server.clientManager.getClientByNickname(name);
            if (c != null) clientList.add(c);
        });

        onStr = clientList.stream()
                .map(IrcClient::getNickname)
                .collect(Collectors.joining(" "));
    }

    @Override
    public void handle() {
        // ERR_NEEDMOREPARAMS
        if (message.getParams().size() == 0) {
            Responses.errorNeedMoreParams(sender, "ISON");
            return;
        }


        IrcServer.logger.info("Got ISON message for %d users".formatted(clientList.size()));


        // RPL_ISON
        Responses.sendIsonMessage(sender, onStr);
    }
}

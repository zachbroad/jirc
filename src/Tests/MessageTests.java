package Tests;

import static org.junit.jupiter.api.Assertions.*;

import JIRC.IrcChannel;
import JIRC.IrcClient;
import JIRC.IrcMessage;
import JIRC.IrcServer;
import JIRC.MessageTypes.*;

import org.junit.jupiter.api.Test;

public class MessageTests {

    IrcServer server;
    IrcClient client;

    MessageTests() {
        server = new IrcServer();
        client = new IrcClient();
        client.nickname = "zach";
        client.username = "zachy64";
        client.realname = "Zachary Broad";
    }

    // TODO: Figure out how to test this
    @Test
    void testJoinTwice() {
        IrcChannel channel = new IrcChannel("test", "test");
        server.channelManager.addChannel(channel);
    }

    @Test
    void testTopicMsg() {
        IrcMessage message = new IrcMessage("TOPIC #test");
        assertEquals(message.getCommand(), "TOPIC");
        assertEquals(message.getParams().get(0), "#test");
    }

    @Test
    void testJoinServer() {
        // CAP
        IrcMessage capMsgRaw = new IrcMessage("CAP LS 302");
        // TODO: CapMessage

        // NICKNAME
        IrcMessage nickMsgRaw = new IrcMessage("NICK zach");
        NickMessage nickMessage = new NickMessage(nickMsgRaw, client);
        assertEquals(nickMessage.getNickname(), "zach");

        // USER
        IrcMessage userMsgRaw = new IrcMessage("USER zachy64 0 * :Zachary Broad");
        UserMessage userMessage = new UserMessage(userMsgRaw, client);
        assertEquals(userMessage.getRealname(), "Zachary Broad");
        assertEquals(userMessage.getUsername(), "zachy64");
    }

    @Test
    void testListMessage() {
        IrcMessage message = new IrcMessage("LIST");
        ListMessage listMessage = new ListMessage(message, client);
        assertEquals(message.getCommand(), "LIST");
    }

    @Test
    void testMotdMessage() {
        IrcMessage message = new IrcMessage("MOTD");
        MotdMessage motdMessage = new MotdMessage(message, client);
        assertEquals(message.getCommand(), "MOTD");
    }


    @Test
    void testJoinMessage() {
        IrcMessage message = new IrcMessage("JOIN #channel");
        JoinMessage joinMessage = new JoinMessage(message, client);
        assertEquals(joinMessage.getChannels().get(0), "#channel");

        message = new IrcMessage("JOIN #channel,#test");
        joinMessage = new JoinMessage(message, client);
        assertEquals(joinMessage.getChannels().get(0), "#channel");
        assertEquals(joinMessage.getChannels().get(1), "#test");
    }

    @Test
    void testQuitMessage() {
        IrcMessage message = new IrcMessage("QUIT :Goodbye");
        QuitMessage quitMessage = new QuitMessage(message, client);
        assertEquals(message.getParams().get(0), "Goodbye");
        assertEquals(quitMessage.getQuitMessage(), "Goodbye");
    }

    @Test
    void testExitMessage() {
        IrcMessage message = new IrcMessage("EXIT :Exiting");
        ExitMessage exitMessage = new ExitMessage(message, client);
        // Add assertions based on the behavior of ExitMessage
    }

    @Test
    void testPrivMsgMessage() {
        IrcMessage message = new IrcMessage("PRIVMSG #channel :Hello, world!");
        PrivMsgMessage privMsgMessage = new PrivMsgMessage(message, client);
        // Add assertions based on the behavior of PrivMsgMessage
    }

    @Test
    void testPingMessage() {
        IrcMessage message = new IrcMessage("PING :Ping message");
        PingMessage pingMessage = new PingMessage(message, client);
        assertEquals(pingMessage.getIdentifier(), "Ping message");
    }

    @Test
    void testPartMessage() {
        IrcMessage message = new IrcMessage("PART #channel :cya guys");
        PartMessage partMessage = new PartMessage(message, client);
        assertEquals(partMessage.getChannel(), "#channel");
        assertEquals(partMessage.getLeaveMessage(), "cya guys");
    }

    @Test
    void testWhoMessage() {
        IrcMessage message = new IrcMessage("WHO #channel");
        WhoMessage whoMessage = new WhoMessage(message, client);
        assertEquals(whoMessage.getChannel(), "#channel");
    }

}

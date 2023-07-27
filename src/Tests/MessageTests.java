package Tests;

import static org.junit.jupiter.api.Assertions.*;

import JIRC.IrcMessage;
import org.junit.jupiter.api.Test;

public class MessageTests {



    @Test
    void testPrivMsg() {
        IrcMessage message = new IrcMessage("TOPIC #test");
//        assertEquals(message.getFirstWord(), "TOPIC");
//        assertEquals(message.get(), "TOPIC");


    }
}

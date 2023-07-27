package JIRC;/*
 *  2.3.1 Message format in Augmented BNF

 *  The protocol messages must be extracted from the contiguous stream of
 *  octets.  The current solution is to designate two characters, CR and
 *  LF, as message separators.  Empty messages are silently ignored,
 *  which permits use of the sequence CR-LF between messages without
 *  extra problems.

 *  The extracted message is parsed into the components <prefix>,
 *  <command> and list of parameters (<params>).

 *  The Augmented BNF representation for this is:

 *  message    =  [ ":" prefix SPACE ] command [ params ] crlf
 *  prefix     =  servername / ( nickname [ [ "!" user ] "@" host ] )
 *  command    =  1*letter / 3digit
 *  params     =  *14( SPACE middle ) [ SPACE ":" trailing ]
 *             =/ 14( SPACE middle ) [ SPACE [ ":" ] trailing ]

 *  nospcrlfcl =  %x01-09 / %x0B-0C / %x0E-1F / %x21-39 / %x3B-FF
 *                  ; any octet except NUL, CR, LF, " " and ":"
 *  middle     =  nospcrlfcl *( ":" / nospcrlfcl )
 *  trailing   =  *( ":" / " " / nospcrlfcl )

 *  SPACE      =  %x20        ; space character
 *  crlf       =  %x0D %x0A   ; "carriage return" "linefeed"
 */

/*
 * EXAMPLE
 * :IRC@127.0.0.1 301 name :message
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class IrcMessage {
    private String prefix;
    private String command;
    private List<String> params;
    public String raw;

    public IrcMessage(String rawMessage) {
        raw = rawMessage.trim();
        this.params = new ArrayList<>();
        parseMessage(rawMessage.trim());
    }

    private void parseMessage(String rawMessage) {
        String[] parts = rawMessage.split(" :", 2);
        String[] msgParts = parts[0].split(" ");

        int startIndex = 0;

        if (msgParts[0].startsWith(":")) {
            this.prefix = msgParts[0].substring(1);
            startIndex++;
        }

        this.command = msgParts[startIndex++];

        for (int i = startIndex; i < msgParts.length; i++) {
            this.params.add(msgParts[i]);
        }

        if (parts.length > 1) {
            this.params.add(parts[1]);
        }
    }

    // getters
    public String getPrefix() {
        return prefix;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getParams() {
        return params;
    }
}

//public class IrcMessage {
//    public String raw;
//    public IrcMessageType type;
//
//    public IrcMessage(String raw) {
//        this.raw = raw;
//    }
//
//    /**
//     * Gets the part of the message after the first word/part
//     *
//     * @return the part of the message after type (ex: JOIN #channel -> returns #channel)
//     */
//    public String getCommands() {
//        return raw.substring(raw.indexOf(' ') + 1, raw.length());
//    }
//
//    public Queue<String> partsOfMessage() {
//        String[] arrOfMessageParts = this.raw.split(" ");
//
//        Queue<String> partsOfMessage = new ArrayDeque<>();
//        for (var m : arrOfMessageParts) {
//            partsOfMessage.add(m);
//        }
//        return partsOfMessage;
//    }
//
//    public String getMessageType() {
//        return raw.split(" ")[0];
//    }
//
//    public String getFirstWord() {
//        return raw.split(" ")[1];
//    }
//
//    public String getTrailer() {
//        try {
//
//            if (getCommands().contains(":")) {
//                return getCommands().substring(getCommands().indexOf(":"));
//            }
//
//            return getCommands().split(" ")[1];
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "INVALID";
//    }
//}

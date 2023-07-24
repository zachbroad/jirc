


/*
    Numerics in the range from 001 to 099 are used for client-server
    connections only and should never travel between servers.  Replies
    generated in the response to commands are found in the range from 200
    to 399.
 */


public class Numerics {
    // On Join
    static final int RPL_WELCOME = 001; // Sent after register messages... pass message, nickname message, user message
    static final int RPL_YOURHOST = 002;
    static final int RPL_CREATED = 003;
    static final int RPL_MYINFO = 004;

    // List Channels
    static final int RPL_LISTSTART = 321;
    static final int RPL_LIST = 322;
    static final int RPL_LISTEND = 323;

    // Topics
    static final int RPL_NOTOPIC = 331;
    static final int RPL_TOPIC = 332;
    static final int RPL_TOPICWHOTIME = 333;

    // Info
    static final int RPL_INFO = 371;

    // MOTD
    static final int RPL_MOTDSTART = 375;
    static final int RPL_MOTD = 372;
    static final int RPL_MOTDEND = 376;
    static final int RPL_TIME = 391;

    // Errors
    static final int ERR_UNKNOWNERROR = 400;
    static final int ERR_NOSUCHNICK = 401;
    static final int ERR_NOSUCHSERVER = 402;
    static final int ERR_NOSUCHCHANNEL = 403;
    static final int ERR_CANNOTSENDTOCHAN = 404;
}

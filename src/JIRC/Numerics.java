package JIRC;

/**
 * JIRC.Numerics in the range from 001 to 099 are used for client-server
 * connections only and should never travel between servers.  Replies
 * generated in the response to commands are found in the range from 200
 * to 399.
 */
public class Numerics {
    // On Join
    public static final int RPL_WELCOME = 001; // Sent after register messages... pass message, nickname message, user message
    public static final int RPL_YOURHOST = 002;
    public static final int RPL_CREATED = 003;
    public static final int RPL_MYINFO = 004;

    // AWAY
    public static final int RPL_AWAY = 301; // "<nick> :<away message>"

    // List Channels
    public static final int RPL_LISTSTART = 321; // obsolete
    public static final int RPL_LIST = 322;
    public static final int RPL_LISTEND = 323;

    // Topics
    public static final int RPL_NOTOPIC = 331;
    public static final int RPL_TOPIC = 332; // on channel join
    public static final int RPL_TOPICWHOTIME = 333;

    // WHO
    public static final int RPL_WHOREPLY = 352;
    public static final int RPL_ENDOFWHO = 315;


    // Info
    static final int RPL_INFO = 371;

    // MOTD
    public static final int RPL_MOTDSTART = 375;
    public static final int RPL_MOTD = 372;
    public static final int RPL_MOTDEND = 376;

    // OPER
    public static final int RPL_YOUREOPER = 381;

    // MISC
    public static final int RPL_TIME = 391;

    // Errors
    public static final int ERR_UNKNOWNERROR = 400;
    public static final int ERR_NOSUCHNICK = 401;
    public static final int ERR_NOSUCHSERVER = 402;
    public static final int ERR_NOSUCHCHANNEL = 403; // <channel name> :No such channel
    public static final int ERR_CANNOTSENDTOCHAN = 404;
    public static final int ERR_NOTONCHANNEL = 442; // <channel> :You're not on that channel

    public static final int ERR_NEEDMOREPARAMS = 461; //  <command> :Not enough parameters
    public static final int ERR_NOPERMFORHOST = 463; //  :Your host isn't among the privileged
    public static final int ERR_PASSWDMISMATCH = 464; //  :Password incorrect
}

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

    public static final int RPL_UNAWAY = 305; // ":You are no longer marked as being away"
    public static final int RPL_NOWAWAY = 306; // ":You have been marked as being away"

    // WHOIS
    public static final int RPL_WHOISUSER = 311; // "<nick> <user> <host> * :<real name>"
    public static final int RPL_WHOISSERVER = 312; // "<nick> <server> :<server info>"
    public static final int RPL_WHOISOPERATOR = 313; // "<nick> is an IRC operator"
    public static final int RPL_WHOISIDLE = 317; // "<nick> <integer> :seconds idle"
    public static final int RPL_ENDOFWHOIS = 318; // "<nick> :End of WHOIS list"

    // List Channels
    public static final int RPL_LISTSTART = 321; // obsolete
    public static final int RPL_LIST = 322;
    public static final int RPL_LISTEND = 323;
    public static final int RPL_CHANNELMODEIS = 324; // "<channel> <mode> <mode params>"

    // INVITE / SUMMON
    public static final int RPL_INVITING = 341; // "<channel> <nick>"
    public static final int RPL_SUMMONING = 342; // "<user> :Summoning user to IRC"
    public static final int RPL_INVITELIST = 346; // "<channel> <invitemask>"
    public static final int RPL_ENDOFINVITELIST = 347; // "<channel> :End of channel invite list"
    public static final int RPL_EXCEPTLIST = 348; // "<channel> <exceptionmask>"
    public static final int RPL_ENDOFEXCEPTLIST = 349; // "<channel> :End of channel exception list"

    //
    public static final int RPL_VERSION = 351; // "<version>.<debuglevel> <server> :<comments>"


    // Topics
    public static final int RPL_NOTOPIC = 331;
    public static final int RPL_TOPIC = 332; // on channel join

    // WHO
    public static final int RPL_WHOREPLY = 352;
    public static final int RPL_ENDOFWHO = 315;

    // NAME
    public static final int RPL_NAMREPLY = 353;
    public static final int RPL_ENDOFNAMES = 366; // "<channel> :End of NAMES list"
    public static final int RPL_ENDOFWHOWAS = 369;

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
    public static final int ERR_NOSUCHCHANNEL = 403; // "<channel name> :No such channel"
    public static final int ERR_CANNOTSENDTOCHAN = 404;
    public static final int ERR_USERNOTINCHANNEL = 441; // "<nick> <channel> :You're not on that channel"
    public static final int ERR_NOTONCHANNEL = 442; // "<channel> :You're not on that channel"
    public static final int ERR_USERONCHANNEL = 443; // "<user> <channel> :is already on channel"
    public static final int ERR_NEEDMOREPARAMS = 461; //  "<command> :Not enough parameters"
    public static final int ERR_NOPERMFORHOST = 463; //  ":Your host isn't among the privileged"
    public static final int ERR_PASSWDMISMATCH = 464; //  ":Password incorrect"
    public static final int ERR_BADCHANMASK = 476; // "<channel> :Bad Channel Mask"


    public static final int ERR_NOPRIVILEGES = 481;
    public static final int ERR_CHANOPRIVSNEEDED = 482;
    public static final int ERR_CANTKILLSERVER = 483;
    public static final int ERR_RESTRICTED = 484;
    public static final int ERR_UNIQOPPRIVSNEEDED = 485;
    public static final int ERR_NOOPERHOST = 491;

    // Info
    public static final int RPL_INFO = 371;
}

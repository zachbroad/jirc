package JIRC;

/**
 * Numerics in the range from 001 to 099 are used for client-server
 * connections only and should never travel between servers.  Replies
 * generated in the response to commands are found in the range from 200
 * to 399.
 */
public class Numerics {
//    // On Join
//    public static final int RPL_WELCOME = 001; // Sent after register messages... pass message, nickname message, user message
//    public static final int RPL_YOURHOST = 002;
//    public static final int RPL_CREATED = 003;
//    public static final int RPL_MYINFO = 004;
//
//    // STATS
//    public static final int RPL_STATSLINKINFO = 211; // "<linkname> <sendq> <sent messages> <sent Kbytes> <received messages> <received Kbytes> <time open>"
//    public static final int RPL_STATSCOMMANDS = 212; // "<command> <count> <byte count> <remote count>"
//    public static final int RPL_ENDOFSTATS = 219; // "<stats letter> :End of STATS report"
//    public static final int RPL_STATSUPTIME = 242; // ":Server Up %d days %d:%02d:%02d"
//    public static final int RPL_STATSOLINE = 243; // "O <hostmask> * <name>"
//
//    public static final int RPL_UMODEIS = 221;
//    public static final int RPL_SERVLIST = 234; // "<name> <server> <mask> <type> <hopcount> <info>"
//    public static final int RPL_SERVLISTEND = 235; // "<mask> <type> :End of service listing"
//
//
//
//    // WHOIS
//    public static final int RPL_WHOISUSER = 311; // "<nick> <user> <host> * :<real name>"
//    public static final int RPL_WHOISSERVER = 312; // "<nick> <server> :<server info>"
//    public static final int RPL_WHOISOPERATOR = 313; // "<nick> is an IRC operator"
//    public static final int RPL_WHOISIDLE = 317; // "<nick> <integer> :seconds idle"
//    public static final int RPL_ENDOFWHOIS = 318; // "<nick> :End of WHOIS list"
//
//    // List Channels
//    public static final int RPL_LISTSTART = 321; // obsolete
//    public static final int RPL_LIST = 322;
//    public static final int RPL_LISTEND = 323;
//    public static final int RPL_CHANNELMODEIS = 324; // "<channel> <mode> <mode params>"
//
//    // INVITE / SUMMON
//    public static final int RPL_INVITING = 341; // "<channel> <nick>"
//    public static final int RPL_SUMMONING = 342; // "<user> :Summoning user to IRC"
//    public static final int RPL_INVITELIST = 346; // "<channel> <invitemask>"
//    public static final int RPL_ENDOFINVITELIST = 347; // "<channel> :End of channel invite list"
//    public static final int RPL_EXCEPTLIST = 348; // "<channel> <exceptionmask>"
//    public static final int RPL_ENDOFEXCEPTLIST = 349; // "<channel> :End of channel exception list"
//
//    //
//    public static final int RPL_VERSION = 351; // "<version>.<debuglevel> <server> :<comments>"
//
//
//
//    // WHO
//    public static final int RPL_WHOREPLY = 352;
//    public static final int RPL_ENDOFWHO = 315;
//
//    // NAME
//    public static final int RPL_NAMREPLY = 353;
//    public static final int RPL_ENDOFNAMES = 366; // "<channel> :End of NAMES list"
//
//    // BANS
//    public static final int RPL_BANLIST = 367; // "<channel> <banlist>"
//    public static final int RPL_ENDOFBANLIST = 368;
//
//    // MOTD
//    public static final int RPL_MOTDSTART = 375;
//    public static final int RPL_MOTD = 372;
//    public static final int RPL_MOTDEND = 376;
//
//    // OPER
//    public static final int RPL_YOUREOPER = 381;
//
//    // MISC
//    public static final int RPL_TIME = 391;

    // Replies
    public static final int RPL_WELCOME = 001; // "Welcome to the Internet Relay Network <nick>!<user>@<host>"
    public static final int RPL_YOURHOST = 002; // "Your host is <servername>, running version <version>"
    public static final int RPL_CREATED = 003; // "This server was created <date>"
    public static final int RPL_MYINFO = 004; // "<servername> <version> <available user modes> <available channel modes>"
    public static final int RPL_BOUNCE = 005; // "Try server <server name>, port <port number>"

    // User commands
    public static final int RPL_AWAY = 301; // "<nick> :<away message>"
    public static final int RPL_USERHOST = 302; // ":-<reply> *( " " <reply> )"
    public static final int RPL_ISON = 303; // ":*1<nick> *( " " <nick> )"
    public static final int RPL_UNAWAY = 305; // ":You are no longer marked as being away"
    public static final int RPL_NOWAWAY = 306; // ":You have been marked as being away"

    // List replies
    public static final int RPL_LISTSTART = 321; // "Channel :Users Name"
    public static final int RPL_LIST = 322; // "<channel> <# visible> :<topic>"
    public static final int RPL_LISTEND = 323; // ":End of LIST"
    public static final int RPL_CHANNELMODEIS = 324; // "<channel> <mode> <mode params>"
    public static final int RPL_UNIQOPIS = 325; // "<channel> <nickname>"

    // WHO replies
    public static final int RPL_WHOISUSER = 311; // "<nick> <user> <host> * :<real name>"
    public static final int RPL_WHOISSERVER = 312; // "<nick> <server> :<server info>"
    public static final int RPL_WHOISOPERATOR = 313; // "<nick> :is an IRC operator"
    public static final int RPL_WHOISIDLE = 317; // "<nick> <integer> :seconds idle"
    public static final int RPL_ENDOFWHOIS = 318; // "<nick> :End of WHOIS list"
    public static final int RPL_WHOISCHANNELS = 319; // "<nick> :*( ( "@" / "+" ) <channel> " " )"

    // Topic replies
    public static final int RPL_NOTOPIC = 331; // "<channel> :No topic is set"
    public static final int RPL_TOPIC = 332; // "<channel> :<topic>"

    // WHO reply
    public static final int RPL_WHOREPLY = 352; // ( "channel" / "0" ) " <user> <host> <server> <nick> ( "H" / "G" > ["*"] [ ( "@" / "+" ) ] :<hopcount> <real name>"
    public static final int RPL_ENDOFWHO = 315; // "<name> :End of WHO list"
    public static final int RPL_ENDOFWHOWAS = 369; // "<nick> :End of WHOWAS"

    // NAMES replies
    public static final int RPL_NAMREPLY = 353; // ( "=" / "*" / "@" ) <channel> :[ "@" / "+" ] <nick> *( " " [ "@" / "+" ] <nick> )
    public static final int RPL_ENDOFNAMES = 366; // "<channel> :End of NAMES list"

    // BANS replies
    public static final int RPL_BANLIST = 367; // "<channel> <banlist>"
    public static final int RPL_ENDOFBANLIST = 368; // "<channel> :End of channel ban list"

    // INFO replies
    public static final int RPL_INFO = 371; // ":<string>"
    public static final int RPL_ENDOFINFO = 374; // ":End of INFO list"

    // MOTD replies
    public static final int RPL_MOTDSTART = 375; // ":- <server> Message of the Day - "
    public static final int RPL_MOTD = 372; // ":- <text>"
    public static final int RPL_MOTDEND = 376; // ":End of MOTD command"

    // RULES replies
    public static final int RPL_RULESSTART = 378; // ":- <server> Message of the Day - "
    public static final int RPL_RULES = 379; // ":- <text>"
    public static final int RPL_ENDOFRULES = 380; // ":End of RULES command"

    // Oper replies
    public static final int RPL_YOUREOPER = 381; // ":You are now an IRC operator"

    // Time replies
    public static final int RPL_TIME = 391; // "<server> :<string showing server local time>"

    // Users replies
    public static final int RPL_USERSSTART = 392; // ":UserID   Terminal  Host"
    public static final int RPL_USERS = 393; // ":%-8s %-9s %-8s"
    public static final int RPL_ENDOFUSERS = 394; // ":End of users"
    public static final int RPL_NOUSERS = 395; // ":Nobody logged in"

    // TRACE replies
    public static final int RPL_TRACELINK = 200; // "Link <version & debug level> <destination> <next server>"
    public static final int RPL_TRACECONNECTING = 201; // "Try. <class> <server>"
    public static final int RPL_TRACEHANDSHAKE = 202; // "H.S. <class> <server>"
    public static final int RPL_TRACEUNKNOWN = 203; // "???? <class> [<client IP address in dot form>]"
    public static final int RPL_TRACEOPERATOR = 204; // "Oper <class> <nick>"
    public static final int RPL_TRACEUSER = 205; // "User <class> <nick>"
    public static final int RPL_TRACESERVER = 206; // "Serv <class> <int>S <int>C <server> <nick!user|*!*>@<host|server>"
    public static final int RPL_TRACENEWTYPE = 208; // "<newtype> 0 <client name>"
    public static final int RPL_TRACELOG = 261; // "File <logfile> <debug level>"

    // STATS
    public static final int RPL_STATSLINKINFO = 211; // "<linkname> <sendq> <sent messages> <sent Kbytes> <received messages> <received Kbytes> <time open>"
    public static final int RPL_STATSCOMMANDS = 212; // "<command> <count> <byte count> <remote count>"
    public static final int RPL_STATSCLINE = 213; // "C <host> * <name>"
    public static final int RPL_STATSNLINE = 214; // "N <host> * <name>"
    public static final int RPL_STATSILINE = 215; // "I <host> * <host> <port> <class>"
    public static final int RPL_STATSKLINE = 216; // "K <host> * <username> <port> <class>"
    public static final int RPL_STATSYLINE = 218; // "Y <class> <ping frequency> <connect frequency> <max sendq>"
    public static final int RPL_ENDOFSTATS = 219; // "<query> :End of STATS report"
    public static final int RPL_STATSLLINE = 241; // "L <hostmask> * <servername> <maxdepth>"
    public static final int RPL_STATSUPTIME = 242; // ":Server Up %d days %d:%02d:%02d"
    public static final int RPL_STATSOLINE = 243; // "O <hostmask> * <name>"
    public static final int RPL_STATSHLINE = 244; // "H <host> * <servername> :Server host"
    public static final int RPL_UMODEIS = 221; // "<user mode string>"
    public static final int RPL_LUSERCLIENT = 251; // ":There are <integer> users and <integer> invisible on <integer> servers"
    public static final int RPL_LUSEROP = 252; // "<integer> :operator(s) online"
    public static final int RPL_LUSERUNKNOWN = 253; // "<integer> :unknown connection(s)"
    public static final int RPL_LUSERCHANNELS = 254; // "<integer> :channels formed"
    public static final int RPL_LUSERME = 255; // ":I have <integer> clients and <integer> servers"
    public static final int RPL_ADMINME = 256; // "<server> :Administrative info"
    public static final int RPL_ADMINLOC1 = 257; // ":<admin info>"
    public static final int RPL_ADMINLOC2 = 258; // ":<admin info>"
    public static final int RPL_ADMINEMAIL = 259; // ":<admin info>"
    public static final int RPL_TRYAGAIN = 263; // "<command> :Please wait a while and try again."

    // Errors
    public static final int ERR_UNKNOWNERROR = 400; // ":Unknown error"
    public static final int ERR_NOSUCHNICK = 401; // "<nickname> :No such nick/channel"
    public static final int ERR_NOSUCHSERVER = 402; // "<server name> :No such server"
    public static final int ERR_NOSUCHCHANNEL = 403; // "<channel name> :No such channel"
    public static final int ERR_CANNOTSENDTOCHAN = 404; // "<channel name> :Cannot send to channel"
    public static final int ERR_USERNOTINCHANNEL = 441; // "<nick> <channel> :You're not on that channel"
    public static final int ERR_NOTONCHANNEL = 442; // "<channel> :You're not on that channel"
    public static final int ERR_USERONCHANNEL = 443; // "<user> <channel> :is already on channel"
    public static final int ERR_NEEDMOREPARAMS = 461; // "<command> :Not enough parameters"
    public static final int ERR_NOPERMFORHOST = 463; // ":Your host isn't among the privileged"
    public static final int ERR_PASSWDMISMATCH = 464; // ":Password incorrect"
    public static final int ERR_BADCHANMASK = 476; // "<channel> :Bad Channel Mask"

    public static final int ERR_NOPRIVILEGES = 481; // ":Permission Denied- You're not an IRC operator"
    public static final int ERR_CHANOPRIVSNEEDED = 482; // "<channel> :You're not channel operator"
    public static final int ERR_CANTKILLSERVER = 483; // ":You can't kill a server!"
    public static final int ERR_RESTRICTED = 484; // ":Your connection is restricted!"
    public static final int ERR_UNIQOPPRIVSNEEDED = 485; // ":You're not the original channel operator"
    public static final int ERR_NOOPERHOST = 491; // ":No O-lines for your host"

    public static final int ERR_NOORIGIN = 409; // ":No origin specified"
    public static final int ERR_NORECIPIENT = 411; // ":No recipient given (<command>)"
    public static final int ERR_NOTEXTTOSEND = 412; // ":No text to send"
    public static final int ERR_NOTOPLEVEL = 413; // "<mask> :No toplevel domain specified"
    public static final int ERR_WILDTOPLEVEL = 414; // "<mask> :Wildcard in toplevel domain"
    public static final int ERR_UNKNOWNCOMMAND = 421; // "<command> :Unknown command"
    public static final int ERR_NOMOTD = 422; // ":MOTD File is missing"
    public static final int ERR_NOADMININFO = 423; // "<server> :No administrative info available"
    public static final int ERR_FILEERROR = 424; // ":File error doing <file op> on <file>"
    public static final int ERR_NONICKNAMEGIVEN = 431; // ":No nickname given"
    public static final int ERR_ERRONEUSNICKNAME = 432; // "<nick> :Erroneous nickname"
    public static final int ERR_NICKNAMEINUSE = 433; // "<nick> :Nickname is already in use"
    public static final int ERR_NICKCOLLISION = 436; // "<nick> :Nickname collision KILL"
    public static final int ERR_UNAVAILRESOURCE = 437; // "<nick/channel> :Nick/channel is temporarily unavailable"
    public static final int ERR_NOLOGIN = 444; // "<user> :User not logged in"
    public static final int ERR_SUMMONDISABLED = 445; // ":SUMMON has been disabled"
    public static final int ERR_USERSDISABLED = 446; // ":USERS has been disabled"
    public static final int ERR_NOTREGISTERED = 451; // ":You have not registered"
    public static final int ERR_ALREADYREGISTRED = 462; // ":Unauthorized command (already registered)"
    public static final int ERR_YOUREBANNEDCREEP = 465; // ":You are banned from this server"
    public static final int ERR_KEYSET = 467; // "<channel> :Channel key already set"
    public static final int ERR_CHANNELISFULL = 471; // "<channel> :Cannot join channel (+l)"
    public static final int ERR_UNKNOWNMODE = 472; // "<char> :is unknown mode char to me"
    public static final int ERR_INVITEONLYCHAN = 473; // "<channel> :Cannot join channel (+i)"
    public static final int ERR_BANNEDFROMCHAN = 474; // "<channel> :Cannot join channel (+b)"
    public static final int ERR_BADCHANNELKEY = 475; // "<channel> :Cannot join channel (+k)"


    // Privacy errors
    public static final int ERR_UMODEUNKNOWNFLAG = 501; // ":Unknown MODE flag"
    public static final int ERR_USERSDONTMATCH = 502; // ":Cannot change mode for other users"
}

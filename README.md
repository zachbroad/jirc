# JIRC: Java IRC Server

JIRC is an Internet Relay Chat (IRC) server written in Java. IRC is a protocol that provides a way for people to
communicate in real time. It's primarily designed for group communication in discussion forums, known as channels, but
also allows one-on-one communication via private messages.

## Current Features

- **Server Configuration:** The server uses a hardcoded configuration with a hostname, port, name, and password.

- **Channel Management:** The server supports the creation and management of channels. Users connected to the server can
  join these channels to participate in group communication.

- **Client Handling:** The server handles multiple clients concurrently. It processes messages from connected clients
  and sends each message for further processing.

- **Broadcasting Messages:** The server can broadcast a message to all connected clients, or to all clients in a
  specific channel.

- **Direct Messages:** The server supports direct one-on-one communication between users.

- **Sending Messages to Specific Clients:** The server can send a message to a specific client.

## Implemented Commands

- [x] AWAY: Marks user as away or returns them.
- [x] INFO: Returns information about a server's configuration.
- [x] INVITE: Invites a user to a channel.
- [x] ISON: Checks if certain nicks are online.
- [x] JOIN: Joins one or more channels.
- [x] KICK: Removes a user from a channel.
- [x] KILL: Removes a user from the server (used by operators).
- [x] LIST: Lists channels and their topics.
- [x] MODE: Sets or retrieves user or channel mode.
- [x] MOTD: Retrieves the "Message of the Day".
- [x] NAMES: Lists users in a channel or all channels.
- [x] NICK: Changes or sets a user's nickname.
- [x] NOTICE: Sends a notice message to a user or channel.
- [x] OPER: Authenticates as an IRC operator.
- [x] PART: Leaves one or more channels.
- [x] PING: Checks for an active client or server connection.
- [x] PRIVMSG: Sends a private message to a user or channel.
- [x] QUIT: Quits from the server.
- [x] RESTART: Restarts the server.
- [x] TIME: Requests the local time from a server.
- [x] TOPIC: Gets or sets the topic for a channel.
- [x] USER: Used at login to specify user information.
- [x] WHO: Returns a list of users matching a criteria.
- [x] WHOIS: Returns information about a user.

## Unimplemented Commands (RFC 2812):

- [ ] ADMIN: Retrieves administrative details about a server.
- [ ] CONNECT: Forces a server to try to establish a new connection to another server.
- [ ] DIE: Shuts the server down.
- [ ] ERROR: Reports an error message.
- [ ] LINKS: Lists all server links known by the server answering the query.
- [ ] LUSERS: Returns statistics about the size of the network.
- [ ] PASS: Sets a connection password during registration.
- [ ] PONG: Response to a PING message, confirming the connection is alive.
- [ ] REHASH: Forces the server to reload or update its configuration.
- [ ] SERVICE: Registers a new service.
- [ ] SERVLIST: Lists services currently connected to the network.
- [ ] SQUERY: Sends a message to a service.
- [ ] SQUIT: Disconnects server links.
- [ ] STATS: Requests server statistics.
- [ ] SUMMON: Summons a user to IRC.
- [ ] TRACE: Finds the route to a user or server.
- [ ] USERHOST: Returns a list of hostnames for the specified nicknames.
- [ ] USERS: Returns a list of users logged into the system.
- [ ] VERSION: Queries the version of a server.
- [ ] WALLOPS: Sends a message to all operators.
- [ ] WHOWAS: Returns information about a user who was previously online.

## Planned Server Features

- **User Authentication:** Implement a user authentication system to allow each user to have their own username and
  password.

- **Persistent Data:** Integrate a database to store persistent data such as chat history or user information.

- **Server Configuration File:** Move the server's configuration to a configuration file that the server reads on
  startup.

- **Security Features:** Implement security features like encryption for messages.

- **Extending IRC Features:** Implement more IRC features like user modes, channel modes, etc.

- **Server Commands:** Implement commands that administrators can use to manage the server (like kicking or banning
  users, changing server settings, etc.).

- **Logging:** Implement more extensive logging to help with debugging and understanding server operation.

- **Desktop or Web UI**: Implement a desktop UI for simple server management or a web UI for remote management.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)

# JIRC: Java IRC Server

JIRC is an Internet Relay Chat (IRC) server written in Java. IRC is a protocol that provides a way for people to communicate in real time. It's primarily designed for group communication in discussion forums, known as channels, but also allows one-on-one communication via private messages.

## Current Features

- **Server Configuration:** The server uses a hardcoded configuration with a hostname, port, name, and password.

- **Channel Management:** The server supports the creation and management of channels. Users connected to the server can join these channels to participate in group communication.

- **Client Handling:** The server handles multiple clients concurrently. It processes messages from connected clients and sends each message for further processing.

- **Broadcasting Messages:** The server can broadcast a message to all connected clients, or to all clients in a specific channel.

- **Direct Messages:** The server supports direct one-on-one communication between users.

- **Sending Messages to Specific Clients:** The server can send a message to a specific client.

## Planned Features

- **User Authentication:** Implement a user authentication system to allow each user to have their own username and password.

- **Persistent Data:** Integrate a database to store persistent data such as chat history or user information.

- **Server Configuration File:** Move the server's configuration to a configuration file that the server reads on startup.

- **Security Features:** Implement security features like encryption for messages.

- **Extending IRC Features:** Implement more IRC features like user modes, channel modes, etc.

- **Server Commands:** Implement commands that administrators can use to manage the server (like kicking or banning users, changing server settings, etc.).

- **Logging:** Implement more extensive logging to help with debugging and understanding server operation.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)

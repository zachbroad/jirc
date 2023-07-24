public class IrcMessage {
    public String raw;
    public IrcMessageType type;

    public IrcMessage(String raw) {
        this.raw = raw;
    }

    /**
     * Gets the part of the message after the first word/part
     *
     * @return the part of the message after type (ex: JOIN #channel -> returns #channel)
     */
    public String afterMessageType() {
        return raw.substring(raw.indexOf(' ') + 1, raw.length());
    }
}

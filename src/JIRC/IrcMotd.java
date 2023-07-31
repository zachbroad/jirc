package JIRC;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IrcMotd {
    private static final String DEFAULT_MOTD = "Welcome to the server! Change this message in motd.txt!";
    private static List<String> motd = new LinkedList<>();

    public static void parseMotd() {
        try {
            File motdFile = new File("motd.txt");
            if (motdFile.createNewFile()) {
                IrcServer.logger.info("motd.txt does not exist, creating...");
                Writer writer = new FileWriter(motdFile, StandardCharsets.UTF_8);
                writer.write(DEFAULT_MOTD);
                writer.close();
                motd.add(DEFAULT_MOTD);
            } else {
                try (Scanner scanner = new Scanner(motdFile, StandardCharsets.UTF_8)) {
                    while (scanner.hasNextLine()) {
                        motd.add(scanner.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                IrcServer.logger.info("Got motd.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getMotd() {
        parseMotd();
        return motd;
    }

}

package JIRC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class IrcOperator {

    private static final HashMap<String, String> operators = new HashMap<>();

    public static boolean isValidUsername(String username) {
        return operators.containsKey(username);
    }

    public static boolean tryLogin(String username, String password) {
        if (isValidUsername(username)) {
            return operators.get(username).equals(password);
        }

        return false;
    }

    public static void parseOperators() {
        try {
            File operFile = new File("operators.txt");
            if (operFile.createNewFile()) {
                IrcServer.logger.info("operators.txt does not exist, creating...");
            } else {
                List<String> lines = new ArrayList<>();
                try (Scanner scanner = new Scanner(operFile)) {
                    while (scanner.hasNextLine()) {
                        lines.add(scanner.next());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (String line : lines) {
                    String[] parts = line.split(",");
                    String user = parts[0];
                    String passwd = parts[1];

                    operators.put(user, passwd);
                }
                IrcServer.logger.info("Got %s operators".formatted(operators.size()));
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

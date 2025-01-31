import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Startup {
    public static void main(String[] args) throws IOException, InterruptedException {
        firstMenu();
    }

    private static void firstMenu() throws IOException, InterruptedException {
        do {
            PrintASCII.printlnMenu(3);
            Scanner userInput = new Scanner(System.in);
            String menuChoice = userInput.next().trim().toLowerCase();

            switch (menuChoice) {
                case "s":
                    signIn();
                    break;
                case "l":
                    logIn();
                    break;
                case "g":
                    Player guestPlayer = new Player("Guest", "password", 2000.00, 0.0, 0, 0);
                    mainMenu(guestPlayer);
                    break;
                case "x":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (true);
    }

    private static void logIn() throws IOException, InterruptedException {
        FileReader reader = new FileReader("./user_data.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        Player loggedInPlayer = null;
        boolean notValid = true;
        Scanner userInput = new Scanner(System.in);
        String username = "";
        String password = "";

        do {
            System.out.print("Enter username: ");
            username = userInput.next();
            System.out.print("Enter password: ");
            password = userInput.next();
            if ((username.contains(" ") || username.isBlank()) || (password.contains(" ") || password.isBlank() || password.length() <= 5)) {
                System.out.println("Username/password cannot have 'space', be blank, or have a password less than 5 characters.");
            } else {
                notValid = false;
            }
        } while (notValid);

        Pattern pattern = Pattern.compile("name: (\\w+), password: (\\S+), balance: R\\$ ([\\d.]+), winRate: (\\d+)%, wins: (\\d+), loses: (\\d+)");

        while ((line = bufferedReader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String storedUsername = matcher.group(1);
                String encodedPasswd = matcher.group(2);
                double balance = Double.parseDouble(matcher.group(3));
                double winRate = Double.parseDouble(matcher.group(4));
                int wins = Integer.parseInt(matcher.group(5));
                int loses = Integer.parseInt(matcher.group(6));

                String decodedPasswd = new String (Base64.getDecoder().decode(encodedPasswd));

                if (storedUsername.equals(username) && decodedPasswd.equals(password)) {
                    System.out.println("Login successful!");
                    loggedInPlayer = new Player(username, password, balance, winRate, wins, loses);
                    break;
                }
            }
        }
        bufferedReader.close();

        if (loggedInPlayer == null) {
            System.out.println("Login failed. Invalid username or password.");
        } else {
            mainMenu(loggedInPlayer);
        }
    }

    private static void signIn() throws IOException {
        Scanner inputName = new Scanner(System.in);
        String username = "";
        String password = "";
        boolean notValid = true;

        do {
            System.out.print("Username: ");
            username = inputName.next();
            System.out.print("Password: ");
            password = inputName.next();

            if ((username.contains(" ") || username.isBlank()) || (password.contains(" ") || password.isBlank() || password.length() <= 5)) {
                System.out.println("Username/password cannot have 'space', be blank, or have a password less than 5 characters.");
            } else {
                notValid = false;
            }
        } while (notValid);

        File file = new File("./user_data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("name: " + username)) {
                System.out.println("Username already exists. Please choose a different username.");
                reader.close();
                return;
            }
        }
        reader.close();

        FileWriter writer = new FileWriter(file, true);
        String userInfo = "player: [name: " + username + ", password: " + Base64.getEncoder().encodeToString(password.getBytes()) + ", balance: R$ 2000.0, winRate: 0%, wins: 0, loses: 0]";
        writer.write(userInfo + "\n");
        writer.close();

        System.out.println("User signed up successfully!");
    }

    private static void mainMenu(Player player) throws IOException, InterruptedException {
        boolean exit = true;
        do {
            PrintASCII.printlnMenu(1);
            Scanner userInput = new Scanner(System.in);
            String menuChoice = userInput.next().trim().toLowerCase();

            switch (menuChoice) {
                case "s":
                    startGame(player);
                    break;
                case "a":
                    account(player, exit);
                    break;
                case "x":
                    exit = false;
                    break;
                case "h":
                    helpMenu(exit);
                    break;
                default:
                    System.out.println("Not a valid option! Try again");
            }
        } while (exit);
    }

    private static void helpMenu(boolean exit) {
        do {
            PrintASCII.printlnMenu(2);
            Scanner userInput = new Scanner(System.in);
            String menuChoice = userInput.next().trim().toLowerCase();
            switch (menuChoice) {
                case "x":
                    exit = false;
                    break;
                default:
                    System.out.println("Not a valid option! Try again");
            }
        } while (exit);
    }

    private static void account(Player player, boolean exit) {
        do {
            PrintASCII.printFMenu(player, 1);
            Scanner userInput = new Scanner(System.in);
            String menuChoice = userInput.next().trim().toLowerCase();
            switch (menuChoice) {
                case "x":
                    exit = false;
                    break;
                default:
                    System.out.println("Not a valid option! Try again");
            }
        } while (exit);
    }

    private static void startGame(Player player) throws IOException, InterruptedException {
        double bet = 0;
        do {
            PrintASCII.printFMenu(player, 2);
            Scanner userInput = new Scanner(System.in);
            System.out.print("Total amount: ");
            String input = userInput.nextLine().trim().replace(',','.');
            bet = Double.parseDouble(input);
            if (bet > player.getBalance()) {
                System.out.println("Not a valid option! Try again, the amount must be within balance of player");
            } else {
                break;
            }
        } while (true);
        player.setBalance(player.getBalance() - bet);
        GameActions.table(player, bet);
    }

    public static void savePlayerInfo(Player player) throws IOException {
        File file = new File("./user_data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i).trim();

            Pattern pattern = Pattern.compile("player: \\[name: (.*?), password: (.*?), balance: (.*?), winRate: (.*?), wins: (.*?), loses: (.*?)\\]");
            Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) {
                String name = matcher.group(1);
                if (name.equals(player.getName())) {
                    String updatedPlayerInfo = "player: [name: " + player.getName() +
                            ", password: " + Base64.getEncoder().encodeToString(player.getPassword().getBytes()) +
                            ", balance: R$ " + player.getBalance() +
                            ", winRate: " + player.getWinRate() + "%" +
                            ", wins: " + player.getWins() +
                            ", loses: " + player.getLoses()+"]";
                    lines.set(i, updatedPlayerInfo);
                    break;
                }
            }
        }

        FileWriter writer = new FileWriter(file);
        for (String updatedLine : lines) {
            writer.write(updatedLine + "\n");
        }
        writer.close();
    }
}

import java.io.*;
import java.util.*;

public class Startup {
    public static void main(String[] args) {
        firstMenu(
                new Player(
                        "jrr",
                        300.43,
                        23.3
                )
        );
    }

    private static void firstMenu(Player player) {
        do {
            PrintASCII.printMainMenu(3);
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
                    mainMenu(player);
                    break;
                case "x":
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    private static void logIn(String username, String password) throws FileNotFoundException {
        FileReader reader = new FileReader("./data/user_data.txt");

        if (reader
        )
    }

    private static void signIn(String username, String password) throws IOException {
        File userData = new File("./data/user_data.txt");
        FileWriter writer = new FileWriter(userData);
        String userInfo = " player: [name: "+username+", password: "+password+", balance: R$ 2000.0 winRate: 0%]";
        writer.write(userInfo+"\n");
    }

    private static void mainMenu(Player player) {
        boolean exit = true;
        do {
            PrintASCII.printMainMenu(1);
            Scanner userInput = new Scanner(System.in);
            String menuChoice = userInput.next().trim().toLowerCase();

            switch (menuChoice) {
                case "p":
                    playGame();
                    break;
                case "a":
                    account(player);
                    break;
                case "x":
                    exit = false;
                    break;
                case "h":
                    helpMenu();
                    break;
                default:
                    System.out.println("Not a valid option! Try again");
            }
        } while (exit);
    }

    private static void helpMenu() {
        boolean exit = true;
        do {
            PrintASCII.printMainMenu(2);
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

    private static void account(Player player) {
        boolean exit = true;
        do {
            System.out.printf("""
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                                                                 𝐀𝐜𝐜𝐨𝐮𝐧𝐭
                        ■ Name: %s
                        ■ Balance: %s
                        ■ Win Rate: %s
                                                         𝗣𝗥𝗘𝗦𝗦 (𝗫) 𝗧𝗢 𝗥𝗘𝗧𝗨𝗥𝗡
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                        """,player.getName(),"R$ "+player.getBalance(),player.getWinRate()+"%");
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

    private static void playGame() {
    }

}

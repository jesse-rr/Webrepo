import java.util.*;

public class Startup {
    public static void main(String[] args) {
        firstMenu();
    }

    private static void firstMenu() {
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
                    mainMenu();
                    break;
                case "x":
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    private static void logIn() {
        
    }

    private static void signIn() {

    }

    private static void mainMenu() {
        boolean exit = true;
        do {
            PrintASCII.printMainMenu(1);
            Scanner userInput = new Scanner(System.in);
            String menuChoice = userInput.next().trim().toLowerCase();

            switch (menuChoice) {
                case "p":
                    playGame();
                    break;
                case "v":
                    account();
                    break;
                case "s":
                    settings();
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

    private static void settings() {
    }

    private static void account() {
    }

    private static void playGame() {
    }


    private boolean isAbove21(int totalHandValue) {
        return totalHandValue <= 21;
    }

    public static List<String> loadDeck() {
        List<String> suits = List.of("♥", "♦", "♣", "♠");
        List<String> cardNumbers = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "A", "J", "Q", "K");
        List<String> deck = new ArrayList<>();

        for (String num : cardNumbers) {
            for (String suit : suits) {
                deck.add(num + suit);
            }
        }

        Collections.shuffle(deck);
        return deck;
    }
}

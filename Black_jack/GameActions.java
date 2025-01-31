import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GameActions {

    public static void table(Player player, double bet) throws IOException, InterruptedException {
        TableActions table = new TableActions();
        List<String> deck = table.createDeck();
        List<String> dealer_cards = table.setDealerCards(deck);
        List<String> player_cards = table.setPlayerCards(deck);
        int dealerCardNumber = 2;
        int playerCardNumber = 2;
        boolean noWinner = true;
        boolean noInsurance = true;
        double insurance = 0.0;
        String confirm = "";

        do {
            PrintASCII.printlnMenu(4);
            PrintASCII.printCards(dealer_cards, dealerCardNumber, true, table.sumCards(dealer_cards.subList(0, dealerCardNumber - 1)));
            PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
            PrintASCII.printlnMenu(5);

            Scanner userInput = new Scanner(System.in);
            System.out.println("""
                    (1) - Hit
                    (2) - Stand
                    (3) - Double Down
                    (4) - Split
                    (5) - Surrender
                    (6) - Insurance
                    """);
            String decision = userInput.next().trim().toLowerCase();
            switch (decision) {
                case "1":
                    noWinner = hitLogic(player_cards, dealer_cards, table, insurance, player, bet, dealerCardNumber, playerCardNumber);
                    break;
                case "2":
                    noWinner = standLogic(dealer_cards, player_cards, table, insurance, player, bet, dealerCardNumber, playerCardNumber);
                    break;
                case "3":
                    doubleDownLogic(dealer_cards, player_cards, table, insurance, player, bet, dealerCardNumber, playerCardNumber);
                    break;
                case "4":
                    table.canSplit(player_cards, playerCardNumber);
                    break;
                case "5":
                    System.out.print("Are you sure of Surrender? I'll not count as a loss, but half your bet will be lost. Y/N: ");
                    confirm = userInput.next().trim().toLowerCase().substring(0, 1);
                    switch (confirm) {
                        case "y":
                            player.setBalance(player.getBalance() + bet / 2);
                            Startup.savePlayerInfo(player);
                            noWinner = false;
                            break;
                    }
                    break;
                case "6":
                    if (noInsurance) {
                        insurance = insuranceLogic(table, dealer_cards, bet, userInput);
                        if (insurance > 0) {
                            noInsurance = false;
                        }
                    } else {
                        System.out.println("You're already insured!");
                    }
            }
        } while (noWinner);
    }

    private static boolean hitLogic(List<String> player_cards, List<String> dealer_cards, TableActions table, double insurance, Player player, double bet, int dealerCardNumber, int playerCardNumber) throws InterruptedException, IOException {
        PrintASCII.printlnMenu(4);
        PrintASCII.printCards(dealer_cards, dealerCardNumber, true, table.sumCards(dealer_cards.subList(0, dealerCardNumber - 1)));
        PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
        PrintASCII.printlnMenu(5);

        int playerTotal = table.sumCards(player_cards.subList(0, playerCardNumber));
        boolean isPlaying = true;

        while (isPlaying && playerTotal <= 21) {
            System.out.println("Player hits!");
            Thread.sleep(2000);

            playerTotal = table.sumCards(player_cards.subList(0, playerCardNumber += 1));
            PrintASCII.printlnMenu(4);
            PrintASCII.printCards(dealer_cards, dealerCardNumber, true, table.sumCards(dealer_cards.subList(0, dealerCardNumber - 1)));
            PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
            PrintASCII.printlnMenu(5);
            Thread.sleep(1000);

            if (playerTotal > 21) {
                System.out.println("Player has gone over 21! Dealer wins.");
                return verifyWinnerPayments(2, player, insurance, bet, table, player_cards, dealer_cards);
            }

            System.out.println("""
                ┏┳┓       ┏┓   •
                 ┃ ┓┏┏┏┓  ┃┃┏┓╋┓┏┓┏┓┏
                 ┻ ┗┻┛┗┛  ┗┛┣┛┗┗┗┛┛┗┛
                            ┛
                (1) - Hit | Stand - (2)
                """);
            Scanner userInput = new Scanner(System.in);
            String decision = userInput.next().trim().substring(0, 1);

            switch (decision) {
                case "1":
                    break;
                case "2":
                    isPlaying = false;
                    break;
                default:
                    System.out.println("Invalid input. Please choose (1) for Hit or (2) for Stand.");
                    break;
            }
        }

        return standLogic(dealer_cards, player_cards, table, insurance, player, bet, dealerCardNumber, playerCardNumber);
    }

    public static void doubleDownLogic(List<String> dealer_cards, List<String> player_cards, TableActions table, double insurance, Player player, double bet, int dealerCardNumber, int playerCardNumber) throws IOException, InterruptedException {
        if (table.canDoubleDown(player_cards, playerCardNumber)) {

            PrintASCII.printlnMenu(4);
            PrintASCII.printCards(dealer_cards, dealerCardNumber, true, table.sumCards(dealer_cards.subList(0, dealerCardNumber - 1)));
            PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
            PrintASCII.printlnMenu(5);

            player.setBalance(player.getBalance() - bet);
            bet = bet * 2;
            standLogic(dealer_cards, player_cards, table, insurance, player, bet, dealerCardNumber, playerCardNumber);
        }
    }

    private static boolean standLogic(List<String> dealer_cards, List<String> player_cards, TableActions table, double insurance, Player player, double bet, int dealerCardNumber, int playerCardNumber) throws InterruptedException, IOException {
        PrintASCII.printlnMenu(4);
        PrintASCII.printCards(dealer_cards, dealerCardNumber, false, table.sumCards(dealer_cards.subList(0, dealerCardNumber)));
        PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
        PrintASCII.printlnMenu(5);
        int winner = 0;

        int dealerTotal = table.sumCards(dealer_cards.subList(0, dealerCardNumber));

        while (dealerTotal < 17) {
            Thread.sleep(3000);
            System.out.print("Dealer hits!" + "\r");
            Thread.sleep(2000);

            dealerTotal = table.sumCards(dealer_cards.subList(0, ++dealerCardNumber));
            PrintASCII.printlnMenu(4);
            PrintASCII.printCards(dealer_cards, dealerCardNumber, false, table.sumCards(dealer_cards.subList(0, dealerCardNumber)));
            PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
            PrintASCII.printlnMenu(5);
            Thread.sleep(2000);
        }

        if (dealerTotal > 21) {
            Thread.sleep(1000);
            System.out.println("Dealer has gone over 21! Player wins.");
            winner = 1;
        } else {
            int playerTotal = table.sumCards(player_cards.subList(0, playerCardNumber));

            if (dealerTotal > playerTotal) {
                Thread.sleep(2000);
                System.out.println("Dealer wins!");
                winner = 2;
            } else if (dealerTotal < playerTotal) {
                Thread.sleep(2000);
                System.out.println("Player wins!");
                winner = 1;
            } else {
                Thread.sleep(2000);
                System.out.println("It's a tie!");
                winner = -1;
            }
        }
        return verifyWinnerPayments(winner, player, insurance, bet, table, player_cards, dealer_cards);
    }

    private static double insuranceLogic(TableActions table, List<String> dealerCards, double bet, Scanner userInput) {
        String confirm = "";
        double insurance = 0.0;
        if (table.canInsurance(dealerCards.get(0))) {
            System.out.print("Would you like insurance? If the dealer has blackjack (A + 10), you win 2:1 on the insurance bet. Y/N: ");
            confirm = userInput.next().trim().toLowerCase();
            if (confirm.equals("y")) {
                do {
                    System.out.print("Insurance cannot be above half your original bet or 0. " +
                            "Enter insurance amount: ");
                    insurance = userInput.nextDouble();
                } while (insurance > bet / 2 || insurance == 0);
            }
        } else {
            System.out.println("The dealer must have an Ace as the face-up card to take insurance!");
        }
        return insurance;
    }

    private static boolean verifyWinnerPayments(int winner, Player player, double insurance, double bet, TableActions table, List<String> player_cards, List<String> dealer_cards) throws IOException {
        if (winner == 1) {
            player.setWins(player.getWins() + 1);
            if (table.isBlackjack(player_cards)) {
                player.setBalance(player.getBalance() + bet * 2.5);
            } else {
                player.setBalance(player.getBalance() + bet * 2);
            }
        } else if (winner == 2) {
            player.setLoses(player.getLoses() + 1);
            if (table.isBlackjack(dealer_cards)) {
                player.setBalance(player.getBalance() + insurance * 2);
            }
        } else if (winner == -1) {
            player.setBalance(player.getBalance() + bet);
            if (insurance > 0 && table.isBlackjack(dealer_cards)) {
                player.setBalance(player.getBalance() + insurance * 2);
            }
        }

        player.setWinRate(Math.round(100.0 * player.getWins() / (player.getWins() + player.getLoses())));
        Startup.savePlayerInfo(player);
        return false;
    }

}

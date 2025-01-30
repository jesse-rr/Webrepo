import java.io.IOException;
import java.sql.Statement;
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
//                    hitLogic(player_cards, dealer_cards, table, insurance, player, bet, true);
//                    player.setWinRate(Math.round(100.0 * player.getWins() / (player.getWins() + player.getLoses())));
//                    Startup.savePlayerInfo(player);
//                    noWinner = false;
                    break;
                case "2":
                    standLogic(dealer_cards, player_cards, table, insurance, player, bet, dealerCardNumber, playerCardNumber);
                    player.setWinRate(Math.round(100.0 * player.getWins() / (player.getWins() + player.getLoses())));
                    Startup.savePlayerInfo(player);
                    noWinner = false;
                    break;
                case "3":
                    if (table.canDoubleDown(player_cards, playerCardNumber)) {
                        bet+=bet;

                        PrintASCII.printlnMenu(4);
                        PrintASCII.printCards(dealer_cards, dealerCardNumber, true, table.sumCards(dealer_cards.subList(0, dealerCardNumber - 1)));
                        PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
                        PrintASCII.printlnMenu(5);

                        standLogic(dealer_cards, player_cards, table, insurance, player, bet, dealerCardNumber, playerCardNumber);
                        player.setWinRate(Math.round(100.0 * player.getWins() / (player.getWins() + player.getLoses())));
                        Startup.savePlayerInfo(player);
                        noWinner = false;
                    }
                    break;
                case "4":
                    break;
                case "5":
                    System.out.print("Are you sure of Surrender? I'll not count as a loss, but half your bet will be lost. Y/N: ");
                    confirm = userInput.next().trim().toLowerCase().substring(0,1);
                    switch (confirm) {
                        case "y":
                            player.setBalance(player.getBalance()-bet/2);
                            Startup.savePlayerInfo(player);
                            noWinner = false;
                            break;
                    }
                    break;
                case "6":
                    insurance = insuranceLogic(table, dealer_cards, bet, userInput);
                    player.setBalance(player.getBalance() - insurance);
            }
        } while (noWinner);
    }

    private static void standLogic(List<String> dealer_cards, List<String> player_cards, TableActions table, double insurance, Player player, double bet, int dealerCardNumber, int playerCardNumber) throws InterruptedException {
        PrintASCII.printlnMenu(4);
        PrintASCII.printCards(dealer_cards, dealerCardNumber, false, table.sumCards(dealer_cards.subList(0, dealerCardNumber)));
        PrintASCII.printCards(player_cards, playerCardNumber, false, table.sumCards(player_cards.subList(0, playerCardNumber)));
        PrintASCII.printlnMenu(5);
        int winner;
        int dealerTotal = table.sumCards(dealer_cards.subList(0, dealerCardNumber));
        Thread.sleep(1000);
        System.out.print("Dealer's total: " + dealerTotal);

        while (dealerTotal < 17) {
            Thread.sleep(3000);
            System.out.print("Dealer hits!" + "\r");
            dealerTotal = table.sumCards(dealer_cards.subList(0, dealerCardNumber += 1));

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
            Thread.sleep(1000);
            System.out.print("Dealer stands with " + dealerTotal + "\r");
            int playerTotal = table.sumCards(player_cards.subList(0, 2));
            Thread.sleep(1000);
            System.out.print("Player's total: " + playerTotal + "\r");

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

        if (winner == 1) player.setWins(player.getWins() + 1);
        else if (winner == 2) player.setLoses(player.getLoses() + 1);

        if (insurance > 0) {
            // If the dealer has blackjack, the player gets 2x insurance (assuming bet is returned in case of dealer blackjack)
            if (table.isBlackjack(dealer_cards.subList(0, dealerCardNumber))) {
                player.setBalance(player.getBalance() + insurance * 2 - bet); // Insurance win, but subtract bet
            } else {
                player.setBalance(player.getBalance() - insurance - bet); // Insurance loss + original bet
            }
        } else if (winner == 1) {
            player.setBalance(player.getBalance() + bet * 2); // Player win: win the bet amount (2x)
        } else if (winner == -1) {
            player.setBalance(player.getBalance() + bet * 2); // Push or draw (same outcome, same bet returned)
        } else {
            player.setBalance(player.getBalance() - bet); // Player loses: bet is subtracted from balance
        }

        if (table.isBlackjack(player_cards.subList(0, playerCardNumber))) {
            player.setBalance(player.getBalance() + bet * 2.5); // Blackjack win (2.5x bet)
        } else if (winner == 1) {
            player.setBalance(player.getBalance() + bet * 2);
        }
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
}

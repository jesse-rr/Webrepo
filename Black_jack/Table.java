import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {

    public static void decision(PlayerDecision decision) {
        switch (decision) {
            case Hit:
                break;
            case Split:
                break;
            case Stand:
                break;
            case Surrender:
                break;
            case Double_down:
                break;
            default:
                System.out.println("Not a valid option! Try again");
        }
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

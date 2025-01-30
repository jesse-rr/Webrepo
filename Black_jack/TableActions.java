import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableActions {

    public List<String> createDeck() {
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

    public int sumCards(List<String> cards) {
        int sum = 0;
        for (String card : cards) {
            String cardValue = card.replaceAll("[^0-9]", "");
            if (cardValue.isEmpty()) {
                if (card.charAt(0) == 'A') {
                    sum += 11;
                } else {
                    sum += 10;
                }
            } else {
                sum += Integer.parseInt(cardValue);
            }
        }
        return sum;
    }

    public boolean isBlackjack(List<String> cards) {
        return cards.contains("A") && cards.contains("♥") || cards.contains("♦") || cards.contains("♣") || cards.contains("♠");
    }

    public boolean canInsurance(String card) {
        return card.contains("A");
    }
}

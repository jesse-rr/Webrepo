import java.util.List;

public class PrintASCII {

    public static void printTwoCards() {
        List<String> deck = Table.loadDeck();
        int cardWidth = 17;
        int cardHeight = 7;

        String card1 = deck.get(0);
        String card2 = deck.get(1);

        printTopBorder(cardWidth);

        for (int i = 0; i < cardHeight - 2; i++) {
            printCardLine(card1, card2, i);
        }
        printBottomBorder(cardWidth);
    }

    private static void printCardLine(String card1, String card2, int row) {
        String rank1 = card1.substring(0, card1.length() - 1);
        String suit1 = card1.substring(card1.length() - 1);
        String rank2 = card2.substring(0, card2.length() - 1);
        String suit2 = card2.substring(card2.length() - 1);

        System.out.print("│");

        if (row == 0) {
            if (rank1.equals("10")) {
                System.out.printf(" %-3s           │", rank1);
            } else {
                System.out.printf(" %-2s            │", rank1);
            }
        } else if (row == 2) {
            System.out.printf("       %-2s      │", suit1);
        } else if (row == 4) {
            if (rank1.equals("10")) {
                System.out.printf("            %-3s│", rank1);
            } else {
                System.out.printf("             %-2s│", rank1);
            }
        } else {
            System.out.print("               │");
        }

        System.out.print("  ");

        System.out.print("│");

        if (row == 0) {
            if (rank2.equals("10")) {
                System.out.printf(" %-3s           │", rank2);
            } else {
                System.out.printf(" %-2s            │", rank2);
            }
        } else if (row == 2) {
            System.out.printf("       %-2s      │", suit2);
        } else if (row == 4) {
            if (rank2.equals("10")) {
                System.out.printf("            %-3s│", rank2);
            } else {
                System.out.printf("             %-2s│", rank2);
            }
        } else {
            System.out.print("               │");
        }

        System.out.println();
    }

    private static void printTopBorder(int cardWidth) {
        System.out.print("┌");
        for (int i = 1; i < cardWidth - 1; i++) {
            System.out.print("─");
        }
        System.out.print("┐");
        System.out.print("  ");
        System.out.print("┌");
        for (int i = 1; i < cardWidth - 1; i++) {
            System.out.print("─");
        }
        System.out.println("┐");
    }

    private static void printBottomBorder(int cardWidth) {
        System.out.print("└");
        for (int i = 1; i < cardWidth - 1; i++) {
            System.out.print("─");
        }
        System.out.print("┘");
        System.out.print("  ");
        System.out.print("└");
        for (int i = 1; i < cardWidth - 1; i++) {
            System.out.print("─");
        }
        System.out.println("┘");
    }

    public static void printMainMenu(int menuNumber) {
        switch (menuNumber) {
            case 1:
                System.out.println("""
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                                                               𝐌𝐚𝐢𝐧 𝐌𝐞𝐧𝐮
                        Play Game (P)
                        Account (A)
                        Exit (X)
                        [Type 'h' for help/how to play]
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                        """);
                break;
            case 2:
                System.out.println("""
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                                                          𝖂𝖊𝖑𝖈𝖔𝖒𝖊 𝖙𝖔 𝕭𝖑𝖆𝖈𝖐𝖏𝖆𝖈𝖐
        
                                                Blackjack (21) is a card game, where the goal
                                            getting as close to 21 as possible without going over.
                                                    Cards 2-10 are worth their face value,
                                    face cards (Jack, Queen, King) are 10 points, and Aces are either 1 or 11.
                                              Players are dealt two cards and can choose to:
                                                [ 𝗛𝗶𝘁, 𝗦𝘁𝗮𝗻𝗱, 𝗗𝗼𝘂𝗯𝗹𝗲 𝗗𝗼𝘄𝗻, 𝗼𝗿 𝗦𝗽𝗹𝗶𝘁 ]
                                    To win, beat the dealer’s hand or hit a Blackjack (Ace + 10) = (21)
            
                                   «[𝗛𝗲𝗿𝗲 𝗮𝗿𝗲 𝘁𝗵𝗲 𝗸𝗲𝘆 𝗰𝗼𝗺𝗺𝗮𝗻𝗱𝘀 𝘆𝗼𝘂 𝗰𝗮𝗻 𝘂𝘀𝗲 𝗱𝘂𝗿𝗶𝗻𝗴 𝘁𝗵𝗲 𝗴𝗮𝗺𝗲]»
                        ■ 𝗛𝗶𝘁 = Take another card to improve your hand.
                        ■ 𝗦𝘁𝗮𝗻𝗱 = Keep your current hand, no more cards.
                        ■ 𝗗𝗼𝘂𝗯𝗹𝗲 𝗱𝗼𝘄𝗻 = Double your bet and take one more card.
                        ■ 𝗦𝗽𝗹𝗶𝘁 = Divide a pair of cards into two separate hands, each with its own bet.
                        ■ 𝗦𝘂𝗿𝗿𝗲𝗻𝗱𝗲𝗿 = Forfeit your hand and get half your bet back.
                        ■ 𝗜𝗻𝘀𝘂𝗿𝗮𝗻𝗰𝗲 = Bet on the dealer having a blackjack if their upcard is an Ace.
            
                                                            «[𝗔𝗱𝗱𝗶𝘁𝗶𝗼𝗻𝗮𝗹 𝗧𝗶𝗽𝘀]»
                              Don’t split Aces and 8s too often: Only split if the dealer’s card is weak (2-6).
                                 Stand on 12-16 if the dealer shows 2-6: The dealer is more likely to bust.
        
                                                       Enjoy the game, and good luck!
                                                          𝗣𝗥𝗘𝗦𝗦 (𝗫) 𝗧𝗢 𝗥𝗘𝗧𝗨𝗥𝗡
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                        """);
                break;
            case 3:
                System.out.println("""
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                                                           ♣ ♠ 𝐁𝐋𝐀𝐂𝐊𝐉𝐀𝐂𝐊 ♥ ♦
                                                               [S] - Sign in
                                                               [L] - Log in
                                                            [G] - Play as Guest
                                                                 (X) - EXIT
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────◄╣
                        """);
                break;
        }

    }
}

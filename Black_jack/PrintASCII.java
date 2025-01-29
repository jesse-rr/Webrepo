import java.util.List;

public class PrintASCII {

//    public static List<String> printTwoCards(List<String> deck, int index, boolean isDealerCards) {
//        int cardWidth = 19;
//        int cardHeight = 13;
//
//        String card1 = deck.get(index);
//        String card2 = deck.get(index+1);
//
//        printTopBorder(cardWidth, isDealerCards);
//
//        for (int i = 0; i < cardHeight - 2; i++) {
//            printCardLine(card1, card2, i, isDealerCards);
//        }
//        printBottomBorder(cardWidth, isDealerCards);
//
//        return List.of(card1,card2);
//    }
//
//    private static void printCardLine(String card1, String card2, int row, boolean isDealerCards) {
//        String rank1 = card1.substring(0, card1.length() - 1);
//        String suit1 = card1.substring(card1.length() - 1);
//        String rank2 = card2.substring(0, card2.length() - 1);
//        String suit2 = card2.substring(card2.length() - 1);
//
//        System.out.print("│");
//
//
//        if (row == 0) {
//            if (rank1.equals("10")) {
//                System.out.printf(" %-3s             │", rank1);
//            } else {
//                System.out.printf(" %-2s              │", rank1);
//            }
//        } else if (row == 5) {
//            System.out.printf("        %-2s       │", suit1);
//        } else if (row == 10) {
//            if (rank1.equals("10")) {
//                System.out.printf("              %-3s│", rank1);
//            } else {
//                System.out.printf("               %-2s│", rank1);
//            }
//        } else {
//            System.out.print("                 │");
//        }
//
//        System.out.print("  ");
//
//        System.out.print("│");
//        if (isDealerCards) {
//            System.out.print("█████████████████│");
//        } else {
//            if (row == 0) {
//                if (rank2.equals("10")) {
//                    System.out.printf(" %-3s             │", rank2);
//                } else {
//                    System.out.printf(" %-2s              │", rank2);
//                }
//            } else if (row == 5) {
//                System.out.printf("        %-2s       │", suit2);
//            } else if (row == 10) {
//                if (rank2.equals("10")) {
//                    System.out.printf("              %-3s│", rank2);
//                } else {
//                    System.out.printf("               %-2s│", rank2);
//                }
//            } else {
//                System.out.print("                 │");
//            }
//        }
//        System.out.println();
//    }
//
//    private static void printTopBorder(int cardWidth, boolean isDealerCards) {
//        System.out.print("┌");
//        for (int i = 1; i < cardWidth - 1; i++) {
//            System.out.print("─");
//        }
//        System.out.print("┐");
//        System.out.print("  ");
//        System.out.print("┌");
//        for (int i = 1; i < cardWidth - 1; i++) {
//            if (isDealerCards) {
//                System.out.print("▄");
//            } else {
//                System.out.print("─");
//            }
//        }
//        System.out.println("┐");
//    }
//
//    private static void printBottomBorder(int cardWidth, boolean isDealerCards) {
//        System.out.print("└");
//        for (int i = 1; i < cardWidth - 1; i++) {
//            System.out.print("─");
//        }
//        System.out.print("┘");
//        System.out.print("  ");
//        System.out.print("└");
//        for (int i = 1; i < cardWidth - 1; i++) {
//            if (isDealerCards) {
//                System.out.print("▀");
//            } else {
//                System.out.print("─");
//            }
//        }
//        System.out.println("┘");
//    }

    public static List<String> printCards(List<String> deck, int index, boolean isDealerCards) {
        int cardWidth = 19;
        int cardHeight = 13;

        List<String> cards = deck.subList(index, index + deck.size());
        printTopBorder(cardWidth, isDealerCards);

        for (int i = 0; i < cardHeight - 2; i++) {
            printCardLine(cards, i, isDealerCards);
        }
        printBottomBorder(cardWidth, isDealerCards);

        return deck;
    }

    private static void printCardLine(List<String> cards, int row, boolean isDealerCards) {
        System.out.print("│");
        for (String card : cards) {
            String rank = card.substring(0, card.length() - 1);
            String suit = card.substring(card.length() - 1);

            if (row == 0) {
                if (rank.equals("10")) {
                    System.out.printf(" %-3s             │", rank);
                } else {
                    System.out.printf(" %-2s              │", rank);
                }
            } else if (row == 5) {
                System.out.printf("        %-2s       │", suit);
            } else if (row == 10) {
                if (rank.equals("10")) {
                    System.out.printf("              %-3s│", rank);
                } else {
                    System.out.printf("               %-2s│", rank);
                }
            } else {
                System.out.print("                 │");
            }
            System.out.print("  ");
        }

        if (isDealerCards) {
            System.out.print("█████████████████│");
        }
        System.out.println();
    }

    private static void printTopBorder(int cardWidth, boolean isDealerCards) {
        System.out.print("┌");
        for (int i = 1; i < cardWidth - 1; i++) {
            System.out.print("─");
        }
        System.out.print("┐");
        System.out.print("  ");

        System.out.print("┌");
        for (int i = 1; i < cardWidth - 1; i++) {
            if (isDealerCards) {
                System.out.print("▄");
            } else {
                System.out.print("─");
            }
        }
        System.out.println("┐");
    }

    private static void printBottomBorder(int cardWidth, boolean isDealerCards) {
        System.out.print("└");
        for (int i = 1; i < cardWidth - 1; i++) {
            System.out.print("─");
        }
        System.out.print("┘");
        System.out.print("  ");

        System.out.print("└");
        for (int i = 1; i < cardWidth - 1; i++) {
            if (isDealerCards) {
                System.out.print("▀");
            } else {
                System.out.print("─");
            }
        }
        System.out.println("┘");
    }


    public static void printMainMenu(int menuNumber) {
        switch (menuNumber) {
            case 1:
                System.out.println("""
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────────◄╣

                                    ███╗   ███╗ █████╗ ██╗███╗   ██╗    ███╗   ███╗███████╗███╗   ██╗██╗   ██╗
                                    ████╗ ████║██╔══██╗██║████╗  ██║    ████╗ ████║██╔════╝████╗  ██║██║   ██║
                                    ██╔████╔██║███████║██║██╔██╗ ██║    ██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║
                                    ██║╚██╔╝██║██╔══██║██║██║╚██╗██║    ██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║
                                    ██║ ╚═╝ ██║██║  ██║██║██║ ╚████║    ██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝
                                    ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝    ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝

                         Start Game (S)
                         Account (A)
                         Exit (X)
                         [Type 'h' for help/how to play]

                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────────◄╣
                        """);
                break;
            case 2:
                System.out.println("""
                    ╟►────────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────────────◄╣

                     ▀█████████▄   ▄█          ▄████████  ▄████████    ▄█   ▄█▄      ▄█    ▄████████ ▄████████    ▄█   ▄█▄
                       ███    ███ ███         ███    ███ ███    ███   ███ ▄███▀     ███   ███    ███ ███    ███   ███ ▄███▀
                       ███    ███ ███         ███    ███ ███    █▀    ███▐██▀       ███   ███    ███ ███    █▀    ███▐██▀
                      ▄███▄▄▄██▀  ███         ███    ███ ███         ▄█████▀        ███   ███    ███ ███         ▄█████▀
                     ▀▀███▀▀▀██▄  ███       ▀███████████ ███        ▀▀█████▄        ███ ▀███████████ ███        ▀▀█████▄
                       ███    ██▄ ███         ███    ███ ███    █▄    ███▐██▄       ███   ███    ███ ███    █▄    ███▐██▄
                       ███    ███ ███▌    ▄   ███    ███ ███    ███   ███ ▀███▄     ███   ███    ███ ███    ███   ███ ▀███▄
                     ▄█████████▀  █████▄▄██   ███    █▀  ████████▀    ███   ▀█▀ █▄ ▄███   ███    █▀  ████████▀    ███   ▀█▀

                                              Blackjack (21) is a card game, where the goal is
                                            getting as close to 21 as possible without going over.
                                                  Cards 2-10 are worth their face value,
                             face cards (Jack, Queen, King) are 10 points, and Aces are either 1 (soft-ace) or 11.
                                               Players are dealt two cards and can choose to:
                                                   [ Hit， Stand， Double Down， or Slip ]
                                     To win, beat the dealer’s hand or hit a Blackjack (Ace + 10) = (21)
            
                                         «［ Here are the key commands you can use during the game! ］»
                     ■ 𝗛𝗶𝘁 = Take another card to improve your hand.
                     ■ 𝗦𝘁𝗮𝗻𝗱 = Keep your current hand, no more cards.
                     ■ 𝗗𝗼𝘂𝗯𝗹𝗲 𝗱𝗼𝘄𝗻 = Double your bet and take one more card.
                     ■ 𝗦𝗽𝗹𝗶𝘁 = Divide a pair of cards into two separate hands, each with its own bet.
                     ■ 𝗦𝘂𝗿𝗿𝗲𝗻𝗱𝗲𝗿 = Forfeit your hand and get half your bet back.
                     ■ 𝗜𝗻𝘀𝘂𝗿𝗮𝗻𝗰𝗲 = Bet on the dealer having a blackjack if their upcard is an Ace.
            
                                                            «[ Additional Tips ]»
                               Don’t split Aces and 8s too often: Only split if the dealer’s card is weak (2-6).
                                 Stand on 12-16 if the dealer shows 2-6: The dealer is more likely to lose.

                                                       Enjoy the game, and good luck!
                                                             Press (X) to Return

                    ╟►────────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────────────◄╣
                    """);
                break;
            case 3:
                System.out.println("""
                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────────◄╣
                                                            ♣  ♠  Blackjack  ♥  ♦

                                                               [S] - Sign in
                                                                [L] - Login
                                                            [G] - Play as Guest
                                                                 (X) - EXIT

                        ╟►────────────────────────────◄═══════════[[████]]══════════►─────────────────────────────────◄╣
                        """);
                break;
        }

    }
}

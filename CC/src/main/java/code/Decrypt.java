package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Decrypt {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/java/encrypted.txt"));
        Scanner comparer = new Scanner(new File("src/main/java/palavras.txt"));
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Set<String> comparerWords = new HashSet<>();
        while (comparer.hasNextLine()) {
            comparerWords.add(comparer.nextLine().toUpperCase());
        }
        comparer.close();

        StringBuilder encryptedText = new StringBuilder();
        while (scanner.hasNextLine()) {
            encryptedText.append(scanner.nextLine()).append("\n");
        }
        scanner.close();

        for (int shift = 1; shift <= 25; shift++) {
            int matches = 0;
            int totalWords = 0;
            StringBuilder decryptedText = new StringBuilder();

            Scanner lineScanner = new Scanner(encryptedText.toString());
            while (lineScanner.hasNext()) {
                String word = lineScanner.next();
                StringBuilder decryptedWord = new StringBuilder();

                for (int j = 0; j < word.length(); j++) {
                    char c = word.charAt(j);
                    if (Character.isLetter(c)) {
                        char upperC = Character.toUpperCase(c);
                        int originalPos = alphabet.indexOf(upperC);
                        int newPos = (originalPos - shift + 26) % 26;
                        char newChar = alphabet.charAt(newPos);
                        decryptedWord.append(newChar);
                    } else {
                        decryptedWord.append(c);
                    }
                }

                String decrypted = decryptedWord.toString();
                decryptedText.append(decrypted).append(" ");
                totalWords++;

                String cleanWord = decrypted.replaceAll("[^A-Z]", "");
                if (!cleanWord.isEmpty() && comparerWords.contains(cleanWord)) {
                    matches++;
                }
            }
            lineScanner.close();

            if (totalWords > 0 && (matches * 100 / totalWords) >= 50) {
                System.out.println(decryptedText.toString().trim());

                System.out.print("(Y/N): ");
                Scanner userInput = new Scanner(System.in);
                if (userInput.nextLine().trim().equalsIgnoreCase("Y")) {
                    System.out.println("DECRYPTED SHIFT: " + shift);
                    return;
                }
            }
        }
        System.err.println("NOT FOUND");
    }
}

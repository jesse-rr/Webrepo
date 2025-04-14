package code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Encrypt {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("src/main/java/source.txt"));
        FileWriter writer = new FileWriter("src/main/java/encrypted.txt");

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int spacing = 5;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (!Character.isLetter(c)) {
                    writer.write(c);
                    continue;
                }

                char upperC = Character.toUpperCase(c);
                int position = alphabet.indexOf(upperC);
                int newPosition = (position + spacing) % alphabet.length();
                char encryptedChar = alphabet.charAt(newPosition);

                if (Character.isLowerCase(c)) {
                    encryptedChar = Character.toLowerCase(encryptedChar);
                }
                writer.write(encryptedChar);
            }
            writer.write("\n");
        }
    }
}
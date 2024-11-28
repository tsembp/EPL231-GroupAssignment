import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FixedLengthWordGenerator {

    public static void main(String[] args) {
        int wordCount = 100; // Default number of words to generate
        int wordLength = 5; // Default word length
        String outputFileName = "./Dictionaries/Same Length/75000.txt";

        // Read user input for word count and word length
        try {
            System.out.print("Enter the number of words to generate: ");
            wordCount = Integer.parseInt(System.console().readLine());

            System.out.print("Enter the length of each word: ");
            wordLength = Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Using default values: 100 words of length 5.");
        }

        try {
            generateWords(wordCount, wordLength, outputFileName);
            System.out.println("Words generated and written to " + outputFileName);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Generates words of a fixed length and writes them to a file.
     *
     * @param wordCount      Number of words to generate.
     * @param wordLength     Length of each word.
     * @param outputFileName Name of the file to write the words to.
     * @throws IOException If an I/O error occurs.
     */
    public static void generateWords(int wordCount, int wordLength, String outputFileName) throws IOException {
        Random random = new Random();
        List<String> words = new ArrayList<>();

        // Generate words
        for (int i = 0; i < wordCount; i++) {
            String word = generateRandomWord(wordLength, random);
            words.add(word);
        }

        // Write words to file
        try (FileWriter writer = new FileWriter(outputFileName)) {
            for (String word : words) {
                writer.write(word + "\n");
            }
        }
    }

    /**
     * Generates a random word of a given length.
     *
     * @param length Length of the word.
     * @param random Random number generator.
     * @return A randomly generated word.
     */
    private static String generateRandomWord(int length, Random random) {
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char letter = (char) ('a' + random.nextInt(26)); // Random letter a-z
            word.append(letter);
        }
        return word.toString();
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordGenerator {

    private static final double MEAN = 6.94; // Mean for the Poisson distribution approximation
    // private static final double VARIANCE = 5.80; // Variance for the Poisson distribution approximation

    public static void main(String[] args) {
        int wordCount = 100; // Default number of words to generate
        String outputFileName = "./Dictionaries/Different Length/75000.txt";

        // Read user input for word count
        try {
            System.out.print("Enter the number of words to generate: ");
            wordCount = Integer.parseInt(System.console().readLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Using default value: 100");
        }

        try {
            generateAndSortWords(wordCount, outputFileName);
            System.out.println("Words generated, sorted, and written to " + outputFileName);

            // Analyze the word lengths in the file
            calculateWordLengthFrequency(outputFileName);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Generates words with lengths based on a shifted Poisson distribution, sorts them by length, and writes them to a file.
     *
     * @param wordCount      Number of words to generate.
     * @param outputFileName Name of the file to write the sorted words to.
     * @throws IOException If an I/O error occurs.
     */
    public static void generateAndSortWords(int wordCount, String outputFileName) throws IOException {
        Random random = new Random();
        List<String> words = new ArrayList<>();

        // Generate words
        for (int i = 0; i < wordCount; i++) {
            int wordLength = generateWordLength(random);
            String word = generateRandomWord(wordLength, random);
            words.add(word);
        }

        // Sort words by length
        words.sort(Comparator.comparingInt(String::length));

        // Write sorted words to file
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

    /**
     * Generates a word length based on a shifted Poisson-like distribution.
     *
     * @param random Random number generator.
     * @return A word length.
     */
    private static int generateWordLength(Random random) {
        // Approximation using a Poisson-like distribution
        double lambda = MEAN; // Poisson parameter (mean of the distribution)
        double L = Math.exp(-lambda);
        int k = 0;
        double p = 1.0;

        do {
            k++;
            p *= random.nextDouble();
        } while (p > L);

        return Math.max(k - 1, 1); // Shifted and ensuring minimum length of 1
    }

    /**
     * Reads the file and calculates the number of words of each length.
     *
     * @param inputFileName Name of the file to read.
     * @throws IOException If an I/O error occurs.
     */
    public static void calculateWordLengthFrequency(String inputFileName) throws IOException {
        Map<Integer, Integer> lengthFrequency = new HashMap<>();

        // Read the file and count word lengths
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                lengthFrequency.put(length, lengthFrequency.getOrDefault(length, 0) + 1);
            }
        }

        // Print the frequency of each word length
        System.out.println("\nWord Length Frequencies:");
        for (Map.Entry<Integer, Integer> entry : lengthFrequency.entrySet()) {
            System.out.println("Words with length " + entry.getKey() + ": " + entry.getValue());
        }
    }
}


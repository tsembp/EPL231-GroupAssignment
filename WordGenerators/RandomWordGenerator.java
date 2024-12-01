import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomWordGenerator {

    // Word length probabilities (cumulative)
    private static final double[] CUMULATIVE_PROBABILITIES = {
            0.00007, 0.00122, 0.00698, 0.02640, 0.06941,
            0.15013, 0.26361, 0.40310, 0.54739, 0.67133,
            0.77276, 0.85419, 0.93288, 0.97111, 0.99501,
            0.99926, 0.99978, 0.99999, 1.00000, 1.00000,
            1.00000, 1.00000, 1.00000, 1.00000, 1.00000,
            1.00000, 1.00000, 1.00000, 1.00000, 1.00000,
            1.00000, 1.00000, 1.00000, 1.00000, 1.00000
    };

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        try {
            // Specify the directory and file name
            String directoryPath = "./Dictionaries/Different Length";
            String fileName = "100000.txt";
            
            // Ensure the directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                boolean created = directory.mkdirs(); // Create the directory and any necessary parent directories
                if (!created) {
                    throw new IOException("Failed to create directory: " + directoryPath);
                }
            }
            
            // Generate random words and save to the file
            generateRandomWords(directoryPath + "/" + fileName, 100000); // Generate 1000 words
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void generateRandomWords(String filePath, int wordCount) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        for (int i = 0; i < wordCount; i++) {
            String word = generateRandomWord();
            writer.write(word + '\n'); // Write each word on a new line
        }

        writer.close();
        System.out.println("Words successfully written to " + filePath);
    }

    private static String generateRandomWord() {
        int wordLength = determineWordLength();
        StringBuilder word = new StringBuilder(wordLength);

        for (int i = 0; i < wordLength; i++) {
            char randomChar = (char) ('a' + RANDOM.nextInt(26)); // Random letter from 'a' to 'z'
            word.append(randomChar);
        }

        return word.toString();
    }

    private static int determineWordLength() {
        double randomValue = RANDOM.nextDouble();

        for (int i = 0; i < CUMULATIVE_PROBABILITIES.length; i++) {
            if (randomValue <= CUMULATIVE_PROBABILITIES[i]) {
                return i + 1; // Word lengths start at 1
            }
        }

        return 20; // Default to maximum length if no match (shouldn't occur)
    }
}

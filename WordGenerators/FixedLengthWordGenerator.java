import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FixedLengthWordGenerator {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        try {
            // Specify the directory and file name
            String directoryPath = "./Dictionaries/Same Length/Length 5/Test 4";
            String fileName = "1000.txt";

            // Ensure the directory exists
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                boolean created = directory.mkdirs(); // Create the directory and any necessary parent directories
                if (!created) {
                    throw new IOException("Failed to create directory: " + directoryPath);
                }
            }

            // Generate fixed-length words and save to the file
            generateFixedLengthWords(directoryPath + "/" + fileName, 1000, 5); // Default: 75000 words of length 5
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Generates words of a fixed length and writes them to a file.
     *
     * @param filePath   The file path to write the words to.
     * @param wordCount  Number of words to generate.
     * @param wordLength Length of each word.
     * @throws IOException If an I/O error occurs.
     */
    private static void generateFixedLengthWords(String filePath, int wordCount, int wordLength) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        for (int i = 0; i < wordCount; i++) {
            String word = generateRandomWord(wordLength);
            writer.write(word + '\n'); // Write each word on a new line
        }

        writer.close();
        System.out.println("Words successfully written to " + filePath);
    }

    /**
     * Generates a random word of a fixed length.
     *
     * @param length Length of the word.
     * @return A randomly generated word.
     */
    private static String generateRandomWord(int length) {
        StringBuilder word = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + RANDOM.nextInt(26)); // Random letter from 'a' to 'z'
            word.append(randomChar);
        }

        return word.toString();
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Loads a dictionary into a Trie data structure and provides functionality to process scripts
 * and validate dictionary contents.
 */
public class DictionaryLoader {
    /** The root of the Trie structure used to store the dictionary. */
    private TrieNode tree;

    /**
     * Constructs a new DictionaryLoader with an empty Trie.
     */
    public DictionaryLoader() {
        this.tree = new TrieNode();
    }

    /**
     * Retrieves the root of the Trie structure.
     *
     * @return the root TrieNode of the dictionary.
     */
    public TrieNode getTree() {
        return this.tree;
    }

    /**
     * Loads words from a dictionary file into the Trie structure.
     * Words are trimmed, converted to lowercase, and cleaned of punctuation.
     * Invalid words are skipped.
     *
     * @param dictionaryPath the file path to the dictionary.
     */
    public void loadDictionary(String dictionaryPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = cleanWord(word.trim().toLowerCase()); // Trim, convert to lowercase, and clean word
                if (!word.isEmpty() && isValidWord(word)) { // Check if word is valid
                    tree.insert(word);
                }
            }
        } catch (IOException e) { // Catch exception thrown while reading
            e.printStackTrace();
        }
    }

    /**
     * Processes a script file and updates the importance of words in the Trie.
     * Words are trimmed, converted to lowercase, split into tokens, and cleaned.
     *
     * @param scriptPath the file path to the script.
     */
    public void processScript(String scriptPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+"); // Trim and split words read
                for (String word : words) {
                    word = cleanWord(word); // Clean up word from punctuation marks at beginning and end
                    if (!word.isEmpty()) {
                        tree.searchImportance(word); // Search word to assign importance
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates the dictionary file by ensuring all words exist in the Trie.
     * Prints a debug message for each word not found.
     *
     * @param dictionaryPath the file path to the dictionary.
     */
    public void validateDictionary(String dictionaryPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = cleanWord(word.trim().toLowerCase()); // Trim, convert to lowercase, and clean word
                if (!word.isEmpty() && !tree.search(word)) { // If word is not found in tree, print debug message
                    System.out.println("Word not found in Trie: " + word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cleans a word by removing punctuation marks at the beginning and end.
     *
     * @param word the word to clean.
     * @return the cleaned word.
     */
    private String cleanWord(String word) {
        int start = 0;
        int end = word.length();

        // Adjust `start` index if the first character is a punctuation mark
        while (start < end && !Character.isLetterOrDigit(word.charAt(start))) {
            start++;
        }

        // Adjust `end` index if the last character is a punctuation mark
        while (end > start && !Character.isLetterOrDigit(word.charAt(end - 1))) {
            end--;
        }

        // Return the cleaned substring
        return word.substring(start, end);
    }

    /**
     * Validates if a word contains only valid characters (letters or digits).
     * The first and last characters are excluded from the check.
     *
     * @param word the word to validate.
     * @return true if the word is valid, false otherwise.
     */
    private boolean isValidWord(String word) {
        for (int i = 1; i < word.length() - 1; i++) { // Skip first and last character
            if (!Character.isLetterOrDigit(word.charAt(i))) {
                return false; // Contains invalid character
            }
        }
        return true;
    }
}

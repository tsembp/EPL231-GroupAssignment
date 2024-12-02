import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryLoader {
    private TrieNode tree;

    public DictionaryLoader() {
        this.tree = new TrieNode();
    }

    public TrieNode getTree() {
        return this.tree;
    }

    public void loadDictionary(String dictionaryPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = cleanWord(word.trim().toLowerCase()); // Trim, convert to lowercase, and clean word
                if (!word.isEmpty() && isValidWord(word)) { // Check if word is valid
                    tree.insert(word);
                }
            }
        } catch (IOException e) { // catch exception thrown while reading
            e.printStackTrace();
        }

    }

    public void processScript(String scriptPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+"); // trim and split words read
                for (String word : words) {
                    word = cleanWord(word); // clean-up word from punctuation marks at begining and end
                    if (!word.isEmpty()) {
                        tree.searchImportance(word); // search word to assign importance
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void validateDictionary(String dictionaryPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = cleanWord(word.trim().toLowerCase()); // Trim, convert to lowercase, and clean word
                if (!word.isEmpty() && !tree.search(word)) { // if word is not found in tree print debug message
                    System.out.println("Word not found in Trie: " + word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String cleanWord(String word) {
        // Remove punctuation marks at the beginning and end of the word
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

    private boolean isValidWord(String word) {
        for (int i = 1; i < word.length() - 1; i++) { // Skip first and last character
            if (!Character.isLetterOrDigit(word.charAt(i))) {
                return false; // Contains invalid character
            }
        }
        return true;
    }

}

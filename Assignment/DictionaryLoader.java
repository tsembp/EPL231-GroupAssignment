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
                word = word.trim().toLowerCase(); // trim word and convert to lowercase
                if (!word.isEmpty()) { // insert word in tree if is not empty after trimming
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
                word = word.trim().toLowerCase(); // trim and convert word to lowercase
                if (!word.isEmpty() && !tree.search(word)) { // if word is not found in tree print debug message
                    System.out.println("Word not found in Trie: " + word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", ""); // regex to remove all punctuation marks
    }
}

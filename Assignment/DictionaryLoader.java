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
                word = word.trim().toLowerCase();
                if (!word.isEmpty()) {
                    tree.insert(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processScript(String scriptPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+");
                for (String word : words) {
                    word = cleanWord(word);
                    if (!word.isEmpty()) {
                        tree.searchImportance(word);
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
                word = word.trim().toLowerCase();
                if (!word.isEmpty() && !tree.search(word)) {
                    System.out.println("Word not found in Trie: " + word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }
}

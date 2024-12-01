import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrieNodeStatic {
    private static int ALPHABET_SIZE = 26;

    private TrieNodeStatic[] children;        // 26 * 4 bytes (reference)
    private int importance;                   // 4 bytes
    private int wordLength;                   // 4 bytes
                                              // TOTAL: 112 bytes

    public TrieNodeStatic() {
        children = new TrieNodeStatic[ALPHABET_SIZE];
        importance = 0;
        wordLength = 0;
    }

    public static void insert(TrieNodeStatic root, String key) {
        TrieNodeStatic current = root;

        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';

            if (current.children[index] == null) {
                current.children[index] = new TrieNodeStatic();
            }
            current = current.children[index];
        }

        current.wordLength = key.length();
    }

    public static boolean search(TrieNodeStatic root, String key) {
        TrieNodeStatic current = root;

        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';

            if (current.children[index] == null) return false;

            current = current.children[index];
        }

        return current != null && key.length() == current.wordLength;
    }

    public static void searchImportance(TrieNodeStatic root, String key) {
        TrieNodeStatic current = root;

        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';

            if (current.children[index] == null) return;

            current = current.children[index];
        }

        current.incrementImportance();
    }

    public void incrementImportance() {
        this.importance++;
    }

    private static void traverseAndPrintImportance(TrieNodeStatic node, StringBuilder word) {
        if (node == null) return;

        // If a word is found, print its importance
        if (node.wordLength > 0 && node.importance > 0) {
            System.out.println("Word: " + word + ", Importance: " + node.importance);
        }

        // Recurse through all children
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (node.children[i] != null) {
                word.append((char) (i + 'a')); // Append the current character
                traverseAndPrintImportance(node.children[i], word);
                word.deleteCharAt(word.length() - 1); // Backtrack
            }
        }
    }

    public static void traverseAndPrintImportance(TrieNodeStatic root) {
        traverseAndPrintImportance(root, new StringBuilder());
    }

    private static String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static void main(String[] args) {
        /* CONSTRUCT DICTIONARY FILE */
        TrieNodeStatic tree = new TrieNodeStatic();
        String dictionary = "./Assignment/Dictionaries/Plan Example/planDict.txt";
        String scriptPath = "./Assignment/Dictionaries/Plan Example/planScript.txt";

        // Step 1: Read words from the file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    insert(tree, word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit the program if there's an issue with the file
        }

        // Step 2: Reopen the file and check if every word exists in the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+");
                for (String word : words) {
                    word = cleanWord(word);
                    if (!word.isEmpty()) {
                        searchImportance(tree, word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Traverse the Trie and print the importance of words
        System.out.println("Words and their importance:");
        traverseAndPrintImportance(tree);
    }

    public TrieNodeStatic[] getChildren() {
        return this.children;
    }
}

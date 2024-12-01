import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrieNodeStatic {
    // Define the size of the alphabet (English lowercase letters)
    private static int ALPHABET_SIZE = 26;

    // Array of child nodes representing each letter of the alphabet
    private TrieNodeStatic[] children;        // 26 * 4 bytes (reference per child)
    // Importance value for the word (used to track frequency of access)
    private int importance;                   // 4 bytes
    // Length of the word stored at this node (0 if not the end of a word)
    private int wordLength;                   // 4 bytes
                                              // TOTAL: 112 bytes per node

    // Constructor initializes child nodes array and default values
    public TrieNodeStatic() {
        children = new TrieNodeStatic[ALPHABET_SIZE];
        importance = 0;
        wordLength = 0;
    }

    // Inserts a word into the Trie
    public static void insert(TrieNodeStatic root, String key) {
        TrieNodeStatic current = root;

        // Iterate through each character in the word
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a'; // Map character to index (0-25)

            // If the corresponding child node does not exist, create it
            if (current.children[index] == null) {
                current.children[index] = new TrieNodeStatic();
            }
            current = current.children[index]; // Move to the child node
        }

        // Mark the end of the word by setting its length
        current.wordLength = key.length();
    }

    // Searches for a word in the Trie and checks if it exists
    public static boolean search(TrieNodeStatic root, String key) {
        TrieNodeStatic current = root;

        // Traverse the Trie for each character in the word
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';

            // If a character path does not exist, the word is not found
            if (current.children[index] == null) return false;

            current = current.children[index]; // Move to the child node
        }

        // Return true if the word exists and matches the stored length
        return current != null && key.length() == current.wordLength;
    }

    // Searches for a word in the Trie and increments its importance if found
    public static void searchImportance(TrieNodeStatic root, String key) {
        TrieNodeStatic current = root;

        // Traverse the Trie for each character in the word
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';

            // If a character path does not exist, stop the search
            if (current.children[index] == null) return;

            current = current.children[index]; // Move to the child node
        }

        // Increment the importance of the word at the end node
        current.incrementImportance();
    }

    // Increments the importance value of the current node
    public void incrementImportance() {
        this.importance++;
    }

    // Helper method to recursively traverse the Trie and print word importance
    private static void traverseAndPrintImportance(TrieNodeStatic node, StringBuilder word) {
        if (node == null) return;

        // If the current node represents a word, print its importance
        if (node.wordLength > 0 && node.importance > 0) {
            System.out.println("Word: " + word + ", Importance: " + node.importance);
        }

        // Recurse through all possible child nodes
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (node.children[i] != null) {
                word.append((char) (i + 'a')); // Append the current character
                traverseAndPrintImportance(node.children[i], word);
                word.deleteCharAt(word.length() - 1); // Backtrack after recursion
            }
        }
    }

    // Wrapper method to start the Trie traversal and printing process
    public static void traverseAndPrintImportance(TrieNodeStatic root) {
        traverseAndPrintImportance(root, new StringBuilder());
    }

    // Cleans a word by removing non-alphanumeric characters
    private static String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static void main(String[] args) {
        /* INITIALIZE TRIE */
        TrieNodeStatic tree = new TrieNodeStatic();
        // File paths for the dictionary and script files
        String dictionary = "./Assignment/Dictionaries/Plan Example/planDict.txt";
        String scriptPath = "./Assignment/Dictionaries/Plan Example/planScript.txt";

        // Step 1: Read words from the dictionary file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase(); // Convert to lowercase for consistency
                if (!word.isEmpty()) {
                    insert(tree, word); // Insert the word into the Trie
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit the program if there's an issue with the file
        }

        // Step 2: Read the script file and check each word in the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+"); // Split line into words
                for (String word : words) {
                    word = cleanWord(word); // Clean the word by removing non-alphanumeric characters
                    if (!word.isEmpty()) {
                        searchImportance(tree, word); // Increment importance if the word is found
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 3: Traverse the Trie and print words with their importance
        System.out.println("Words and their importance:");
        traverseAndPrintImportance(tree);
    }

    // Getter method to access the children of a Trie node
    public TrieNodeStatic[] getChildren() {
        return this.children;
    }
}

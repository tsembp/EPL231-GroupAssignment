import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportanceTester {

    public static void main(String[] args) {

        TrieNode tree = new TrieNode();
        String dictionary = "dictionary100K.txt"; // Replace with the path to your dictionary file
        String smallFile = "importance.txt"; // Replace with the path to your smaller file

        // Step 1: Read words from the dictionary file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    tree.insert(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 2: Read words from testFile1k.txt and search them in the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(smallFile))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    boolean found = tree.search(word);
                    if (!found) {
                        System.out.println("Word not found: " + word);
                    } else {
                        System.out.println("Word found: " + word);
                    }

                    tree.searchImportance(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

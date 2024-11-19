import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {

    public static void main(String[] args) {
        TrieNode tree = new TrieNode();
        String filename = "testFile1.txt"; // Replace with the path to your file

        // Read words from the file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = br.readLine()) != null) {
                // tree.printTree();
                word = word.trim(); // Remove leading and trailing whitespace
                if (!word.isEmpty()) {
                    tree.insert(word);
                    System.out.println("Word is: " + word);
                    System.out.println("Searching " + word + ".Found = " + tree.search(word));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Optionally, print the Trie to verify the words have been inserted
        tree.printTree();
    }
}

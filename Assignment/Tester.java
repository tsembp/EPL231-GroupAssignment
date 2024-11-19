import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {

    public static void main(String[] args) {
        TrieNode tree = new TrieNode();
        String filename = "testoFile.txt"; // Replace with the path to your file

        // Step 1: Read words from the file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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

        // Step 2: Read words from the file again and search them in the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    boolean found = tree.search(word);
                    // System.out.println("Searching \"" + word + "\". Found = " + found);
                    if(!found) {
                        System.out.println("Word not found: " + word);
                        boolean testo = tree.search(word, found);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(tree.search("ad"));
    }

    // public static void main(String[] args) {
    //     TrieNode tree = new TrieNode();

    //     tree.insert("add");
    //     tree.insert("address");
    //     tree.insert("administration");
    //     tree.insert("admit");
    //     tree.insert("adult");

    //     System.out.println(tree.search("add"));
    //     System.out.println(tree.search("address"));
    //     System.out.println(tree.search("administration"));
    //     System.out.println(tree.search("admit"));
    //     System.out.println(tree.search("adult"));
    // }
}

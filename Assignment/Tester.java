import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {

    public static void main(String[] args) {
        TrieNode tree = new TrieNode();
        String filename = "testFile100K.txt"; // Replace with the path to your file

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
    }

    //     System.out.println(tree.search("ad"));
    // }

    // public static void main(String[] args) {
    //     TrieNode tree = new TrieNode();

    //     // Insert the provided words into the Trie
    //     String[] words = {
    //         "ability", "able", "about", "above", "accept", "according", "account", "across", 
    //         "act", "action", "activity", "actually", "add", "address", "administration", 
    //         "admit", "adult", "affect", "after", "again", "against", "age", "agency", "agent", 
    //         "ago", "agree", "agreement", "ahead", "air", "all", "allow", "almost", "alone", 
    //         "along", "already", "also", "although", "always", "American", "among", "amount", 
    //         "analysis", "and", "animal", "another", "answer", "any", "anyone", "anything", 
    //         "appear", "apply", "approach", "area", "argue", "arm", "around", "arrive", "art", 
    //         "article", "artist", "as", "ask", "assume", "at", "attack", "attention", "attorney", 
    //         "audience", "author", "authority", "available", "avoid", "away"
    //     };

    //     for (String word : words) {
    //         tree.insert(word);
    //     }

    //     // Perform search for every word in words[] and print the results
    //     for (String word : words) {
    //         System.out.println("Word: " + word + " - Found: " + tree.search(word));
    //     }
    // }
}

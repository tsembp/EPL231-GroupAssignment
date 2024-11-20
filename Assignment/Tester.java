import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {

    public static void main(String[] args) {

        TrieNode tree = new TrieNode();
        String dictionary = "dictionary100K.txt"; // Replace with the path to your file

        // Step 1: Read words from the file and insert them into the Trie
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

        // Step 2: Read words from the file again and search them in the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                if(word.equals("has")) System.out.println("Read has");
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

        String filename = "importance.txt"; // Replace with the path to your file

        // Step 1: Read words from the file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+"); // Split line into words
                for (String word : words) {
                    String cleanWord = word.replaceAll("[.,]", ""); // Remove punctuation
                    if (!cleanWord.isEmpty()) {
                        // tree.insert(cleanWord);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // // Step 2: Read the file again to detect words with '.' or ',' and search them in the Trie
        // try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        //     String line;
        //     while ((line = br.readLine()) != null) {
        //         String[] words = line.trim().toLowerCase().split("\\s+"); // Split line into words
        //         for (String word : words) {
        //             if (word.endsWith(".") || word.endsWith(",")) {
        //                 String cleanWord = word.replaceAll("[.,]", ""); // Remove punctuation
        //                 boolean found = tree.search(cleanWord);
        //                 if (!found) {
        //                     System.out.println("Word not found: " + cleanWord);
        //                 } else{
                            
        //                 }
        //             }
        //         }
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}


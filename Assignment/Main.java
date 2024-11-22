import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /* CONSTRUCT DICTIONARY FILE */
        TrieNode tree = new TrieNode();
        String dictionary = "dictionary.txt"; // Replace with the path to your file

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
                if (!word.isEmpty()) {
                    boolean found = tree.search(word);
                    // System.out.println("Searching \"" + word + "\". Found = " + found);
                    if(!found) {
                        // System.out.println("Word not found: " + word);
                        boolean testo = tree.search(word, found);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        /* CALCULATE IMPORTANCE OF EACH WORD OF THE DICTIONARY */
        String filename = "script.txt"; // Replace with the path to your file
        String outputFile = "scriptEdited.txt";

        // Step 1: Read words from the file, clean them, and write to the output file
        try (BufferedReader br = new BufferedReader(new FileReader(filename));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+"); // Split line into words
                for (String word : words) {
                    // Clean the word by removing unwanted punctuation
                    String cleanWord = word.replaceAll("[\"“”.,?}{-]", "");
                    if (!cleanWord.isEmpty()) {
                        // Write the cleaned word with a punctuation mark to the output file
                        // bw.write(cleanWord + ". ");
                        tree.searchImportance(cleanWord);
                    }
                }
                // Add a newline after processing each line
                bw.newLine();
            }
            
            System.out.println("Processing completed. Cleaned words are written to " + outputFile);
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
        //                 String cleanWord = word.replaceAll("[.,?}{-]", ""); // Remove punctuation
        //                 tree.searchImportance(cleanWord);
        //             }
        //         }
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        

        Scanner scanner = new Scanner(System.in);
        String word = "";
        int k = 0;

        while(true){
            System.out.print("Enter a word (or 'exit' to end): ");
            word = scanner.nextLine();

            if(word.equals("exit")){
                System.out.println("End of program.");
                break;
            }

            System.out.println("Enter number of alternative words (k): ");
            try {
                k = Integer.parseInt(scanner.nextLine());

                prefix(word, k, tree);
                


                
            } catch (NumberFormatException e) {
                System.out.println("Please insert a valid number.");
            }

            System.out.println(); 
        }

        scanner.close();
    }

    public static void prefix(String searchWord, int k, TrieNode dictionaryTree ){
        // Element[] altWords = new Element[k];

        if(!dictionaryTree.search(searchWord)){
            System.out.println("Word " + searchWord + " not found (prefix method).");
            // return null;
            return;
        } 

        // Go to node that searchWord ends at
        TrieNode current = dictionaryTree;
        for(char c : searchWord.toCharArray()){
            int index = current.hash.getIndex(c);

            current = current.hash.table[index].node;
        }
        // Now current is pointing at the node in which the searchWord's last letter is at

        // RobinHoodHashing hash = current.hash;
        StringBuilder str = new StringBuilder(searchWord);
        MinHeap heap = new MinHeap(k);
        traverseTrie(current, searchWord, str, heap, 1);
        traverseTrie(current, searchWord, str, heap, 2);



        heap.print();

        // return null;
    }

    private static void traverseTrie(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap, int criteriaFlag) {
        if(node == null) return;

        for(Element element : node.hash.table) {
            if(element != null) {
                // Append element key at the word
                currentWord.append(element.key);

                // Check if isWord and add to storage data structure
                if(element.isWord) {

                    if(heap.isFull()) {
                        // CRITERIA 1
                        if(criteriaFlag == 1){
                            // We know that the currentWord is indeed a prefix of searchWord, so we just check their importance
                            // If heap is full, replace root element with current element (if its importance is less)
                            if(element.getImportance() > heap.getRootImportance()) {
                                heap.remove();
                                heap.insert(element);
                            }
                        }
                        // CRITERIA 2
                        else if (criteriaFlag == 2){
                            // If heap is full, replace the root element with the current element (if criteria 2 applies)
                            if(currentWord.length() == searchWord.length()){
                                boolean criteria2Result = differentByTwoChars(currentWord.toString(), searchWord);
                                if(criteria2Result){
                                    System.out.println("found " + currentWord.toString() + " and " + searchWord + " differ!!");
                                }
                                // If the two words differ by two AND element's importance is less => replace with heap's root
                                if(criteria2Result && element.getImportance() > heap.getRootImportance()){
                                    heap.remove();
                                    heap.insert(element);
                                }
                            }
                        }
                        // CRITERIA 3
                        else if(criteriaFlag == 3){

                        } else{
                            System.out.println("Wrong criteria flag value passed in traverseTrie().");
                            return;
                        }
                    } else {
                        heap.insert(element);
                    }

                }

                // Recursively traverse downwards
                if (element.node != null) {
                    traverseTrie(element.node, searchWord, currentWord, heap, criteriaFlag);
                }

                // When recursion is complete, explore more paths from other elements in the hash table
                currentWord.deleteCharAt(currentWord.length() - 1);

            }
        }
    }

    private static boolean differentByTwoChars(String s1, String s2){
        // No need to check for s1.length() and s2.length(), we checked that before
        int diffCount = 0;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diffCount++;
            }
    
            // If more than two characters are different, return false early
            if (diffCount > 2) {
                return false;
            }
        }
    
        // Return true if exactly two characters are different
        return diffCount == 2;
    }
}

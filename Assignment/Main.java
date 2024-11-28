import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static int recursiveCallsTraverse = 0;
    static int recursiveCalls2 = 0;
    static int recursiveCalls3 = 0;

    public static void main(String[] args) {
        /* CONSTRUCT DICTIONARY FILE */
        TrieNode tree = new TrieNode();
        String dictionary = "testDictionary.txt"; // Replace with the path to your file

        // Step 1: Read words from the file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                word = word.replaceAll("[\"“”.,?}{-]!", "");
                if (!word.isEmpty() && !containsPunctuation(word)) {
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
                    if(!found){
                        System.out.println("Word not found: " + word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* CALCULATE NUMBER OF NODES WITH 5, 11, 19 and 29 SIZE HASH TABLE */
        int[] counts = new int[4];
        traverseTrie(tree, counts);
        System.out.println("Nodes with hash table size 5: " + counts[0]);
        System.out.println("Nodes with hash table size 11: " + counts[1]);
        System.out.println("Nodes with hash table size 19: " + counts[2]);
        System.out.println("Nodes with hash table size 29: " + counts[3]);

        /* CALCULATE IMPORTANCE OF EACH WORD OF THE DICTIONARY */
        String filename = "script.txt"; // Replace with the path to your file
        // String outputFile = "scriptEdited.txt";

        // Step 1: Read words from the file, clean them, and write to the output file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().toLowerCase().split("\\s+"); // Split line into words
                for (String word : words) {
                    // Clean the word by removing unwanted punctuation
                    word = word.toLowerCase();

                    // If word contains punctuation marks in-between (excluding first and last char) move to next word
                    if(containsPunctuation(word)) continue;

                    // Remove all punctuations in the word
                    word = word.replaceAll("[^a-zA-Z0-9]", "");
                    
                    // Search word's importance
                    if (!word.isEmpty() && !containsPunctuation(word)) {
                        tree.searchImportance(word);
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                findRelevantWords(word, k, tree);
            } catch (NumberFormatException e) {
                System.out.println("Please insert a valid number.");
            }
            System.out.println(); 
        }
        scanner.close();
    }

    public static void findRelevantWords(String searchWord, int k, TrieNode dictionaryTree ){
        if(!dictionaryTree.search(searchWord)){
            System.out.println("Word " + searchWord + " not found (prefix method).");
            return;
        } 

        // Go to node that searchWord ends at
        TrieNode current = dictionaryTree;
        for(char c : searchWord.toCharArray()){
            int index = current.getHashTable().getIndex(c);
            current = current.getHashTable().getTable()[index].getNode();
        }
        // Now current is pointing at the node in which the searchWord's last letter is at

        StringBuilder str3 = new StringBuilder(searchWord);
        StringBuilder str4 = new StringBuilder();
        MinHeap heap2 = new MinHeap(k);

        findWordsCriteria1(current, searchWord, str3, heap2, 1); // Find words that adhere to criteria 1
        findWordsCriteria2(dictionaryTree, searchWord, str4, heap2, 2); // Find words that adhere to criteria 2
        findWordsCriteria3(dictionaryTree, searchWord, str4, heap2, 3); // Find words that adhere to criteria 3

        heap2.printList();

        System.out.println("Recursvie calls in travserse: " + recursiveCallsTraverse);
        System.out.println("Recursive calls in criteria 2: " + recursiveCalls2);
        System.out.println("Recursive calls in criteria 3: " + recursiveCalls3);
    }

    private static void findWordsCriteria1(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap, int criteriaFlag){
        if(node == null) return;

        for(Element element : node.getHashTable().getTable()) {
            if(element != null) {
                // Append element key at the word
                currentWord.append(element.getKey());

                // Check if isWord and add to storage data structure
                if(node.getWordLength() != 0 && element.getImportance() != 0) {
                    // Insert in heap
                    heap.insert(currentWord.toString(), element.getImportance());
                }

                // Recursively traverse downwards
                if (element.getNode() != null) {
                    findWordsCriteria1(element.getNode(), searchWord, currentWord, heap, criteriaFlag);
                }
                // When recursion is complete, explore more paths from other elements in the hash table
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private static void findWordsCriteria2(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap, int criteriaFlag){
        if (node == null) return;

        recursiveCalls2++;
    
        // Stop traversal if the current word exceeds the length of searchWord
        if (currentWord.length() > searchWord.length()) { return; } // No need to explore further if the word is longer

        // Continue recursively traversing the Trie for other possible words
        for (Element element : node.getHashTable().getTable()) {
            if (element != null) {
                currentWord.append(element.getKey());  // Append current character to the word

                if(node.getWordLength() != 0 && element.getImportance() != 0) {
                    if(currentWord.length() == searchWord.length()){
                        boolean criteria2Result = differentByTwoChars(currentWord.toString(), searchWord);
                        // If the two words differ by two AND element's importance is less => replace with heap's root
                        if(criteria2Result){
                            heap.insert(currentWord.toString(), element.getImportance());
                        }
                    }
                }
                // Recursively traverse the Trie for the next level of nodes
                if (element.getNode() != null) {
                    findWordsCriteria2(element.getNode(), searchWord, currentWord, heap, criteriaFlag);
                }
    
                // Backtrack by removing the last character
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }
    
    private static void findWordsCriteria3(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap, int criteriaFlag) {
        if(node == null) return;

        recursiveCalls3++;

        if(currentWord.length() > searchWord.length() + 2) { return; }

        for(Element element : node.getHashTable().getTable()) {
            if(element != null) {
                // Append element key at the word
                currentWord.append(element.getKey());

                // Check if isWord and add to storage data structure
                if(node.getWordLength() != 0 && element.getImportance() != 0) {
                    if(isValidWord(searchWord, currentWord.toString())) { // If current word is valid
                        heap.insert(currentWord.toString(), element.getImportance());
                    }
                }

                // Recursively traverse downwards
                if (element.getNode() != null) {
                    findWordsCriteria3(element.getNode(), searchWord, currentWord, heap, criteriaFlag);
                }

                // When recursion is complete, explore more paths from other elements in the hash table
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private static void traverseTrie(TrieNode node, int[] counts) {
        if (node == null) return;
    
        // Check the size of the hash table and update the corresponding count
        int hashTableSize = node.getHashTable().getTable().length;
        if (hashTableSize == 5) {
            counts[0]++;
        } else if (hashTableSize == 11) {
            counts[1]++;
        } else if (hashTableSize == 19) {
            counts[2]++;
        } else if (hashTableSize == 29) {
            counts[3]++;
        }
    
        // Recursively traverse the child nodes in the hash table
        for (Element element : node.getHashTable().getTable()) {
            if (element != null && element.getNode() != null) {
                traverseTrie(element.getNode(), counts);
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
        // Return true if the difference is less than or equal to 2 characters
        return true;
    }

    private static boolean isValidWord(String inputWord, String suggestedWord) {
        int inputLength = inputWord.length();
        int suggestedLength = suggestedWord.length();
    
        // Check length constraint
        if (suggestedLength > inputLength + 2 || suggestedLength < inputLength - 1) {
            return false;
        }
        if(suggestedLength < inputLength) { // If smaller
            int i = 0;
            int j = 0;
            while(i < suggestedLength && j < inputLength) {
                if(inputWord.charAt(j) == suggestedWord.charAt(i)) {
                    j++;
                    i++;
                } else {
                    j++;
                }
            }
            return i == suggestedLength;
        }
        int i = 0;
        int j = 0;
        while(j < suggestedLength && i < inputLength) {
            if(inputWord.charAt(i) == suggestedWord.charAt(j)) {
                j++;
                i++;
            } else {
                j++;
            }
        }
        return i == inputLength;
    }

    private static boolean containsPunctuation(String word) {
        if (word.length() <= 2) {
            return false; // No inner characters to check for punctuation
        }
        for (int i = 1; i < word.length() - 1; i++) {
            char c = word.charAt(i);
            if (!Character.isLetterOrDigit(c)) { // Check for non-alphanumeric characters
                return true;
            }
        }
        return false;
    }

}

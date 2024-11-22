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
        String filename = "outputScript_with_spaces.txt"; // Replace with the path to your file
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
                        tree.searchImportance(cleanWord);
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

                findRelevantWords(word, k, tree);
                


                
            } catch (NumberFormatException e) {
                System.out.println("Please insert a valid number.");
            }

            System.out.println(); 
        }

        scanner.close();
    }

    public static void findRelevantWords(String searchWord, int k, TrieNode dictionaryTree ){
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
        StringBuilder str1 = new StringBuilder(searchWord);
        StringBuilder str2 = new StringBuilder();
        MinHeap heap = new MinHeap(k);
        traverseTrie(current, searchWord, str1, heap, 1); // Find words that adhere to criteria 1
        traverseTrie(dictionaryTree, searchWord, str2, heap, 2); // Find words that adhere to criteria 2
        traverseTrie(dictionaryTree, searchWord, str2, heap, 3); // Find words that adhere to criteria 3

        heap.print();

    }

    private static void findWordsCriteria1(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap, int criteriaFlag){
        if(node == null) return;

        for(Element element : node.hash.table) {
            if(element != null) {
                // Append element key at the word
                currentWord.append(element.key);

                // Check if isWord and add to storage data structure
                if(element.isWord) {

                    if(heap.isFull()) {
                        // We know that the currentWord is indeed a prefix of searchWord, so we just check their importance
                        // If heap is full, replace root element with current element (if its importance is less)
                        if(element.getImportance() > heap.getRootImportance()) {
                            heap.remove();
                            heap.insert(element);
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

    private static void findWordsCriteria2(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap, int criteriaFlag){
        if (node == null) return;
    
        // Stop traversal if the current word exceeds the length of searchWord
        if (currentWord.length() > searchWord.length()) {
            return;  // No need to explore further if the word is longer
        }
    
        // Check for words with the same length as searchWord
        if (currentWord.length() == searchWord.length()) {
            // Check if the word differs by at most two characters
            for (Element element : node.hash.table) {
                if (element != null) {
                    // Only consider elements that are words and have the same length as the searchWord
                    if (element.isWord && differentByTwoChars(currentWord.toString(), searchWord)) {
                        // If heap is full, compare the current word's importance with the heap's root
                        if (heap.isFull()) {
                            if (element.getImportance() > heap.getRootImportance()) {
                                heap.remove();
                                heap.insert(element);
                            }
                        } else {
                            // If heap is not full, simply insert the element
                            heap.insert(element);
                        }
                    }
                }
            }
        }
    
        // Continue recursively traversing the Trie for other possible words
        for (Element element : node.hash.table) {
            if (element != null) {
                currentWord.append(element.key);  // Append current character to the word
    
                // Recursively traverse the Trie for the next level of nodes
                if (element.node != null) {
                    findWordsCriteria2(element.node, searchWord, currentWord, heap, criteriaFlag);
                }
    
                // Backtrack by removing the last character
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }
    
    private static void findWordsCriteria3(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap, int criteriaFlag) {
        if(node == null) return;

        for(Element element : node.hash.table) {
            if(element != null) {
                // Append element key at the word
                currentWord.append(element.key);

                // Check if isWord and add to storage data structure
                if(element.isWord) {

                    if(heap.isFull()) {
                        if(isValidWord(searchWord, currentWord.toString())) { // If current word is valid
                            System.out.println("Found valid word which is: " + currentWord.toString());
                            if(currentWord.toString().equals("pan")) { 
                                System.out.println("Found pan with importance of: " + element.getImportance()); 
                                System.out.println("Current heap front is: " + heap.Heap[0].word + " with importance of: " + heap.Heap[0].getImportance());
                            }
                            if(element.getImportance() > heap.getRootImportance()) {
                                System.out.println("Printing heap before inserting: ");
                                heap.print();
                                System.out.println("Switching heap front: " + heap.Heap[0].word + " with " + element.getWord());
                                if(element.getWord().equals("pan")) {
                                    System.out.println(heap.search(element));
                                }
                                if(!heap.search(element)) { // If the element doesn't exist in the heap already insert it
                                    System.out.println("In removing");
                                    heap.remove();
                                    heap.insert(element);
                                }
                                System.out.println("Printing heap after inserting: ");
                                heap.print();
                            }
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
                                // If the two words differ by two AND element's importance is less => replace with heap's root
                                if(criteria2Result && element.getImportance() > heap.getRootImportance()){
                                    if(!heap.search(element)) { // If the element doesn't exist in the heap already insert it
                                        heap.remove();
                                        heap.insert(element);
                                    }
                                }
                            }
                        }
                        // CRITERIA 3
                        else if(criteriaFlag == 3) {
                            if(isValidWord(searchWord, currentWord.toString())) { // If current word is valid
                                System.out.println("Found valid word which is: " + currentWord.toString());
                                if(currentWord.toString().equals("pan")) { 
                                    System.out.println("Found pan with importance of: " + element.getImportance()); 
                                    System.out.println("Current heap front is: " + heap.Heap[0].word + " with importance of: " + heap.Heap[0].getImportance());
                                }
                                if(element.getImportance() > heap.getRootImportance()) {
                                    System.out.println("Printing heap before inserting: ");
                                    heap.print();
                                    System.out.println("Switching heap front: " + heap.Heap[0].word + " with " + element.getWord());
                                    if(element.getWord().equals("pan")) {
                                        System.out.println(heap.search(element));
                                    }
                                    if(!heap.search(element)) { // If the element doesn't exist in the heap already insert it
                                        System.out.println("In removing");
                                        heap.remove();
                                        heap.insert(element);
                                    }
                                    System.out.println("Printing heap after inserting: ");
                                    heap.print();
                                }
                            }
                        } else {
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

}

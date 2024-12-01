import java.util.Scanner;

public class SearchEngine {
    private TrieNode tree;

    public SearchEngine(TrieNode tree) {
        this.tree = tree;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Retrieve input from user (word and number of relevant words)
        while (true) {
            System.out.print("Enter a word (or '0' to end): ");
            String word = scanner.nextLine();

            // Handle exit code
            if ("0".equalsIgnoreCase(word)) {
                System.out.println("End of program.");
                break;
            }

            System.out.print("Enter the number of alternative words (k): ");
            int k;
            try {
                // Retrieve heap size
                k = Integer.parseInt(scanner.nextLine());
                
                // Find 'k' relevant words to 'word'
                findRelevantWords(word, k);
            } catch (NumberFormatException e) { // catch exception of reading string
                System.out.println("Please enter a valid number.");
            }
            System.out.println();
        }

        scanner.close();
    }

    public void findRelevantWords(String searchWord, int k) {
        // If 'searchWord' is not in tree, we can't find relevant words
        if (!tree.search(searchWord)) {
            System.out.println("Word " + searchWord + " not found in the dictionary");
            return;
        }

        TrieNode current = tree;
        for (char c : searchWord.toCharArray()) {
            int index = current.getHashTable().getIndex(c);
            current = current.getHashTable().getTable()[index].getNode();
        }

        // Get most important words in heap
        MinHeap heap = new MinHeap(k);
        findWordsCriteria1(current, searchWord, new StringBuilder(searchWord), heap);
        findWordsCriteria2(tree, searchWord, new StringBuilder(), heap);
        findWordsCriteria3(tree, searchWord, new StringBuilder(), heap);

        // Print heap contents
        heap.printList();
    }

    private void findWordsCriteria1(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap) {
        if (node == null) return;

        for (Element element : node.getHashTable().getTable()) { // for each element in the hash table
            if (element != null) { // if element has a value
                currentWord.append(element.getKey()); // append char value of element to word constructed

                if (element.getImportance() != 0) { // if the word has an importance associated -> insert to heap
                    heap.insert(currentWord.toString(), element.getImportance());
                }
                if (element.getNode() != null) { // if element points to another node (has more letters), explore
                    findWordsCriteria1(element.getNode(), searchWord, currentWord, heap);
                }

                // Delete the last character to explore more words
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private void findWordsCriteria2(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap) {
        if (node == null || currentWord.length() > searchWord.length()) return;

        for (Element element : node.getHashTable().getTable()) { // for each element in the hash table
            if (element != null) { // if element has a value
                currentWord.append(element.getKey()); // append char value of element to word constructed

                // Check if word has an importance and its the same length as the user's input
                if (element.getImportance() != 0 && currentWord.length() == searchWord.length()) {
                    // Check if words differ max by 2 characters
                    boolean criteria2Result = differentByTwoChars(currentWord.toString(), searchWord);
                    
                    if (criteria2Result) {
                        // insert to heap if word adheres to criteria 2 rules
                        heap.insert(currentWord.toString(), element.getImportance());
                    }
                }

                // Explore more nodes below the current element (DFS call)
                if (element.getNode() != null) {
                    findWordsCriteria2(element.getNode(), searchWord, currentWord, heap);
                }

                // Delete the last character to explore more words
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private void findWordsCriteria3(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap) {
        // Reached leaf (node = null) or found word with more than 2 size more
        if (node == null || currentWord.length() > searchWord.length() + 2) return;

        for (Element element : node.getHashTable().getTable()) {
            if (element != null) {
                currentWord.append(element.getKey()); // append element's key

                // If word has importance and is a vaid word insert it to heap
                if (element.getImportance() != 0 && isValidWord(searchWord, currentWord.toString())) {
                    heap.insert(currentWord.toString(), element.getImportance());
                }

                // Explore more nodes below the current element (DFS call)
                if (element.getNode() != null) {
                    findWordsCriteria3(element.getNode(), searchWord, currentWord, heap);
                }

                // Delete the last character to explore more words
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private boolean differentByTwoChars(String s1, String s2) {
        int diffCount = 0;

        for (int i = 0; i < s1.length(); i++) {
            // Mismatched characters
            if (s1.charAt(i) != s2.charAt(i)) {
                diffCount++;
            }

            // Skip unessecary iterations
            if (diffCount > 2) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidWord(String inputWord, String suggestedWord) {
        int inputLength = inputWord.length();
        int suggestedLength = suggestedWord.length();
    
        // If the length difference exceeds the allowed range, return false.
        if (suggestedLength > inputLength + 2 || suggestedLength < inputLength - 1) {
            return false;
        }
    
        // Handle case where the suggested word is shorter than the input word.
        if (suggestedLength < inputLength) {
            int i = 0, j = 0;

            // Traverse both words, ensuring all characters of the suggested word match in order.
            while (i < suggestedLength && j < inputLength) {
                if (inputWord.charAt(j) == suggestedWord.charAt(i)) {
                    i++; // match found, move both pointers.
                }

                j++; // always move input pointer.
            }
            return i == suggestedLength;
        }
    
        // Handle case where the suggested word is equal or longer than the input word.
        int i = 0, j = 0;

        // Traverse both words, ensuring all characters of the input word match in order.
        while (j < suggestedLength && i < inputLength) {
            if (inputWord.charAt(i) == suggestedWord.charAt(j)) {
                i++; // match found, move both pointers.
            }
            j++; // always move suggested pointer.
        }
        
        return i == inputLength;
    }

}

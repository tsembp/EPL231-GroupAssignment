import java.util.Scanner;

public class SearchEngine {
    private TrieNode tree;

    public SearchEngine(TrieNode tree) {
        this.tree = tree;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a word (or 'exit' to end): ");
            String word = scanner.nextLine();

            if ("exit".equalsIgnoreCase(word)) {
                System.out.println("End of program.");
                break;
            }

            System.out.print("Enter the number of alternative words (k): ");
            int k;
            try {
                k = Integer.parseInt(scanner.nextLine());
                findRelevantWords(word, k);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
            System.out.println();
        }

        scanner.close();
    }

    public void findRelevantWords(String searchWord, int k) {
        if (!tree.search(searchWord)) {
            System.out.println("Word " + searchWord + " not found in the dictionary");
            return;
        }

        TrieNode current = tree;
        for (char c : searchWord.toCharArray()) {
            int index = current.getHashTable().getIndex(c);
            current = current.getHashTable().getTable()[index].getNode();
        }

        MinHeap heap = new MinHeap(k);
        findWordsCriteria1(current, searchWord, new StringBuilder(searchWord), heap);
        findWordsCriteria2(tree, searchWord, new StringBuilder(), heap);
        findWordsCriteria3(tree, searchWord, new StringBuilder(), heap);

        heap.printList();
    }

    private void findWordsCriteria1(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap) {
        if (node == null) return;

        for (Element element : node.getHashTable().getTable()) {
            if (element != null) {
                currentWord.append(element.getKey());
                if (element.getImportance() != 0) {
                    heap.insert(currentWord.toString(), element.getImportance());
                }
                if (element.getNode() != null) {
                    findWordsCriteria1(element.getNode(), searchWord, currentWord, heap);
                }
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private void findWordsCriteria2(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap) {
        if (node == null || currentWord.length() > searchWord.length()) return;

        for (Element element : node.getHashTable().getTable()) {
            if (element != null) {
                currentWord.append(element.getKey());

                if (element.getImportance() != 0 && currentWord.length() == searchWord.length()) {
                    boolean criteria2Result = differentByTwoChars(currentWord.toString(), searchWord);
                    if (criteria2Result) {
                        heap.insert(currentWord.toString(), element.getImportance());
                    }
                }

                if (element.getNode() != null) {
                    findWordsCriteria2(element.getNode(), searchWord, currentWord, heap);
                }

                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private void findWordsCriteria3(TrieNode node, String searchWord, StringBuilder currentWord, MinHeap heap) {
        if (node == null || currentWord.length() > searchWord.length() + 2) return;

        for (Element element : node.getHashTable().getTable()) {
            if (element != null) {
                currentWord.append(element.getKey());

                if (element.getImportance() != 0 && isValidWord(searchWord, currentWord.toString())) {
                    heap.insert(currentWord.toString(), element.getImportance());
                }

                if (element.getNode() != null) {
                    findWordsCriteria3(element.getNode(), searchWord, currentWord, heap);
                }

                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    private boolean differentByTwoChars(String s1, String s2) {
        int diffCount = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diffCount++;
            }
            if (diffCount > 2) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidWord(String inputWord, String suggestedWord) {
        int inputLength = inputWord.length();
        int suggestedLength = suggestedWord.length();

        if (suggestedLength > inputLength + 2 || suggestedLength < inputLength - 1) {
            return false;
        }
        if (suggestedLength < inputLength) {
            int i = 0, j = 0;
            while (i < suggestedLength && j < inputLength) {
                if (inputWord.charAt(j) == suggestedWord.charAt(i)) {
                    j++;
                    i++;
                } else {
                    j++;
                }
            }
            return i == suggestedLength;
        }
        int i = 0, j = 0;
        while (j < suggestedLength && i < inputLength) {
            if (inputWord.charAt(i) == suggestedWord.charAt(j)) {
                j++;
                i++;
            } else {
                j++;
            }
        }
        return i == inputLength;
    }
}

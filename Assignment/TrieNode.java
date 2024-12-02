/**
 * Represents a node in a Trie data structure. Each node maintains a Robin Hood Hash Table
 * to manage its child elements and supports operations like insertion, search, and importance tracking.
 */
public class TrieNode {

    private RobinHoodHashing hash; // Hash table for storing child elements

    /**
     * Constructs a TrieNode with an empty Robin Hood Hash Table.
     */
    public TrieNode() {
        hash = new RobinHoodHashing();
    }

    /**
     * Retrieves the hash table of the TrieNode.
     *
     * @return the hash table associated with this node.
     */
    public RobinHoodHashing getHashTable() {
        return this.hash;
    }

    /**
     * Inserts a word into the Trie. Creates new nodes as necessary.
     *
     * @param key the word to insert into the Trie.
     */
    public void insert(String key) {
        if (key == null || key.equals("")) return;

        TrieNode current = this;
        int finalIndex = 0;

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            // Get the index of the character in the hash table
            int index = current.hash.getIndex(c);
            if (index == -1) { // Character not present, insert into hash table
                current.hash.insert(c);
                index = current.hash.getIndex(c); // Get index after insertion
            }

            // If last character, break to keep reference to current node
            if (i == key.length() - 1) {
                finalIndex = index;
                break;
            }

            // If the node at the current index is null, create a new node
            if (current.hash.getTable()[index].getNode() == null) {
                current.hash.getTable()[index].setNode(new TrieNode());
            }

            // Move to the next node
            current = current.hash.getTable()[index].getNode();
        }

        // Mark the final character as the end of a word
        current.hash.getTable()[finalIndex].setIsWord(true);
    }

    /**
     * Searches for a word in the Trie.
     *
     * @param key the word to search for.
     * @return true if the word exists in the Trie, false otherwise.
     */
    public boolean search(String key) {
        TrieNode current = this;
        int finalIndex = 0;

        // Iterate over each character in the word
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            // Get the index of the character in the hash table
            int index = current.hash.getIndex(c);
            if (index == -1) { // Character not found
                return false;
            }

            // If last character, break to keep reference to the current node
            if (i == key.length() - 1) {
                finalIndex = index;
                break;
            }

            // If the node at the current index is null, word doesn't exist
            if (current.hash.getTable()[index].getNode() == null) {
                return false;
            }

            // Move to the next node
            current = current.hash.getTable()[index].getNode();
        }

        // Return true if the final character marks the end of a word
        return finalIndex != -1 && current.hash.getTable()[finalIndex] != null
                && current.hash.getTable()[finalIndex].getIsWord();
    }

    /**
     * Searches for a word in the Trie and increases its importance if found.
     *
     * @param key the word to search for.
     */
    public void searchImportance(String key) {
        TrieNode current = this;

        // Iterate over each character in the word
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            // Get the index of the character in the hash table
            int index = current.hash.getIndex(c);

            // If last character, break to keep reference to the current node
            if (i == key.length() - 1) {
                break;
            }

            if (index == -1 || current.hash.getTable()[index].getNode() == null) {
                return; // Word doesn't exist
            }

            // Move to the next node
            current = current.hash.getTable()[index].getNode();
        }

        int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
        if (finalIndex != -1 && current.hash.getTable()[finalIndex] != null
                && current.hash.getTable()[finalIndex].getIsWord()) {
            increaseImportance(current.hash.getTable()[finalIndex]);
        }
    }

    /**
     * Increases the importance of the given element.
     *
     * @param element the element whose importance is to be increased.
     */
    private void increaseImportance(Element element) {
        element.setImportance(element.getImportance() + 1);
    }

    /**
     * Reconstructs the word leading to a specific node in the Trie.
     *
     * @param root the root of the Trie.
     * @param targetNode the node for which the word is to be reconstructed.
     * @return the word leading to the target node, or null if the node is not found.
     */
    public String reconstructWord(TrieNode root, TrieNode targetNode) {
        StringBuilder word = new StringBuilder();
        if (findWordDFS(root, targetNode, word)) {
            return word.toString();
        }
        return null;
    }

    /**
     * Performs a depth-first search to find a target node and reconstruct the word leading to it.
     *
     * @param currentNode the current node in the search.
     * @param targetNode the target node.
     * @param word the StringBuilder to build the word.
     * @return true if the target node is found, false otherwise.
     */
    private boolean findWordDFS(TrieNode currentNode, TrieNode targetNode, StringBuilder word) {
        if (currentNode == targetNode) {
            return true; // Found the target node
        }

        for (Element element : currentNode.getHashTable().getTable()) {
            if (element != null) {
                word.append(element.getKey()); // Add the character to the word
                if (findWordDFS(element.getNode(), targetNode, word)) {
                    return true; // If found in this path, return true
                }
                word.deleteCharAt(word.length() - 1); // Backtrack
            }
        }

        return false; // Not found in this path
    }

    /**
     * Traverses the Trie from the given node and prints all words with their importance values.
     *
     * @param node the starting node for traversal.
     * @param currentWord the StringBuilder to construct words during traversal.
     */
    public void traverse(TrieNode node, StringBuilder currentWord) {
        if (node == null) return;

        for (Element element : node.getHashTable().getTable()) {
            if (element != null) {
                currentWord.append(element.getKey()); // Add the character to form the word

                // If this element represents a complete word, print its importance
                if (element.getIsWord()) {
                    System.out.println(currentWord.toString() + ": Importance = " + element.getImportance());
                }

                // Recursively traverse the next node
                if (element.getNode() != null) {
                    traverse(element.getNode(), currentWord);
                }

                // Backtrack
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }
}

public class TrieTree {
    
    TrieNode root;

    public TrieTree(){
        root = new TrieNode();
    }

    public void insert(String word){
        if(word == null || word == "") return;

        TrieNode current = root;

        for(char c : word.toCharArray()){
            int index = current.hash.hashFunction(c);
            if(!current.hash.search(c)){ // iterate up to not found char c
                current.insert(c); // insert char
            }

            if (current.hash.table[index].node == null) {
                current.hash.table[index].node = new TrieNode();
            }
            current = current.hash.table[index].node;
        }
        
        current.isWord = true;
    }

    public boolean search(String word){
        TrieNode current = root;
        for(char c : word.toCharArray()){
            if(!current.search(c) || current == null){
                return false;
            }

            int index = current.hash.hashFunction(c);
            if (current.hash.table[index].node == null) {
                current.hash.table[index].node = new TrieNode();
            }
            current = current.hash.table[index].node;
            // current = current.hash.table[index].node;
        }

        return current.isWord;
    }

    public void printTree() {
        System.out.println("Words in the Trie:");
        printWords(root, new StringBuilder(), "ROOT"); // Start with the root node
    }
    
    // Helper method to recursively print words
    private void printWords(TrieNode node, StringBuilder prefix, String parent) {
        if (node == null) return;
    
        // Print the hash table of the current node using printHash()
        System.out.println("Parent: " + parent + ", Current TrieNode's Hash Table:");
        node.hash.printHash();
    
        // If the current node represents the end of a word, print it
        if (node.isWord) {
            System.out.println("Word: " + prefix.toString() + " (Parent: " + parent + ")");
        }
    
        // Recursively traverse each character in the hash table
        for (int i = 0; i < node.hash.table.length; i++) {
            Element entry = node.hash.table[i];
            if (entry != null) {
                char c = entry.key;
                prefix.append(c); // Append the character to the prefix
                printWords(entry.node, prefix, Character.toString(c)); // Recur with the current character as the parent
                prefix.deleteCharAt(prefix.length() - 1); // Backtrack
            }
        }
    }
    


}

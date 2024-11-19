public class TrieNode {
	
	public RobinHoodHashing hash;
	public int importance;
	public boolean isWord;

	public TrieNode() {
		hash = new RobinHoodHashing();
		importance = 0;
		isWord = false;
	}
	
	public void insert(String key) {
		if(key == null || key.equals("")) return;

		TrieNode current = this;

		for(char c : key.toCharArray()){
			int index = current.hash.getIndex(c);
			if(index == -1){ // c is not in current.hash
				current.hash.insert(c); // insert c into current.hash
				index = current.hash.getIndex(c); // get the index after insertion
			}

			if (current.hash.table[index].node == null) {
				current.hash.table[index].node = new TrieNode();
			}
			current = current.hash.table[index].node;
		}
		
		current.isWord = true;
	}
	
	public boolean search(String key) {
		TrieNode current = this;
		for(char c : key.toCharArray()){
			int index = current.hash.getIndex(c);
			if(index == -1){
				return false;
			}

			if (current.hash.table[index].node == null) {
				return false;
			}
			current = current.hash.table[index].node;
		}

		return current.isWord;
	}

	public void printTree() {
        System.out.println("Words in the Trie:");
        printWords(this, new StringBuilder(), "ROOT"); // Start with the root node
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

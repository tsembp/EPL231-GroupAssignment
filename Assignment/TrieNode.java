public class TrieNode {
	
	public RobinHoodHashing hash;

	public TrieNode() {
		hash = new RobinHoodHashing();
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

			// System.out.println("Printing current index before creating new node: " + index);

			// Check if node at index is null => create one if yes
			if (current.hash.table[index].node == null) {
				current.hash.table[index].node = new TrieNode();
			}

			// Move to next node
			current = current.hash.table[index].node;
		}

		// Look for end of word to mark it with isWord = true
		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		if (finalIndex != -1 && current.hash.table[finalIndex] != null) {
			current.hash.table[finalIndex].isWord = true;
		}
	}
	
	public boolean search(String key) {
		TrieNode current = this;

		// Iterate over every character of string
		for(char c : key.toCharArray()){
			int index = current.hash.getIndex(c);
			if(index == -1){ // character not found => word doesnt exist
				return false;
			}

			// Check if node at index is null => create one if yes
			if (current.hash.table[index].node == null) {
				return false;
			}

			// Move to next node
			current = current.hash.table[index].node;
		}

		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		return finalIndex != -1 && current.hash.table[finalIndex] != null && current.hash.table[finalIndex].isWord;
	}

	public boolean search(String key, boolean flag) {
		TrieNode current = this;
		for(int j = 0; j < key.length(); j++){
			// Get character at index j
			char c = key.charAt(j);

			// Get character's index
			int index = current.hash.getIndex(c);

			if(index == -1){ // not found
				return false;
			}

			// doesnt exist
			if (current.hash.table[index].node == null) {
				return false;
			}

			// Move to next node
			current = current.hash.table[index].node;
		}

		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		return finalIndex != -1 && current.hash.table[finalIndex] != null && current.hash.table[finalIndex].isWord;
	}

	// public void printTree() {
    //     System.out.println("Words in the Trie:");
    //     printWords(this, new StringBuilder(), "ROOT"); // Start with the root node
    // }
    
    // // Helper method to recursively print words
    // private void printWords(TrieNode node, StringBuilder prefix, String parent) {
    //     if (node == null) return;
    
    //     // Print the hash table of the current node using printHash()
    //     System.out.println("Parent: " + parent + ", Current TrieNode's Hash Table:");
    //     node.hash.printHash();
    
    //     // If the current node represents the end of a word, print it
    //     if (node.isWord) {
    //         System.out.println("Word: " + prefix.toString() + " (Parent: " + parent + ")");
    //     }
    
    //     // Recursively traverse each character in the hash table
    //     for (int i = 0; i < node.hash.table.length; i++) {
    //         Element entry = node.hash.table[i];
    //         if (entry != null) {
    //             char c = entry.key;
    //             prefix.append(c); // Append the character to the prefix
    //             printWords(entry.node, prefix, Character.toString(c)); // Recur with the current character as the parent
    //             prefix.deleteCharAt(prefix.length() - 1); // Backtrack
    //         }
    //     }
    // }

	// public void printTree() {
	// 	System.out.println("Words in the Trie:");
	// 	printWords(this, new StringBuilder());
	// }
	
	// Helper method to recursively print words and their importance
	// private void printWords(TrieNode node, StringBuilder prefix) {
	// 	if (node == null) return;
	
	// 	// Iterate through each entry in the hash table
	// 	for (int i = 0; i < node.hash.table.length; i++) {
	// 		Element entry = node.hash.table[i];
	// 		if (entry != null) {
	// 			char c = entry.key;
	// 			prefix.append(c); // Append the character to the prefix
	
	// 			// Check if this node marks the end of a word
	// 			if (entry.node != null && entry.node.isWord) {
	// 				System.out.println("Word: " + prefix.toString() + ", Importance: " + entry.node.importance);
	// 			}
	
	// 			// Recur for the next node
	// 			if (entry.node != null) {
	// 				printWords(entry.node, prefix);
	// 			}
	
	// 			prefix.deleteCharAt(prefix.length() - 1); // Backtrack
	// 		}
	// 	}
	// }
	
	
}

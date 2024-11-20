public class TrieNode {
	
	public RobinHoodHashing hash;

	public TrieNode() {
		hash = new RobinHoodHashing();
	}
	
	public void insert(String key) {
		if(key == null || key.equals("")) return;

		TrieNode current = this;
		int finalIndex = 0;

		for(int i=0; i < key.length(); i++){

			char c = key.charAt(i);

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
			
			// finalIndex = index;
			// Move to next node
			if(i == (key.length() - 1)) {
				break;
			}
			current = current.hash.table[index].node;
		}

		// Look for end of word to mark it with isWord = true
		finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		if (finalIndex != -1 && current.hash.table[finalIndex] != null) {
			current.hash.table[finalIndex].isWord = true;
			current.hash.table[finalIndex].word = key;
			if(key.equals("h")) System.out.println("Assigning the word at index: " + finalIndex + " " + current.hash.table[finalIndex].word + " " + current.hash.table[finalIndex].isWord);
		}
	}
	
	public boolean search(String key) {
		if(key.equals("has")) System.out.println("TEESTTOOTTOTOTT");
		TrieNode current = this;
		int finalIndex = 0;

		// Iterate over every character of string
		for(int i=0; i < key.length(); i++){

			char c = key.charAt(i);
			

			int index = current.hash.getIndex(c);
			if(key.equals("h")) {
				System.out.println("Current index = " + index);
				current.hash.printHash();
			}
			if(index == -1){ // character not found => word doesnt exist
				return false;
			}

			// Check if node at index is null => create one if yes
			if (current.hash.table[index].node == null) {
				return false;
			}

			// Move to next node
			// if(key.equals("has")) current.hash.printHash();
			if(i == key.length() - 1) { 
				if(key.equals("h")) {
					System.out.println("Current index at last iteration: " + index);
					System.out.println("Printing current hash table: ");
					hash.printHash();
					System.out.println("Printing word at last index: " + current.hash.table[index].word);
					hash.printHash();
				}
				finalIndex = index;
				break;
			}
			current = current.hash.table[index].node;
		}


		// int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		if(key.equals("h")) System.out.println(current.hash.table[finalIndex].key + " " + current.hash.table[finalIndex].word);
		return finalIndex != -1 && current.hash.table[finalIndex] != null && current.hash.table[finalIndex].isWord && current.hash.table[finalIndex].word.equals(key);
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
			if(j == key.length() - 1) break;
			current = current.hash.table[index].node;
		}

		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		return finalIndex != -1 && current.hash.table[finalIndex] != null && current.hash.table[finalIndex].isWord;
	}

	public void searchImportance(String key) {
		TrieNode current = this;

		// Iterate over every character of string
		for(int i=0; i < key.length(); i++){

			char c = key.charAt(i);

			int index = current.hash.getIndex(c);
			if(index == -1 || current.hash.table[index].node == null){ // character not found => word doesnt exist
				// System.out.println("Word not found in searchImportance!");
				return;
			}

			// Move to next node
			if(i == key.length() - 1) break;
			current = current.hash.table[index].node;
		}

		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		if (finalIndex != -1 && current.hash.table[finalIndex] != null && current.hash.table[finalIndex].isWord) {
			increaseImportance(current.hash.table[finalIndex]);
		}

		System.out.println(current.hash.table[finalIndex].importance);
	}

	private void increaseImportance(Element element) {
		element.importance++;
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

public class TrieNode {
	
	private RobinHoodHashing hash;		// sizeOf(RobinHoodHash) * 4 bytes (reference)
	private int wordLength;				// 4 bytes
										// TOTAL: 4 bytes + sizeOf(RobinHoodHash)

	public TrieNode() {
		hash = new RobinHoodHashing();
	}

	public RobinHoodHashing getHashTable(){
		return this.hash;
	}

	public int getWordLength(){
		return this.wordLength;
	}

	public void setWordLength(int value){
		this.wordLength = value;
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


			// Check if node of element at index is null => create one if yes
			if (current.hash.getTable()[index].getNode() == null) {
				current.hash.getTable()[index].setNode(new TrieNode());
			}
			
			// If we reach the last character in the string, break to keep the current node's reference
			if(i == (key.length() - 1)) {
				finalIndex = index;
				break;	
			} 

			// Move to next node
			current = current.hash.getTable()[index].getNode();
		}

		// Set word length of word to key's length
		current.wordLength = key.length();
		current.hash.getTable()[finalIndex].setIsWord(true);
	}
	
	public boolean search(String key) {
		TrieNode current = this;
		int finalIndex = 0;

		// Iterate over every character of string
		for(int i=0; i < key.length(); i++){
			char c = key.charAt(i);

			int index = current.hash.getIndex(c);
			if(index == -1){ // character not found => word doesnt exist
				return false;
			}

			// Check if node at index is null => create one if yes
			if (current.hash.getTable()[index].getNode() == null) {
				return false;
			}

			// If we reach the last character in the string, break to keep the current node's reference
			if(i == key.length() - 1) { 
				finalIndex = index;
				break;
			}

			// Move to next node
			current = current.hash.getTable()[index].getNode();
		}

		return finalIndex != -1 && current.hash.getTable()[finalIndex] != null 
				&& current.hash.getTable()[finalIndex].getIsWord();
	}

	public void searchImportance(String key) {
		TrieNode current = this;

		// Iterate over every character of string
		for(int i=0; i < key.length(); i++){
			char c = key.charAt(i);

			// Get 'c's index
			int index = current.hash.getIndex(c);
			if(index == -1 || current.hash.getTable()[index].getNode() == null){ // character not found => word doesnt exist
				return;
			}

			// If we reach the last character in the string, break to keep the current node's reference
			if(i == key.length() - 1) {
				break;
			}

			// Move to next node
			current = current.hash.getTable()[index].getNode();
		}


		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		if (finalIndex != -1 && current.hash.getTable()[finalIndex] != null && current.wordLength == key.length() && current.hash.getTable()[finalIndex].getIsWord()) {
			increaseImportance(current.hash.getTable()[finalIndex]);
		}

	}

	private void increaseImportance(Element element) {
		element.setImportance(element.getImportance() + 1);
	}

	public String reconstructWord(TrieNode root, TrieNode targetNode) {
		StringBuilder word = new StringBuilder();
		if (findWordDFS(root, targetNode, word)) {
			return word.toString();
		}
		return null; // If the targetNode is not found in the trie
	}
	
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

}

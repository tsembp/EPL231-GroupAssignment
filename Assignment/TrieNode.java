public class TrieNode {
	
	private RobinHoodHashing hash;

	public TrieNode() {
		hash = new RobinHoodHashing();
	}

	public RobinHoodHashing getHashTable(){
		return this.hash;
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
				// current.hash.table[index].node = new TrieNode();
				current.hash.getTable()[index].setNode(new TrieNode());

			}
			
			// If we reach the last character in the string, break to keep the current node's reference
			if(i == (key.length() - 1)) break;

			// Move to next node
			current = current.hash.getTable()[index].getNode();
		}

		// Look for end of word and mark it with isWord = true and assign element.word field
		finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		if (finalIndex != -1 && current.hash.getTable()[finalIndex] != null) {
			current.hash.getTable()[finalIndex].setWord(true);
			current.hash.getTable()[finalIndex].setWord(key);
		}
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
				&& current.hash.getTable()[finalIndex].isWord() && current.hash.getTable()[finalIndex].getWord().equals(key);
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

			// Word doesn't exist
			if (current.hash.getTable()[index].getNode() == null) {
				return false;
			}

			// If we reach the last character in the string, break to keep the current node's reference
			if(j == key.length() - 1) break;

			// Move to next node
			current = current.hash.getTable()[index].getNode();
		}

		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		return finalIndex != -1 && current.hash.getTable()[finalIndex] != null 
				&& current.hash.getTable()[finalIndex].isWord();
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
			if(i == key.length() - 1) break;

			// Move to next node
			current = current.hash.getTable()[index].getNode();
		}

		int finalIndex = current.hash.getIndex(key.charAt(key.length() - 1));
		if (finalIndex != -1 && current.hash.getTable()[finalIndex] != null && current.hash.getTable()[finalIndex].isWord()) {
			increaseImportance(current.hash.getTable()[finalIndex]);
		}

	}

	private void increaseImportance(Element element) {
		element.setImportance(element.getImportance() + 1);
	}

}

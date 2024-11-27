import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrieNodeStatic {
	
	private static int ALPHABET_SIZE = 26;
	
	private TrieNodeStatic[] children;
	private int importance;
	private boolean isWord;
	private int wordLength;

	public TrieNodeStatic() {
		children = new TrieNodeStatic[ALPHABET_SIZE];
		isWord = false;
		importance = -1;
		wordLength = -1;
	}
	
	public static void insert(TrieNodeStatic root, String key) {
		TrieNodeStatic current = root;
		
		for(int i = 0; i < key.length(); i++) {
			int index = key.charAt(i) - 'a';
			
			if(current.children[index] == null) {
				current.children[index] = new TrieNodeStatic();
			}

			// last iteration -> break to keep reference to last node
			if(i == key.length() - 1){
				break;
			}

			current = current.children[index];
			
		}

		// get index of last character of word key
		int lastIndex = key.charAt(key.length() - 1) - 'a';

		// set as word
		current.children[lastIndex].isWord = true;
		current.children[lastIndex].setWordLength(key.length());
	}
	
	public static boolean search(TrieNodeStatic root, String key) {
		TrieNodeStatic current = root;
		
		for(int i = 0; i < key.length(); i++) {
			int index = key.charAt(i) - 'a';
			
			if(current.children[index] == null) return false;
			
			// last iteration -> break to keep reference to last node
			if(i == key.length() - 1){
				break;
			}

			current = current.children[index];
		}
		
		return current.children[key.charAt(key.length() - 1) - 'a'] != null 
    			&& current.children[key.charAt(key.length() - 1) - 'a'].isWord;
	}
	
	public static void searchImportance(TrieNodeStatic root, String key) {
		TrieNodeStatic current = root;
		
		for(int i = 0; i < key.length(); i++) {
			int index = key.charAt(i) - 'a';
			
			// key word doesn't exist
			if(current.children[index] == null) return;
			
			// last iteration -> break to keep reference to last node
			if(i == key.length() - 1){
				break;
			}

			// move to next (downwards)
			current = current.children[index];
		}
		
		int lastIndex = key.charAt(key.length() - 1) - 'a';
		if(current.children[lastIndex] != null && current.children[lastIndex].isWord){
			current.children[lastIndex].importance++;
		}
	}

	public int getWordLength(){
		return this.wordLength;
	}

	public int getImportance(){
		return this.importance;
	}

	public void incrementImportance(){
		this.importance++;
	}

	public boolean getIsWord(){
		return isWord;
	}

	public void setIsWord(boolean exp){
		this.isWord = exp;
	}

	public void setWordLength(int value){
		this.wordLength = value;
	}

	public static int traverseTrie(TrieNodeStatic root) {
		// Base case: if the current node is null, return 0
		if (root == null) return 0;
	
		// Initialize the count for the current node
		int count = 1; // Count the current node
	
		// Recursively traverse all children and add their counts
		for (int i = 0; i < ALPHABET_SIZE; i++) {
			count += traverseTrie(root.children[i]);
		}
	
		// Return the total count of nodes
		return count;
	}

	public static void main(String[] args) {
		/* CONSTRUCT DICTIONARY FILE */
        TrieNodeStatic tree = new TrieNodeStatic();
        String dictionary = "./Dictionaries/Different Length/100000.txt"; // Replace with the path to your file

        // Step 1: Read words from the file and insert them into the Trie
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    insert(tree, word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

		int count = 0;
        count = traverseTrie(tree);

        // Print the results
        System.out.println("Number of nodes: " + count);
	}
	
	public static void printImportance(TrieNodeStatic root, StringBuilder currentWord) {
		// Base case: If the current node is a word, print it and its importance
		if (root.isWord) {
			System.out.println("Word: " + currentWord + ", Importance: " + root.importance);
		}
	
		// Traverse each child node
		for (int i = 0; i < ALPHABET_SIZE; i++) {
			if (root.children[i] != null) {
				// Append the character corresponding to this child
				currentWord.append((char) (i + 'a'));
	
				// Recursive call for the child node
				printImportance(root.children[i], currentWord);
	
				// Backtrack: Remove the last character after returning from recursion
				currentWord.deleteCharAt(currentWord.length() - 1);
			}
		}
	}
	
}

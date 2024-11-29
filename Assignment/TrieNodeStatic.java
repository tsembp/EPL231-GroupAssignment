import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrieNodeStatic {
	
	private static int ALPHABET_SIZE = 26;
	
	private TrieNodeStatic[] children;		// 26 * 4 bytes (reference)
	private int importance;					// 4 bytes
	private boolean isWord;					// 1 byte
	private int wordLength;					// 4 bytes
											// TOTAL: 113 bytes

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

	public static int calculateMemory(TrieNodeStatic root){
		// traverseTrie(root) returns the number of nodes in the Trie tree
		return 113 * traverseTrie(root); // sizeOf(TrieNodeStatic) = 113 bytes
	}

	public static void main(String[] args) {
		/* CONSTRUCT DICTIONARY FILE */
        TrieNodeStatic tree = new TrieNodeStatic();
        String dictionary = "./Dictionaries/Different Length/1000.txt";

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
		System.out.println("Total memory: " + calculateMemory(tree));
	}
	
}

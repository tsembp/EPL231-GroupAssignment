import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrieNodeStatic {
	
	private static int ALPHABET_SIZE = 26;
	
	private TrieNodeStatic[] children;		// 26 * 4 bytes (reference)
	private int importance;					// 4 bytes
	private int wordLength;					// 4 bytes
											// TOTAL: 112 bytes

	public TrieNodeStatic() {
		children = new TrieNodeStatic[ALPHABET_SIZE];
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
			current = current.children[index];
			
		}

		current.wordLength = key.length();
	}
	
	public static boolean search(TrieNodeStatic root, String key) {
		TrieNodeStatic current = root;
		
		for(int i = 0; i < key.length(); i++) {
			int index = key.charAt(i) - 'a';
			
			if(current.children[index] == null) return false;
			
			current = current.children[index];
		}
		
		return current != null && key.length() == current.wordLength;
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
        String dictionary = "./Dictionaries/Same Length/Length 5/Test 1/5000.txt";

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
            return; // Exit the program if there's an issue with the file
        }

        int count = traverseTrie(tree);

        // Step 2: Reopen the file and check if every word exists in the Trie
        int missingWords = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim(); // Remove leading and trailing whitespace
                word = word.toLowerCase();
                if (!word.isEmpty()) {
                    if (!search(tree, word)) {
                        System.out.println("Word not found in Trie: " + word);
                        missingWords++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the results
        System.out.println("Number of nodes: " + count);
        System.out.println("Total memory: " + calculateMemory(tree));
        System.out.println("Number of missing words: " + missingWords);
    }

	public TrieNodeStatic[] getChildren() { return this.children; }
	
}

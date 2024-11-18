package lab7;

public class TrieNode {
	
	private static int ALPHABET_SIZE = 26;
	
	public TrieNode[] children;
	public int wordCount;

	public TrieNode() {
		children = new TrieNode[ALPHABET_SIZE];
		wordCount = 0;
	}
	
	static void insert(TrieNode root, String key) {
		TrieNode current = root;
		
		for(int i = 0; i < key.length(); i++) {
			int index = key.charAt(i) - 'a';
			
			if(current.children[index] == null) {
				current.children[index] = new TrieNode();
			}
			
			current = current.children[index];
			
		}
		
		current.wordCount++;
	}
	
	static boolean search(TrieNode root, String key) {
		TrieNode current = root;
		
		for(int i = 0; i < key.length(); i++) {
			int index = key.charAt(i) - 'a';
			
			if(current.children[index] == null) return false;
			
			current = current.children[index];
		}
		
		return current.wordCount != 0 ? true : false;
	}
	
	
	public static void main(String args[]) {
		TrieNode root = new TrieNode();
		
		insert(root, "application");
		insert(root, "app");
		
		System.out.println(search(root, "app"));
		
	}
	
	
	
}

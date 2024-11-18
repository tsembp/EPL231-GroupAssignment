public class TrieNode {
	
	public RobinHoodHashing hash;
	public int importance;
	public boolean isWord;

	public TrieNode() {
		hash = new RobinHoodHashing();
		importance = 0;
		isWord = false;
	}
	
	public void insert(char key) {
		hash.insert(key);
	}
	
	public boolean search(char key) {
		return hash.search(key);
	}
	
}

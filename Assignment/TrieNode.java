public class TrieNode {
	
	public RobinHoodHashing hash;
	public int importance;

	public TrieNode() {
		hash = new RobinHoodHashing();
		importance = 0;
	}
	
	public void insert(char key) {
		hash.insert(key);
	}
	
	public boolean search(String key) {
		

		return false;
	}
	
	
	public static void main(String args[]) {
		
		
	}
	
	
	
}

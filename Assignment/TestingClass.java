public class TestingClass {

    public static void main(String[] args) {
        testInsertAndSearch();
    }
    
    public static void testInsertAndSearch() {
        TrieNode trie = new TrieNode();
    
        // Insert some test words
        trie.insert("cat");
        trie.insert("car");
        trie.insert("care");
        trie.insert("cart");
        trie.insert("root");
        trie.insert("roofe");
        
        // Test searching for words
        assert trie.search("cat") : "Error: 'cat' should be found in the Trie.";
        assert trie.search("car") : "Error: 'car' should be found in the Trie.";
        assert trie.search("care") : "Error: 'care' should be found in the Trie.";
        assert trie.search("cart") : "Error: 'cart' should be found in the Trie.";
        assert trie.search("roof") : "Error: 'roof' should be found in the Trie.";
        
        // Test searching for words not in the Trie
        assert !trie.search("ca") : "Error: 'ca' should not be found in the Trie.";
        assert !trie.search("cats") : "Error: 'cats' should not be found in the Trie.";
    }
    


}

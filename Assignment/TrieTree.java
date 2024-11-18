public class TrieTree {
    
    TrieNode root;

    public TrieTree(){
        root = new TrieNode();
    }

    public void insert(String word){
        TrieNode current = root;

        for(char c : word.toCharArray()){
            int index = current.hash.hashFunction(c);
            if(!current.hash.search(c)){ // iterate up to not found char c
                current.insert(c); // insert char
            }

            current.hash.table[index].node = new TrieNode();
            current = current.hash.table[index].node;
        }
        
        current.isWord = true;
    }

    public boolean search(String word){
        TrieNode current = root;
        for(char c : word.toCharArray()){
            if(!current.search(c) || current == null){
                return false;
            }

            int index = current.hash.hashFunction(c);
            current = current.hash.table[index].node;
        }

        return current.isWord;
    }


}

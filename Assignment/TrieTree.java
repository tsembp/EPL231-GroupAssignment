public class TrieTree {
    
    TrieNode root;

    public TrieTree(){
        root = new TrieNode();
    }

    public void insert(String word){
        TrieNode current = root;

        for(char c : word.toCharArray()){
            int index = current.hash.hashFunction(c);
            if(current.hash.search(c)){ // iterate up to not found char c
                current = current.hash.table[index].node; // move to next node
            } else{
                current.hash.insert(c); // insert char
            }
        }
        
    }

    public boolean search(String word){
        TrieNode current = root;
        for(char c : word.toCharArray()){
            if(!current.hash.search(c)){
                return false;
            }

            int index = current.hash.hashFunction(c);
            current = current.hash.table[index].node;
        }

        return true;
    }


}

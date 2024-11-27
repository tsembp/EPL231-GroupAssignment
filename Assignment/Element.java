public class Element {

    private char key;
    private int probeLength;
    private TrieNode node;
    private int importance;
    // private boolean isWord;
    // private String word;

    // Constructor
    public Element(char key, int probeLength) {
        this.key = key;
        this.probeLength = probeLength;
        this.importance = 0;
        // this.isWord = false;
        // this.word = "";
    }

    // Getters and Setters
    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public int getProbeLength() {
        return probeLength;
    }

    public void setProbeLength(int probeLength) {
        this.probeLength = probeLength;
    }

    public void incrementProbeLength(){
        this.probeLength++;
    }

    public TrieNode getNode() {
        return node;
    }

    public void setNode(TrieNode node) {
        this.node = node;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public void setWordLength(int value){
        
    }

    // public boolean isWord() {
    //     return isWord;
    // }

    // public void setWord(boolean isWord) {
    //     this.isWord = isWord;
    // }

    // public String getWord() {
    //     return word;
    // }

    // public void setWord(String word) {
    //     this.word = word;
    // }
}

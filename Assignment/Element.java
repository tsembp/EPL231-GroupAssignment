public class Element {

    private char key;
    private int probeLength;
    private TrieNode node;
    private int importance;

    // Constructor
    public Element(char key, int probeLength) {
        this.key = key;
        this.probeLength = probeLength;
        this.importance = 0;
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

}

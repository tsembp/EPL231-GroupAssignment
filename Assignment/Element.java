public class Element {

    char key;
    int probeLength;
    TrieNode node;
    int importance;
    boolean isWord;
    String word;
    
    public Element(char key, int probeLength){
        this.key = key;
        this.probeLength = probeLength;
        this.importance = 0;
        this.isWord = false;
        word = "";
    }

    public int getProbeLength(){
        return this.probeLength;
    }

    public char getKey(){
        return this.key;
    }

    public void setProbeLength(int value){
        this.probeLength = value;
    }


}

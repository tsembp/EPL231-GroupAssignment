public class Element {

    char key;
    int probeLength;
    TrieNode node;
    
    public Element(char key, int probeLength){
        this.key = key;
        this.probeLength = probeLength;
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

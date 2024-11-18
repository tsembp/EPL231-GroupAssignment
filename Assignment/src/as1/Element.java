public class Element {

    int key;
    int probeLength;
    
    public Element(int key, int probeLength){
        this.key = key;
        this.probeLength = probeLength;
    }

    public int getProbeLength(){
        return this.probeLength;
    }

    public int getKey(){
        return this.key;
    }

    public void setProbeLength(int value){
        this.probeLength = value;
    }


}

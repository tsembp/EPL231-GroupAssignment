public class RobinHoodHashing {
    
    Element table[];
    int capacity;
    int size;
    int maxProbeLength;

    public RobinHoodHashing(){
        this.size = 0;
        this.capacity = 5;
        this.maxProbeLength = 0;
        this.table = new Element[capacity];
    }

    public void insert(int key){
        Element newElement = new Element(key, 0);
        int index = hashFunction(key);

        int probeLength = 0;
        while(table[index] != null){
            if(probeLength > table[index].getProbeLength()){ // Switch elements
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
            }

            index = (index + 1) % capacity;
            probeLength++;
        }

        newElement.setProbeLength(probeLength);
        table[index] = newElement;
        size++;
        maxProbeLength = Math.max(maxProbeLength, probeLength);

        // 90% full table => rehash
        if((double)size / capacity > 0.9) rehash();
    }

    public boolean search(int key){
        int index = hashFunction(key);
        int probeLength = 0;

        while(table[index] != null && probeLength <= maxProbeLength){
            if(table[index].getKey() == key){
                return true;
            }

            index = (index + 1) % capacity;
            probeLength++;
        }

        return false; // key not found
    }
    
	private void rehash(){
        if(size == 5) { Element newTable[] = new Element[11]; }
        else if(size == 11) { Element newTable[] = new Element[19]; }
        if(size == 19) { Element newTable[] = new Element[29]; }

        


    	 
    	
    	
    	
    }

    private int hashFunction(int key){
        return key % capacity;
    }

    public void printHash(){
        System.out.print("[");
        for(int i = 0; i < table.length; i++){
            if(table[i] == null){
                System.out.print("_, ");
            } else{
                System.out.print("(" + table[i].getKey() + ", " + table[i].getProbeLength() + ")");
            }
        }

        System.out.println("]");
    }

}

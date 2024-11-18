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
    
	private void rehash() {
        // Determine the new capacity based on a predefined sequence or doubling strategy
        int newCapacity;
        if (capacity == 5) {
            newCapacity = 11;
        } else if (capacity == 11) {
            newCapacity = 19;
        } else if (capacity == 19) {
            newCapacity = 29;
        } else {
            newCapacity = capacity * 2; // Use doubling for future expansions
        }
    
        // Create a new table with the updated capacity
        Element[] newTable = new Element[newCapacity];
        Element[] oldTable = table; // Keep reference to the old table
    
        // Update instance variables
        table = newTable;
        capacity = newCapacity;
        size = 0; // Reset size as elements will be reinserted
        maxProbeLength = 0; // Reset maxProbeLength
    
        // Reinsert all elements into the new table
        for (Element element : oldTable) {
            if (element != null) {
                insert(element.getKey());
            }
        }
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

public class RobinHoodHashing {
    
    Element table[];
    int capacity;
    int size;
    int maxProbeLength;

    public RobinHoodHashing(){
        this.size = 5;
        this.capacity = 0;
        this.maxProbeLength = 0;
        this.table = new Element[size];
    }

    public void insert(char key){
        Element newElement = new Element(key, 0);


        // 90% full table => rehash
        if(((double)capacity + 1) / size > 0.9) rehash();

        int index = hashFunction(key);

        while(table[index] != null){
            if(newElement.probeLength > table[index].getProbeLength()){ // Switch elements
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
            } else {
                newElement.probeLength++;
            }

            index = (index + 1) % size;
        }

        table[index] = newElement;
        capacity++;
        maxProbeLength = Math.max(maxProbeLength, newElement.probeLength);

    }

    public boolean search(char key){
        int index = hashFunction(key);
        int probeLength = 0;

        while(table[index] != null && probeLength <= maxProbeLength){
            if(table[index].getKey() == key){
                return true;
            }

            index = (index + 1) % size;
            probeLength++;
        }

        return false; // key not found
    }
    
	private void rehash() {
        // Determine the new capacity based on a predefined sequence or doubling strategy
        int newSize;
        if (size == 5) {
            newSize = 11;
        } else if (size == 11) {
            newSize = 19;
        } else {
            newSize = 29;
        }
    
        // Create a new table with the updated capacity
        Element[] newTable = new Element[newSize];
        Element[] oldTable = this.table; // Keep reference to the old table
    
        // Update instance variables
        this.table = newTable;
        this.capacity = 0;
        this.size = newSize; // Reset size as elements will be reinserted
        this.maxProbeLength = 0; // Reset maxProbeLength
    
        // Reinsert all elements into the new table
        for (Element element : oldTable) {
            if (element != null) {
                // insert(element.getKey());
                int index = hashFunction(element.getKey());
                table[index] = element;
            }
        }
    }
    
    public int hashFunction(char key){
        return (int)key % size;
    }

    public void printHash(){
        System.out.print("[");
        for(int i = 0; i < size; i++){
            if(table[i] == null){
                System.out.print("_, ");
            } else {
                System.out.print("(" + table[i].getKey() + ", " + table[i].getProbeLength() + "), ");
            }
        }

        System.out.println("]");
    }

    public static void main(String[] args) {
        RobinHoodHashing hash = new RobinHoodHashing();

        hash.insert('a');
        hash.insert('b');
        hash.insert('c');
        hash.insert('e');
        hash.insert('d');
        hash.insert('l');

        
        hash.printHash();

    }

}

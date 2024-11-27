public class RobinHoodHashing {

    private Element table[];
    private int capacity;
    private int size;
    private int maxProbeLength;
    private boolean isRehashing = false; // Flag to prevent rehashing during rehash

    public RobinHoodHashing() {
        this.size = 5;
        this.capacity = 0;
        this.maxProbeLength = 0;
        this.table = new Element[size];
    }

    public Element[] getTable(){
        return this.table;
    }

    public void insert(char key) {
        Element newElement = new Element(key, 0);

        // 90% full table => rehash
        if (!isRehashing && ((double) capacity + 1) / size > 0.9) {
            rehash();
        }

        // Get index of key
        int index = hashFunction(key);

        while (table[index] != null) {
            if (newElement.getProbeLength() > table[index].getProbeLength()) { // sSwitch elements
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
                maxProbeLength = Math.max(maxProbeLength, table[index].getProbeLength());
            }

            newElement.setProbeLength(newElement.getProbeLength() + 1); // increment probeLength
            index = (index + 1) % size; // move to next
        }

        maxProbeLength = Math.max(maxProbeLength, newElement.getProbeLength()); // update maxProbeLength
        table[index] = newElement; // insert new element
        capacity++; // increase capacity
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

        // Keep reference to the old table
        Element[] oldTable = this.table;

        // Create a new table with the updated capacity
        this.table = new Element[newSize];
        this.size = newSize;
        this.capacity = 0; // reset capacity
        this.maxProbeLength = 0; // reset maxProbeLength

        isRehashing = true; // Set flag to avoid rehashing during insert

        // Reinsert all elements into the new table
        for (Element element : oldTable) {
            if (element != null) {
                insertRehash(element);
            }
        }

        isRehashing = false; // Reset flag
    }

    private void insertRehash(Element element) {
        Element newElement = new Element(element.getKey(), 0);
        newElement.setNode(element.getNode()); // Preserve the node reference
        newElement.setImportance(element.getImportance());

        int index = hashFunction(newElement.getKey());

        while (table[index] != null) {
            if (newElement.getProbeLength() > table[index].getProbeLength()) { // Switch elements
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
                maxProbeLength = Math.max(maxProbeLength, table[index].getProbeLength());
            }
            newElement.incrementProbeLength(); // increase probeLength
            index = (index + 1) % size; // move to next index
        }
        table[index] = newElement; // insert element
        capacity++; // icrease capacity
        maxProbeLength = Math.max(maxProbeLength, newElement.getProbeLength()); // update maxProbeLength
    }

    public boolean search(char key) {
        int index = hashFunction(key);
        int probeLength = 0;

        while (table[index] != null && probeLength <= maxProbeLength) {
            if (table[index].getKey() == key) { // same key => found
                return true;
            }

            index = (index + 1) % size; // move to next index
            probeLength++; // increase probeLength to avoid useless iterations
        }

        return false; // key not found
    }

    public int getIndex(char key) {
        int index = hashFunction(key);
        int probeLength = 0;

        while (table[index] != null && probeLength <= maxProbeLength) {
            if (table[index].getKey() == key) { // key found => return its index
                return index;
            }
            index = (index + 1) % size; // move to next hash entry
            probeLength++; // increase probeLength to avoid useless iterations
        }
        return -1; // key not found
    }

    public int hashFunction(char key) {
        return (int) key % size;
    }

    public void printHash() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            if (table[i] == null) {
                System.out.print("_, ");
            } else {
                System.out.print("(" + table[i].getKey() + ", " + table[i].getProbeLength() + "), ");
            }
        }

        System.out.println("]");
    }
}

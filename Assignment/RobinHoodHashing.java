/**
 * Implements Robin Hood Hashing for collision resolution in a hash table.
 * Elements are inserted in such a way to minimize probe lengths and maximize performance.
 */
public class RobinHoodHashing {

    private Element table[];                // table.size * 4 bytes (reference)
    private int capacity;                   // Number of elements currently in the table
    private int size;                       // Current capacity of the hash table
    private int maxProbeLength;             // Maximum probe length for any element in the table
    private boolean isRehashing = false;    // Indicates whether rehashing is in progress
                                            // TOTAL: 13 bytes + (size * 4 bytes)

    /**
     * Constructs a RobinHoodHashing object with an initial table size of 5.
     */
    public RobinHoodHashing() {
        this.size = 5;
        this.capacity = 0;
        this.maxProbeLength = 0;
        this.table = new Element[size];
    }

    /**
     * Returns the current number of elements in the table.
     *
     * @return the number of elements in the table.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the hash table.
     *
     * @return the array representing the hash table.
     */
    public Element[] getTable() {
        return this.table;
    }

    /**
     * Inserts a new key into the hash table. Performs rehashing if the table is 90% full.
     *
     * @param key the character key to insert.
     */
    public void insert(char key) {
        Element newElement = new Element(key, 0);

        // Rehash if the table is 90% full
        if (!isRehashing && ((double) capacity + 1) / size > 0.9) {
            rehash();
        }

        // Get the index of the key
        int index = hashFunction(key);

        while (table[index] != null) {
            // Compare probe lengths of new and current elements
            if (newElement.getProbeLength() > table[index].getProbeLength()) {
                // Swap if the new element has a longer probe length
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
                maxProbeLength = Math.max(maxProbeLength, table[index].getProbeLength());
            }

            newElement.setProbeLength(newElement.getProbeLength() + 1); // Increment probe length
            index = (index + 1) % size; // Move to the next index
        }

        maxProbeLength = Math.max(maxProbeLength, newElement.getProbeLength()); // Update max probe length
        table[index] = newElement; // Insert the new element
        capacity++; // Increment capacity
    }

    /**
     * Rehashes the hash table to increase its size and reinsert all elements.
     */
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

        // Keep a reference to the old table
        Element[] oldTable = this.table;

        // Create a new table with the updated size
        this.table = new Element[newSize];
        this.size = newSize;
        this.capacity = 0; // Reset capacity
        this.maxProbeLength = 0; // Reset max probe length

        isRehashing = true; // Set flag to avoid rehashing during insert

        // Reinsert all elements into the new table
        for (Element element : oldTable) {
            if (element != null) {
                insertRehash(element);
            }
        }

        isRehashing = false; // Reset flag
    }

    /**
     * Inserts an element during rehashing.
     *
     * @param element the element to insert.
     */
    private void insertRehash(Element element) {
        Element newElement = new Element(element.getKey(), 0); // Create a copy of the element
        newElement.setNode(element.getNode());
        newElement.setImportance(element.getImportance());
        newElement.setIsWord(element.getIsWord());

        int index = hashFunction(newElement.getKey());

        while (table[index] != null) {
            // Compare probe lengths of new and current elements
            if (newElement.getProbeLength() > table[index].getProbeLength()) {
                // Swap if the new element has a longer probe length
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
                maxProbeLength = Math.max(maxProbeLength, table[index].getProbeLength());
            }

            newElement.incrementProbeLength(); // Increment probe length
            index = (index + 1) % size; // Move to the next index
        }

        table[index] = newElement; // Insert the new element
        capacity++; // Increment capacity
        maxProbeLength = Math.max(maxProbeLength, newElement.getProbeLength()); // Update max probe length
    }

    /**
     * Searches for a key in the hash table.
     *
     * @param key the character key to search for.
     * @return true if the key is found, false otherwise.
     */
    public boolean search(char key) {
        int index = hashFunction(key);
        int probeLength = 0;

        // Use probe length to avoid unnecessary iterations
        while (table[index] != null && probeLength <= maxProbeLength) {
            if (table[index].getKey() == key) { // Key found
                return true;
            }

            index = (index + 1) % size; // Move to the next index
            probeLength++; // Increment probe length
        }

        return false; // Key not found
    }

    /**
     * Gets the index of a key in the hash table.
     *
     * @param key the character key to search for.
     * @return the index of the key, or -1 if not found.
     */
    public int getIndex(char key) {
        int index = hashFunction(key);
        int probeLength = 0;

        while (table[index] != null && probeLength <= maxProbeLength) {
            if (table[index].getKey() == key) { // Key found
                return index;
            }
            index = (index + 1) % size; // Move to the next index
            probeLength++; // Increment probe length
        }
        return -1; // Key not found
    }

    /**
     * Computes the hash function for a given key.
     *
     * @param key the character key.
     * @return the hash index for the key.
     */
    public int hashFunction(char key) {
        return (int) key % size;
    }

    /**
     * Prints the contents of the hash table, displaying keys and their probe lengths.
     */
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

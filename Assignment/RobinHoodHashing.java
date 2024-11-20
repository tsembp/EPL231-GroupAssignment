public class RobinHoodHashing {

    Element table[];
    int capacity;
    int size;
    int maxProbeLength;
    private boolean isRehashing = false; // Flag to prevent rehashing during rehash

    public RobinHoodHashing() {
        this.size = 5;
        this.capacity = 0;
        this.maxProbeLength = 0;
        this.table = new Element[size];
    }

    public void insert(char key) {
        Element newElement = new Element(key, 0);

        // 90% full table => rehash
        if (!isRehashing && ((double) capacity + 1) / size > 0.9) {
            rehash();
        }

        int index = hashFunction(key);

        while (table[index] != null) {
            if (newElement.getKey() == 'd') {
                // System.out.println(newElement.getKey() + " current probe length: " +
                // newElement.getProbeLength());
                // System.out.println("Current max probe is: " + maxProbeLength);
            }

            if (newElement.probeLength > table[index].getProbeLength()) { // Switch elements
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
                maxProbeLength = Math.max(maxProbeLength, table[index].probeLength);
                if (newElement.getKey() == 'd') {
                    // System.out.println("In if statement");
                    // System.out.println("Perfomed swap between " + temp.getKey() + " and " +
                    // table[index].getKey());
                }
            }
            newElement.probeLength++;

            index = (index + 1) % size;
        }

        // System.out.println("New Element value: " + newElement.getKey() + " probe
        // length: " + newElement.probeLength);
        maxProbeLength = Math.max(maxProbeLength, newElement.probeLength);
        table[index] = newElement;
        capacity++;
        // System.out.println("Current maxProbeLength: " + maxProbeLength);
        // printHash();
    }

    private void rehash() {
        // Determine the new capacity based on a predefined sequence or doubling
        // strategy
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
        newElement.node = element.node; // Preserve the node reference
        int index = hashFunction(newElement.getKey());

        while (table[index] != null) {

            if (newElement.probeLength > table[index].getProbeLength()) { // Switch elements
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
                maxProbeLength = Math.max(maxProbeLength, table[index].probeLength);
            }
            newElement.probeLength++;

            index = (index + 1) % size;
        }

        table[index] = newElement;
        capacity++;
        maxProbeLength = Math.max(maxProbeLength, newElement.probeLength);
    }

    public boolean search(char key) {
        int index = hashFunction(key);
        int probeLength = 0;

        while (table[index] != null && probeLength <= maxProbeLength) {
            if (table[index].getKey() == key) {
                return true;
            }

            index = (index + 1) % size;
            probeLength++;
        }

        return false; // key not found
    }

    public int getIndex(char key) {
        int index = hashFunction(key);
        int probeLength = 0;

        while (table[index] != null && probeLength <= maxProbeLength) {
            // System.out.println("Printing current index: " + index);
            // System.out.println("Printing character at table[index]: " +
            // table[index].getKey());
            if (table[index].getKey() == key) {
                return index;
            }

            index = (index + 1) % size;
            probeLength++;
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

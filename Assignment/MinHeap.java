/**
 * Represents a MinHeap data structure that stores strings (words) and their associated importance values.
 * The heap ensures the element with the smallest importance value is at the root.
 */
public class MinHeap {

    private String[] strHeap; // Array to store strings (words)
    private int[] importanceHeap; // Array to store integer importance values
    private int size; // Current size of the heap
    private int maxsize; // Maximum size of the heap

    private static final int FRONT = 0; // Index of the root element

    /**
     * Constructs a MinHeap with the specified maximum size.
     *
     * @param maxsize the maximum number of elements the heap can hold.
     */
    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        this.strHeap = new String[this.maxsize];
        this.importanceHeap = new int[this.maxsize];
    }

    /**
     * Returns the index of the parent of the given position.
     *
     * @param pos the index of the child node.
     * @return the index of the parent node.
     */
    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    /**
     * Returns the index of the left child of the given position.
     *
     * @param pos the index of the parent node.
     * @return the index of the left child node.
     */
    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    /**
     * Returns the index of the right child of the given position.
     *
     * @param pos the index of the parent node.
     * @return the index of the right child node.
     */
    private int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    /**
     * Swaps two elements in the heap at the specified positions.
     *
     * @param fpos the index of the first element to swap.
     * @param spos the index of the second element to swap.
     */
    private void swap(int fpos, int spos) {
        int tempImportance = importanceHeap[fpos]; // Swap importance values
        importanceHeap[fpos] = importanceHeap[spos];
        importanceHeap[spos] = tempImportance;

        String tempString = strHeap[fpos]; // Swap associated strings
        strHeap[fpos] = strHeap[spos];
        strHeap[spos] = tempString;
    }

    /**
     * Ensures the heap property is maintained by percolating down from the given position.
     *
     * @param pos the index to start heapifying from.
     */
    private void minHeapify(int pos) {
        int left = leftChild(pos); // Retrieve left child position
        int right = rightChild(pos); // Retrieve right child position
        int smallest = pos; // Assume the smallest element is at the current position

        // Compare with left child
        if (left < size && importanceHeap[left] < importanceHeap[smallest]) {
            smallest = left;
        }

        // Compare with right child
        if (right < size && importanceHeap[right] < importanceHeap[smallest]) {
            smallest = right;
        }

        // If the smallest element is not at the current position, swap and heapify further
        if (smallest != pos) {
            swap(pos, smallest);
            minHeapify(smallest);
        }
    }

    /**
     * Inserts a new word with its associated importance value into the heap.
     * If the heap is full, replaces the root if the new element has greater importance.
     *
     * @param word       the word to insert.
     * @param importance the importance value of the word.
     */
    public void insert(String word, int importance) {
        if (search(word, importance)) return; // If 'word' already exists in the heap, do not insert again

        if (isFull()) {
            // If heap is full, check if the least important word can be replaced with the new one
            if (importance > importanceHeap[FRONT]) {
                remove(); // Remove the root
                insert(word, importance); // Insert the new word
            }
        } else {
            importanceHeap[size] = importance; // Add the new importance value
            strHeap[size] = word; // Add the new word
            int current = size;
            size++;

            // Percolate up to maintain heap property
            while (current > 0 && importanceHeap[current] < importanceHeap[parent(current)]) {
                swap(current, parent(current));
                current = parent(current);
            }
        }
    }

    /**
     * Removes and returns the root element (minimum importance) from the heap.
     *
     * @return the word associated with the removed element, or null if the heap is empty.
     */
    public String remove() {
        if (size == 0) { // Ensure the heap is not empty
            System.out.println("Heap is empty");
            return null;
        }

        String poppedWord = strHeap[FRONT]; // Store the root word
        importanceHeap[FRONT] = importanceHeap[size - 1]; // Replace root with the last element
        strHeap[FRONT] = strHeap[size - 1];
        size--;

        minHeapify(FRONT); // Percolate down to maintain heap property
        return poppedWord;
    }

    /**
     * Searches the heap for a specific word.
     *
     * @param word       the word to search for.
     * @param importance the importance value to compare.
     * @return true if the word is found, false otherwise.
     */
    public boolean search(String word, int importance) {
        for (int i = 0; i < size; i++) {
            if (importanceHeap[i] > importance) {
                return false; // No need to check further if importance exceeds
            }
            if (strHeap[i].equals(word)) {
                return true; // Word found
            }
        }
        return false; // Word not found
    }

    /**
     * Retrieves the importance value of the root element.
     *
     * @return the importance value of the root, or -1 if the heap is empty.
     */
    public int getRootImportance() {
        if (size == 0) { // Error handling for empty heap
            System.out.println("Heap is empty");
            return -1;
        }
        return importanceHeap[FRONT];
    }

    /**
     * Checks if the heap is full.
     *
     * @return true if the heap is full, false otherwise.
     */
    public boolean isFull() {
        return size == maxsize;
    }

    /**
     * Prints all elements in the heap along with their importance values.
     */
    public void printList() {
        for (int i = 0; i < size; i++) {
            System.out.println(strHeap[i] + " (" + importanceHeap[i] + ")");
        }
    }

    /**
     * Prints the structure of the heap, including parent and child relationships.
     */
    public void print() {
        for (int i = 0; i < size / 2; i++) {
            String parent = strHeap[i];
            String leftChild = (2 * i + 1 < size) ? strHeap[2 * i + 1] : "null";
            String rightChild = (2 * i + 2 < size) ? strHeap[2 * i + 2] : "null";

            System.out.println(
                "PARENT: " + parent + " (" + importanceHeap[i] + ")" +
                " LEFT CHILD: " + leftChild + " (" + (2 * i + 1 < size ? importanceHeap[2 * i + 1] : "null") + ")" +
                " RIGHT CHILD: " + rightChild + " (" + (2 * i + 2 < size ? importanceHeap[2 * i + 2] : "null") + ")"
            );
        }
    }
}

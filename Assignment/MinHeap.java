public class MinHeap {

    private String[] strHeap; // Array to store strings (words)
    private int[] importanceHeap; // Array to store integer importance values
    private int size;
    private int maxsize;

    private static final int FRONT = 0;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        this.strHeap = new String[this.maxsize];
        this.importanceHeap = new int[this.maxsize];
    }

    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    private int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    private void swap(int fpos, int spos) {
        // Swap importance values
        int tempImportance = importanceHeap[fpos];
        importanceHeap[fpos] = importanceHeap[spos];
        importanceHeap[spos] = tempImportance;

        // Swap associated strings
        String tempString = strHeap[fpos];
        strHeap[fpos] = strHeap[spos];
        strHeap[spos] = tempString;
    }

    private void minHeapify(int pos) {
        int left = leftChild(pos);
        int right = rightChild(pos);
        int smallest = pos;

        if (left < size && importanceHeap[left] < importanceHeap[smallest]) {
            smallest = left;
        }
        if (right < size && importanceHeap[right] < importanceHeap[smallest]) {
            smallest = right;
        }

        if (smallest != pos) {
            swap(pos, smallest);
            minHeapify(smallest);
        }
    }

    public void insert(String word, int importance) {
        if(search(word)) return;

        if(isFull()){
            if(importance > importanceHeap[FRONT]){
                remove();
                insert(word, importance);
            }
        } else{
            // Insert the new element at the end of the heap
            importanceHeap[size] = importance;
            strHeap[size] = word;
            int current = size;
            size++;
    
            // Percolate up
            while (current > 0 && importanceHeap[current] < importanceHeap[parent(current)]) {
                swap(current, parent(current));
                current = parent(current);
            }
        }
    }

    public String remove() {
        if (size == 0) {
            System.out.println("Heap is empty");
            return null;
        }

        // Remove the root (minimum element)
        String poppedWord = strHeap[FRONT];
        importanceHeap[FRONT] = importanceHeap[size - 1];
        strHeap[FRONT] = strHeap[size - 1];
        size--;

        // Percolate down
        minHeapify(FRONT);

        return poppedWord;
    }

    public boolean search(String word) {
        for (int i = 0; i < size; i++) {
            if (strHeap[i].equals(word)) {
                return true;
            }
        }
        
        return false;
    }

    public int getRootImportance() {
        if (size == 0) {
            System.out.println("Heap is empty");
            return -1;
        }
        return importanceHeap[FRONT];
    }

    public boolean isFull() {
        return size == maxsize;
    }

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


    // public static void main(String[] args) {
    //     // Create a MinHeap with a maximum size of 10
    //     MinHeap minHeap = new MinHeap(3);

    //     // Insert elements into the MinHeap
    //     minHeap.insert("apple", 5);
    //     minHeap.insert("banana", 3);
    //     minHeap.insert("cherry", 8);
    //     minHeap.insert("date", 2);
    //     minHeap.insert("elderberry", 6);

    //     // Print the MinHeap
    //     System.out.println("Heap after insertions:");
    //     minHeap.print();

    //     // Remove the root element and print the heap
    //     System.out.println("Removing the root element: " + minHeap.remove());
    //     System.out.println("Heap after removing the root:");
    //     minHeap.print();

    //     // Check if a specific word exists in the heap
    //     String searchWord1 = "banana";
    //     System.out.println("Does the heap contain '" + searchWord1 + "'? " + minHeap.search(searchWord1));

    //     String searchWord2 = "cherry";
    //     System.out.println("Does the heap contain '" + searchWord2 + "'? " + minHeap.search(searchWord2));

    //     // Insert more elements to test dynamic behavior
    //     minHeap.insert("fig", 1);
    //     System.out.println("Heap after inserting 'fig':");
    //     minHeap.print();
    // }


}
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
        if(search(word, importance)) return;

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

    public boolean search(String word, int importance) {
        for (int i = 0; i < size; i++) {
            // If we encounter an importance greater than the given importance, exit early
            if (importanceHeap[i] > importance) {
                return false; // No need to check further
            }
    
            // Check if the word matches
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

    public void printList(){
        for (int i = 0; i < size; i++) {
            System.out.println(strHeap[i] + " (" + importanceHeap[i] + ")");
        }
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

}
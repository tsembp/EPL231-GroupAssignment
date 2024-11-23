public class MinHeap {

    private Element[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 0;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;

        Heap = new Element[this.maxsize];
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

    public int getRootImportance(){
        return Heap[FRONT].getImportance();
    }

    public boolean isFull(){
        return size == maxsize;
    }

    // Helper method for minHeapify | Swaps elements
    private void swap(int fpos, int spos) {
        Element tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    private void minHeapify(int pos) {
            int left = leftChild(pos);
            int right = rightChild(pos);
            int smallest = pos;

            if (left < size) {
                if(Heap[left].getImportance() < Heap[smallest].getImportance()) 
                    smallest = left;
            }
            if (right < size) {
                if(Heap[right].getImportance() < Heap[smallest].getImportance()) {
                    smallest = right;
                }
            }

            if(Heap[parent(pos)].getImportance() > Heap[smallest].getImportance()) {
                swap(pos, parent(pos));
            }

            if (smallest != pos) {
                swap(pos, smallest);
                minHeapify(smallest);
            }
    }

    public void insert(Element element) {
        if (size < maxsize) {
            // Standard insertion if the heap is not full
            Heap[size] = element;
            int current = size;
            size++;
    
            while (Heap[current].getImportance() < Heap[parent(current)].getImportance()) {
                swap(current, parent(current));
                current = parent(current);
            }
        } 
    }

    public Element remove() {
        if (size == 0) { // Check for empty heap
            System.out.println("Heap is empty");
            return null;
        }

        // Get element at Heap[FRONT] and replace it with the last
        Element popped = Heap[FRONT];
        Heap[FRONT] = Heap[size-1];
        size--;

        // Percolate down the last element which now is at the root
        minHeapify(FRONT);

        return popped;
    }

    public boolean search(Element element) {
        // Iterate over the heap array
        for (int i = 0; i < size; i++) {
            // Check if the current element matches the target key
            if (Heap[i].getWord().equals(element.getWord())) {
                return true; // Return the true if element already exists
            }
        }
        
        // If no element is found, return null
        return false;
    }

    public void print() {
        for (int i = 0; i < size / 2; i++) {
            String leftChild = ((2 * i) + 1 < size) ? Heap[(2 * i) + 1].getWord() + "" : "null";
            String rightChild = ((2 * i) + 2 < size) ? Heap[(2 * i) + 2].getWord() + "" : "null";
            System.out.println(
                "PARENT : " + Heap[i].getWord()
                + " LEFT CHILD : " + leftChild
                + " RIGHT CHILD : " + rightChild
            );
        }
    }

}

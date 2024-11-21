public class MinHeap {

    private Element[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 1;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;

        Heap = new Element[this.maxsize + 1];
        Heap[0] = new Element('\0',0); // Dummy element with minimum probeLength
        Heap[0].importance = Integer.MAX_VALUE; // Assign the dummy element's importance to INT_MAX
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    private boolean isLeaf(int pos) {
        return pos > (size / 2) && pos <= size;
    }

    // helper for minHeapify
    private void swap(int fpos, int spos) {
        Element tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            int left = leftChild(pos);
            int right = rightChild(pos);
            int smallest = pos;

            if (left <= size && Heap[left].getImportance() < Heap[smallest].getImportance()) {
                smallest = left;
            }
            if (right <= size && Heap[right].getImportance() < Heap[smallest].getImportance()) {
                smallest = right;
            }
            if (smallest != pos) {
                swap(pos, smallest);
                minHeapify(smallest);
            }
        }
    }

    public void insert(Element element) {
        if (size < maxsize) {
            // Standard insertion if the heap is not full
            Heap[++size] = element;
            int current = size;
    
            while (Heap[current].getImportance() < Heap[parent(current)].getImportance()) {
                swap(current, parent(current));
                current = parent(current);
            }
        } else {
            // Heap is full, find the largest importance element
            int largestIndex = 1;
            for (int i = 2; i <= size; i++) {
                if (Heap[i].getImportance() > Heap[largestIndex].getImportance()) {
                    largestIndex = i;
                }
            }
    
            // Compare and replace if the new element has smaller importance
            if (element.getImportance() < Heap[largestIndex].getImportance()) {
                Heap[largestIndex] = element;
                minHeapify(largestIndex); // Reheapify the heap
            } else {
                System.out.println("Element with higher importance not inserted: " + element.getImportance());
            }
        }
    }
    

    public void print() {
        for (int i = 1; i <= size / 2; i++) {
            String leftChild = (2 * i <= size) ? Heap[2 * i].getImportance() + "" : "null";
            String rightChild = (2 * i + 1 <= size) ? Heap[2 * i + 1].getImportance() + "" : "null";
            System.out.println(
                "PARENT : " + Heap[i].getImportance()
                + " LEFT CHILD : " + leftChild
                + " RIGHT CHILD : " + rightChild
            );
        }
    }

    public Element remove() {
        if (size == 0) {
            System.out.println("Heap is empty");
            return null;
        }

        Element popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--];
        minHeapify(FRONT);
        return popped;
    }

    public static void main(String[] arg) {
        System.out.println("The Min Heap is ");
    
        MinHeap minHeap = new MinHeap(5);
    
        // Inserting Elements with different 'importance' values
        Element e1 = new Element('a', 5);
        e1.importance = 20;
    
        Element e2 = new Element('b', 3);
        e2.importance = 10;
    
        Element e3 = new Element('c', 17);
        e3.importance = 30;
    
        Element e4 = new Element('d', 10);
        e4.importance = 5;
    
        Element e5 = new Element('e', 84);
        e5.importance = 50;
    
        Element e6 = new Element('f', 19);
        e6.importance = 25;
    
        Element e7 = new Element('g', 6);
        e7.importance = 15;
    
        Element e8 = new Element('h', 22);
        e8.importance = 35;
    
        Element e9 = new Element('i', 9);
        e9.importance = 8;
    
        // Insert elements into the heap
        minHeap.insert(e1);
        minHeap.insert(e2);
        minHeap.insert(e3);
        minHeap.insert(e4);
        minHeap.insert(e5);
        minHeap.insert(e6);
        minHeap.insert(e7);
        minHeap.insert(e8);
        minHeap.insert(e9);
    
        // Print heap contents
        minHeap.print();
    
        // Removing minimum value based on importance
        System.out.println("The Min val is " + minHeap.remove().importance);
    }
    
}

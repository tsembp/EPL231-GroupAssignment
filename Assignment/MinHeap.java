public class MinHeap {

    public Element[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 0;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;

        Heap = new Element[this.maxsize];
        Heap[0] = new Element('\0',0); // Dummy element with minimum probeLength
        Heap[0].importance = Integer.MAX_VALUE; // Assign the dummy element's importance to INT_MAX
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

    private boolean isLeaf(int pos) {
        return pos > size / 2 && pos <= size; 
    }

    // helper for minHeapify
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

            if(Heap[parent(pos)].importance > Heap[smallest].importance) {
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
        // else {
        //     // Heap is full, find the largest importance element
        //     int largestIndex = 0;
        //     for (int i = 1; i < size; i++) {
        //         if (Heap[i].getImportance() > Heap[largestIndex].getImportance()) {
        //             largestIndex = i;
        //         }
        //     }

        //     if(element.getImportance() == 8) {
        //         System.out.println("Largest Index for inserting 8: " + largestIndex);
        //     }
    
        //     if (element.getImportance() < Heap[largestIndex].getImportance()) {
        //         Heap[largestIndex] = element;
        //         minHeapify(largestIndex); // Reheapify the heap after replacement
        //     } else {
        //         System.out.println("Element with higher importance not inserted: " + element.getImportance());
        //     }
        // }
    }
    
    public boolean isFull(){
        return size == maxsize;
    }

    public int getRootImportance(){
        return Heap[FRONT].getImportance();
    }


    public void print() {
        for (int i = 0; i <= size / 2; i++) {
            String leftChild = ((2 * i) + 1 < size) ? Heap[(2 * i) + 1].word + "" : "null";
            String rightChild = ((2 * i) + 2 < size) ? Heap[(2 * i) + 2].word + "" : "null";
            System.out.println(
                "PARENT : " + Heap[i].word
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
        Heap[FRONT] = Heap[size-1];
        size--;
        minHeapify(FRONT);
        return popped;
    }

    public boolean search(Element element) {
        // Iterate over the heap array
        for (int i = 0; i < size; i++) {
            // Check if the current element matches the target key
            if (Heap[i].word == element.word) {
                return true; // Return the true if element already exists
            }
        }
        // If no element is found, return null
        return false;
    }

    // public static void main(String[] arg) {
    //     System.out.println("The Min Heap is ");
    
    //     MinHeap minHeap = new MinHeap(15); // Increased size to accommodate more elements
    
    //     // Inserting Elements with different 'importance' values
    //     Element e1 = new Element('a', 5);
    //     e1.importance = 20;
    
    //     Element e2 = new Element('b', 3);
    //     e2.importance = 10;
    
    //     Element e3 = new Element('c', 17);
    //     e3.importance = 30;
    
    //     Element e4 = new Element('d', 10);
    //     e4.importance = 5;
    
    //     Element e5 = new Element('e', 84);
    //     e5.importance = 50;
    
    //     Element e6 = new Element('f', 19);
    //     e6.importance = 25;
    
    //     Element e7 = new Element('g', 6);
    //     e7.importance = 15;
    
    //     Element e8 = new Element('h', 22);
    //     e8.importance = 35;
    
    //     Element e9 = new Element('i', 9);
    //     e9.importance = 8;
    
    //     Element e10 = new Element('j', 12);
    //     e10.importance = 12;
    
    //     Element e11 = new Element('k', 8);
    //     e11.importance = 18;
    
    //     Element e12 = new Element('l', 7);
    //     e12.importance = 7;
    
    //     Element e13 = new Element('m', 14);
    //     e13.importance = 28;
    
    //     Element e14 = new Element('n', 21);
    //     e14.importance = 3;
    
    //     Element e15 = new Element('o', 25);
    //     e15.importance = 40;
    
    //     // Insert elements into the heap
    //     minHeap.insert(e1);
    //     minHeap.insert(e2);
    //     minHeap.insert(e3);
    //     minHeap.insert(e4);
    //     minHeap.insert(e5);
    //     minHeap.insert(e6);
    //     minHeap.insert(e7);
    //     minHeap.insert(e8);
    //     minHeap.insert(e9);
    //     minHeap.insert(e10);
    //     minHeap.insert(e11);
    //     minHeap.insert(e12);
    //     minHeap.insert(e13);
    //     minHeap.insert(e14);
    //     minHeap.insert(e15);
    
    //     // Print heap contents
    //     minHeap.print();
    
    //     // Removing minimum values based on importance
    //     System.out.println("The Min val is " + minHeap.remove().importance);
    //     minHeap.print();

    //     System.out.println("The Min val is " + minHeap.remove().importance);
    //     minHeap.print();

    //     System.out.println("The Min val is " + minHeap.remove().importance);

    
    //     // Print heap contents again
    //     minHeap.print();
    // }


}

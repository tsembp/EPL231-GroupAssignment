package lab8;

public class RobinHoodHashing {
    
    Element table[];
    int capacity;
    int size;
    int maxProbeLength;

    public RobinHoodHashing(){
        this.size = 0;
        this.capacity = 5;
        this.maxProbeLength = 0;
        this.table = new Element[capacity];
    }

    public void insert(int key){
        Element newElement = new Element(key, 0);
        int index = hashFunction(key);

        int probeLength = 0;
        while(table[index] != null){
            if(probeLength > table[index].getProbeLength()){ // Switch elements
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp;
            }

            index = (index + 1) % capacity;
            probeLength++;
        }

        newElement.setProbeLength(probeLength);
        table[index] = newElement;
        size++;
        maxProbeLength = Math.max(maxProbeLength, probeLength);

        // 90% full table => rehash
        if((double)size / capacity > 0.9) rehash();
    }

    public boolean search(int key){
        int index = hashFunction(key);
        int probeLength = 0;

        while(table[index] != null && probeLength <= maxProbeLength){
            if(table[index].getKey() == key){
                return true;
            }

            index = (index + 1) % capacity;
            probeLength++;
        }

        return false; // key not found
    }
    
    private int findNextPrime(int n) {
    	if(n <= 2) return 2;
    	
    	while(!isPrime(n)) {
    		n++;
    	}
    	
    	return n;
    }
    
    private boolean isPrime(int n) {
		if(n < 2) return false;
		if(n == 2 || n == 3) return true;
		if(n % 2 == 0 || n % 3 == 0) return false;
		
		for(int i = 5; i * i <= n; i+=6) {
			if(n % i == 0 || n % (i + 2) == 0) return false;
		}
		
		return true;
	}

	private void rehash(){
    	 Element newTalbe[] = new Element[findNextPrime(capacity) * 2*(capacity)];
    	 
    	
    	
    	
    }

    private int hashFunction(int key){
        return key % capacity;
    }

    public void printHash(){
        System.out.print("[");
        for(int i = 0; i < table.length; i++){
            if(table[i] == null){
                System.out.print("_, ");
            } else{
                System.out.print("(" + table[i].getKey() + ", " + table[i].getProbeLength() + ")");
            }
        }

        System.out.println("]");
    }

}

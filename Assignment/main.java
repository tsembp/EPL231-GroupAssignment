public class main {

    public static void main(String args[]){
    	
        RobinHoodHashing hash = new RobinHoodHashing();

        hash.insert(10);

        hash.insert(22);

        hash.insert(31);

        hash.insert(4);
        hash.printHash();

        System.out.println("Searching for 7... Does it exist? " + hash.search(7));

        System.out.println("Searching for 17... Does it exist? " + hash.search(17));

        System.out.println("Searching for 4... Does it exist? " + hash.search(4));

        hash.insert(15);
        // hash.insert(16);
        System.out.println("mPL = " + hash.maxProbeLength);




        hash.printHash();

    }





}

package lab8;

public class main {

    public static void main(String args[]){
    	
        RobinHoodHashing hash = new RobinHoodHashing();

        hash.insert(10);
        hash.printHash();
        System.out.println("mPL = " + hash.maxProbeLength);

        hash.insert(22);
        hash.printHash();
        System.out.println("mPL = " + hash.maxProbeLength);

        hash.insert(31);
        hash.printHash();
        System.out.println("mPL = " + hash.maxProbeLength);

        hash.insert(4);
        hash.printHash();
        System.out.println("mPL = " + hash.maxProbeLength);

        System.out.println("Searching for 7... Does it exist? " + hash.search(7));

        System.out.println("Searching for 17... Does it exist? " + hash.search(17));

        System.out.println("Searching for 4... Does it exist? " + hash.search(4));





    }





}

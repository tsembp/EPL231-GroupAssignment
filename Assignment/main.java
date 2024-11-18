public class main {

    public static void main(String args[]){
    	
        RobinHoodHashing hash = new RobinHoodHashing();

        hash.insert('a');

        hash.insert('d');

        hash.insert('t');

        System.out.println("Searching for 'f'... Does it exist? " + hash.search('f'));

        System.out.println("Searching for 'd'... Does it exist? " + hash.search('d'));

        hash.insert('j');
        // hash.insert(16);
        System.out.println("mPL = " + hash.maxProbeLength);




        hash.printHash();

    }





}

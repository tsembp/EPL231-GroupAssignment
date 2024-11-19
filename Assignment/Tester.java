public class Tester {

    public static void main(String args[]){
    	
        TrieNode tree = new TrieNode();

        tree.insert("abc");
        tree.insert("cd");
        tree.insert("cae");
        tree.insert("cab");
        tree.insert("cac");
        tree.insert("cb");
        tree.insert("cc");
        tree.insert("b");
        tree.insert("eef");
        tree.insert("ea");
        tree.insert("eb");
        tree.insert("ec");
        tree.insert("d");
        // tree.insert("li");

        // System.out.println(tree.search("d"));
        tree.printTree();

        // System.out.println(tree.search("cooked"));


    }





}

import java.io.*;
import java.util.*;

public class BatchTrieTest {

    public static void main(String[] args) {
        String basePath = "./Dictionaries/Same Length/"; // Update this to the correct path
        String outputFile = "memory_report.txt";
        
        List<String> lengths = Arrays.asList("Length 5", "Length 7", "Length 9", "Length 13", "Length 15", "Length 17");
        List<String> testFolders = Arrays.asList("Test 1", "Test 2", "Test 3", "Test 4", "Test 5");
        List<String> fileNames = Arrays.asList("1000.txt", "5000.txt", "10000.txt", "25000.txt", "50000.txt", "75000.txt", "100000.txt");

        Map<String, Map<String, List<Integer>>> memoryUsages = new HashMap<>();

        for (String length : lengths) {
            memoryUsages.put(length, new HashMap<>());
            for (String fileName : fileNames) {
                memoryUsages.get(length).put(fileName, new ArrayList<>());
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String length : lengths) {
                for (String testFolder : testFolders) {
                    for (String fileName : fileNames) {
                        String filePath = basePath + length + "/" + testFolder + "/" + fileName;
                        System.out.println("Processing: " + filePath);

                        // Step 1: Construct Trie
                        TrieNode tree = new TrieNode();
                        // TrieNodeStatic tree1 = new TrieNodeStatic();

                        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                            String word;
                            while ((word = br.readLine()) != null) {
                                word = word.trim().toLowerCase();
                                if (!word.isEmpty()) {
                                    tree.insert(word);
                                    // TrieNodeStatic.insert(tree1, word);
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + filePath);
                            e.printStackTrace();
                            continue;
                        }

                        // Step 2: Calculate Memory
                        int memoryUsage = calculateMemory(tree);
                        memoryUsages.get(length).get(fileName).add(memoryUsage);
                    }
                }
            }

            // Calculate and write averages
            for (String length : lengths) {
                bw.write("Length: " + length);
                bw.newLine();
                for (String fileName : fileNames) {
                    List<Integer> memoryValues = memoryUsages.get(length).get(fileName);
                    long totalMemory = 0;
                    for (int memory : memoryValues) {
                        totalMemory += memory;
                    }
                    long averageMemory = memoryValues.isEmpty() ? 0 : totalMemory / memoryValues.size();
                    bw.write("    Average for " + fileName.replace(".txt", "") + " words is: " + averageMemory + " bytes");
                    bw.newLine();
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + outputFile);
            e.printStackTrace();
        }

        System.out.println("Processing complete. Results written to " + outputFile);
    }

    private static int calculateMemory(TrieNode root){
        if(root == null) return 0;

        int totalMemory = 0;
        int hashTableSize = root.getHashTable().getTable().length;
        
        int nodeMemory = 4; // sizeOf(int wordLength)
        int robinHoodMemory = 13 + (hashTableSize * 4); // 13 bytes + table.size * 4 reference
        nodeMemory += 4 * robinHoodMemory; // reference to RobinHoodHash: 4 * sizeOf(RobinHoodHash)

        totalMemory += nodeMemory;

        // for(Element element : root.getHashTable().getTable()){
        //     if(element != null){
        //         totalMemory += 15; // sizeOf(Element) = 15
        //     }
        //     // if element is null don't add its size to total memory
        // }

        totalMemory += 15 * root.getHashTable().getCapacity();

        for (Element element : root.getHashTable().getTable()) {
            if (element != null && element.getNode() != null) {
                totalMemory += calculateMemory(element.getNode());
            }
        }

        return totalMemory;
    }

    public static int traverseTrie(TrieNodeStatic root) {
		// Base case: if the current node is null, return 0
		if (root == null) return 0;
	
		// Initialize the count for the current node
		int count = 1; // Count the current node
	
		// Recursively traverse all children and add their counts
		for (int i = 0; i < 26; i++) {
			count += traverseTrie(root.getChildren()[i]);
		}
	
		// Return the total count of nodes
		return count;
	}

	public static int calculateMemory(TrieNodeStatic root){
		// traverseTrie(root) returns the number of nodes in the Trie tree
		return 113 * traverseTrie(root); // sizeOf(TrieNodeStatic) = 113 bytes
	}
}

public class Main {
    public static void main(String[] args) {
        // Ensure proper arguments passed
        if (args.length < 2) {
            System.out.println("Usage: java Main <dictionaryFile> <scriptFile>");
            return;
        }

        String dictionaryFile = args[0]; // First argument: Dictionary file path
        String scriptFile = args[1];    // Second argument: Script file path

        // Load the dictionary and script into the Trie
        DictionaryLoader loader = new DictionaryLoader();
        loader.loadDictionary(dictionaryFile);
        
        // Check for not found words in dictionary
        loader.validateDictionary(dictionaryFile);

        // Process script to assign importance to each word
        loader.processScript(scriptFile);

        // Star the search engine process
        SearchEngine searchEngine = new SearchEngine(loader.getTree());
        searchEngine.start();
    }

}

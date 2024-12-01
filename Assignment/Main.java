public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <dictionaryFile> <scriptFile>");
            return;
        }

        String dictionaryFile = args[0]; // First argument: Dictionary file path
        String scriptFile = args[1];    // Second argument: Script file path

        // Step 1: Load the dictionary and script into the Trie
        DictionaryLoader loader = new DictionaryLoader();
        loader.loadDictionary(dictionaryFile);
        loader.validateDictionary(dictionaryFile);
        loader.processScript(scriptFile);

        // Step 2: Start the search engine
        SearchEngine searchEngine = new SearchEngine(loader.getTree());
        searchEngine.start();
    }
}

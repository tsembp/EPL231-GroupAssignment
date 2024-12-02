/**
 * Represents an element with a key, probe length, importance, and a reference to a TrieNode.
 * This class is used to store information in a trie structure.
 */
public class Element {

    /**
     * The character key associated with this element.
     */
    private char key;           // 2 bytes

    /**
     * The probe length for this element, used in hashing or trie operations.
     */
    private int probeLength;    // 4 bytes

    /**
     * A reference to the associated TrieNode for this element.
     */
    private TrieNode node;      // 4 bytes (reference)

    /**
     * The importance value of this element, used for prioritization or scoring.
     */
    private int importance;     // 4 bytes

    /**
     * Indicates whether this element represents a complete word in the trie.
     */
    private boolean isWord;     // 1 byte
                                // TOTAL: 15 bytes

    /**
     * Constructs an Element with the specified key and probe length.
     * The importance is initialized to 0, and the node reference is set to null.
     *
     * @param key          the character key for this element.
     * @param probeLength  the probe length for this element.
     */
    public Element(char key, int probeLength) {
        this.key = key;
        this.probeLength = probeLength;
        this.importance = 0;
        this.node = null;
    }

    /**
     * Retrieves the key of this element.
     *
     * @return the character key of this element.
     */
    public char getKey() {
        return key;
    }

    /**
     * Updates the key of this element.
     *
     * @param key the new character key for this element.
     */
    public void setKey(char key) {
        this.key = key;
    }

    /**
     * Retrieves the probe length of this element.
     *
     * @return the probe length of this element.
     */
    public int getProbeLength() {
        return probeLength;
    }

    /**
     * Updates the probe length of this element.
     *
     * @param probeLength the new probe length for this element.
     */
    public void setProbeLength(int probeLength) {
        this.probeLength = probeLength;
    }

    /**
     * Increments the probe length of this element by 1.
     */
    public void incrementProbeLength() {
        this.probeLength++;
    }

    /**
     * Retrieves the associated TrieNode of this element.
     *
     * @return the TrieNode reference for this element, or null if none exists.
     */
    public TrieNode getNode() {
        return node;
    }

    /**
     * Sets the associated TrieNode for this element.
     *
     * @param node the TrieNode reference to associate with this element.
     */
    public void setNode(TrieNode node) {
        this.node = node;
    }

    /**
     * Retrieves the importance of this element.
     *
     * @return the importance value of this element.
     */
    public int getImportance() {
        return importance;
    }

    /**
     * Updates the importance of this element.
     *
     * @param importance the new importance value for this element.
     */
    public void setImportance(int importance) {
        this.importance = importance;
    }

    /**
     * Checks if this element represents a complete word.
     *
     * @return true if this element represents a complete word, false otherwise.
     */
    public boolean getIsWord() {
        return this.isWord;
    }

    /**
     * Sets whether this element represents a complete word.
     *
     * @param flag true to indicate this element is a word, false otherwise.
     */
    public void setIsWord(boolean flag) {
        this.isWord = flag;
    }
}

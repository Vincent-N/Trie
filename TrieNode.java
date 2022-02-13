import java.util.LinkedList;
import java.util.List;

public class TrieNode {
    private char letter;
    private int usageCount; // Stores how often a letter is used. Possibly use for future 
                            // implementation of sorting by more frequently used words
    private List<TrieNode> children;

    /**
     * Creates a new TrieNode
     * @param letter != null
     */
    public TrieNode(char letter) {
        this.letter = letter;
        children = new LinkedList<>();
        usageCount = 0; // 0 if not a word, >0 if it is a word
    }

    /**
     * gets letter stored in TrieNode
     * @return letter
     */
    public char getLetter() {
        return letter;
    }

    /**
     * gets how many times this letter is used
     * @return usageCount
     */
    public int getUsageCount() {
        return usageCount;
    }

    /**
     * gets a list of the children of this TrieNode
     * @return children of TrieNode
     */
    public List<TrieNode> getChildren() {
        return children;
    }

    /**
     * increments usageCount by 1
     */
    public void updateUsageCount() {
        usageCount++;
    }

    public String toString() {
        return "Letter: " + letter + " usageCount: " + usageCount;
    }
    
    /**
     * Overrides equals
     */
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        return letter == ((TrieNode) other).letter;
    }
}

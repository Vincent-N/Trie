import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Trie {
    private TrieNode root;
	private int numWords;
	private int numNodes; // Counts number of nodes, including root
	
	/**
	 * Creates new Trie
	 */
	public Trie() {
	    root = new TrieNode(' ');
	    numNodes = 1;
		// Default values okay for numWords
	}
	
	/**
	 * Gets number of unique valid words in Trie
	 * @return numWords
	 */
	public int getNumWords() {
	    return numWords;
	}
	
	/**
	 * Get number of nodes in Trie, including root node
	 * @return numNodes
	 */
	public int getNumNodes() {
	    return numNodes;
	}
	
	/**
     * Add a word to Trie
     * @param word.length() > 0, word != null
     */
    public void addWord(String word) {
        word = word.replaceAll("\\W", "").toLowerCase();
        if (word.length() > 0) {
            TrieNode temp = null;
            List<TrieNode> list = root.getChildren();
            int charIndex = 0;
            // Go through and add all letters
            while (charIndex < word.length()) {
                char currChar = word.charAt(charIndex);
                TrieNode nextNode = getChild(list, currChar);
                if (nextNode == null) {
                    // Add node if it doesn't exist
                    TrieNode newNode = new TrieNode(currChar);
                    list.add(newNode);
                    nextNode = newNode;
                    numNodes++;
                }
                charIndex++;
                temp = nextNode;
                list = temp.getChildren();
                }
            // Increment usage to reflect it's a word
            if (temp.getUsageCount() == 0) {
                numWords++;
            }
            temp.updateUsageCount();   
        }
    }

	/**
	 * Finds and returns a list with all the words that start with a given prefix. Given no prefix
	 * will return all words
	 * @param prefix != null
	 * @return list with all words that start with a given prefix
	 */
	public List<String> findAllWordsWithPrefix(String prefix) {
	    prefix = prefix.toLowerCase();
	    TrieNode node = findNodeWithPrefix(prefix);
	    List<String> allWords = new ArrayList<>();
	    getAllWordsGivenNode(allWords, node, prefix); // Null node check done in function itself
	    return allWords;
	}
	
	/**
	 * Gets the most used word given a prefix
	 * @param prefix != null
	 * @return most used word of given prefix
	 */
	public String getMostUsedWord(String prefix) {
	    // PriorityQueue can possibly be stored for future features
	    PriorityQueue<WordAndUsageCount> pq = new PriorityQueue<>();
	    TrieNode node = findNodeWithPrefix(prefix);
	    getAllWordAndUsageGivenNode(pq, node, prefix);
	    if (pq.size() == 0) {
	        return "";
	    }
	    WordAndUsageCount mostUsed = pq.poll();
	    return mostUsed.word;
	}
	
	/**
	 * Finds node that corresponds with given prefix. Inputting prefix of empty string returns root
	 * node
	 * @param prefix != null, prefix.length() > 0
	 * @return node that corresponds with given prefix. If it doesn't exist, returns null
	 */
	private TrieNode findNodeWithPrefix(String prefix) {
	    TrieNode node = root;
	    List<TrieNode> currChildrenList = root.getChildren();
	    int wordIndex = 0;
	    // Go through word
	    while (wordIndex < prefix.length()) {
	        boolean childFound = false;
	        int childIndex = 0;
	        char currChar = prefix.charAt(wordIndex);
	        // Iterate through all children of current TrieNode until next letter found
	        while (!childFound && childIndex < currChildrenList.size()) {
	            if (currChildrenList.get(childIndex).getLetter() == currChar) {
	                childFound = true;
	                node = currChildrenList.get(childIndex);
	            }
	            childIndex++;
	        }
	        if (!childFound) {
	            return null;
	        }
	        currChildrenList = node.getChildren();
	        wordIndex++;
	    }
	    return node;
	}
	
	/**
	 * Finds all words that are descendants of or are from the given node
	 * @param listOfWords != null
	 * @param node none
	 * @param word != null
	 */
	private void getAllWordsGivenNode(List<String> listOfWords, TrieNode node, String word) {
	    if (node != null) {
	        if (node.getUsageCount() > 0) {
	            listOfWords.add(word);
	        }
	        
	        List<TrieNode> childNode = node.getChildren();
	        for (int i = 0; i < childNode.size(); i++) {
	            getAllWordsGivenNode(listOfWords, childNode.get(i), 
	                    word + childNode.get(i).getLetter());
	        }
	    }
	}
	
	/**
	 * Recursively traverses through Trie, starting at node given. All words from or below that
	 * node are put into a priorityqueue and ranked based on how many times those words are used
	 * @param pq != null
	 * @param node != null
	 * @param word != null
	 */
	private void getAllWordAndUsageGivenNode(PriorityQueue<WordAndUsageCount> pq, TrieNode node, 
	        String word) {
	    if (node != null) {
	        if (node.getUsageCount() > 0) {
                pq.add(new WordAndUsageCount(word, node.getUsageCount()));
            }
	        
	        List<TrieNode> childNode = node.getChildren();
            for (int i = 0; i < childNode.size(); i++) {
                getAllWordAndUsageGivenNode(pq, childNode.get(i), 
                        word + childNode.get(i).getLetter());
            }
	    }
	}
	
	/**
	 * Finds TrieNode in the given list that has the given letter.
	 * @param list != null
	 * @param letter != null
	 * @return TrieNode with given letter. null if nonexistent
	 */
	private TrieNode getChild(List<TrieNode> list, char letter) {
	    for (int i = 0; i < list.size(); i++) {
	        if (list.get(i).getLetter() == letter) {
	            return list.get(i);
	        }
	    }
	    return null;
	}
	
	/**
	 * Stores words along with their corresponding usageCount so that we can order based on 
	 * usageCount
	 * @author Vincent
	 *
	 */
	private static class WordAndUsageCount implements Comparable<WordAndUsageCount> {
	    private String word;
	    private int usageCount;
	    
	    /**
	     * Creates a WordAndUsageCount object
	     * @param word != null
	     * @param usageCount none
	     */
	    public WordAndUsageCount(String word, int usageCount) {
	        this.word = word;
	        this.usageCount = usageCount;
	    }
	    
	    /**
	     * implements compareTo. Sorts first based on usageCount, higher usageCount first (in
	     * other words, the more frequently used word first). If equal, sorts based on 
	     * lexicographical ordering of word
	     */
        public int compareTo(WordAndUsageCount o) {
            if (usageCount == o.usageCount) {
                return word.compareTo(word);
            }
            return o.usageCount - usageCount;
        }
	}
	
	
}

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class to run Trie functions
 * @author Vincent
 *
 */
public class UserInputTrieTest {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        Trie t = new Trie();
        boolean ended = false;
        while (!ended) {
            System.out.print("Insert next command: ");
            String userInput = in.nextLine();
            if (userInput.equals("-.end")) {
                ended = true;
            } else if (userInput.equals("")) {
                System.out.println("Please enter a valid command.");
            } else {
                String[] allWords = userInput.split(" ");
                if (allWords[0].equals("-.print") && allWords.length == 2) { // Key word "print"
                    if (allWords[1].equals("-a")) { // "all"
                        System.out.println(t.findAllWordsWithPrefix(""));
                    } else {
                        System.out.println(t.findAllWordsWithPrefix(allWords[1]));
                    }
                } else if (allWords[0].equals("-.most") && allWords.length == 2) { // Key word "most"
                    if (allWords[1].equals("-a")) {
                        System.out.println(t.getMostUsedWord(""));
                    } else {
                        System.out.println(t.getMostUsedWord(allWords[1]));
                    }
                } else if (allWords[0].equals("-.ifile") && allWords.length == 2) { // Key word "ifile"
                    File file = new File(allWords[1]);
                    if (!file.exists()) {
                        System.out.println("File does not exist.");
                    } else {
                        Scanner inputFile = new Scanner(file);
                        while (inputFile.hasNext()) {
                            t.addWord(inputFile.next().replace(",", "").replace("(", "")
                                    .replace(")", "").toLowerCase());
                        }
                        inputFile.close();
                    }
                } else { // Just words to add
                    for (int i = 0; i < allWords.length; i++) {
                        t.addWord(allWords[i]);
                    }
                }
            }
            System.out.println();
        }
        System.out.println("Program has ended. Have a good day!");
        in.close();
    }
}

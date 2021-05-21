import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author thomas de borst ID: 1004302
 */

/**
 * Class OddWords to read in a text input and tokenize each word in a string value
 * for entry into binary tree
 */
public class OddWords {

    private Stream<String> tokens;
    private final String path;
    private final BSTLex tree;
    private static String fileName;

    public OddWords(String path) {
        this.path = path;
        this.tree = new BSTLex();
        input();
    }

    /**
     * Method for operation of OddWords class
     * Checks if characters are letters and set all to lowercase
     * Check for any special characters and omits them..................
     * @return Returns an OddWords object which contains the BST
     */
    public OddWords operate() {
        final List<String> refinedTokens = tokens
                .filter(it -> it.chars().allMatch(Character::isLetterOrDigit))
                .map(String::toLowerCase)
                .flatMap(it -> {
                    if (it.chars().anyMatch(Character::isDigit))
                        return new Scanner(it).skip("\\d").tokens();
                    else return Stream.of(it);
                })
                .collect(Collectors.toList());
        refinedTokens.forEach(tree::insert);

        return this;
    }

//    /**
//     * Method to return tree for testing
//     * @return Returns BST
//     */
//    public BSTLex getTree() {
//        return tree;
//    }

    /**
     * Method for inputting file
     * Uses scanner to read in text file
     */
    private void input() {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file).useDelimiter(Pattern.compile("\\d")).useDelimiter(" ");;
            this.tokens = scanner.tokens();
        } catch (FileNotFoundException e) {
            System.err.println("CANNOT READ THE FILE");
            System.exit(1);
        }
    }

    /**
     * Main method for operating OddWords
     *
     * @param args Accepts a text file as argument
     */
    public static void main(String[] args) {
        try {
            fileName = args[0];
        } catch (Exception ex) {
            //display error message
            System.out.println(ex);
        }

        //inset try catch for filename input
        System.out.println("LEXICON");
        new OddWords(fileName).operate().tree.retrieve().forEach(System.out::println);
    }
}
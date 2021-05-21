import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author thomas de borst ID: 1004302
 */

public class BSTLexTest {

    public static BSTLex tree1 = new BSTLex();
    public static BSTLex tree2 = new BSTLex();
    public static BSTLex tree3 = new BSTLex();
    public static BSTLex tree4 = new BSTLex();



    //public static List<Stream> tokens;
    public static Stream<String> tokens = Arrays.asList("Hello", "Hi", "Bonjour").stream();
    public static Stream<String> tokens2 = Arrays.asList("Cat", "Duck", "Dog", "Mouse", "Elephant").stream();
    public static Stream<String> tokens3 = Arrays.asList("Ciao", "Hi", "bonjour444", "salutations").stream();
    public static Stream<String> tokens4 = Arrays.asList("Dog", "Cat", "Mouse", "duck", "elephant").stream();


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {





    }

    @AfterClass
    public static void tearDown() throws Exception {

    }

    @Before
    public void setUp() throws Exception {

        processLikeFile(tree1, tokens);
        tree1.insert("hej");

        //Add the animal Lemur in with additional symbols to test if they are stripped away by processLikeFile method
        tokens2 = Arrays.asList("Cat", "Duck", "Dog", "Mouse", "Elephant", "lemur").stream();
        processLikeFile(tree2, tokens2);



        /*Add duplicates and a mix of lower/upper case and additional symbols to make sure the strings are processed
        correctly before doubles are deleted.*/
        tokens3 = Arrays.asList("Ciao", "Hi", "bonjour", "salutations", "bonjour", "SALUTATIONS").stream();
        processLikeFile(tree3, tokens3);



        processLikeFile(tree4, tokens4);
        tree4.insert("Duck");
        tree4.insert("Elephant");
    }

    @After


    @Test
    public void testAdd1() {

            final List<String> strings = tree1.retrieve().collect(Collectors.toList());
            assert strings.containsAll(List.of("hello", "hi", "bonjour", "hej"));

    }

    @Test
    public void testAdd2() {

        final List<String> strings = tree2.retrieve().collect(Collectors.toList());
        assert strings.containsAll(List.of("cat", "duck", "dog", "mouse", "elephant", "lemur"));
    }

    @Test
    public void test3AddExisting1() {

        final List<String> strings = tree3.retrieve().collect(Collectors.toList());
        assert strings.containsAll(List.of("ciao", "hi"));
    }

    @Test
    public void test4AddExisting2() {


        final List<String> strings = tree4.retrieve().collect(Collectors.toList());
        assert strings.containsAll(List.of("dog", "cat", "mouse"));
    }

    /**
     * Method to process strings in the same manner as files in OddWords class.
     * @param treeName Method is passed the name of the tree to insert the refined tokens into
     * @param tokensName Mehtod is also passed the name of the stream tokens that need to be refined to lower case and with special characters etc
     */
    public void processLikeFile(BSTLex treeName, Stream<String> tokensName) {
        if (tokensName != null) {
            final List<String> refinedTokens = tokensName
                    .filter(it -> it.chars().allMatch(Character::isLetterOrDigit))
                    .map(String::toLowerCase)
                    .flatMap(it -> {
                        if (it.chars().anyMatch(Character::isDigit))
                            return new Scanner(it).useDelimiter("\\d,;:!'#&").tokens();
                        else return Stream.of(it);
                    })
                    .collect(Collectors.toList());
            refinedTokens.forEach(treeName::insert);
        }
    }
}

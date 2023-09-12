package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
public class TestMultiWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String SMALL_WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String LARGE_WORDS_FILE = "data/ngrams/top_49887_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                SMALL_WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testOccurenceAndChangeKEmpty() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("pad", "movement", "set", "press", "lead", "effect", "shape", "center", "right");
        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980, 6);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testOccurenceAndChangeKBigN() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("position", "vertical");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 8);
        String actual = studentHandler.handle(nq);
        String expected = "[post, vertical]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testOccurenceAndChangeKSmallN() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("food", "cake");
        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[biscuit, cake, kiss, snap, wafer]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testOccurenceAndChangeKNotUsed() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("communication", "canticle");
        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980, 9);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);

        List<String> words3 = List.of("metric", "ng");
        NgordnetQuery nq3 = new NgordnetQuery(words3, 1470, 2019, 3);
        String actual3 = studentHandler.handle(nq3);
        String expected3 = "[ng]";
        assertThat(actual3).isEqualTo(expected3);
    }

    @Test
    public void testNoHyponyms() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("hello", "jfyn isodfhnuiad");

        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980, 4);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testNoHyponymsPt2() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                SMALL_WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("Jonathan");

        NgordnetQuery nq = new NgordnetQuery(words, 1920, 1980, 2);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testOneMissingWordList() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("food", "ksfhkhk");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testMissingWordList() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                LARGE_WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("sweet", "cake", "ksfhkhk", "treat");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

}

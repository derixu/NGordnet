package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.wordnet.WordNet;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        WordNet net = new WordNet(synsetFile, hyponymFile);
        NGramMap allWords = new NGramMap(wordFile, countFile);
        return new HyponymsHandler(net, allWords);
    }
}

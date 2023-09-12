package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.wordnet.WordNet;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordNet wordNet;
    NGramMap map;

    public HyponymsHandler(WordNet net, NGramMap map) {
        wordNet = net;
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words(); // this all gets the data from q
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        String[] hyponyms = wordNet.Hyponyms(words);
        if (hyponyms.length == 0) {
            return "[]";
        }
        if (k > 0) {
            List<String> bigKList = helpingHandle(hyponyms, k, startYear, endYear); // call to a helper method
            int size = Math.min(bigKList.size(), k);
            String[] newHyponyms = new String[size];
            int i = 0;
            for (String word : hyponyms) { // iterates through all the words in hyponyms
                if (i == size) {
                    break;
                }
                if (bigKList.contains(word)) { // if the word is in the k most frequent, it is added to newHyponyms
                    newHyponyms[i] = word;
                    i++;
                }
            }
            return Arrays.toString(newHyponyms);
        }
        return Arrays.toString(hyponyms);
        // sorts out the least frequent while preserving alphabetic order
    }

    /** returns a list of k words sorted by most to least frequent within the time **/
    private List<String> helpingHandle(String[] hyponyms, int k, int sy, int ey) {
        TreeMap<Double, String> bigKMap = new TreeMap<>(); // TreeMap so the values are sorted by freq
        List<String> bigKList = new ArrayList<>();
        for (String current : hyponyms) { // iterates through all the words and gets their countHistory
            TimeSeries ts = map.countHistory(current, sy, ey);
            List<Double> data = ts.data(); // gets a list of their data
            double sum = 0.0;
            for (int i = 0; i < data.size(); i++) { // sums up the data NEGATIVELY which helps with sorting
                sum -= data.get(i);
            }
            if (sum != 0.0) { //Tests to make sure that the word is used within the time period
                bigKMap.put(sum, current);
                //TreeMaps sort from small to large so the k most frequent will be at the front
            }
        }
        int count = 0;
        for (Double val : bigKMap.keySet()) { // adds the top k values to a new list
            if (count == k) {
                break;
            } // ends the for loop after k occurrences
            bigKList.add(bigKMap.get(val));
            count++;
        }
        return bigKList;
    }
}


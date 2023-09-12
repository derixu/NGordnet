package ngordnet.wordnet;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class WordNet {

    private Graph wordGraph;
    private HashMap<String, ArrayList<Integer>> wordLocations; //Map to find the indexed synset of a word using this map (one word can be in multiple synsets)
    private ArrayList<String[]> wordList; //Find the words at a given synset using this list (Could something else be faster than an ArrayList? A HashMap??)
    private int size = 0;

    public WordNet(String SynsetsFileName, String HyponymsFileName) {
        In synsets = new In(SynsetsFileName); //Reader for synsets file
        In hyponyms = new In(HyponymsFileName); //Reader for hyponyms file
        wordLocations = new HashMap<>();
        wordList = new ArrayList<>();

        while (!synsets.isEmpty()) {
            size++;
            String[] line = synsets.readLine().split(","); //Split line so we can isolate elements of it
            int index = Integer.valueOf(line[0]); //Numerical index/location of the synset on the graph
            String[] words = line[1].split(" "); //Split by space so multiple words in a synset can be accounted for
            for (String word : words) {
                if (!wordLocations.containsKey(word)) { //Create/add location of word to locations map
                    wordLocations.put(word, new ArrayList<>());
                }
                wordLocations.get(word).add(index);
            }
            wordList.add(words); //Add synset to list (index in list represents the position of the synset)
        }

        wordGraph = new Graph(size);

        while (!hyponyms.isEmpty()) { //Make proper edge connections based on hyponyms file
            String curr = hyponyms.readLine();
            String[] elems = curr.split(",");
            int hypernymIndex = Integer.valueOf(elems[0]);
            for (int i = 1; i < elems.length; i++) {
                wordGraph.addEdge(hypernymIndex, Integer.valueOf(elems[i]));
            }
        }
    }

    public String[] Hyponyms(List<String> hypernyms) {
        TreeSet<String> hyponyms = new TreeSet<>(); //Tree set to alphabetize and ensure uniqueness
        TreeSet<String> crossCheck = new TreeSet<>();
        ArrayList<Integer> Locations = wordLocations.get(hypernyms.get(0));
        if (Locations == null) {
            return new String[0];
        }
        for (int hypernymIndex : Locations) { //For each location of the first hypernym
            GraphTraverser traversalHelper = new GraphTraverser(wordGraph, hypernymIndex); //new Traversal Object to find the hypernym indices from each location
            for (int i : traversalHelper.family) { //for each index, find the corresponding synset -> add each word in the synset to the return Set
                for (String word : wordList.get(i)) {
                    hyponyms.add(word);
                }
            }
        }
        for (String other : hypernyms.subList(1, hypernyms.size())) { //Works fine for 2/3 word lists on the largest files given, but unsure if this is the best way
            if (wordLocations.get(other) == null) {
                return new String[0];
            }
            for (int hypernymIndex : wordLocations.get(other)) { //For each location of the other hypernym
                GraphTraverser traversalHelper = new GraphTraverser(wordGraph, hypernymIndex); //new Traversal Object to find the hypernym indices from each location
                for (int i : traversalHelper.family) { //for each index, find the corresponding synset -> add each word in the synset to the cross-checking Set
                    for (String word : wordList.get(i)) {
                        crossCheck.add(word);
                    }
                }
            }
            hyponyms.retainAll(crossCheck); //Cross-check to only keep shared words
            crossCheck.clear(); //clear the Set for another loop
        }
        String[] result = hyponyms.toArray(new String[hyponyms.size()]); //formatting
        return result;
    }
}

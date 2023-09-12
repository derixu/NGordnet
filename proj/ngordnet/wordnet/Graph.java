package ngordnet.wordnet;

import java.util.HashSet;

public class Graph {

    private HashSet<Integer>[] AdjList; //Adjacency List: each index is a node location which points to a HashSet of indexes the node is adjacent to
    private int numVertices;
    private int numEdges = 0; //Don't know if this is necessary, keeping in case
    public Graph(int size) { //Create a properly sized array, each box pointing to an empty HashSet
        AdjList = new HashSet[size];
        for (int i = 0; i < size; i++) {
            AdjList[i] = new HashSet<>();
        }
        numVertices = size;
    }

    public void addEdge(int v, int w) { //Add an index to the correct HashSet to represent adjacency
        AdjList[v].add(w);
        numEdges++;
    }

    public HashSet<Integer> Adjacents(int v) { //Return the HashSet of all adjacent synset locations
        return AdjList[v];
    }

    public int V() {
        return numVertices;
    }

    public int E() {
        return numEdges;
    }
}

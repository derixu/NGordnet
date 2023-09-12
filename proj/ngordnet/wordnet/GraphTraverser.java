package ngordnet.wordnet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GraphTraverser {
    private boolean[] marked;
    private int[] edgeTo;
    private int start;

    public HashSet<Integer> family;

    public GraphTraverser(Graph g, int v) { //Pre-order depth first traversal to create a HashSet of family locations (family being all hyponym locations)
        start = v;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        family = new HashSet<>();
        traversalHelper(g, v);
    }

    public void traversalHelper(Graph g, int v) {
        marked[v] = true;
        family.add(v);
        for (int w : g.Adjacents(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                traversalHelper(g, w);
            }
        }
    }
    public static void main (String args[]) {
        Graph testGraph = new Graph(7);
        testGraph.addEdge(0, 1);
        testGraph.addEdge(0, 2);
        testGraph.addEdge(1, 3);
        testGraph.addEdge(2, 4);
        testGraph.addEdge(1, 5);
        testGraph.addEdge(5, 6);

        GraphTraverser testTraverser = new GraphTraverser(testGraph, 5);
        System.out.println(testTraverser.family);
    }
}

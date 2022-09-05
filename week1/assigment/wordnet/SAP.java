import java.util.HashMap;
import java.util.HashSet;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
  private class Pair {
    final private int length, ancestor;

    Pair(int len, int ans) {
      length = len;
      ancestor = ans;
    }

    public int getLength() {
      return length;
    }

  }

  private final Digraph graph;
  final private HashMap<HashSet<Integer>, Pair> cached;

  public SAP(Digraph G) {
    if (G == null) {
      throw new IllegalArgumentException("Digraph is null");
    }
    cached = new HashMap<>();
    graph = G;
  }

  public int length(int v, int w) {

    nullChecker(v);
    nullChecker(w);

    HashSet<Integer> temp = new HashSet<>();
    temp.add(v);
    temp.add(w);

    if (cached.containsKey(temp)) {
      return cached.get(temp).length;
    }
    return sapHelper(v, w).length;

  }

  public int ancestor(int v, int w) {

    nullChecker(v);
    nullChecker(w);

    HashSet<Integer> temp = new HashSet<>();
    temp.add(v);
    temp.add(w);

    if (cached.containsKey(temp)) {
      return cached.get(temp).ancestor;
    }
    return sapHelper(v, w).ancestor;
  }

  private Pair sapHelper(int v, int w) {

    BreadthFirstDirectedPaths BFSV = new BreadthFirstDirectedPaths(graph, v);
    BreadthFirstDirectedPaths BFSW = new BreadthFirstDirectedPaths(graph, w);

    int distance = Integer.MAX_VALUE;
    int ancestor = 0;
    for (int it = 0; it < graph.V(); it++) {
      int sum = BFSV.distTo(it) + BFSW.distTo(it);
      if (BFSV.hasPathTo(it) && BFSW.hasPathTo(it) && sum < distance) {
        distance = sum;
        ancestor = it;
      }
    }

    if (distance == Integer.MAX_VALUE) {
      distance = -1;
      ancestor = -1;

    }
    HashSet<Integer> temp = new HashSet<>();
    temp.add(v);
    temp.add(w);

    Pair rP = new Pair(distance, ancestor);
    cached.put(temp, rP);
    return rP;

  };

  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    iterableNullChecker(v);
    iterableNullChecker(w);

    return sapHelperIterable(v, w).length;
  }

  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    iterableNullChecker(v);
    iterableNullChecker(w);

    return sapHelperIterable(v, w).ancestor;
  }

  private Pair sapHelperIterable(Iterable<Integer> v, Iterable<Integer> w) {

    BreadthFirstDirectedPaths BFSV = new BreadthFirstDirectedPaths(graph, v);
    BreadthFirstDirectedPaths BFSW = new BreadthFirstDirectedPaths(graph, w);

    int distance = Integer.MAX_VALUE;
    int ancestor = 0;
    for (int it = 0; it < graph.V(); it++) {
      int sum = BFSV.distTo(it) + BFSW.distTo(it);
      if (BFSV.hasPathTo(it) && BFSW.hasPathTo(it) && sum < distance) {
        distance = sum;
        ancestor = it;
      }
    }

    if (distance == Integer.MAX_VALUE) {
      distance = -1;
      ancestor = -1;
    }

    Pair rP = new Pair(distance, ancestor);
    return rP;
  };

  private void nullChecker(Integer var) {
    if (var == null)
      throw new IllegalArgumentException("null is passed");
  }

  private void iterableNullChecker(Iterable<Integer> it) {
    if (it == null)

      throw new IllegalArgumentException("iterable is null");
    for (Integer integer : it) {
      if (integer == null) {
        throw new IllegalArgumentException("null is passed throw iterable");
      }
    }
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);

    int max = G.V();
    System.out.println("nodes : " + max);
    System.out.println("connections : " + G.E());
    int v, w;
    // while (!StdIn.isEmpty()) {

    for (int i = 0; i <= max; i++) {
      for (int j = i + 1; j < max; j++) {
        v = i;
        w = j;
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);

        StdOut.printf("length from " + v + " -> " + w + "= %d, ancestor = %d\n", length, ancestor);
        // StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
      }
    }

    // while (!StdIn.isEmpty()) {
    // int v = StdIn.readInt();
    // int w = StdIn.readInt();
    // int length = sap.length(v, w);
    // int ancestor = sap.ancestor(v, w);
    // StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    // }

    // ArrayList<Integer> arr1 = new ArrayList<>();
    // arr1.add(13);
    // arr1.add(23);
    // arr1.add(24);
    // ArrayList<Integer> arr2 = new ArrayList<>();
    // arr2.add(6);
    // arr2.add(null);
    // arr2.add(17);

    // System.out.println(sap.length(arr1, arr2));

    // System.out.println(sap.ancestor(arr1, arr2));

  }

}

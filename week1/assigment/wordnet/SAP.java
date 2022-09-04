import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

  class pair {
    int v, w;

    pair(int x, int y) {
      v = x;
      w = y;
    }
  }

  class returnPair {
    private int length, ancestor;

    returnPair(int l, int a) {
      length = l;
      ancestor = a;
    }

    public int getLength() {
      return length;
    }

  }

  private final Digraph graph;
  private HashMap<pair, returnPair> cached;

  SAP(Digraph G) {
    if (G == null) {
      throw new IllegalArgumentException("Digraph is null");
    }
    cached = new HashMap<>();
    graph = G;
  }

  public int length(int v, int w) {

    nullChecker(v);
    nullChecker(w);

    if (cached.containsKey(new pair(v, w)))
      return cached.get(new pair(v, w)).length;
    return SAPHelper(v, w).length;

  }

  public int ancestor(int v, int w) {

    nullChecker(v);
    nullChecker(w);

    if (cached.containsKey(new pair(v, w))) {

      System.out.println("CACHHHHHHHED");

      return cached.get(new pair(v, w)).ancestor;
    }
    return SAPHelper(v, w).ancestor;
  }

  private returnPair SAPHelper(int v, int w) {

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

    pair p = new pair(v, w);
    returnPair rP = new returnPair(distance, ancestor);
    cached.put(p, rP);

    return rP;

  };

  void nullChecker(Integer o) {
    if (o == null)
      throw new IllegalArgumentException("null is passed");
  }

  void iterableNullChecker(Iterable<Integer> it) {
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

import java.util.ArrayList;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

  private Digraph doubleEdge;
  private Digraph G;
  private BreadthFirstDirectedPaths BFS;

  public SAP(Digraph GOr) {
    if (GOr == null) {

      throw new IllegalArgumentException("Digraph is null");
    }
    // doubleEdge digraph (normal graph);
    doubleEdge = new Digraph(GOr);
    G = new Digraph(GOr);

    for (int i = 0; i < doubleEdge.V(); i++) {
      for (int w : doubleEdge.adj(i)) {
        doubleEdge.addEdge(w, i);
      }
    }
  }

  public int length(int v, int w) {

    nullChecker(v);
    nullChecker(w);

    BFS = new BreadthFirstDirectedPaths(doubleEdge, v);
    int t = BFS.distTo(w);
    if (t == Integer.MAX_VALUE)
      return -1;
    return t;
  }

  public int ancestor(int v, int w) {

    nullChecker(v);
    nullChecker(w);

    BFS = new BreadthFirstDirectedPaths(doubleEdge, v);
    int t = BFS.distTo(w);
    if (t == Integer.MAX_VALUE)
      return -1;
    else {
      return ancestorHelper(w, BFS);
    }
  }

  private int ancestorHelper(int p, BreadthFirstDirectedPaths BFS) {

    int past = 0;
    boolean first = true;

    for (int pathway : BFS.pathTo(p)) {
      if (first) {
        past = pathway;
        first = false;
        continue;
      }
      boolean found = false;
      for (int parent : G.adj(past)) {
        if (parent == pathway) {
          found = true;
          break;
        }
      }
      if (!found) {
        return past;
      }
      past = pathway;
    }
    return p;
  };

  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    iterableNullChecker(v);
    iterableNullChecker(w);

    int small = Integer.MAX_VALUE;
    BFS = new BreadthFirstDirectedPaths(doubleEdge, v);

    int to;
    for (int W : w) {
      // System.out.print("shortest to: " + W + " is: ");
      int V = BFS.distTo(W);
      // System.out.print(V + ", ");

      if (V < small)
        small = V;
      // System.out.println(" ");
    }

    if (small == Integer.MAX_VALUE) {
      return -1;
    }
    return small;
  }

  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    iterableNullChecker(v);
    iterableNullChecker(w);

    int small = Integer.MAX_VALUE;
    BFS = new BreadthFirstDirectedPaths(doubleEdge, v);

    int to = 0;
    for (int W : w) {
      int V = BFS.distTo(W);

      if (V < small) {
        small = V;
        to = W;
      }
    }

    if (small == Integer.MAX_VALUE) {
      return -1;
    }

    int temp = 0;
    boolean first = true;
    for (int integer : BFS.pathTo(to)) {

      if (first)
        temp = integer;
    }
    return ancestorHelper(temp, BFS);
  }

  private void nullChecker(Integer o) {
    if (o == null)
      throw new IllegalArgumentException("null is passed");
  }

  private void iterableNullChecker(Iterable<Integer> it) {
    for (Integer integer : it) {
      if (integer == null) {
        throw new IllegalArgumentException("null is passed throw iterable");
      }
    }
  }

  public static void main(String[] args) {
    In in = new In("digraph1.txt");
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }

    ArrayList<Integer> arr1 = new ArrayList<>();
    arr1.add(13);
    arr1.add(23);
    arr1.add(24);
    ArrayList<Integer> arr2 = new ArrayList<>();
    arr2.add(6);
    arr2.add(null);
    arr2.add(17);

    System.out.println(sap.length(arr1, arr2));

    System.out.println(sap.ancestor(arr1, arr2));

  }

}

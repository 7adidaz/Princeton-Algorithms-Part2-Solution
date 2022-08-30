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

  SAP(Digraph GOr) {
    doubleEdge = new Digraph(GOr);
    G = new Digraph(GOr);

    // for (int i = 0; i < doubleEdge.V(); i++) {
    // System.out.println("constructor");
    // System.out.print("V: " + i + " ");
    // for (int w : G.adj(i)) {
    // System.out.print(w + ", ");
    // }
    // System.out.println(" ");
    // }
    for (int i = 0; i < doubleEdge.V(); i++) {
      for (int w : doubleEdge.adj(i)) {
        doubleEdge.addEdge(w, i);
      }
    }
  }

  public int length(int v, int w) {

    BFS = new BreadthFirstDirectedPaths(doubleEdge, v);
    int t = BFS.distTo(w);
    if (t == Integer.MAX_VALUE)
      return -1;
    return t;
  }

  public int ancestor(int v, int w) {

    BFS = new BreadthFirstDirectedPaths(doubleEdge, v);
    int t = BFS.distTo(w);
    if (t == Integer.MAX_VALUE)
      return -1;
    else {
      return ancestorHelper(w, BFS);
      // what i do here is i match the path to the original digraph.
      // ArrayList<Integer> path = new ArrayList<>();
      // for (int pathway : BFS.pathTo(w)) {
      // path.add(pathway);
      // // System.out.print(pathway + " -> ");

      // }
      // // System.out.println(" ");
      // for (int pathway = 0; pathway < path.size() - 1; pathway++) {
      // boolean found = false;
      // // System.out.print(path.get(pathway) + ": ");

      // for (int parent : G.adj(path.get(pathway))) {
      // // System.out.print(parent + ", ");
      // if (parent == path.get(pathway + 1)) {
      // found = true;
      // break;
      // }
      // }
      // if (!found) {
      // return path.get(pathway);
      // }
      // }
    }
  }

  private int ancestorHelper(int p, BreadthFirstDirectedPaths BFS) {

    int past = 0;
    boolean first = true;

    // for (int pathway : BFS.pathTo(p)) {

    // System.out.print(pathway + "- > ");

    // }
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
    return 10000000;
  };

  public int length(Iterable<Integer> v, Iterable<Integer> w) {
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
    int small = Integer.MAX_VALUE;
    BFS = new BreadthFirstDirectedPaths(doubleEdge, v);

    int to = 0;
    for (int W : w) {
      // System.out.print("shortest to: " + W + " is: ");
      int V = BFS.distTo(W);
      // System.out.print(V + ", ");

      if (V < small) {
        small = V;
        to = W;
      }
      // System.out.println(" ");
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

  public static void main(String[] args) {
    In in = new In("digraph25.txt");
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    // while (!StdIn.isEmpty()) {
    // int v = StdIn.readInt();
    // int w = StdIn.readInt();
    // int length = sap.length(v, w);
    // int ancestor = sap.ancestor(v, w);
    // StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    // }

    ArrayList<Integer> arr1 = new ArrayList<>();
    arr1.add(13);
    arr1.add(23);
    arr1.add(24);
    ArrayList<Integer> arr2 = new ArrayList<>();
    arr2.add(6);
    arr2.add(16);
    arr2.add(17);

    System.out.println(sap.length(arr1, arr2));

    System.out.println(sap.ancestor(arr1, arr2));

  }

}


/**
 * Graph
 */

import edu.princeton.cs.algs4.Bag;

public class Graph {
  private final int v;

  private int e;
  private Bag<Integer>[] adj;

  Graph(int V) {

    this.v = V;
    adj = (Bag<Integer>[]) new Bag[v];
    for (int i = 0; i < V; i++) {
      adj[v] = new Bag<Integer>();
    }
  }

  public void addEdge(int v, int w) {
    adj[v].add(w);
    adj[w].add(v);
    e++;
  }

  public Iterable<Integer> adj(int v) {
    return adj[v];
  }

  public int V() {
    return this.v;
  }

  public int E() {

    return this.e;
  }

  public static int degree(Graph G, int v) {
    int degree = 0;

    for (int w : G.adj(v))
      degree++;
    return degree;
  }

  public static int maxDegree(Graph G) {

    int max = 0;
    for (int i = 0; i < G.V(); i++) {
      int degree = 0;
      for (int W : G.adj(i))
        degree++;

      if (degree > max)
        degree = max;
    }
    return max;
  }

  public static double averageDegree(Graph G) {
    return 2.0 * G.E() / G.V();
  }

  public static int numberOfselfLoops(Graph G) {

    int loops = 0;
    for (int i = 0; i < G.V(); i++) {

      for (int W : G.adj(i)) {
        if (W == i) {
          loops++;
          break;
        }
      }
    }
    return loops / 2;
  }

  public static void main(String[] args) {

  }
}

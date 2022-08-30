
/**
 * Digraph
 */

import edu.princeton.cs.algs4.Bag;

public class Digraph {
  private final int v;

  private int e;
  private Bag<Integer>[] adj;

  Digraph(int V) {

    this.v = V;
    adj = (Bag<Integer>[]) new Bag[v];
    for (int i = 0; i < V; i++) {
      adj[v] = new Bag<Integer>();
    }
  }

  public void addEdge(int v, int w) {
    adj[v].add(w);
    // adj[w].add(v);
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

  public static void main(String[] args) {

  }
}

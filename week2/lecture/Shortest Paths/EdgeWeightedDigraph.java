import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedDigraph {

  private Bag<DirectedEdge>[] adj;
  private final int V;
  private int E;

  public EdgeWeightedDigraph(int v) {
    this.V = v;
    E = 0;
    adj = (Bag<DirectedEdge>[]) new Bag[v];
    for (int i = 0; i < V; i++) {
      adj[i] = new Bag<>();
    }
  }

  public int V() {
    return this.V;
  }

  public void addDirectedEdge(DirectedEdge e) {

    int v = e.from();
    int w = e.to();
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  public Iterable<DirectedEdge> adj(int v) {
    return adj[v];
  }

  public Iterable<DirectedEdge> edges() {
    Bag<DirectedEdge> Edges = new Bag<>();

    for (int i = 0; i < V; i++) {
      for (DirectedEdge e : adj(i)) {
        Edges.add(e);

      }
    }
    return Edges;
  }
}

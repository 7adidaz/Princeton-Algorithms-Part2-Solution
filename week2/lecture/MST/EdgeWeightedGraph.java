import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedGraph {

  private Bag<Edge>[] adj;
  private final int V;
  private int E;

  public EdgeWeightedGraph(int v) {
    this.V = v;
    E = 0;
    adj = (Bag<Edge>[]) new Bag[v];
    for (int i = 0; i < V; i++) {
      adj[i] = new Bag<>();
    }
  }

  public int V() {
    return this.V;
  }

  public void addEdge(Edge e) {

    int v = e.either();
    int w = e.other(v);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  public Iterable<Edge> adj(int v) {
    return adj[v];
  }

  public Iterable<Edge> edges() {
    Bag<Edge> Edges = new Bag<>();

    for (int i = 0; i < V; i++) {
      for (Edge e : adj(i)) {
        if (e.other(i) > i)
          Edges.add(e);

      }
    }
    return Edges;
  }
}

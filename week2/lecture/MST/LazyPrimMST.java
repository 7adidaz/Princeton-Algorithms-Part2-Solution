import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;

public class LazyPrimMST {

  private boolean[] marked; // MST vertices
  private Queue<Edge> mst; // MST edges
  private MinPQ<Edge> pq; // PQ of edges

  public LazyPrimMST(EdgeWeightedGraph G) {
    pq = new MinPQ<Edge>();
    mst = new Queue<Edge>();
    marked = new boolean[G.V()];
    visit(G, 0);

    while (!pq.isEmpty() && mst.size() < G.V() - 1) {
      Edge e = pq.delMin();
      int v = e.either(), w = e.other(v);
      if (marked[v] && marked[w])
        continue;
      mst.enqueue(e);
      if (!marked[v])
        visit(G, v);
      if (!marked[w])
        visit(G, w);
    }
  }

  private void visit(EdgeWeightedGraph G, int v) {
    marked[v] = true;
    for (Edge e : G.adj(v))
      if (!marked[e.other(v)])
        pq.insert(e);
  }

  public Iterable<Edge> mst() {
    return mst;
  }
}

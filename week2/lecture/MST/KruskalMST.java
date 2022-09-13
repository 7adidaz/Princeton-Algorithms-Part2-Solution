import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.MinPQ;

public class KruskalMST {
  private Queue<Edge> mst = new Queue<>();

  public KruskalMST(EdgeWeightedGraph G) {
    // a prioirity Q for the edges to be stored in ascending order.
    MinPQ<Edge> pq = new MinPQ<>();
    for (Edge e : G.edges())
      pq.insert(e);

    // union find to check wether the new node creates a cycle.
    UF uf = new UF(G.V());

    while (!pq.isEmpty() && mst.size() < G.V() - 1) {
      Edge e = pq.delMin();
      int v = e.either(), w = e.other(v);
      if (uf.connected(v, w)) {
        uf.union(v, w);
        mst.enqueue(e);
      }
    }

  }

  public Iterable<Edge> edges() {
    return mst;
  }

  public static void main(String[] args) {

  }
}

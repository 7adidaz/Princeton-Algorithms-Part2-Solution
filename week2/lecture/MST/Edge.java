public class Edge implements Comparable<Edge> {

  private final int v;
  private final int w;
  private final double weight;

  public Edge(int v, int w, double weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public double weight() {
    return weight;
  }

  public int either() {
    return v;
  }

  public int other(int v) {
    if (this.v == v)
      return w;
    return v;
  }

  public int compareTo(Edge that) {
    return Double.compare(this.weight, that.weight);
  }

  public String toString() {
    return v + "->" + w;
  }

}

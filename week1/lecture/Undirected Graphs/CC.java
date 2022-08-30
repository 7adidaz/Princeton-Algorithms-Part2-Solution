public class CC {
  private boolean marked[];
  private int[] id;
  private int count = 0;

  public CC(Graph G) {
    marked = new boolean[G.V()];
    id = new int[G.V()];
    for (int i = 0; i < G.V(); i++) {
      if (!marked[i]) {
        dfs(G, i);
        count++;
      }
    }
  }

  public void dfs(Graph G, int v) {
    marked[v] = true;
    id[v] = count;
    for (int i : G.adj(v)) {
      if (!marked[i])
        dfs(G, i);
    }
  }

  public int count() {
    return count;
  }

  public int id(int v) {
    return id[v];
  }

  public static void main(String[] args) {

  }
}

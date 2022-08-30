import java.util.Stack;

public class DepthFirstPaths {

  private boolean[] marked; // marked[v] = true if v is reachable from s
  private int[] edgeTo; // edgeTo[v] = last edge on shortest path to v
  private int s; // source vertex

  public DepthFirstPaths(Graph G, int s) {
    marked = new boolean[G.V()];
    edgeTo = new int[G.V()];
    dfs(G, s);
  }

  private void dfs(Graph G, int v) {
    marked[v] = true;

    for (int W : G.adj(v)) {
      if (!marked[W])
        dfs(G, W);
      edgeTo[W] = v;
    }
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public Iterable<Integer> pathTo(int v) {

    if (!hasPathTo(v))
      return null;
    Stack<Integer> path = new Stack<>();
    for (int i = v; i != s; i = edgeTo[i]) {
      path.push(i);
    }
    path.push(s);
    return path;
  }

  public static void main(String[] args) {

  }
}

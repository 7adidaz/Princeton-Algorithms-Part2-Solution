import java.util.Queue;

public class BreadthFirstPaths {

  private Boolean[] marked;
  private int[] edgeTo;

  BreadthFirstPaths(Graph G, int v) {

  }

  private bfs(Graph G, int s){

    Queue<Integer> q = new Queue<Integer>();
    q.enqueue(s);
    marked[s] = true;
    while (!q.isEmpty()) {
    int v = q.dequeue();

    for(int w: G.adj(v)){
    if(!marked[w]){
    q.enqueue(w);
    marked[w] = ture;
    edgeTo[w]  =v;


    }
    }

    }
  }

  public static void main(String[] args) {

  }
}

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
// import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
// import java.util.Stack;

public class WordNet {
  private Digraph G;
  final private HashMap<String, Bag<Integer>> map;

  private int synLength;
  final private SAP sap;
  // private BST<String, Integer> tree;

  public WordNet(String synsets, String hypernyms) {

    map = new HashMap<>();

    synCtor(synsets);
    hyperCtor(hypernyms);
    sap = new SAP(G);
    DirectedCycle c = new DirectedCycle(G);
    if (c.hasCycle())
      throw new IllegalArgumentException();
    // visited = new boolean[G.V()];
    // s = new Stack<>();
    // tsort = new ArrayList<>();
    // checkCycle();
  }

  // private boolean[] visited;
  // private Stack<Integer> s;
  // private ArrayList<Integer> tsort;

  // private boolean isCycle() {
  // for (int i = 0; i < G.V(); i++) {
  // if (visited[i] == false) {
  // dfs(i);
  // }
  // }

  // if (checkCycle())
  // return true;
  // else
  // return false;
  // }

  // private boolean checkCycle() {
  // Map<Integer, Integer> pos = new HashMap<>();
  // int count = 0;
  // while (!s.isEmpty()) {

  // pos.put(s.peek(), count);
  // tsort.add(s.peek());

  // count += 1;
  // s.pop();

  // }

  // for (int i = 0; i < G.V(); i++) {
  // for (Integer it : G.adj(i)) {

  // if (pos.get(i) > pos.get(it)) {
  // return true;
  // }
  // }
  // }
  // return false;

  // }

  // void dfs(int v) {
  // visited[v] = true;
  // for (Integer t : G.adj(v)) {
  // if (visited[t] == false)
  // dfs(t);
  // }
  // s.push(v);
  // }

  private void synCtor(String synsets) {

    In synSet = new In(synsets);
    while (synSet.hasNextLine()) {
      String[] line = synSet.readLine().split(",");
      String[] nouns = line[1].split(" ");
      int id = Integer.parseInt(line[0]);
      for (String string : nouns) {
        Bag<Integer> bag;
        if (map.containsKey(string)) {
          bag = map.get(string);
        } else
          bag = new Bag<Integer>();
        bag.add(id);
        map.put(string.trim(), bag);
      }
      synLength = id + 1;
    }
  }

  private void hyperCtor(String hypernyms) {

    In hypernymsSet = new In(hypernyms);
    // System.out.println(synLength);
    G = new Digraph(synLength);
    while (hypernymsSet.hasNextLine()) {
      String[] hyper = hypernymsSet.readLine().split(",");
      for (int i = 1; i < hyper.length; i++) {
        G.addEdge(Integer.parseInt(hyper[0]), Integer.parseInt(hyper[i]));
      }
    }
  }

  // tree = new BST<String, Integer>();
  // synList = new ArrayList<>();
  // In synSets = new In(synsets);

  // while (synSets.hasNextLine()) {
  // String[] nouns = synSets.readLine().split(",");
  // // System.out.println(nouns[1]);
  // String[] noun = nouns[1].split(" ");
  // for (String s : noun) {

  // synList.add(s);
  // tree.put(s, Integer.parseInt(nouns[0]));
  // }
  // }

  // G = new Digraph(synList.size());

  // while (hypernymsSet.hasNextLine()) {
  // String[] hyper = hypernymsSet.readLine().split(",");
  // for (int i = 1; i < hyper.length; i++) {
  // G.addEdge(Integer.parseInt(hyper[0]), Integer.parseInt(hyper[i]));
  // }
  // }

  // }

  public Iterable<String> nouns() {
    // java.util.Collections.sort(synList);
    // return tree.keys();

    return map.keySet();
  }

  public boolean isNoun(String word) {
    if (word == null)

      throw new IllegalArgumentException();
    if (!map.containsKey(word))
      return false;
    return true;
  }

  public int distance(String nounA, String nounB) {

    if (nounA == null || nounB == null)
      throw new IllegalArgumentException();
    return sap.length(map.get(nounA), map.get(nounB));

  }

  public String sap(String nounA, String nounB) {

    if (nounA == null || nounB == null)
      throw new IllegalArgumentException();
    int temp = sap.ancestor(map.get(nounA), map.get(nounB));

    StringBuilder last = new StringBuilder();

    Iterator<String> keys = map.keySet().iterator();
    while (keys.hasNext()) {
      String s = keys.next();
      for (int t : map.get(s)) {
        if (t == temp) {

          last.append(s + " ");
        }
      }
    }
    return last.toString();

    // int ancestor = sap.ancestor(map.get(nounA), map.get(nounB));

    // StringBuilder synset = new StringBuilder();
    // Iterator<String> keys = map.keySet().iterator();

    // while (keys.hasNext()) {
    // String next = keys.next();
    // for (int val : map.get(next)) {
    // if (val == ancestor) {
    // synset.append(next);
    // synset.append(" ");
    // }
    // }
    // }

    // return synset.toString();
  }

  public static void main(String[] args) {

    // WordNet w = new WordNet("synTest.txt", "hyperText.txt");
    WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
    // for (String s : w.nouns()) {
    // StdOut.println(s);
    // }
    // String s = "worm";
    // String s2 = "bird";

    String s = "white_marlin";
    String s2 = "mileage";
    StdOut.println(w.distance(s, s2));
    StdOut.println(w.sap(s, s2));

  }
}

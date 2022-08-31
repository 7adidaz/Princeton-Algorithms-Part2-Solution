import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BST;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;

public class WordNet {
  private Digraph G;
  private HashMap<String, Bag<Integer>> map;
  private ArrayList<String> synList;
  private int SynLength;
  private SAP sap;
  // private BST<String, Integer> tree;

  public WordNet(String synsets, String hypernyms) {

    map = new HashMap<>();
    synCtor(synsets);
    hyperCtor(hypernyms);
    sap = new SAP(G);

  }

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
      SynLength = id + 1;
    }
  }

  private void hyperCtor(String hypernyms) {

    In hypernymsSet = new In(hypernyms);
    // System.out.println(SynLength);
    G = new Digraph(SynLength);
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
    // if (tree.get(word) == null)
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
    int temp = sap.ancestor(map.get(nounB), map.get(nounB));

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

    String s = "edible_fruit";
    String s2 = "physical_entity";
    StdOut.println(w.distance(s, s2));
    StdOut.println(w.sap(s, s2));

  }
}

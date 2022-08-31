import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.BST;

import java.util.ArrayList;

public class WordNet {
    private Digraph G;
    private ArrayList<String> synList;
    private int SynLength;
    private BST<String, Integer> tree;

    public WordNet(String synsets, String hypernyms) {

        tree = new BST<String, Integer>();
        synList = new ArrayList<>();
        In synSets = new In(synsets);
        In hypernymsSet = new In(hypernyms);

        while (synSets.hasNextLine()) {
            String[] nouns = synSets.readLine().split(",");
            // System.out.println(nouns[1]);
            String[] noun = nouns[1].split(" ");
            for (String s : noun) {

                synList.add(s);
                tree.put(s, Integer.parseInt(nouns[0]));
            }
        }

        G = new Digraph(synList.size());

        while (hypernymsSet.hasNextLine()) {
            String[] hyper = hypernymsSet.readLine().split(",");
            for (int i = 1; i < hyper.length; i++) {
                G.addEdge(Integer.parseInt(hyper[0]), Integer.parseInt(hyper[i]));
            }
        }

    }

    public Iterable<String> nouns() {
        // java.util.Collections.sort(synList);
        return tree.keys();
    }

    public boolean isNoun(String word) {
        if (tree.get(word) == null)
            return false;
        return true;
    }

    public int distance(String nounA, String nounB) {
        SAP sap = new SAP(G);
        int A = tree.get(nounA);
        int B = tree.get(nounB);
        return sap.length(A, B);
    }

    public static void main(String[] args) {

        WordNet w = new WordNet("synTest.txt", "hypernyms15Tree.txt");
        // WordNet w = new WordNet("synsets15.txt", "hypernyms15Tree.txt");
        for (String s : w.nouns()) {
            StdOut.println(s);
        }
        StdOut.println(w.distance("a", "b"));

    }
}

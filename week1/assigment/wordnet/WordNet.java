import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class WordNet {
    private Digraph G;
    private ArrayList<String> synList;

    public WordNet(String synsets, String hypernyms) {


        synList = new ArrayList<>();
        In synSets = new In(synsets);
        In hypernymsSet = new In(hypernyms);

        while (synSets.hasNextLine()) {
            String[] nouns = synSets.readLine().split(",");
            String[] noun = nouns[1].split(" ");
            for (String s: noun) {

                synList.add(s);
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
        return synList;
    }

    public boolean isNoun(String word) {
        return true;
    }

    public static void main(String[] args) {

        WordNet w = new WordNet("synsets.txt", "hypernyms15Tree.txt");
        // WordNet w = new WordNet("synsets15.txt", "hypernyms15Tree.txt");
        for (String s : w.nouns()) {
            StdOut.println(s);

        }

    }
}

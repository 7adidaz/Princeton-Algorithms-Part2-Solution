import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
  private final WordNet wordNet;

  public Outcast(WordNet wordnet) {
    wordNet = wordnet;
  }

  public String outcast(String[] nouns) {
    int farest = 0;
    int distance;
    String farString = "", temping = "";
    for (String string : nouns) {
      distance = 0;
      for (String string2 : nouns) {
        distance += wordNet.distance(string, string2);
        temping = string;
      }
      if (distance > farest) {
        farString = temping;
        farest = distance;
      }
    }
    return farString;
  }

  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }

  }

}

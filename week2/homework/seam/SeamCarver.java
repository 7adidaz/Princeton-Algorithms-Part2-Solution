import java.awt.Color;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.AcyclicSP;
import edu.princeton.cs.algs4.DirectedEdge;

public class SeamCarver {

  // private final Picture tempPic;
  private Picture pic;
  private int width;
  private int height;
  private double[][] energies;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture) {
    if (picture == null)
      throw new IllegalArgumentException();
    // tempPic = picture;
    pic = new Picture(picture);
    width = pic.width();
    height = pic.height();

    updateEnergy();
  }

  private void updateEnergy() {

    energies = new double[height()][width()];
    for (int i = 0; i < height(); i++) {
      for (int j = 0; j < width(); j++) {
        energies[i][j] = energy(j, i);

        // System.out.print((int) energies[i][j] + " ");

      }
      // System.out.println();
    }
  }

  // current picture
  public Picture picture() {
    return pic;
  }

  // // width of current picture
  public int width() {
    return pic.width();
  }

  // // height of current picture
  public int height() {
    return pic.height();
  }

  // // energy of pixel at column x and row y
  public double energy(int x, int y) {
    if (validate(y, x)) {
      throw new IllegalArgumentException();
    }

    if (isBoarder(x, y))
      return 1000.0;
    return delta(x, y);
  }

  private boolean isBoarder(int x, int y) {
    return x == 0 || y == 0 || x == width() - 1 || y == height() - 1;
  }

  private double delta(int x, int y) {

    double deltaX = 0, deltaY = 0, xValue = 0, yValue = 0;
    String colors = "rgb";
    for (int i = 0; i < 3; i++) {
      char color = colors.charAt(i);
      xValue = power(value(x + 1, y, color) - value(x - 1, y, color));
      yValue = power(value(x, y + 1, color) - value(x, y - 1, color));
      deltaX += xValue;
      deltaY += yValue;
    }
    return Math.sqrt(deltaX + deltaY);
  }

  private double power(double x) {
    return Math.pow(x, 2);
  }

  private double value(int x, int y, char color) {
    Color c = pic.get(x, y);
    if (color == 'r') {
      return c.getRed();

    } else if (color == 'g') {
      return c.getGreen();

    } else {
      return c.getBlue();
    }

  }

  // // sequence of indices for horizontal seam
  public int[] findHorizontalSeam() {
    filp();
    int[] ret = findVerticalSeam();
    filp();
    return ret;
  }

  private void filp() {
    // System.out.println("before : " + width + " : " + height);
    int temp = width;
    width = height;
    height = temp;

    // System.out.println("before : " + width + " : " + height);
    double[][] energiestemp = new double[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        energiestemp[i][j] = energies[j][i];
      }
    }
    energies = energiestemp;

    // print();
    // System.out.println(' ');
    // System.out.println(' ');
  }

  // // sequence of indices for vertical seam #TODO
  public int[] findVerticalSeam() {
    EdgeWeightedDigraph graph = new EdgeWeightedDigraph(width * height + 2);

    for (int i = 0; i < height - 1; i++) {
      for (int j = 0; j < width; j++) {
        graph.addEdge(new DirectedEdge(to(i, j), to(i + 1, j), energies[i + 1][j]));
        // System.out.println(to(i, j) + " -> " + to(i + 1, j) + " = " + energies[i +
        // 1][j]);
        if (j != 0) {
          graph.addEdge(new DirectedEdge(to(i, j), to(i + 1, j - 1), energies[i + 1][j - 1]));
          // System.out.println(to(i, j) + " -> " + to(i + 1, j - 1) + " = " + energies[i
          // + 1][j - 1]);
        }
        if (j != width - 1) {
          graph.addEdge(new DirectedEdge(to(i, j), to(i + 1, j + 1), energies[i + 1][j + 1]));
          // System.out.println(to(i, j) + " -> " + to(i + 1, j + 1) + " = " + energies[i
          // + 1][j + 1]);
        }
        // System.out.println(to(i, j) + " " + energies[i][j]);
      }
    }
    for (int i = 0; i < width; i++) {
      graph.addEdge(new DirectedEdge(0, to(0, i), energies[0][i]));
      // System.out.println(0 + " -> " + to(0, i) + " = " + energies[0][i]);
      graph.addEdge(new DirectedEdge(to(height - 1, i), height * width + 1, energies[height - 1][i]));
      // System.out.println(to(height - 1, i) + " -> " + (height * width + 1) + " = "
      // + energies[height - 1][i]);
    }

    AcyclicSP sp = new AcyclicSP(graph, 0);
    int[] arr = new int[height];
    int temp;
    int count = 0;
    for (DirectedEdge path : sp.pathTo(height * width + 1)) {
      temp = (path.from() - 1) % width;
      // System.out.print(temp + " ");
      if (count >= 1 && count <= arr.length)
        arr[count - 1] = temp;
      count++;
    }
    if (height >= 2)
      arr[0] = arr[1];
    // System.out.println();

    // for (int i : arr) {
    // System.out.println(i);
    // }

    return arr;
  }

  private int to(int i, int j) {
    return ((i * width) + j) + 1;
  }

  private boolean validate(int x, int y) {
    return x < 0 || y < 0 || x >= height || y >= width;
  }

  // private void print() {

  // for (int i = 0; i < height; i++) {

  // for (int j = 0; j < width; j++) {

  // StdOut.printf("%7.2f ", energies[i][j]);
  // }
  // System.out.println();
  // }

  // System.out.println();
  // System.out.println();
  // }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam) {
    if (height <= 1)

      throw new IllegalArgumentException();
    if (seam == null)
      throw new IllegalArgumentException();
    for (int i = 0; i < seam.length - 1; i++) {

      if (Math.abs(seam[i] - seam[i + 1]) > 1 || seam[i] >= height || seam[i] < 0)
        throw new IllegalArgumentException();

    }

    Picture temp = new Picture(width, height - 1);
    boolean shift = true;

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height - 1; j++) {

        if (seam[i] == j)

          shift = false;
        if (!shift)
          temp.set(i, j, pic.get(i, j + 1));
        else
          temp.set(i, j, pic.get(i, j));
      }
      shift = true;
    }

    pic = temp;
    height--;
    updateEnergy();
  }

  // // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam) {

    if (width <= 1)
      throw new IllegalArgumentException();
    if (seam == null)
      throw new IllegalArgumentException();
    for (int i = 0; i < seam.length - 1; i++) {
      if (Math.abs(seam[i] - seam[i + 1]) > 1 || seam[i] >= width || seam[i] < 0)
        throw new IllegalArgumentException();
    }

    Picture temp = new Picture(width() - 1, height);
    boolean shift = true;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width - 1; j++) {
        if (seam[i] == j)
          shift = false;
        if (!shift)
          temp.set(j, i, pic.get(j + 1, i));
        else
          temp.set(j, i, pic.get(j, i));

      }

      shift = true;
    }

    pic = temp;
    width--;
    updateEnergy();
  }

  // unit testing (optional)
  public static void main(String[] args) {

    Picture pic = new Picture(args[0]);
    // Picture pic = new Picture("6x5.png");
    pic.show();
    System.out.println();
    // SeamCarver s = new SeamCarver(new Picture("6x5.png"));
    SeamCarver s = new SeamCarver(pic);
    double eng = s.energy(1, 1);
    // System.out.println(eng);
    // System.out.println();

    // System.out.println(s.width + " " + s.height);
    // for (int i : s.findHorizontalSeam())

    // System.out.print(i + "-> ");
    // System.out.println();
    // System.out.println("__________________________________");
    // int[] arr = s.findVerticalSeam();
    // for (int i = 0; i < arr.length; i++) {
    // System.out.print(arr[i] + " ");
    // }

    // System.out.println();
    // arr = s.findHorizontalSeam();
    // for (int i = 0; i < arr.length; i++) {
    // System.out.print(arr[i] + " ");
    // }
    // for (int i : s.findVerticalSeam())
    // System.out.print(i + "-> ");

    // s.print();
    // s.findVerticalSeam();
    // System.out.println();

    for (int i = 0; i < 4; i++) {

      int[] arr = s.findVerticalSeam();
      for (int j = 0; j < arr.length; j++) {
        System.out.print(arr[j] + " ");
      }
      System.out.println();
      s.removeVerticalSeam(arr);
      System.out.println(s.width() + " : " + s.height());
    }

    // System.out.println(s.width);
    // System.out.println(s.height);
    // for (int i = 0; i < s.height; i++) {
    // for (int j = 0; j < s.width; j++) {
    // System.out.print(s.energies[j][i] + " ");
    // }
    // System.out.println();
    // }

  }
}

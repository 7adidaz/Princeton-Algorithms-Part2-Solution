import java.awt.Color;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

  private Picture pic;
  private int width;
  private int height;
  private int[] arraySeam;
  double[][] energies;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture) {
    pic = picture;
    width = pic.width();
    height = pic.height();

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
    return width;
  }

  // // height of current picture
  public int height() {
    return height;
  }

  // // energy of pixel at column x and row y
  public double energy(int x, int y) {
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
    arraySeam = new int[height];
    filp();
    // System.out.println(width + " " + height);
    findVerticalSeam();
    filp();
    return arraySeam;
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

  private int count;

  // // sequence of indices for vertical seam
  public int[] findVerticalSeam() {
    arraySeam = new int[height];
    int[] minarraySeam = new int[height];
    // double min = Double.MAX_VALUE;
    // int start = 0;
    // for (int i = 0; i < width; i++) {
    // double energy = energies[1][i];
    // if (energy < min) {
    // start = i;
    // min = energy;
    // }
    // }

    double minEnergy = Double.MAX_VALUE;
    for (int i = 0; i < width; i++) {

      count = 0;
      sum = 0;
      search(0, i);
      if (sum < minEnergy) {
        minEnergy = sum;
        minarraySeam = arraySeam.clone();
      }
      // System.out.println(sum);
    }
    arraySeam = minarraySeam.clone();
    return arraySeam;
  }

  double sum;

  private void search(int x, int y) {
    sum += energies[x][y];
    int temp;
    temp = y;
    arraySeam[count] = temp;
    count++;
    // System.out.print(y + " -> ");
    int[] toi = { -1, 0, +1 };
    int[] toj = { +1, +1, +1 };

    double min = Double.MAX_VALUE;
    int lastx = 0, lasty = 0;
    for (int i = 0; i < toj.length; i++) {

      int newX = x + toj[i];
      int newY = y + toi[i];
      if (!validate(newX, newY)) {
        if (energies[newX][newY] <= min) {
          min = energies[newX][newY];
          lastx = newX;
          lasty = newY;
        }
      }
    }
    if (energies[lastx][lasty] == 1000.0) {

      sum += energies[lastx][lasty];
      arraySeam[count] = lasty;
      return;
    }

    search(lastx, lasty);
  }

  private boolean validate(int x, int y) {
    return x < 0 || y < 0 || x >= height || y >= width;
  }

  private void print() {

    for (int i = 0; i < height; i++) {

      for (int j = 0; j < width; j++) {

        StdOut.printf("%7.2f ", energies[i][j]);
      }
      System.out.println();
    }

    System.out.println();
    System.out.println();
  }

  // // remove horizontal seam from current picture
  // public void removeHorizontalSeam(int[] seam)

  // // remove vertical seam from current picture
  // public void removeVerticalSeam(int[] seam)

  // unit testing (optional)
  public static void main(String[] args) {
    Picture pic = new Picture("3x4.png");
    pic.show();
    System.out.println();
    SeamCarver s = new SeamCarver(new Picture("12x10.png"));
    double eng = s.energy(1, 1);
    // System.out.println(eng);
    System.out.println();

    System.out.println(s.width + " " + s.height);
    s.print();
    // s.findHorizontalSeam();

    s.findVerticalSeam();
    System.out.println();

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

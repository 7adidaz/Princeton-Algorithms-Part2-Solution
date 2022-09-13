import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

  private Picture pic;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture) {
    pic = picture;

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
  // public int[] findHorizontalSeam()

  // // sequence of indices for vertical seam
  // public int[] findVerticalSeam()

  // // remove horizontal seam from current picture
  // public void removeHorizontalSeam(int[] seam)

  // // remove vertical seam from current picture
  // public void removeVerticalSeam(int[] seam)

  // unit testing (optional)
  public static void main(String[] args) {
    Picture pic = new Picture("3x4.png");
    pic.show();
    System.out.println();
    SeamCarver s = new SeamCarver(new Picture("6x5.png"));
    double eng = s.energy(1, 1);
    System.out.println(eng);
  }
}

package model;

/**
 * This is a class named represents a single pixel in an image.
 * It has three integer variables, 'r', 'g', and 'b', representing the red,
 * green, and blue components of the pixel.
 */
final class Pixel {
  private final int r;
  private final int g;
  private final int b;

  /**
   * Single constructor of this class that takes three integer parameters
   * representing r, g, and b channels in a pixel
   * and initializes the 'r', 'g', and 'b' variables.
   *
   * @param r represents the red channel in a pixel
   * @param g represents the green channel in a pixel
   * @param b represents the blue channel in a pixel
   */
  Pixel(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Getter method for Red channel of a pixel.
   *
   * @return red value
   */
  int getR() {
    return this.r;
  }

  /**
   * Getter method for Green channel of a pixel.
   *
   * @return green value
   */
  int getG() {
    return this.g;
  }

  /**
   * Getter method for Blue channel of a pixel.
   *
   * @return blue value
   */
  int getB() {
    return this.b;
  }
}

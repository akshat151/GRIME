package model;

import java.util.List;

/**
 * This class represents a PPMImage.
 */
public class PPMImage extends AbstractImage {
  private final String token;
  private final int maxValue;
  private final List<Pixel> pixels;


  /**
   * Single constructor of this class used to initialize the member variables.
   *
   * @param token    the token value used by ppm images.
   * @param pixels   the pixels stored in the image.
   * @param maxValue the maximum value of the pixel channel.
   * @param width    the width of the image.
   * @param height   the height of the image.
   */
  PPMImage(String token, int width, int height, int maxValue, List<Pixel> pixels) {
    this.token = token;
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixels = pixels;
  }


  /**
   * This method is used to create and return a new PPM image object
   * based on the new pixels values provided.
   *
   * @param pixels the new pixel values.
   * @return PPMImage object.
   */

  PPMImage getNewImage(List<Pixel> pixels) {
    return new PPMImage(
            this.token,
            this.width,
            this.height,
            this.maxValue,
            pixels
    );
  }

  /**
   * Used to return the width of the image.
   *
   * @return width of the calling image.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Used to return the height of the image.
   *
   * @return height of the calling image.
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Used to return the token of the image.
   *
   * @return token of the calling image.
   */
  String getPPMToken() {
    return token;
  }

  /**
   * Used to return the maxvalue of the image.
   *
   * @return maxvalue of the calling image.
   */
  int getPPMMaxValue() {
    return maxValue;
  }

  /**
   * Used to return the array of pixels of the image.
   *
   * @return pixels of the calling image.
   */
  List<Pixel> getPPMPixels() {
    return pixels;
  }
}

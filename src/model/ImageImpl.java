package model;

import java.util.List;

/**
 * This class represents a single PPM-type image.
 * It has variables that are stored in a standard PPM file.
 */
final class ImageImpl implements ImageExtension {

  /**
   * Maxvalue of pixel which is stored in a standard PPM file.
   */
  private final int maxValue;

  private final List<Pixel> pixels;

  private final String type;

  /**
   * The height of the image.
   */
  private final int width;

  /**
   * The width of the image.
   */
  private final int height;


  /**
   * This is the single constructor for this class that is used to initialize
   * the values of a PPM image.
   *
   * @param width    the width of the image.
   * @param height   the height of the image.
   * @param maxValue the maxvalue of pixel store in a PPM image.
   * @param pixels   an Array of {@link Pixel} objects, representing each pixel of this image.
   * @param type     type of image
   */
  ImageImpl(int width, int height, int maxValue, List<Pixel> pixels, String type) {
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixels = pixels;
    this.type = type;
  }

  /**
   * An array of pixels representing this image.
   */
  @Override
  public List<Pixel> getPixels() {
    return this.pixels;
  }

  /**
   * Used to return the width of the image.
   *
   * @return width of the calling image.
   */
  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Used to return the width of the image.
   *
   * @return width of the calling image.
   */
  @Override
  public String getType() {
    return this.type;
  }

  /**
   * This method acts as a builder for this class and is used to create and return a new
   * image based on the values of new pixels.
   *
   * @param pixels The array of new pixels representing a new image.
   * @return a new {@link ImageImpl} object.
   */
  public ImageImpl getNewImage(List<Pixel> pixels) {
    return new ImageImpl(
            this.width,
            this.height,
            this.maxValue,
            pixels,
            type);
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }
}

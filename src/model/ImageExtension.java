package model;

import java.util.List;

/**
 * This class represents ImageExtension class.
 */
public interface ImageExtension extends Image {

  /**
   * An array of pixels representing this image.
   */
  List<Pixel> getPixels();

  /**
   * Used to return the width of the image.
   *
   * @return width of the calling image.
   */
  int getMaxValue();

  /**
   * Used to return the width of the image.
   *
   * @return width of the calling image.
   */
  String getType();

  /**
   * This method acts as a builder for this class and is used to create and return a new
   * image based on the values of new pixels.
   *
   * @param pixels The array of new pixels representing a new image.
   * @return a new {@link Image} object.
   */
  ImageExtension getNewImage(List<Pixel> pixels);
}

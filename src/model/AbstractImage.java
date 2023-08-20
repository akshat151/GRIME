package model;

/**
 * This class is an abstract class that implements the @{@link Image} interface.
 * The class contains the common attributes of an image like - width, height.
 */
abstract class AbstractImage implements Image {
  /**
   * The height of the image.
   */
  protected int width;

  /**
   * The width of the image.
   */
  protected int height;

}

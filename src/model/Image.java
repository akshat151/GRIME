package model;

/**
 * This class represents an image in the project, it contains common functionalities
 * that are needed by all types of images.
 */
public interface Image {
  /**
   * Used to return the width of the image.
   *
   * @return width of the calling image.
   */
  int getWidth();

  /**
   * Used to return the height of the image.
   *
   * @return height of the calling image.
   */
  int getHeight();

}
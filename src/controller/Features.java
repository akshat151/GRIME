package controller;

import java.io.IOException;

/**
 * This class represents all the features that are added in the GUI program.
 * Each operation has a separate method.
 */
public interface Features {

  /**
   * This method is used to load image to the GUI program, when the
   * user clicks on "load" from the menu items.
   *
   * @throws IOException if file to be loaded is invalid or not found.
   */
  void loadImage() throws IOException;

  /**
   * This method is used to save an image to the GUI program, when the
   * user clicks on "save" option from the menu items.
   *
   * @throws IOException if file to be saved is invalid or not found.
   */
  void saveImage() throws IOException;

  /**
   * This method is used to flip the image and display it in the GUI program,
   * when the user clicks on the "flip" button.
   *
   * @throws IOException if file to be flipped is invalid or not found.
   */
  void flip() throws IOException;

  /**
   * This method is used to convert an image  to greyscale and display it in the GUI program,
   * when the user clicks on the "greyscale" button.
   *
   * @throws IOException if file to be converted is invalid or not found.
   */
  void greyscale() throws IOException;

  /**
   * This method is used to apply filter to an image and display it in the GUI program,
   * when the user clicks on the "filter" button.
   *
   * @throws IOException if file to be converted is invalid or not found.
   */
  void filter() throws IOException;

  /**
   * This method is used to apply transformation to an image and display the result
   * in the GUI program,
   * when the user clicks on the "transform" button.
   *
   * @throws IOException if file to be converted is invalid or not found.
   */
  void transform() throws IOException;

  /**
   * This method is used to dithering to an image and display the result in the GUI program,
   * when the user clicks on the "dither" button.
   *
   * @throws IOException if file to be converted is invalid or not found.
   */
  void dither() throws IOException;

  /**
   * This method is used to brighten an image and display the result in the GUI program,
   * when the user clicks on the "brighten" button.
   *
   * @throws IOException if file to be converted is invalid or not found.
   */
  void brighten() throws IOException;

  /**
   * This method is used to split an image into its RGB channels and display the result
   * in the GUI program,
   * when the user clicks on the "split" button.
   *
   * @throws IOException if file to be converted is invalid or not found.
   */
  void rgbSplit() throws IOException;

  /**
   * This method is used to combine different user-selected images into one and
   * display the resultant image in the GUI program,
   * when the user clicks on the "combine" button.
   *
   * @throws IOException if file to be converted is invalid or not found.
   */
  void rgbCombine() throws IOException;

  /**
   * This method is used to exit the program when the user clicks on the exit
   * menu item.
   **/
  void exit();
}

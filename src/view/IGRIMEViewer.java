package view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import controller.Features;

/**
 * The interface for our view class that handles all the GUI operations.
 */
public interface IGRIMEViewer extends IMEViewer {


  /**
   * This method is used to load image after used clicks on the load option
   * from menu.
   *
   * @return The filepath of loaded images.
   */
  String loadImage();


  /**
   * This is used to display options to the user to help navigate.
   * This is specific to some operations/
   *
   * @param options the available options for the type of message.
   * @param message the message that needs to be shown.
   * @param title   the title of the message box.
   * @return string that indicates the state of operation.
   */
  String showOptionsDialog(String[] options, String message, String title);

  /**
   * This is used to display input field to the user to help navigate.
   * This is specific to some operations/
   *
   * @param message the message that needs to be shown.
   * @param title   the title of the message box.
   * @return string that indicates the state of operation.
   */
  String showInputDialog(String message, String title);

  /**
   * The following method is executed when the used clicks on the
   * exit option from the menu item.
   */
  void exit();

  /**
   * This method is used to ask the location of the image file
   * that the user wants to save the image to.
   *
   * @return string that indicates the state of operation.
   */
  String selectImageFileToSave();

  /**
   * This method is used to display the loaded image in the GUI.
   *
   * @param inStream represents the image.
   * @throws IOException if something goes wrong while loading the image.
   */
  void showImage(ByteArrayInputStream inStream) throws IOException;

  /**
   * This method encapsulates all the features that are operated on the
   * GUI.
   *
   * @param features the features the needs to be added.
   */
  void addFeatures(Features features);

  /**
   * This method is used to view histogram of the loaded image.
   *
   * @param data the data required to view graph.
   */
  void showHistogram(Map<String, int[]> data);


  /**
   * This method is used to preview the error that is thrown by the
   * operation.
   *
   * @param message the message to display.
   */
  void displayError(String message);
}

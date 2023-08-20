package controller;

import java.io.IOException;

/**
 * This class represents a controller for the project.
 * A controller controls the complete flow of the project.
 */
public interface IMEController {

  /**
   * This method acts as the starting point for the controller
   * for the image processing operations.
   *
   * @throws IOException throws IOException if there are invalid read or write operations.
   */
  void executeIMEController() throws IOException;

}

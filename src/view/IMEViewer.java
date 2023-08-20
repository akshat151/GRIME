package view;

import java.io.IOException;

/**
 * This class represents a viewer class that displays messages to
 * the user based of their entered commands.
 */
public interface IMEViewer {

  /**
   * This method is used to display the message in the interface.
   *
   * @param message the message that needs to be shown.
   */
  void displayMsg(String message);


  /**
   * This method is used to dynamically show messages based on the result of operation performed.
   *
   * @param state   represents if the operation was successful or not.
   * @param out     an {@link Appendable} object, used to output the result.
   * @param action  the action for which this message is getting displayed.
   * @param message additional message, if required, to display.
   */
  void displayOpMsg(int state, Appendable out, String action, String message) throws IOException;

}

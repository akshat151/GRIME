package controller.command;

import java.io.IOException;

import model.IMEModelExtension;

/**
 * Interface for Command class, used to execute different commands.
 */
public interface IMECommand {

  /**
   * This method is used by different child classes to execute corresponding
   * functionality.
   *
   * @param model {@link model.ImageExtension} object.
   */
  boolean executeIMECommand(IMEModelExtension model) throws IOException;
}

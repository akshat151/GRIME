package controller.command;

import model.IMEModelExtension;

/**
 * This class is used for Brightening the image. The following class is used to
 * brighten (or darken) an image, based on the constant integer value
 * provided.
 */
public class BrightenIMECommand implements IMECommand {

  private final String destImageKey;
  private final String srcImageKey;
  private final int brightnessConstant;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public BrightenIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.destImageKey = commandArgs[3];
    this.srcImageKey = commandArgs[2];
    this.brightnessConstant = Integer.parseInt(commandArgs[1]);
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) {
    if (model.imageExists(srcImageKey)) {
      model.brighten(srcImageKey, destImageKey, brightnessConstant);
      return true;
    }
    return false;
  }
}

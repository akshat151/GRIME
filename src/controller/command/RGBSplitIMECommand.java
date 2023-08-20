package controller.command;

import java.io.IOException;

import model.Constants;
import model.IMEModelExtension;

/**
 * This class is used to convert an image into its greyscale format.
 * The resulting image will be filtered on greyscale.
 */
public class RGBSplitIMECommand implements IMECommand {

  private final String srcImageKey1;
  private final String redImageKey;
  private final String greenImageKey;
  private final String blueImageKey;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public RGBSplitIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.srcImageKey1 = commandArgs[1];
    this.redImageKey = commandArgs[2];
    this.greenImageKey = commandArgs[3];
    this.blueImageKey = commandArgs[4];
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) throws IOException {
    if (model.imageExists(srcImageKey1)) {
      model.greyscale(srcImageKey1, redImageKey, Constants.C_RED);
      model.greyscale(srcImageKey1, greenImageKey, Constants.C_GREEN);
      model.greyscale(srcImageKey1, blueImageKey, Constants.C_BLUE);
      return true;
    }
    return false;
  }
}

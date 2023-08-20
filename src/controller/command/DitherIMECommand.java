package controller.command;

import model.Constants;
import model.IMEModelExtension;

/**
 * This class is used to filter an image using the dithering algorithm.
 * The resulting image will be filtered based on the algorithm.
 */
public class DitherIMECommand implements IMECommand {

  private final String destImageKey;
  private final String srcImageKey;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public DitherIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.srcImageKey = commandArgs[1];
    this.destImageKey = commandArgs[2];
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) {
    if (model.imageExists(srcImageKey)) {
      model.transformation(srcImageKey, destImageKey, Constants.GREYSCALE);
      model.dither(destImageKey, destImageKey);
      return true;
    }
    return false;
  }
}

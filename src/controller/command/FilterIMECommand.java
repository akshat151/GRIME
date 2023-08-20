package controller.command;

import model.Constants;
import model.IMEModelExtension;

/**
 * This class is used to filter an image based on the matrix values.
 * The resulting image will be filtered based on the constants.
 */
public class FilterIMECommand implements IMECommand {
  private final String destImageKey;
  private final String srcImageKey;
  private final String type;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public FilterIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.srcImageKey = commandArgs[1];
    this.destImageKey = commandArgs[2];
    this.type = commandArgs[0].toLowerCase();
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) {
    if (model.imageExists(srcImageKey) && (type.equals(Constants.BLUR)
            || type.equals(Constants.SHARPEN))) {
      model.filter(srcImageKey, destImageKey, type);
      return true;
    }
    return false;
  }
}

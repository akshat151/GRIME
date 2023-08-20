package controller.command;

import model.Constants;
import model.IMEModelExtension;

/**
 * This class is used to flip an image based on the direction provided by user.
 * The resulting image will be flipped in horizontal or vertical direction.
 */
public class FlipIMECommand implements IMECommand {
  private final String destImageKey;
  private final String srcImageKey;
  private final String type;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public FlipIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.srcImageKey = commandArgs[1];
    this.destImageKey = commandArgs[2];
    this.type = commandArgs[0].toLowerCase();
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) {
    if (model.imageExists(srcImageKey) && (type.equals(Constants.HORIZONTAL)
            || type.equals(Constants.VERTICAL))) {
      model.flip(srcImageKey, destImageKey, type);
      return true;
    }
    return false;
  }
}

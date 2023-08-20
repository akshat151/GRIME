package controller.command;

import model.Constants;
import model.IMEModelExtension;

/**
 * This class is used to convert an image into its greyscale format.
 * The resulting image will be filtered on greyscale.
 */
public class GreyScaleIMECommand implements IMECommand {

  private final String destImageKey;
  private final String srcImageKey;
  private final String component;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public GreyScaleIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.destImageKey = commandArgs[3];
    this.srcImageKey = commandArgs[2];
    this.component = commandArgs[1];
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) {
    if (model.imageExists(srcImageKey) && (Constants.COMPONENT_TYPES.contains(component))) {
      model.greyscale(srcImageKey, destImageKey, component);
      return true;
    }
    return false;
  }
}

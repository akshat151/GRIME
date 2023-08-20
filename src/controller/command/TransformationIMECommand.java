package controller.command;

import model.Constants;
import model.IMEModelExtension;

/**
 * This class is used to convert an image into its greyscale format.
 * The resulting image will be filtered on greyscale.
 */
public class TransformationIMECommand implements IMECommand {

  private final String destImageKey;
  private final String srcImageKey;
  private final String type;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public TransformationIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.srcImageKey = commandArgs[2];
    this.destImageKey = commandArgs[3];
    this.type = commandArgs[1];
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) {
    if (model.imageExists(srcImageKey) && (Constants.TRANSFORMATION_TYPES.contains(type))) {
      model.transformation(srcImageKey, destImageKey, type);
      return true;
    }
    return false;
  }
}

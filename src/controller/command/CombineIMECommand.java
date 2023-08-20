package controller.command;

import model.IMEModelExtension;

/**
 * This class is used to combine different channels of pixel of an image.
 * The resulting image will be split into different color channels.
 */
public class CombineIMECommand implements IMECommand {

  private final String srcImageKey1;
  private final String srcImageKey2;
  private final String srcImageKey3;
  private final String destImageKey;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public CombineIMECommand(String command) {
    String[] commandArgs = command.split("\\s+");
    this.srcImageKey1 = commandArgs[2];
    this.srcImageKey2 = commandArgs[3];
    this.srcImageKey3 = commandArgs[4];
    this.destImageKey = commandArgs[1];
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) {
    if (model.imageExists(srcImageKey1) && model.imageExists(srcImageKey2)
            && model.imageExists(srcImageKey3)) {
      model.combine(srcImageKey1, srcImageKey2, srcImageKey3, destImageKey);
      return true;
    }
    return false;
  }
}

package controller.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import controller.fileoperations.FileReaderImpl;
import model.IMEModelExtension;


/**
 * This class is used to convert an image into its greyscale format.
 * The resulting image will be filtered on greyscale.
 */
public class LoadIMECommand implements IMECommand {

  private final InputStream fileInputStream;
  private final String destImageKey;
  private final String type;

  /**
   * Single constructor for this class.
   * This is used to initialize member variables for this class.
   *
   * @param command string cmd passed by the user.
   */
  public LoadIMECommand(String command) throws FileNotFoundException {
    String[] commandArgs = command.split("\\s+");
    this.fileInputStream = new FileReaderImpl().fileRead(commandArgs[1]);
    this.destImageKey = commandArgs[2];
    this.type = commandArgs[1].split("\\.")[1];
  }

  @Override
  public boolean executeIMECommand(IMEModelExtension model) throws IOException {
    return model.loadImage(fileInputStream, destImageKey, type);
  }
}

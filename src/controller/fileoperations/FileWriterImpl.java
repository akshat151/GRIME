package controller.fileoperations;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class represents the I/O operations in a file.
 */
public class FileWriterImpl implements FileWriteOperations {

  /**
   * This method represents the I/O operations in a file.
   * * @param out         representation of the file.
   * * @param type        the filetype.
   * * @param destination the destination of the file.
   * * @return represents if the operation was successful.
   * * @throws IOException if the file is not found.
   */

  @Override
  public boolean writeFile(ByteArrayOutputStream out, String destination, String type)
          throws IOException {
    byte[] byteArray = out.toByteArray();
    ByteArrayInputStream inStream = new ByteArrayInputStream(byteArray);
    BufferedImage newImage = ImageIO.read(inStream);
    out.close();
    return ImageIO.write(newImage, type, new File(destination));
  }

}

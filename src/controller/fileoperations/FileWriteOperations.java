package controller.fileoperations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class represents the I/O operations in a file.
 */
public interface FileWriteOperations {

  /**
   * This class represents the I/O operations in a file.
   *
   * @param out         representation of the file.
   * @param type        the filetype.
   * @param destination the destination of the file.
   * @return represents if the operation was successful.
   * @throws IOException if the file is not found.
   */
  boolean writeFile(ByteArrayOutputStream out, String destination, String type) throws IOException;

}

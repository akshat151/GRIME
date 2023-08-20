package controller.fileoperations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * This class represents the I/O operations in a file.
 */
public class FileReaderImpl implements FileReadOperations {

  /**
   * This class represents reading of a file.
   *
   * @param sourcePath the source-path of the file.
   * @return This represents the string representation of the file.
   * @throws FileNotFoundException if the file is not found.
   */

  @Override
  public InputStream fileRead(String sourcePath) throws FileNotFoundException {
    return new FileInputStream(sourcePath);
  }

}

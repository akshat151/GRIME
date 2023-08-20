package controller.fileoperations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class represents the I/O operations in a file.
 */
public class PPMFileWriterImpl implements FileWriteOperations {
  @Override
  public boolean writeFile(ByteArrayOutputStream out, String destination, String type) {
    // Retrieves data from the output stream in string format
    String streamData = out.toString();
    try {
      File myFile = new File(destination);
      Files.writeString(Path.of(myFile.getAbsolutePath()), streamData,
              StandardCharsets.US_ASCII);
      out.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }
}

package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * This model represents the Model for GUI operations.
 */
public interface IGRIMEModel extends IMEModelExtension {

  /**
   * This method is used to loadImage an image from a valid image-file-path
   * and store it for further use.
   *
   * @param byteArrayOutputStream the path of file that needs be operated on.
   * @return boolean returns false with the loadImage operation was unsuccessful else returns true.
   * @throws IOException if there is a problem in loading the image file.
   */
  ByteArrayInputStream getImageStream(ByteArrayOutputStream byteArrayOutputStream)
          throws IOException;

  /**
   * This method is used to generate graph-readable data from the passed image.
   *
   * @param image the image that needs to be considered for histogram.
   * @return the graph data.
   * @throws IOException if there is a problem in loading the image file.
   */
  Map<String, int[]> getHistogramData(ByteArrayInputStream image) throws IOException;

}

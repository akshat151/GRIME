package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This represents an extension of the {@link IMEModel} class.
 */
public interface IMEModelExtension extends IMEModel {

  /**
   * This method is used to loadImage an image from a valid image-file-path
   * and store it for further use.
   *
   * @param fileInputStream the path of file that needs be operated on.
   * @param destImageKey    the key from which the changed file will be referenced in the future.
   * @param type            type of image
   * @return boolean returns false with the loadImage operation was unsuccessful else returns true.
   * @throws IOException if there is a problem in loading the image file.
   */
  boolean loadImage(InputStream fileInputStream, String destImageKey, String type)
          throws IOException;

  /**
   * This method is used to save the operated image to a valid image file.
   *
   * @param srcImageKey the key for the image that needs to be saved.
   * @param type        type of image
   * @return returns non-empty byteArrayOutputStream for successful save operation.
   */
  ByteArrayOutputStream saveImage(String srcImageKey, String type);

  /**
   * This method is used to filter the given image and generate an image that is
   * filtered based on the type provided.
   *
   * @param type         the type of filtering to be executed.
   * @param srcImageKey  the key of source-image.
   * @param destImageKey the key of destination-image.
   */
  void filter(String srcImageKey, String destImageKey, String type);

  /**
   * This method is used to transform the given image and generate an image that is
   * transformed based on the type provided.
   *
   * @param type         the type of transforming to be executed.
   * @param srcImageKey  the key of source-image.
   * @param destImageKey the key of destination-image.
   */
  void transformation(String srcImageKey, String destImageKey, String type);

  /**
   * This method is used to apply dithering algorithm to the given image and
   * generate an image that is
   * transformed based on the algorithm.
   *
   * @param srcImgKey    the key of source-image.
   * @param destImageKey the key of destination-image.
   */
  void dither(String srcImgKey, String destImageKey);

}
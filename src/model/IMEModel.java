package model;

/**
 * Interface for IME class.
 * This contains all the operations executed on the image.
 * This class is further implemented by Controller classes.
 */
public interface IMEModel {

  /**
   * This method is used to loadImage an image from a valid image-file-path
   * and store it for further use.
   *
   * @param filename     the path of file that needs be operated on.
   * @param destImageKey the key from which the changed file will be referenced in the future.
   * @return boolean returns false if the loadImage operation was unsuccessful else returns true.
   */
  boolean load(String filename, String destImageKey);


  /**
   * This method is used to save the operated image to a valid image file.
   *
   * @param destination the path of file where the image will be saved.
   * @param srcImageKey the key for the image that needs to be saved.
   * @return boolean returns true if the save operation was successful else returns false.
   */
  boolean save(String destination, String srcImageKey);

  /**
   * This method is used to convert an image into a greyscale component based
   * on the type of component.
   *
   * @param srcImageKey  the key for the image for which this operation needs to be performed.
   * @param destImageKey the key for the image where the operated
   *                     image will be saved for future references.
   * @param component    the type of component based on which this method will return results.
   */
  void greyscale(String srcImageKey, String destImageKey, String component);


  /**
   * This method is used to convert flip an image either horizontally or vertically based
   * on the type passed.
   *
   * @param srcImageKey  the key for the image for which this operation needs to be performed.
   * @param destImageKey the key for the image where the operated
   *                     image will be saved for future references.
   * @param type         perform horizontal flip or vertical flip.
   */
  void flip(String srcImageKey, String destImageKey, String type);

  /**
   * This method is used to brighten an image based on the value provided.
   *
   * @param srcImageKey        the key for the image for which this operation needs to be performed.
   * @param destImageKey       the key for the image where the operated
   *                           image will be saved for future references.
   * @param brightnessConstant the amount by which the image should be brightened.
   */
  void brighten(String srcImageKey, String destImageKey, int brightnessConstant);

  /**
   * This method is used to combine three images into one based on
   * the red green and blue channels.
   *
   * @param srcImageKey1 the key for the first image for the image to combine.
   * @param srcImageKey2 the key for the second image for the image to combine.
   * @param srcImageKey3 the key for the third image for the image to combine.
   * @param destImageKey the key for the image where the operated
   *                     image will be saved for future references.
   */
  void combine(String srcImageKey1, String srcImageKey2, String srcImageKey3, String destImageKey);

  /**
   * This image return true if the image is loaded by the user before else it returns true.
   *
   * @param imageKey the imageKey for which the user wants to check.
   * @return the result if the image exists or not.
   */
  boolean imageExists(String imageKey);
}

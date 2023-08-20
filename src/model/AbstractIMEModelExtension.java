package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents an abstract class which extends the existing IMEPPMModelImpl class and
 * implements the IMEModelExtension interface.
 */
abstract class AbstractIMEModelExtension extends IMEPPMModelImpl
        implements IMEModelExtension {
  protected static final HashMap<String, ImageExtension> imageExtensionHashMap = new HashMap<>();

  /**
   * This method is used to loadImage an image from a valid image-file-path
   * and store it for further use.
   *
   * @param fileInputStream the path of file that needs be operated on.
   * @param imageKey        the key from which the changed file will be referenced in the future.
   * @param type            type of image
   * @return boolean returns false if the loadImage operation was  unsuccessful else true.
   * @throws IOException if there is a problem in loading the image file.
   */
  protected abstract boolean loadImg(InputStream fileInputStream, String imageKey, String type)
          throws IOException;

  /**
   * This method is used to save the operated image to a valid image file.
   *
   * @param imageKey the key for the image that needs to be saved.
   * @param type     type of image
   * @return returns non-empty byteArrayOutputStream for successful save operation.
   */
  protected abstract ByteArrayOutputStream saveImg(String imageKey, String type);

  @Override
  public boolean loadImage(InputStream fileInputStream, String imageKey, String type)
          throws IOException {
    return loadImg(fileInputStream, imageKey, type);
  }

  @Override
  public ByteArrayOutputStream saveImage(String imageKey, String type) {
    return saveImg(imageKey, type);
  }

  @Override
  public void greyscale(String srcImageKey, String destImageKey, String component) {
    ImageExtension image = imageExtensionHashMap.get(srcImageKey);
    List<Pixel> newPixels = new ArrayList<>();
    for (Pixel pixel : image.getPixels()) {
      newPixels.add(getGreyscaleCompVal(component, pixel.getR(), pixel.getG(), pixel.getB()));
    }
    imageExtensionHashMap.put(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void flip(String srcImageKey, String destImageKey, String type) {
    ImageExtension image = imageExtensionHashMap.get(srcImageKey);
    ArrayList<Pixel> newPixels = new ArrayList<>(image.getPixels());
    // decide vertical or horizontal
    if (type.equals(Constants.HORIZONTAL)) {
      flipHorizontally(newPixels, image.getWidth(), image.getHeight());
    } else {
      flipVertically(newPixels, image.getWidth(), image.getHeight());
    }
    imageExtensionHashMap.put(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void brighten(String srcImageKey, String destImageKey, int brightnessConstant) {
    ImageExtension image = imageExtensionHashMap.get(srcImageKey);
    ArrayList<Pixel> newPixels = new ArrayList<>();

    for (Pixel pixel : image.getPixels()) {
      int r = brightenChannel(pixel.getR(), brightnessConstant);
      int g = brightenChannel(pixel.getG(), brightnessConstant);
      int b = brightenChannel(pixel.getB(), brightnessConstant);
      newPixels.add(new Pixel(r, g, b));
    }
    imageExtensionHashMap.put(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void combine(
          String srcImageKeyR,
          String srcImageKeyG,
          String srcImageKeyB,
          String destImageKey
  ) {
    ImageExtension imageRed = imageExtensionHashMap.get(srcImageKeyR);
    ImageExtension imageGreen = imageExtensionHashMap.get(srcImageKeyG);
    ImageExtension imageBlue = imageExtensionHashMap.get(srcImageKeyB);

    ArrayList<Pixel> newPixels = new ArrayList<>();
    for (int i = 0; i < imageRed.getPixels().size(); i++) {
      newPixels.add(new Pixel(
              imageRed.getPixels().get(i).getR(),
              imageGreen.getPixels().get(i).getG(),
              imageBlue.getPixels().get(i).getB()
      ));
    }
    imageExtensionHashMap.put(destImageKey, imageRed.getNewImage(newPixels));
  }

  @Override
  public boolean imageExists(String imageKey) {
    return imageExtensionHashMap.containsKey(imageKey);
  }

  @Override
  public void filter(String srcImageKey, String destImageKey, String type) {
    double[][] kernel;
    if (type.equals(Constants.BLUR)) {
      kernel = Constants.BLUR_KERNEL;
    } else {
      kernel = Constants.SHARPEN_KERNEL;
    }

    ImageExtension image = imageExtensionHashMap.get(srcImageKey);
    ArrayList<Pixel> newPixels = new ArrayList<>();

    int kernelRowLen = kernel.length;
    int kernelColLen = kernel[0].length;

    for (int currPixelIndex = 0; currPixelIndex < image.getPixels().size(); currPixelIndex++) {

      int sumR = 0;
      int sumG = 0;
      int sumB = 0;

      for (int i = 0; i < kernelRowLen; i++) {
        for (int j = 0; j < kernelColLen; j++) {

          int currW = (i - (Math.floorDiv(kernelRowLen, 2))) * image.getWidth();
          int pixelT = currPixelIndex + currW;
          int pixelIndex = pixelT - Math.floorDiv(kernelRowLen, 2) + j;

          if (Math.floorDiv(pixelT, image.getWidth()) != Math.floorDiv(pixelIndex,
                  image.getWidth())
                  || pixelIndex > (image.getWidth() * image.getHeight()) - 1 ||
                  pixelIndex < 0
          ) {
            pixelIndex = -1;
          }

          int pixelValueRed = 0;
          int pixelValueGreen = 0;
          int pixelValueBlue = 0;

          if (pixelIndex >= 0) {
            pixelValueRed = image.getPixels().get(pixelIndex).getR();
            pixelValueGreen = image.getPixels().get(pixelIndex).getG();
            pixelValueBlue = image.getPixels().get(pixelIndex).getB();
          }

          sumR += pixelValueRed * kernel[i][j];
          sumG += pixelValueGreen * kernel[i][j];
          sumB += pixelValueBlue * kernel[i][j];

        }
      }
      newPixels.add(new Pixel(capped(sumR), capped(sumG), capped(sumB)));
    }
    imageExtensionHashMap.put(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void transformation(String srcImageKey, String destImageKey, String type) {

    double[][] matrix;
    if (type.equals(Constants.SEPIA)) {
      matrix = Constants.SEPIA_MATRIX;
    } else {
      matrix = Constants.GREYSCALE_MATRIX;
    }

    ImageExtension image = imageExtensionHashMap.get(srcImageKey);
    ArrayList<Pixel> newPixels = new ArrayList<>();

    for (Pixel pixel : image.getPixels()) {
      int r = (int) (pixel.getR() * matrix[0][0]
              + pixel.getR() * matrix[0][1]
              + pixel.getR() * matrix[0][2]);

      int g = (int) (pixel.getR() * matrix[1][0]
              + pixel.getR() * matrix[1][1]
              + pixel.getR() * matrix[1][2]);

      int b = (int) (pixel.getR() * matrix[2][0]
              + pixel.getR() * matrix[2][1]
              + pixel.getR() * matrix[2][2]);

      newPixels.add(new Pixel(capped(r), capped(g), capped(b)));
    }
    imageExtensionHashMap.put(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void dither(String srcImageKey, String destImageKey) {

    ImageExtension image = imageExtensionHashMap.get(srcImageKey);
    List<Pixel> newPixels = new ArrayList<>(image.getPixels());
    for (int pixelIndex = 0; pixelIndex < image.getPixels().size(); pixelIndex++) {

      Pixel currPixel = newPixels.get(pixelIndex);
      int newColor = getBWColor(currPixel);
      int error = currPixel.getR() - newColor;
      newPixels.set(pixelIndex, new Pixel(newColor, newColor, newColor));

      if (checkPixelLoc(Constants.PixelPos.RIGHT, pixelIndex, image)) {
        setPixel(newPixels, pixelIndex + 1, error, Constants.RIGHT_CONS);
      }

      if (checkPixelLoc(Constants.PixelPos.BELOW_LEFT, pixelIndex, image)) {
        setPixel(newPixels, pixelIndex + image.getWidth() - 1, error, Constants.BELOW_LEFT_CONS);
      }

      if (checkPixelLoc(Constants.PixelPos.BELOW, pixelIndex, image)) {
        setPixel(newPixels, pixelIndex + image.getWidth(), error, Constants.BELOW_CONS);
      }

      if (checkPixelLoc(Constants.PixelPos.BELOW_RIGHT, pixelIndex, image)) {
        setPixel(newPixels, pixelIndex + image.getWidth() + 1, error, Constants.BELOW_RIGHT_CONS);
      }
    }
    imageExtensionHashMap.put(destImageKey, image.getNewImage(newPixels));
  }

  private boolean checkPixelLoc(Constants.PixelPos type, int pixelIndex, ImageExtension image) {

    switch (type) {
      case RIGHT:
        return Math.floorDiv(pixelIndex + 1, image.getWidth())
                == Math.floorDiv(pixelIndex, image.getWidth());

      case BELOW_LEFT:
        int pixelBelowLeft = pixelIndex + image.getWidth() - 1;
        return ((pixelBelowLeft < (image.getWidth() * image.getHeight())) &&
                Math.floorDiv(pixelBelowLeft, image.getWidth()) ==
                        Math.floorDiv(pixelIndex, image.getWidth()) + 1);

      case BELOW:
        int pixelBelow = pixelIndex + image.getWidth();
        return (pixelBelow < (image.getWidth() * image.getHeight()));


      case BELOW_RIGHT:
        int pixelBelowRight = pixelIndex + image.getWidth() + 1;
        return ((pixelBelowRight < (image.getWidth() * image.getHeight())) &&
                Math.floorDiv(pixelBelowRight, image.getWidth()) ==
                        Math.floorDiv(pixelIndex, image.getWidth()) + 1);

      default:
        return false;

    }

  }

  private void setPixel(List<Pixel> newPixels, int nextPixelIndex, int error, double constant) {
    Pixel pixelVal = newPixels.get(nextPixelIndex);
    int color = capped((int) ((pixelVal.getR()) + (constant * error)));
    newPixels.set(nextPixelIndex, new Pixel(color, color, color));
  }

  private int getBWColor(Pixel pixel) {
    return ((255 - pixel.getR()) > (pixel.getR())) ? 0 : 255;
  }

  private int capped(int value) {
    if (value > 255) {
      return 255;
    } else if (value < 0) {
      return 0;
    }
    return value;
  }
}

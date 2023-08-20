package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method
 * as required.
 */
public class IMEPPMModelImpl implements IMEModel {

  private final HashMap<String, PPMImage> images = new HashMap<>();

  @Override
  public boolean load(String filename, String imageKey) {
    // check extension
    if (!filename.endsWith("ppm")) {
      System.out.println(Constants.INVALID_PPM);
      return false;
    }

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return false;
    }
    updateImages(imageKey, initialize(sc));
    return true;
  }

  @Override
  public boolean save(String dest, String imageKey) {

    if (!dest.endsWith(".ppm")) {
      System.out.println(Constants.INVALID_PPM_DEST);
      return false;
    }
    PPMImage image = images.get(imageKey);

    try {
      File myFile = new File(dest);
      FileWriter mWriter = new FileWriter(myFile.getAbsoluteFile());

      mWriter.write(image.getPPMToken() + System.lineSeparator());
      mWriter.write(image.getWidth() + " " + image.getHeight() + System.lineSeparator());
      mWriter.write(image.getPPMMaxValue() + System.lineSeparator());

      for (Pixel pixel : image.getPPMPixels()) {
        mWriter.write(pixel.getR() + System.lineSeparator());
        mWriter.write(pixel.getG() + System.lineSeparator());
        mWriter.write(pixel.getB() + System.lineSeparator());
      }
      mWriter.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public void greyscale(String srcImageKey, String destImageKey, String component) {
    PPMImage image = images.get(srcImageKey);
    List<Pixel> newPixels = new ArrayList<>();
    for (Pixel pixel : image.getPPMPixels()) {
      newPixels.add(getGreyscaleCompVal(component, pixel.getR(), pixel.getG(), pixel.getB()));
    }
    updateImages(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void flip(String srcImageKey, String destImageKey, String type) {
    PPMImage image = images.get(srcImageKey);
    List<Pixel> newPixels = new ArrayList<>(image.getPPMPixels());
    // decide vertical or horizontal
    if (type.equals(Constants.HORIZONTAL)) {
      flipHorizontally(newPixels, image.width, image.height);
    } else {
      flipVertically(newPixels, image.width, image.height);
    }
    updateImages(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void brighten(String srcImageKey, String destImageKey, int brightnessConstant) {
    PPMImage image = images.get(srcImageKey);
    List<Pixel> newPixels = new ArrayList<>();

    for (Pixel pixel : image.getPPMPixels()) {
      int r = brightenChannel(pixel.getR(), brightnessConstant);
      int g = brightenChannel(pixel.getG(), brightnessConstant);
      int b = brightenChannel(pixel.getB(), brightnessConstant);
      newPixels.add(new Pixel(r, g, b));
    }
    updateImages(destImageKey, image.getNewImage(newPixels));
  }

  @Override
  public void combine(
          String srcImageKeyR,
          String srcImageKeyG,
          String srcImageKeyB,
          String destImageKey
  ) {
    PPMImage imageRed = images.get(srcImageKeyR);
    PPMImage imageGreen = images.get(srcImageKeyG);
    PPMImage imageBlue = images.get(srcImageKeyB);

    List<Pixel> newPixels = new ArrayList<>();
    for (int i = 0; i < imageRed.getPPMPixels().size(); i++) {
      newPixels.add(new Pixel(
              imageRed.getPPMPixels().get(i).getR(),
              imageGreen.getPPMPixels().get(i).getG(),
              imageBlue.getPPMPixels().get(i).getB()
      ));
    }
    updateImages(destImageKey, imageRed.getNewImage(newPixels));
  }

  @Override
  public boolean imageExists(String imageKey) {
    return images.containsKey(imageKey);
  }

  // helper
  protected Pixel getGreyscaleCompVal(String component, int r, int g, int b) {
    int calculatedValue;
    switch (component) {
      case Constants.C_RED:
        calculatedValue = r;
        break;
      case Constants.C_GREEN:
        calculatedValue = g;
        break;
      case Constants.C_BLUE:
        calculatedValue = b;
        break;
      case Constants.C_VALUE:
        calculatedValue = Math.max(Math.max(r, g), b);
        break;
      case Constants.C_INTENSITY:
        calculatedValue = Math.floorDiv((r + g + b), 3);
        break;
      case Constants.C_LUMA:
        calculatedValue = calculateLuma(r, g, b);
        break;
      default:
        return null;
    }
    return new Pixel(calculatedValue, calculatedValue, calculatedValue);
  }

  // helper
  protected int brightenChannel(int channel, int brightnessConstant) {
    channel += brightnessConstant;
    if (channel < 0) {
      channel = 0;
    } else if (channel > 255) {
      channel = 255;
    }
    return channel;
  }

  // helper - core logic behind flipping
  protected void flipHorizontally(List<Pixel> pixels, int width, int height) {

    int total = width * height;

    int upperBound = total - Math.floorDiv(width, 2);
    if (width % 2 != 0) {
      upperBound -= 1;
    }

    int topPixel = 0;
    int sidePixel = width - topPixel - 1;

    while (topPixel < upperBound - 1) {

      if (sidePixel - topPixel <= 0) {
        topPixel = topPixel + width - Math.floorDiv(width, 2);
      }

      if (topPixel > 0 && topPixel % width == 0) {
        sidePixel = topPixel + width - 1;
      }

      swapPixels(pixels, topPixel, sidePixel);
      topPixel += 1;
      sidePixel -= 1;
    }
  }

  // helper - core logic behind flipping
  protected void flipVertically(List<Pixel> pixels, int width, int height) {
    int total = width * height;
    int upperBound = (total / 2);
    if (height % 2 != 0) {
      upperBound -= (width / 2);
    }

    int topPixel = 0;
    int lowerPixel = total - width;

    while (topPixel < upperBound - 1) {

      if (topPixel > 0 && topPixel % width == 0) {
        lowerPixel = total - topPixel - width;
      }

      swapPixels(pixels, topPixel, lowerPixel);
      topPixel += 1;
      lowerPixel += 1;
    }
  }

  //helper
  protected void swapPixels(List<Pixel> pixels, int pos1, int pos2) {
    // Swapping for each of the three channels - R G B
    Pixel tempPixel = pixels.get(pos1);
    pixels.set(pos1, pixels.get(pos2));
    pixels.set(pos2, tempPixel);
  }

  // helper
  protected int calculateLuma(int r, int g, int b) {
    return (int) (Constants.LUMA_R * r + Constants.LUMA_G * g + Constants.LUMA_B * b);
  }

  protected List<Pixel> readPixels(Scanner scanner) {
    List<Pixel> pixels = new ArrayList<>();
    while (scanner.hasNextInt()) {
      int r = scanner.nextInt();
      int g = scanner.nextInt();
      int b = scanner.nextInt();
      pixels.add(new Pixel(r, g, b));
    }
    return pixels;
  }

  protected PPMImage initialize(Scanner scanner) {

    // represent ppm image meta data: [token, width, height, maxValue]
    String[] imageMetaData = new String[4];

    int i = 0;
    while (i < imageMetaData.length) {
      String line = scanner.next();
      if (!line.startsWith("#")) {
        imageMetaData[i++] = line;
      } else {
        scanner.nextLine();
      }
    }

    List<Pixel> pixels = readPixels(scanner);
    return new PPMImage(
            imageMetaData[0],                   // token
            Integer.parseInt(imageMetaData[1]), // width
            Integer.parseInt(imageMetaData[2]), // height
            Integer.parseInt(imageMetaData[3]), // max-value
            pixels
    );
  }

  // helper
  private void updateImages(String key, PPMImage image) {
    images.put(key, image);
  }

}

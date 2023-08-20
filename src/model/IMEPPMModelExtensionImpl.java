package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * This class represents an extension for the Model extension and its ppm implementation.
 */
public class IMEPPMModelExtensionImpl extends AbstractIMEModelExtension {
  @Override
  protected boolean loadImg(InputStream fileInputStream, String imageKey, String type) {
    Scanner sc = new Scanner(fileInputStream);
    PPMImage ppmImage = initialize(sc);
    ImageExtension imageExtension = new ImageImpl(ppmImage.getWidth(), ppmImage.getHeight(),
            ppmImage.getPPMMaxValue(), ppmImage.getPPMPixels(), ppmImage.getPPMToken());
    imageExtensionHashMap.put(imageKey, imageExtension);
    return true;
  }

  @Override
  protected ByteArrayOutputStream saveImg(String imageKey, String type) {
    ImageExtension image = imageExtensionHashMap.get(imageKey);
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(Constants.PPM_TOKEN).append(System.lineSeparator());
      sb.append(image.getWidth()).append(" ").append(image.getHeight())
              .append(System.lineSeparator());
      sb.append(image.getMaxValue()).append(System.lineSeparator());

      for (Pixel pixel : image.getPixels()) {
        sb.append(pixel.getR()).append(System.lineSeparator());
        sb.append(pixel.getG()).append(System.lineSeparator());
        sb.append(pixel.getB()).append(System.lineSeparator());
      }
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] array = sb.toString().getBytes();

      // Writes data to the output stream
      out.write(array);
      return out;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return new ByteArrayOutputStream();
    }
  }
}

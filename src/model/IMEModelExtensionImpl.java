package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This class represents an extension for the Model extension.
 */
public class IMEModelExtensionImpl extends AbstractIMEModelExtension {

  @Override
  protected boolean loadImg(InputStream fileInputStream, String imageKey, String type)
          throws IOException {
    BufferedImage image = ImageIO.read(fileInputStream);
    List<Pixel> pixels = new ArrayList<>();

    // Write the image data
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;
        pixels.add(new Pixel(r, g, b));
      }
    }
    ImageExtension imageExtension = new ImageImpl(image.getWidth(), image.getHeight(),
            255, pixels, type);
    imageExtensionHashMap.put(imageKey, imageExtension);
    fileInputStream.close();
    return true;
  }

  @Override
  protected ByteArrayOutputStream saveImg(String imageKey, String type) {
    ImageExtension image = imageExtensionHashMap.get(imageKey);
    try {
      BufferedImage im = new BufferedImage(image.getWidth(), image.getHeight(),
              BufferedImage.TYPE_INT_RGB);

      int[] pixelList = new int[image.getPixels().size()];
      int i = 0;
      for (Pixel pixel : image.getPixels()) {
        int r = pixel.getR();
        int g = pixel.getG();
        int b = pixel.getB();
        pixelList[i] = (r << 16) | (g << 8) | b;
        i++;
      }
      im.setRGB(0, 0, image.getWidth(), image.getHeight(), pixelList, 0,
              image.getWidth());
      ByteArrayOutputStream bas = new ByteArrayOutputStream();
      ImageIO.write(im, type, bas);
      return bas;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return new ByteArrayOutputStream();
    }
  }
}

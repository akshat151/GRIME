package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * This class is the implementation of the {@link IGRIMEModel} class,
 * this class is specific to PPM image-types.
 */
public class IGRIMEModelPPMImpl extends IMEPPMModelExtensionImpl implements IGRIMEModel {

  @Override
  public ByteArrayInputStream getImageStream(ByteArrayOutputStream byteArrayOutputStream)
          throws IOException {
    byte[] byteArray = byteArrayOutputStream.toByteArray();
    InputStream inStream = new ByteArrayInputStream(byteArray);
    Scanner scanner = new Scanner(inStream);

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
    int width = Integer.parseInt(imageMetaData[1]);
    int height = Integer.parseInt(imageMetaData[2]);
    int pixelSize = width * height * 3;
    int[] pixelList = new int[pixelSize];
    i = 0;
    while (scanner.hasNextInt()) {
      int r = scanner.nextInt();
      int g = scanner.nextInt();
      int b = scanner.nextInt();
      pixelList[i] = (r << 16) | (g << 8) | b;
      i++;
    }

    BufferedImage im = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);
    im.setRGB(0, 0, width, height, pixelList, 0,
            width);
    ByteArrayOutputStream bas = new ByteArrayOutputStream();
    ImageIO.write(im, "png", bas);
    return new ByteArrayInputStream(bas.toByteArray());
  }

  @Override
  public Map<String, int[]> getHistogramData(ByteArrayInputStream imageInputStream)
          throws IOException {

    BufferedImage image = ImageIO.read(imageInputStream);
    int[] redChannels = new int[256];
    int[] greenChannels = new int[256];
    int[] blueChannels = new int[256];
    int[] valueChannels = new int[256];

    // initialize
    Arrays.fill(redChannels, 0);
    Arrays.fill(greenChannels, 0);
    Arrays.fill(blueChannels, 0);
    Arrays.fill(valueChannels, 0);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;
        int v = Math.floorDiv((r + g + b), 3);

        redChannels[r]++;
        greenChannels[g]++;
        blueChannels[b]++;
        valueChannels[v]++;
      }
    }

    HashMap<String, int[]> data = new HashMap<>();
    data.put(Constants.H_RED, redChannels);
    data.put(Constants.H_GREEN, greenChannels);
    data.put(Constants.H_BLUE, blueChannels);
    data.put(Constants.H_INTENSITY, valueChannels);

    return data;
  }
}

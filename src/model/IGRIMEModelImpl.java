package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * This method is the implementation of the {@link IGRIMEModel} interface.
 */
public class IGRIMEModelImpl extends IMEModelExtensionImpl implements IGRIMEModel {
  @Override
  public ByteArrayInputStream getImageStream(ByteArrayOutputStream byteArrayOutputStream) {
    return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
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

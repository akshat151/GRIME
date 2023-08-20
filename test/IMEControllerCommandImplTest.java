import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

import controller.IMEControllerCommandImpl;
import model.IMEModelExtension;
import model.IMEModelExtensionImpl;
import view.IMEViewer;
import view.IMEViewerImpl;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Testcases.
 */
public class IMEControllerCommandImplTest {

  static class MockIMEExtensionImpl implements IMEModelExtension {

    private final StringBuilder log;

    public MockIMEExtensionImpl(StringBuilder log) {
      this.log = log;
    }

    /**
     * This method is used to load an image from a valid image-file-path
     * and store it for further use.
     *
     * @param filename     the path of file that needs be operated on.
     * @param destImageKey the key from which the changed file will be referenced in the future.
     * @return boolean returns false with the load operation for not successful else returns true.
     */
    @Override
    public boolean load(String filename, String destImageKey) {
      return false;
    }

    /**
     * This method is used to load an image from a valid image-file-path
     * and store it for further use.
     *
     * @param fileInputStream     the path of file that needs be operated on.
     * @param destImageKey the key from which the changed file will be referenced in the future.
     * @param type         type of image
     * @return boolean returns false with the load operation for not successful else returns true.
     */
    @Override
    public boolean loadImage(InputStream fileInputStream, String destImageKey, String type) {
      Scanner sc = new Scanner(fileInputStream);
      log.append("Received Inputs ")
              .append(sc.next())
              .append(" and ")
              .append(destImageKey)
              .append(" and ")
              .append(type);
      return true;
    }

    @Override
    public boolean save(String destination, String srcImageKey) {
      log.append("Received Inputs ")
              .append(destination)
              .append(" and ")
              .append(srcImageKey);
      return true;
    }

    @Override
    public void greyscale(String srcImage, String destImage, String component) {
      log.append("Received Inputs ")
              .append(srcImage)
              .append(" and ")
              .append(destImage)
              .append(" and ")
              .append(component);
    }

    @Override
    public void flip(String srcImage, String destImage, String type) {
      log.append("Received Inputs ")
              .append(srcImage)
              .append(" and ")
              .append(destImage)
              .append(" and ")
              .append(type);
    }

    @Override
    public void brighten(String srcImage, String destImage, int k) {
      log.append("Received Inputs ")
              .append(srcImage)
              .append(" and ")
              .append(destImage)
              .append(" and ").append(k);
    }

    @Override
    public void combine(String srcImage1, String srcImage2, String srcImage3, String destImage) {
      log.append("Received Inputs ")
              .append(srcImage1)
              .append(" and ")
              .append(srcImage2)
              .append(" and ")
              .append(srcImage3)
              .append(" and ")
              .append(destImage);
    }

    @Override
    public boolean imageExists(String image) {
      log.append("Image Exists ").append(image).append(" ");
      return true;
    }

    /**
     * This method is used to save the operated image to a valid image file.
     *
     * @param srcImageKey the key for the image that needs to be saved.
     * @param type        type of image
     * @return boolean returns true if the save operation was successful else returns false.
     */
    @Override
    public ByteArrayOutputStream saveImage(String srcImageKey, String type) {
      log.append("Received Inputs ")
              .append(srcImageKey)
              .append(" and ")
              .append(type);
      return null;
    }

    @Override
    public void filter(String srcImageKey, String destImageKey, String type) {
      log.append("Received Inputs ")
              .append(srcImageKey)
              .append(" and ")
              .append(destImageKey)
              .append(" and ")
              .append(type);
    }

    @Override
    public void transformation(String srcImageKey, String destImageKey, String type) {
      log.append("Received Inputs ")
              .append(srcImageKey)
              .append(" and ")
              .append(destImageKey)
              .append(" and ")
              .append(type);
    }

    @Override
    public void dither(String srcImgKey, String destImageKey) {
      log.append("Received Inputs ")
              .append(srcImgKey)
              .append(" and ")
              .append(destImageKey);
    }
  }

  @Test
  public void testIMEExtensionImplLoadMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "images/snow.png";
    String destination = "koala";
    String type = "png";
    Reader in = new StringReader("Load " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Received Inputs �PNG " + "and " + destination + " and "
            + type, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplSaveMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "images/snow.png";
    String destination = "koala";
    Reader in = new StringReader("Save " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + destination + " Received Inputs koala"
            + " and png", mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplFlipMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-vertical";
    String type = "vertical-flip";
    Reader in = new StringReader("vertical-flip " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination + " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplGreyScaleMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-greyscale";
    String type = "value-component";
    Reader in = new StringReader("greyscale " + type + " " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination
            + " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplBrightenMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala-R";
    String destination = "koala-R";
    String amount = "50";
    Reader in = new StringReader("brighten " + amount + " " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination
            + " and " + amount, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplCombineMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source1 = "koala-R";
    String source2 = "koala-G";
    String source3 = "koala-B";
    String destination = "koala-R-tint";
    Reader in = new StringReader("rgb-combine " + destination + " " + source1 + " " +
            source2 + " " + source3);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source1 + " Image Exists " + source2 + " Image Exists "
            + source3 + " Received Inputs " + source1 + " and " + source2 + " and " + source3
            + " and " + destination, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplFilterBlurMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-blur";
    String type = "blur";
    Reader in = new StringReader(type + " " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination + " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplFilterSharpenMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-sharpen";
    String type = "sharpen";
    Reader in = new StringReader(type + " " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination + " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplTransformSepiaMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-transform";
    String type = "transform";
    Reader in = new StringReader("transformation " + type + " " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination + " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplTransformGreyScaleMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-greyscale";
    String type = "greyscale";
    Reader in = new StringReader("transformation " + type + " " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination + " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEExtensionImplDitherMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModelExtension model = new MockIMEExtensionImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "Koala";
    String destination = "koala-Dither";
    Reader in = new StringReader("Dither " + source + " " + destination);
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("Image Exists Koala Received Inputs Koala and koala-Dither"
            + " and greyscaleReceived Inputs koala-Dither and koala-Dither", mockLog.toString());
  }

  @Test
  public void testCustomScriptFile() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run res/script.txt");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();

    assertEquals("'load' Operation Successful."
            + " 'vertical-flip' Operation Successful. 'horizontal-flip' Operation Successful. "
            + "'brighten' Operation Successful. 'brighten' Operation Successful. "
            + "'greyscale' Operation Successful. 'save' Operation Successful."
            + " Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testAssignmentScriptFile() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run res/scriptCompy.txt");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'brighten' Operation Successful." +
            " 'vertical-flip' Operation Successful."
            + " 'horizontal-flip' Operation Successful."
            + " 'greyscale' Operation Successful."
            + " 'save' Operation Successful."
            + " Saved image successfully.'save' Operation Successful."
            + " Saved image successfully.'load' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully."
            + "'rgb-split' Operation Successful."
            + " 'brighten' Operation Successful."
            + " 'rgb-combine' Operation Successful."
            + " 'save' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testLoadViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. ", out.toString());
  }

  @Test
  public void testLoadViaCommandLine2() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. ", out.toString());
  }

  @Test
  public void testSaveViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("save images/koala.ppm koala9");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'save' Operation Failed. Please load image first", out.toString());
  }

  @Test
  public void testLoadSaveViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala" +
            "\n save images/koala6.ppm koala");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBSplitViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. 'rgb-split' Operation Successful. ",
            out.toString());
  }

  @Test
  public void testHorizontalFlipViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n#flip koala horizontally\n"
            + "horizontal-flip koala koala-horizontal"
            + "\nsave images/koala-horizontal.ppm koala-horizontal");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'horizontal-flip' Operation Successful. "
            + "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testVerticalFlipViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n#flip koala vertically\n"
            + "vertical-flip koala koala-vertical"
            + "\nsave images/koala-vertical.ppm koala-vertical");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'vertical-flip' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testHorizontalFlipOfImageAfterItsVerticalFlipViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala" +
            "\n#flip koala VerticalHorizontally\n" +
            "vertical-flip koala koala-vertical" +
            "\nsave images/koala-vertical.ppm koala-vertical" +
            "\nhorizontal-flip koala-vertical koala-vertical-horizontal" +
            "\nsave images/koala-vertical-horizontal.ppm koala-vertical-horizontal");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. " +
            "'vertical-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully." +
            "'horizontal-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBCombineAfterSplitViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B " +
            "\n#combine them back, but by using the brightened red we get a red tint\n" +
            "rgb-combine koala-R-tint koala-R koala-G koala-B\n" +
            "save images/koala-red-tint.ppm koala-R-tint");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'rgb-split' Operation Successful. "
            + "'rgb-combine' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testOverRideCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala" +
            "\nload images/snow.ppm koala_2" +
            "\nsave images/koala3.ppm koala_2");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. 'load' Operation Successful." +
            " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testLoadSaveWhenCommandsAreNotSeperatedByNextLineViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala" +
            " save images/koala6.ppm koala");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Failed. Too many number of arguments", out.toString());
  }


  @Test
  public void testSaveViaCommandLine2() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("save images/koala.ppm koala10");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'save' Operation Failed. Please load image first", out.toString());
  }

  @Test
  public void testLoadSaveViaCommandLine2() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala " +
            "\n save images/koala6.ppm koala");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'save' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testBrightenedCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten 10 koala koala-brighter"
            + "\n save images/koala-brighter.ppm koala-brighter");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testImageDarkerCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten -20 koala koala-brighter"
            + "\n save images/koala-darker.ppm koala-brighter");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
                    + " 'save' Operation Successful."
                    + " Saved image successfully.",
            out.toString());
  }

  @Test
  public void testSharpenFilterCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n sharpen koala Koala-sharpen"
            + "\n save images/Koala-sharpen.ppm Koala-sharpen");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'sharpen' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testBlurFilterCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n blur koala Koala-blur"
            + "\n save images/Koala-blur.ppm Koala-blur");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'blur' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testSepiaTransformCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n transformation transform koala Koala-transform"
            + "\n save images/Koala-transform.ppm Koala-transform");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'transformation' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testGreyScaleTransformCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n transformation greyscale koala Koala-greyscale"
            + "\n save images/Koala-greyscale.ppm Koala-greyscale");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'transformation' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testDitherCommandViaCommandLine() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/koala2.ppm koala"
            + "\n dither koala Koala-dither"
            + "\n save images/Koala-dither.ppm Koala-dither");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'dither' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testAssignmentScriptFileWithAllCommands() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run res/script_3292023.txt");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'brighten' Operation Successful. "
            + "'brighten' Operation Successful. "
            + "'vertical-flip' Operation Successful. "
            + "'horizontal-flip' Operation Successful. "
            + "'greyscale' Operation Successful. "
            + "'greyscale' Operation Successful. "
            + "'greyscale' Operation Successful. "
            + "'greyscale' Operation Successful. "
            + "'greyscale' Operation Successful. "
            + "'greyscale' Operation Successful. "
            + "'rgb-split' Operation Successful. "
            + "'brighten' Operation Successful. "
            + "'rgb-combine' Operation Successful. "
            + "'sharpen' Operation Successful. "
            + "'blur' Operation Successful. "
            + "'transformation' Operation Successful. "
            + "'transformation' Operation Successful. "
            + "'dither' Operation Successful. "
            + "'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBSplitViaCommandLineForPNGImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. 'rgb-split' Operation Successful. ",
            out.toString());
  }

  @Test
  public void testHorizontalFlipViaCommandLineForPNGImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n#flip koala horizontally\n"
            + "horizontal-flip koala koala-horizontal"
            + "\nsave images/koala-horizontal.ppm koala-horizontal");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'horizontal-flip' Operation Successful. "
            + "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testVerticalFlipViaCommandLineForPNGImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n#flip koala vertically\n"
            + "vertical-flip koala koala-vertical"
            + "\nsave images/koala-vertical.ppm koala-vertical");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'vertical-flip' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testHorizontalFlipOfImageAfterItsVerticalFlipViaCommandLineForPNGImage()
          throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala" +
            "\n#flip koala VerticalHorizontally\n" +
            "vertical-flip koala koala-vertical" +
            "\nsave images/koala-vertical.ppm koala-vertical" +
            "\nhorizontal-flip koala-vertical koala-vertical-horizontal" +
            "\nsave images/koala-vertical-horizontal.ppm koala-vertical-horizontal");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. " +
            "'vertical-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully." +
            "'horizontal-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBSplitViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B " +
            "\n#combine them back, but by using the brightened red we get a red tint\n" +
            "rgb-combine koala-R-tint koala-R koala-G koala-B\n" +
            "save images/koala-red-tint.ppm koala-R-tint");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'rgb-split' Operation Successful. "
            + "'rgb-combine' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testOverRideCommandViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala" +
            "\nload images/snow.ppm koala_2" +
            "\nsave images/koala3.ppm koala_2");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. 'load' Operation Successful." +
            " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testLoadSaveWhenCommandsAreNotSeperatedByNextLineViaCommandLineForPNGImage()
          throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala" +
            " save images/koala6.ppm koala");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Failed. Too many number of arguments", out.toString());
  }


  @Test
  public void testSaveViaCommandLine2ForPNGImageSavingAsPPM() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("save images/snow.png koala10");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'save' Operation Failed. Please load image first", out.toString());
  }

  @Test
  public void testLoadSaveViaCommandLine2ForPNGImageSavingAsPPM() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala " +
            "\n save images/koala6.ppm koala");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'save' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testBrightenedCommandViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten 10 koala koala-brighter"
            + "\n save images/koala-brighter.ppm koala-brighter");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testImageDarkerCommandViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten -20 koala koala-brighter"
            + "\n save images/koala-darker.ppm koala-brighter");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
                    + " 'save' Operation Successful."
                    + " Saved image successfully.",
            out.toString());
  }

  @Test
  public void testSharpenFilterCommandViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n sharpen koala Koala-sharpen"
            + "\n save images/Koala-sharpen.ppm Koala-sharpen");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'sharpen' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testBlurFilterCommandViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n blur koala Koala-blur"
            + "\n save images/Koala-blur.ppm Koala-blur");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'blur' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testSepiaTransformCommandViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n transformation transform koala Koala-transform"
            + "\n save images/Koala-transform.ppm Koala-transform");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'transformation' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testGreyScaleTransformCommandViaCommandLineForPNGImageSavingAsPPM()
          throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n transformation greyscale koala Koala-greyscale"
            + "\n save images/Koala-greyscale.ppm Koala-greyscale");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'transformation' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testDitherCommandViaCommandLineForPNGImageSavingAsPPM() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/snow.png koala"
            + "\n dither koala Koala-dither"
            + "\n save images/Koala-dither.ppm Koala-dither");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'dither' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBSplitViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. 'rgb-split' Operation Successful. ",
            out.toString());
  }

  @Test
  public void testHorizontalFlipViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n#flip koala horizontally\n"
            + "horizontal-flip koala koala-horizontal"
            + "\nsave images/koala-horizontal.bmp koala-horizontal");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'horizontal-flip' Operation Successful. "
            + "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testVerticalFlipViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n#flip koala vertically\n"
            + "vertical-flip koala koala-vertical"
            + "\nsave images/koala-vertical.bmp koala-vertical");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'vertical-flip' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testHorizontalFlipOfImageAfterItsVerticalFlipViaCommandLineForBMPImage()
          throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala" +
            "\n#flip koala VerticalHorizontally\n" +
            "vertical-flip koala koala-vertical" +
            "\nsave images/koala-vertical.ppm koala-vertical" +
            "\nhorizontal-flip koala-vertical koala-vertical-horizontal" +
            "\nsave images/koala-vertical-horizontal.bmp koala-vertical-horizontal");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. " +
            "'vertical-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully." +
            "'horizontal-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBCombineAfterSplitViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B " +
            "\n#combine them back, but by using the brightened red we get a red tint\n" +
            "rgb-combine koala-R-tint koala-R koala-G koala-B\n" +
            "save images/koala-red-tint.bmp koala-R-tint");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'rgb-split' Operation Successful. "
            + "'rgb-combine' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testOverRideCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala" +
            "\nload images/snow.ppm koala_2" +
            "\nsave images/koala3.bmp koala_2");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Successful. 'load' Operation Successful." +
            " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testLoadSaveWhenCommandsAreNotSeperatedByNextLineViaCommandLineForBMPImage()
          throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala" +
            " save images/koala6.bmp koala");
    IMEControllerCommandImpl iMEController = new IMEControllerCommandImpl(model, view, in, out);
    iMEController.executeIMEController();
    assertEquals("'load' Operation Failed. Too many number of arguments", out.toString());
  }


  @Test
  public void testSaveViaCommandLine2ForBMPImage() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("save images/Koala.bmp koala10");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'save' Operation Failed. Please load image first", out.toString());
  }

  @Test
  public void testLoadSaveViaCommandLine2ForBMPImage() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala " +
            "\n save images/koala6.bmp koala");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'save' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testBrightenedCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten 10 koala koala-brighter"
            + "\n save images/koala-brighter.bmp koala-brighter");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testImageDarkerCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten -20 koala koala-brighter"
            + "\n save images/koala-darker.bmp koala-brighter");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
                    + " 'save' Operation Successful."
                    + " Saved image successfully.",
            out.toString());
  }

  @Test
  public void testSharpenFilterCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n sharpen koala Koala-sharpen"
            + "\n save images/Koala-sharpen.bmp Koala-sharpen");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'sharpen' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testBlurFilterCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n blur koala Koala-blur"
            + "\n save images/Koala-blur.bmp Koala-blur");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'blur' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testSepiaTransformCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n transformation transform koala Koala-transform"
            + "\n save images/Koala-transform.bmp Koala-transform");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'transformation' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testGreyScaleTransformCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n transformation greyscale koala Koala-greyscale"
            + "\n save images/Koala-greyscale.bmp Koala-greyscale");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'transformation' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testDitherCommandViaCommandLineForBMPImage() throws IOException {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.bmp koala"
            + "\n dither koala Koala-dither"
            + "\n save images/Koala-dither.bmp Koala-dither");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'dither' Operation Successful. 'save' Operation Successful. "
            + "Saved image successfully.", out.toString());
  }

  @Test
  public void testAssignmentScriptFileWithValidAndInvalidCommands() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run res/script4.txt");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'brighten' Operation Failed. "
            + "Too many number of arguments'vertical-flip' Operation Failed. "
            + "Too many number of arguments'horizontal-flip' Operation Failed. "
            + "Too many number of arguments'greyscale' Operation Successful. "
            + "'rgb-split' Operation Successful. 'rgb-combine' Operation Successful. "
            + "'load' Operation Failed. java.io.FileNotFoundException: snow.ppm "
            + "(The system cannot find the file specified)", out.toString());
  }

  @Test
  public void testAssignmentScriptFileWithAllInvalidCommands() throws Exception {
    IMEModelExtension model = new IMEModelExtensionImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run res/script2.txt");
    IMEControllerCommandImpl controller6 = new IMEControllerCommandImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Failed. "
            + "Too many number of arguments'vertical-flip' Operation Failed."
            + " Too many number of arguments'horizontal-flip' Operation Failed."
            + " Too many number of arguments'greyscale' Operation Successful."
            + " 'rgb-split' Operation Failed. Too few number of "
            + "arguments'rgb-combine' Operation Failed. Too few number of"
            + " arguments'blur' Operation Failed. "
            + "Too many number of arguments'sharpen' Operation Failed. "
            + "Too few number of arguments'save' Operation Failed. "
            + "Please load image first", out.toString());
  }

}
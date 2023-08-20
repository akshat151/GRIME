import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.IMEPPMControllerImpl;
import model.IMEModel;
import model.IMEPPMModelImpl;
import view.IMEViewer;
import view.IMEViewerImpl;

import static org.junit.Assert.assertEquals;

/**
 * Junit test class to test IMEPPM operations.
 */
public class IMEPPMControllerImplTest {

  static class MockIMEModelImpl implements IMEModel {

    private final StringBuilder log;

    public MockIMEModelImpl(StringBuilder log) {
      this.log = log;
    }

    @Override
    public boolean load(String filename, String destImageKey) {
      log.append("Received Inputs ")
              .append(filename)
              .append(" and ")
              .append(destImageKey);
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
  }

  @Test
  public void testIMEModelImplLoadMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModel model = new MockIMEModelImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "images/Koala.ppm";
    String destination = "koala";
    Reader in = new StringReader("Load " + source + " " + destination);
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("Received Inputs " + source + " and " + destination, mockLog.toString());
  }

  @Test
  public void testIMEModelImplSaveMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModel model = new MockIMEModelImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "images/Koala.ppm";
    String destination = "koala";
    Reader in = new StringReader("Save " + source + " " + destination);
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("Image Exists " + destination + " Received Inputs " + source + " and "
            + destination, mockLog.toString());
  }

  @Test
  public void testIMEModelImplFlipMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModel model = new MockIMEModelImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-vertical";
    String type = "vertical-flip";
    Reader in = new StringReader("vertical-flip " + source + " " + destination);
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination +
            " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEModelImplGreyScaleMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModel model = new MockIMEModelImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala";
    String destination = "koala-greyscale";
    String type = "value-component";
    Reader in = new StringReader("greyscale " + type + " " + source + " " + destination);
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination +
            " and " + type, mockLog.toString());
  }

  @Test
  public void testIMEModelImplBrightenMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModel model = new MockIMEModelImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source = "koala-R";
    String destination = "koala-R";
    String amount = "50";
    Reader in = new StringReader("brighten " + amount + " " + source + " " + destination);
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("Image Exists " + source + " Received Inputs " + source + " and "
            + destination
            + " and " + amount, mockLog.toString());
  }

  @Test
  public void testIMEModelImplCombineMethodUsingMock() throws Exception {
    StringBuilder mockLog = new StringBuilder();
    IMEModel model = new MockIMEModelImpl(mockLog);
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    String source1 = "koala-R";
    String source2 = "koala-G";
    String source3 = "koala-B";
    String destination = "koala-R-tint";
    Reader in = new StringReader("rgb-combine " + destination + " " + source1 + " " +
            source2 + " " + source3);
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("Image Exists " + source1 + " Image Exists " + source2 + " Image Exists "
            + source3 + " Received Inputs " + source1 + " and " + source2 + " and " + source3
            + " and " + destination, mockLog.toString());
  }

  @Test
  public void testCustomScriptFile() throws Exception {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run res/script.txt");
    IMEPPMControllerImpl controller6 = new IMEPPMControllerImpl(model, view, in, out);
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
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run res/scriptCompy.txt");
    IMEPPMControllerImpl controller6 = new IMEPPMControllerImpl(model, view, in, out);
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
            + " Saved image successfully."
            + "'brighten' Operation Successful."
            + " 'rgb-combine' Operation Successful."
            + " 'save' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testLoadViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. ", out.toString());
  }

  @Test
  public void testLoadViaCommandLine2() throws Exception {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala");
    IMEPPMControllerImpl controller6 = new IMEPPMControllerImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful. ", out.toString());
  }

  @Test
  public void testSaveViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("save images/koala.ppm koala");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'save' Operation Failed. Please load image first", out.toString());
  }

  @Test
  public void testLoadSaveViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            "\n save images/koala6.ppm koala");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. "
            + "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBSplitViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. 'rgb-split' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testHorizontalFlipViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            "\n#flip koala horizontally\n" +
            "horizontal-flip koala koala-horizontal" +
            "\nsave images/koala-horizontal.ppm koala-horizontal");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. " +
            "'horizontal-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testVerticalFlipViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            "\n#flip koala vertically\n" +
            "vertical-flip koala koala-vertical" +
            "\nsave images/koala-vertical.ppm koala-vertical");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. " +
            "'vertical-flip' Operation Successful. 'save' Operation Successful. " +
            "Saved image successfully.", out.toString());
  }

  @Test
  public void testHorizontalFlipOfImageAfterItsVerticalFlipViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            "\n#flip koala VerticalHorizontally\n" +
            "vertical-flip koala koala-vertical" +
            "\nsave images/koala-vertical.ppm koala-vertical" +
            "\nhorizontal-flip koala-vertical koala-vertical-horizontal" +
            "\nsave images/koala-vertical-horizontal.ppm koala-vertical-horizontal");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. " +
            "'vertical-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully." +
            "'horizontal-flip' Operation Successful. " +
            "'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testRGBCombineAfterSplitViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            "\n #give the koala a red tint\n" +
            "rgb-split koala koala-R koala-G koala-B " +
            "\n#combine them back, but by using the brightened red we get a red tint\n" +
            "rgb-combine koala-R-tint koala-R koala-G koala-B\n" +
            "save images/koala-red-tint.ppm koala-R-tint");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'rgb-split' Operation Successful." + " Saved image successfully."
            + "'rgb-combine' Operation Successful." + " 'save' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testOverRideCommandViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            "\nload images/snow.ppm koala_2" +
            "\nsave images/koala3.ppm koala_2");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. 'load' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testLoadSaveWhenCommandsAreNotSeperatedByNextLineViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala" +
            " save images/koala6.ppm koala");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Failed. Too many number of arguments", out.toString());
  }


  @Test
  public void testSaveViaCommandLine2() throws Exception {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("save images/koala.ppm koala");
    IMEPPMControllerImpl controller6 = new IMEPPMControllerImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'save' Operation Failed. Please load image first", out.toString());
  }

  @Test
  public void testLoadSaveViaCommandLine2() throws Exception {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load images/koala2.ppm koala " +
            "\n save images/koala6.ppm koala");
    IMEPPMControllerImpl controller6 = new IMEPPMControllerImpl(model, view, in, out);
    controller6.executeIMEController();
    assertEquals("'load' Operation Successful."
            + " 'save' Operation Successful."
            + " Saved image successfully.", out.toString());
  }

  @Test
  public void testBrightenedCommandViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten 10 koala koala-brighter"
            + "\n save images/koala-brighter.ppm koala-brighter");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
            + " 'save' Operation Successful. Saved image successfully.", out.toString());
  }

  @Test
  public void testImageDarkerCommandViaCommandLine() throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("Load images/Koala.ppm koala"
            + "\n #brighten koala by adding 10\n"
            + "brighten -20 koala koala-brighter"
            + "\n save images/koala-darker.ppm koala-brighter");
    IMEPPMControllerImpl iMEPPMControllerImpl6 = new IMEPPMControllerImpl(model, view, in, out);
    iMEPPMControllerImpl6.executeIMEController();
    assertEquals("'load' Operation Successful. 'brighten' Operation Successful."
                    + " 'save' Operation Successful."
                    + " Saved image successfully.",
            out.toString());
  }

}
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all the constant variables that are used at multiple places
 * in the entire project. model.Constants like the messages to preview, commands, etc.
 */
public class Constants {

  /**
   * Message to display.
   */
  final public static String LIST = "\n\nOperations: \n\n"
          + "loadImage [image-path] [destImg]\n"
          + "brighten [brightnessConstant] [srcImg] [destImg]\n"
          + "vertical-flip [srcImg] [destImg]\n"
          + "horizontal-flip [srcImg] [destImg]\n"
          + "greyscale [component-type] [srcImg] [destImg]\n"
          + "rgb-split [srcImg] [destImgRed] [destImgGreen] [destImgBlue]\n"
          + "rgb-combine [destImgRed] [srcImg] [srcImg] [srcImg]\n"
          + "sharpen [srcImg] [destImg]\n"
          + "blur [srcImg] [destImg]\n"
          + "transformation [transformationType] [srcImg] [destImg]\n"
          + "dither [srcImg] [destImg]\n"
          + "run [scriptFilepath]\n\n"
          + "Enter 0 to exit.\n";

  final public static String INVALID_PPM = "Not a PPM file, please upload a file "
          + "with PPM extension.";
  final public static String INVALID_PPM_DEST = "Invalid destination path, "
          + "does not lead to a PPM file, "
          + "please enter filename with PPM extension (.ppm).";
  final public static String ENTER_VALID_CMD = "PLease enter a valid command (or) comment.";
  final public static String MAIN_MENU = "\nEnter 'menu' (without quotes) to preview "
          + "the list of commands."
          + "\nEnter 0 to exit.\nEnter commands below:\n";
  final public static String THANK_YOU = "\nThank you.";
  final public static String SAVED_SUCCESS = "Saved image successfully.";
  final public static String SAVED_FAILURE = "Save image Failed. Please Enter Valid image type";
  final public static String SPLIT_SUCCESS = "Saved image successfully.";
  final public static String INVALID_CMD = "Invalid command entered.";
  final public static String INVALID_COMP = "Invalid component type.";
  final public static String INVALID_TRANS_TYPE = "Invalid transformation type.";

  final public static String TOO_FEW_ARGS = "Too few number of arguments";
  final public static String TOO_MANY_ARGS = "Too many number of arguments";
  final public static String IMG_NOT_LOADED = "Please load image first";
  final public static String EMPTY_MSG = "";
  final public static String RECHECK_CMD = "Please recheck command.";
  final public static String ONE_SPACE = " ";


  /**
   * Commands in the menu.
   */
  final public static String LOAD = "load";
  final public static String SAVE = "save";
  final public static String GREYSCALE = "greyscale";
  final public static String HORIZONTAL = "horizontal-flip";
  final public static String VERTICAL = "vertical-flip";
  final public static String BRIGHTEN = "brighten";
  final public static String SPLIT = "rgb-split";
  final public static String COMBINE = "rgb-combine";
  final public static String RUN = "run";
  final public static String RUNSCRIPT = "run-script";
  final public static String MENU = "menu";
  final public static String EXIT = "0";
  final public static String BLUR = "blur";
  final public static String SHARPEN = "sharpen";
  final public static String TRANSFORMATION = "transformation";
  final public static String SEPIA = "sepia";
  final public static String DITHER = "dither";


  /**
   * No. of arguments required by each command.
   */
  final public static HashMap<String, Integer> ARGS_REQUIRED = new HashMap<>() {{
        put(LOAD, 3);
        put(SAVE, 3);
        put(GREYSCALE, 4);
        put(HORIZONTAL, 3);
        put(VERTICAL, 3);
        put(BRIGHTEN, 4);
        put(BLUR, 3);
        put(SHARPEN, 3);
        put(TRANSFORMATION, 4);
        put(DITHER, 3);
        put(SPLIT, 5);
        put(COMBINE, 5);
        put(RUN, 2);
    }};

  /**
   * Components types.
   */
  final public static String C_RED = "red-component";
  final public static String C_GREEN = "green-component";
  final public static String C_BLUE = "blue-component";
  final public static String C_VALUE = "value-component";
  final public static String C_INTENSITY = "intensity-component";
  final public static String C_LUMA = "luma-component";

  /**
   * Type of Components used by greyscale.
   */
  final public static List<String> COMPONENT_TYPES = new ArrayList<>() {{
        add(C_RED);
        add(C_GREEN);
        add(C_BLUE);
        add(C_VALUE);
        add(C_INTENSITY);
        add(C_LUMA);
    }};

  /**
   * No. of arguments required by each command.
   */
  final public static HashMap<String, String> RGB_SPLIT = new HashMap<>() {{
        put(C_RED, C_RED);
        put(C_GREEN, C_GREEN);
        put(C_BLUE, C_BLUE);
    }};

  /**
   * Type of Components used by transformers.
   */
  final public static List<String> TRANSFORMATION_TYPES = new ArrayList<>() {{
        add(SEPIA);
        add(GREYSCALE);
    }};


  /**
   * State.
   */
  final public static String SUCCESSFUL = "Successful";
  final public static String FAILED = "Failed";
  final public static String OPERATION = "Operation";


  /**
   * LUMA constants.
   */
  final public static double LUMA_R = 0.2126;
  final public static double LUMA_G = 0.7152;
  final public static double LUMA_B = 0.0722;


  /**
   * Blur matrix constant.
   */
  final public static double[][] BLUR_KERNEL = {
          {0.0625, 0.125, 0.0625},
          {0.125, 0.250, 0.125},
          {0.0625, 0.125, 0.0625}
  };

  /**
   * Sharpening matrix constant.
   */
  final public static double[][] SHARPEN_KERNEL = {
          {-0.125, -0.125, -0.125, -0.125, -0.125},
          {-0.125, 0.250, 0.250, 0.250, -0.125},
          {-0.125, 0.250, 1, 0.250, -0.125},
          {-0.125, 0.250, 0.250, 0.250, -0.125},
          {-0.125, -0.125, -0.125, -0.125, -0.125},
  };

  /**
   * Greyscale matrix.
   */
  final public static double[][] GREYSCALE_MATRIX = {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
  };

  /**
   * Sepia matrix.
   */
  final public static double[][] SEPIA_MATRIX = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131},
  };

  /**
   * Dither constants.
   */
  final public static double RIGHT_CONS = 0.4375;
  final public static double BELOW_LEFT_CONS = 0.1875;
  final public static double BELOW_CONS = 0.3125;
  final public static double BELOW_RIGHT_CONS = 0.0625;

  /**
   * Pixel positions.
   */
  public enum PixelPos {
    RIGHT,
    BELOW_LEFT,
    BELOW,
    BELOW_RIGHT
  }

  /**
   * Token for PPM images.
   */
  final public static String PPM_TOKEN = "P3";


  /**
   * Channels.
   */
  final public static String H_RED = "RED";
  final public static String H_GREEN = "GREEN";
  final public static String H_BLUE = "BLUE";
  final public static String H_INTENSITY = "VALUE";

  /**
   * Graph labels.
   */

  final public static String GRAPH_LABEL_VALUE = "Value";
  final public static String GRAPH_LABEL_FREQ = "Frequency";

  /**
   * GUI Miscellaneous.
   */
  final public static int G_BINS = 256;
  final public static int G_WIDTH = G_BINS * 4;
  final public static int G_HEIGHT = 400;
  final public static String G_TITLE = "GRIME";
  final public static String G_MAIN_TITLE = "GRIME: Graphical Image Manipulation and Enhancement";
  final public static String G_MSG = "GRIME_Message";


  /**
   * GUI menu items.
   */

  final public static String G_MENU_FILE = "File";
  final public static String G_MENU_LOAD = "Load";
  final public static String G_MENU_SAVE = "Save";
  final public static String G_MENU_EXIT = "Exit";
  final public static String G_MENU_EDIT = "Edit";
  final public static String G_MENU_UNDO = "Undo";

  /**
   * GUI Button Text.
   */

  final public static String G_FLP_BTN = "Flip";
  final public static String G_GRC_BTN = "Greyscale-Component";
  final public static String G_FTR_BTN = "Filter";
  final public static String G_CLT_BTN = "Color Transform";
  final public static String G_DIT_BTN = "Dither";
  final public static String G_BRT_BTN = "Brighten";
  final public static String G_SPT_BTN = "RGB-Split";
  final public static String G_COM_BTN = "RGB-Combine";

  /**
   * GUI Grid.
   */

  final public static int G_GRD_ROWS = 2;
  final public static int G_GRD_COLS = 4;
  final public static int G_GRD_HGAP = 10;
  final public static int G_GRD_VGAP = 10;

  /**
   * GUI Messages.
   */

  final public static String G_OP_FAIL = "Operation Failed!";
  final public static String G_INV_BRT = "Invalid input, please enter an integer.";

  final public static String G_LOD_FAIL = "Load Image Failed! Please Try Again";
  final public static String G_SAV_FAIL = "Save Operation Failed!";

  final public static String G_BRT_MSG = "Enter brightness, constant value: ";

  /**
   * GUI Operation specific messages.
   */

  final public static String G_FLP_DIR = "Choose flip direction.";
  final public static String G_FLP_TYP = "Directions.";
  final public static String G_GRY_COM = "Select component.";
  final public static String G_GRY_TYP = "Greyscale Components.";
  final public static String G_FLT_SEL = "Select filter type.";
  final public static String G_FLT_TYP = "Filter types.";
  final public static String G_TRN_SEL = "Select transformation type.";
  final public static String G_TRN_TYP = "Transformation types.";
  final public static String G_BRT_SEL = "Enter brightness factor.";
  final public static String G_BRT_VAL = "Brightness Value.";
  final public static String G_BRT_INV = "Invalid input, please enter an integer.";
  final public static String G_COM_MSG = "Select the three images one-by-one "
          + "that you want to combine.\n"
          + "Follow this order of loading: Red -> Green -> Blue";
  final public static String G_SPL_MSG = "Select save location for color channels.\n"
          + "Follow this order of saving: Green -> Blue -> Red";
  final public static String G_SPL_SUC = "Images split successfully. Load saved images to view.";
  final public static String G_INV_TYP = "Please select valid image file-type.";
  final public static String G_LOD_IMG = "Please load image.";

  /**
   * Supported image file-types.
   */

  final public static String JPG = "jpg";
  final public static String PNG = "png";
  final public static String JPEG = "jpeg";
  final public static String BMP = "bmp";
  final public static String PPM = "ppm";

  /**
   * Image key.
   */
  final public static String IMG_KEY = "image";

  /**
   * Over-the-project.
   */
  final public static String CORR_ARGS = "Please provide correct arguments for using "
          + "the jar file.";
  final public static String MAIN_ARGS_FILE = "-file";
  final public static String MAIN_ARGS_TEXT = "-text";


}

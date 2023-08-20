package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import controller.fileoperations.FileReaderImpl;
import controller.fileoperations.FileWriteOperations;
import controller.fileoperations.FileWriterImpl;
import controller.fileoperations.PPMFileWriterImpl;
import model.Constants;
import model.IGRIMEModel;
import model.IGRIMEModelPPMImpl;
import view.IGRIMEViewer;

/**
 * This class represents the controller for GUI program.
 * This controller handles communication between GUI and the model.
 */
public class GRIMEController implements Features {

  private final IGRIMEModel model;
  private final IGRIMEModel ppmModel;
  private IGRIMEViewer view;
  private String type;

  /**
   * Constructor of the controller, used to initialize the values.
   *
   * @param m model which is passed.
   */
  public GRIMEController(IGRIMEModel m) {
    model = m;
    ppmModel = new IGRIMEModelPPMImpl();
    type = Constants.EMPTY_MSG;
  }

  /**
   * This method is used to add all the GUI features.
   *
   * @param v viewer of the GUI.
   */
  public void setView(IGRIMEViewer v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  @Override
  public void loadImage() throws IOException {
    String file = view.loadImage();
    if (!file.equals(Constants.EMPTY_MSG)) {
      if (isSupportedImageFile(file)) {
        String[] commandArgs = file.split("\\\\");
        type = commandArgs[commandArgs.length - 1].split("\\.")[1];
        InputStream fileInputStream = new FileReaderImpl().fileRead(file);
        String destImageKey = Constants.IMG_KEY;
        if (type.equalsIgnoreCase(Constants.PPM)) {
          ppmModel.loadImage(fileInputStream, destImageKey, type);
        } else {
          model.loadImage(fileInputStream, destImageKey, type);
        }
        viewData();
      } else {
        view.displayError(Constants.G_INV_TYP);
      }
    }
  }

  @Override
  public void saveImage() throws IOException {
    if (model.imageExists(Constants.IMG_KEY)) {
      String file = view.selectImageFileToSave();
      if (!file.equals(Constants.EMPTY_MSG)) {
        if (isSupportedImageFile(file)) {
          String[] imageFile = file.split("\\.");
          String imageType = imageFile[1];
          FileWriteOperations writer;
          ByteArrayOutputStream byteArray;
          if (imageType.equalsIgnoreCase(Constants.PPM)) {
            byteArray = ppmModel.saveImage(Constants.IMG_KEY, imageType);
            writer = new PPMFileWriterImpl();
          } else {
            byteArray = model.saveImage(Constants.IMG_KEY, imageType);
            writer = new FileWriterImpl();
          }
          saveImageToFile(file, imageType, byteArray, writer);
        } else {
          view.displayError(Constants.G_INV_TYP);
        }
      }
    } else {
      view.displayError(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void flip() throws IOException {
    if (model.imageExists(Constants.IMG_KEY)) {
      String[] options = {Constants.HORIZONTAL, Constants.VERTICAL};
      String flipType = view.showOptionsDialog(options,
              Constants.G_FLP_DIR,
              Constants.G_FLP_TYP);
      if (!Objects.equals(flipType, Constants.EMPTY_MSG)) {
        model.flip(Constants.IMG_KEY, Constants.IMG_KEY, flipType);
        viewData();
      }
    } else {
      view.displayError(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void greyscale() throws IOException {
    if (model.imageExists(Constants.IMG_KEY)) {
      String[] options = new String[Constants.COMPONENT_TYPES.size()];
      Constants.COMPONENT_TYPES.toArray(options);
      String component = view.showOptionsDialog(options,
              Constants.G_GRY_COM,
              Constants.G_GRY_TYP);
      if (!Objects.equals(component, Constants.EMPTY_MSG)) {
        model.greyscale(Constants.IMG_KEY, Constants.IMG_KEY, component);
        viewData();
      }
    } else {
      view.displayError(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void filter() throws IOException {
    if (model.imageExists(Constants.IMG_KEY)) {
      String[] options = {Constants.BLUR, Constants.SHARPEN};
      String filterType = view.showOptionsDialog(options,
              Constants.G_FLT_SEL,
              Constants.G_FLT_TYP);
      if (!Objects.equals(filterType, Constants.EMPTY_MSG)) {
        model.filter(Constants.IMG_KEY, Constants.IMG_KEY, filterType);
        viewData();
      }
    } else {
      view.displayError(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void transform() throws IOException {
    if (model.imageExists(Constants.IMG_KEY)) {
      String[] options = {Constants.SEPIA, Constants.GREYSCALE};
      String colorTransformationType = view.showOptionsDialog(options,
              Constants.G_TRN_SEL,
              Constants.G_TRN_TYP);
      if (!Objects.equals(colorTransformationType, Constants.EMPTY_MSG)) {
        model.transformation(Constants.IMG_KEY, Constants.IMG_KEY, colorTransformationType);
        viewData();
      }
    } else {
      view.displayError(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void dither() throws IOException {
    if (model.imageExists(Constants.IMG_KEY)) {
      model.dither(Constants.IMG_KEY, Constants.IMG_KEY);
      viewData();
    } else {
      view.displayError(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void brighten() throws IOException {
    if (model.imageExists(Constants.IMG_KEY)) {
      try {
        String brightConstant = view.showInputDialog(
                Constants.G_BRT_SEL,
                Constants.G_BRT_VAL
        );
        int brightnessConstant = Integer.parseInt(brightConstant);
        model.brighten(Constants.IMG_KEY, Constants.IMG_KEY, brightnessConstant);
        viewData();
      } catch (NumberFormatException e) {
        view.displayError(Constants.G_BRT_INV);
      }
    } else {
      view.displayError(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void rgbCombine() throws IOException {
    view.displayMsg(Constants.G_COM_MSG);
    List<String> fileNames = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      String fileName = view.loadImage();
      if (fileName.equals(Constants.EMPTY_MSG)) {
        return;
      } else if (!isSupportedImageFile(fileName)) {
        view.displayError(Constants.G_LOD_IMG);
        return;
      }
      fileNames.add(fileName);
    }

    try {
      List<String> imageKeys = new ArrayList<>();
      int i = 0;
      for (String fileName : fileNames) {
        String imageKey = Constants.IMG_KEY + i;
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (fileExtension.equalsIgnoreCase(Constants.PPM)) {
          ppmModel.loadImage(new FileReaderImpl().fileRead(fileName), imageKey, fileExtension);
        } else {
          model.loadImage(new FileReaderImpl().fileRead(fileName), imageKey, fileExtension);
        }
        imageKeys.add(imageKey);
        i += 1;
      }
      combineAndDisplayImages(imageKeys);
    } catch (IndexOutOfBoundsException e) {
      view.displayMsg(Constants.G_LOD_IMG);
    }
  }

  @Override
  public void exit() {
    view.exit();
  }

  @Override
  public void rgbSplit() throws IOException {
    if (!model.imageExists(Constants.IMG_KEY)) {
      view.displayError(Constants.G_LOD_IMG);
      return;
    }
    view.displayMsg(Constants.G_SPL_MSG);
    for (Map.Entry<String, String> entry : Constants.RGB_SPLIT.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      model.greyscale(Constants.IMG_KEY, key, value);
      if (!saveSplitImageToFile(key)) {
        return;
      }
    }
    view.displayMsg(Constants.G_SPL_SUC);
  }

  private void combineAndDisplayImages(List<String> imageKeys) throws IOException {
    String outputImageKey = Constants.IMG_KEY;
    model.combine(imageKeys.get(0), imageKeys.get(1), imageKeys.get(2), outputImageKey);
    this.type = Constants.PNG;
    view.showImage(model.getImageStream(model.saveImage(outputImageKey, type)));
    view.showHistogram(
            model.getHistogramData(
                    model.getImageStream(model.saveImage(Constants.IMG_KEY, type))
            ));
  }

  private boolean saveSplitImageToFile(String key) throws IOException {
    boolean splitStatus = false;
    String file = view.selectImageFileToSave();
    if (!file.equals(Constants.EMPTY_MSG)) {
      splitStatus = true;
      if (isSupportedImageFile(file)) {
        String[] imageFile = file.split("\\.");
        String imageType = imageFile[1];

        FileWriteOperations writer;
        ByteArrayOutputStream byteArray;
        if (imageType.equalsIgnoreCase(Constants.PPM)) {
          byteArray = ppmModel.saveImage(key, imageType);
          writer = new PPMFileWriterImpl();
        } else {
          byteArray = model.saveImage(key, imageType);
          writer = new FileWriterImpl();
        }
        saveImageToFile(file, imageType, byteArray, writer);
      } else {
        view.displayError(Constants.G_INV_TYP);
        return false;
      }
    }
    return splitStatus;
  }

  private void viewData() throws IOException {
    if (type.equalsIgnoreCase(Constants.PPM)) {
      view.showImage(ppmModel.getImageStream(ppmModel.saveImage(Constants.IMG_KEY, type)));
      view.showHistogram(
              ppmModel.getHistogramData(
                      ppmModel.getImageStream(ppmModel.saveImage(Constants.IMG_KEY, type))
              ));
    } else {
      view.showImage(model.getImageStream(model.saveImage(Constants.IMG_KEY, type)));
      view.showHistogram(
              model.getHistogramData(
                      model.getImageStream(model.saveImage(Constants.IMG_KEY, type))
              ));
    }
  }

  private void saveImageToFile(String file, String imageType, ByteArrayOutputStream byteArray,
                               FileWriteOperations writer) throws IOException {
    boolean saved = writer.writeFile(byteArray, file, imageType);
    if (saved) {
      this.view.displayMsg(Constants.SAVED_SUCCESS);
    } else {
      this.view.displayMsg(Constants.SAVED_FAILURE);
    }
  }

  private boolean isSupportedImageFile(String filePath) {
    if (filePath.split("\\.").length == 2) {
      String type = filePath.split("\\.")[1];
      return (type.equals(Constants.JPG) ||
              type.equals(Constants.JPEG) ||
              type.equals(Constants.BMP) ||
              type.equals(Constants.PNG) ||
              type.equals(Constants.PPM));
    }
    return false;
  }

}

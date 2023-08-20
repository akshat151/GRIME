import java.io.IOException;

import controller.GRIMEController;
import controller.IMEController;
import controller.IMEControllerCommandImpl;
import model.Constants;
import model.IGRIMEModel;
import model.IGRIMEModelImpl;
import model.IMEModelExtension;
import model.IMEModelExtensionImpl;
import view.GrimeView;
import view.IGRIMEViewer;
import view.IMEViewer;
import view.IMEViewerImpl;

/**
 * This class represents the started of the program.
 * This is used to start the program.
 */
public class IMEImplementation {

  /**
   * Driver method; initiates the program.
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 2 && args[0].equals(Constants.MAIN_ARGS_FILE)) {
      IMEModelExtension model = new IMEModelExtensionImpl();
      IMEViewer view = new IMEViewerImpl();
      IMEController controller = new IMEControllerCommandImpl(model, view, args[1]);
    } else if (args.length == 1 && args[0].equals(Constants.MAIN_ARGS_TEXT)) {
      IMEModelExtension model = new IMEModelExtensionImpl();
      IMEViewer view = new IMEViewerImpl();
      IMEController controller = new IMEControllerCommandImpl(model, view);
      controller.executeIMEController();
    } else if (args.length == 0) {
      IGRIMEModel model = new IGRIMEModelImpl();
      GRIMEController grimeController = new GRIMEController(model);
      IGRIMEViewer grimeView = new GrimeView();
      grimeController.setView(grimeView);
    } else {
      System.out.println(Constants.CORR_ARGS);
    }
  }
}

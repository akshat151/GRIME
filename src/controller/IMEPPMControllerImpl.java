package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import model.Constants;
import model.IMEModel;
import model.IMEPPMModelImpl;
import view.IMEViewer;
import view.IMEViewerImpl;

/**
 * This class represents the Controller in this project.
 * This class implements the {@link IMEController} class and is responsible
 * for passing user commands to the model class and perform image editing operations.
 * Furthermore, this class displays the preview messages using the {@link IMEViewer} class.
 */
public class IMEPPMControllerImpl implements IMEController {
  final private IMEModel model;
  final private IMEViewer view;
  private Readable in;
  private Appendable out;


  /**
   * The constructor of this class, used to initialize variables.
   *
   * @param model the {@link IMEModel} class object.
   * @param view  the {@link IMEViewer} class object.
   * @param in    the Readable class object used to read user's inputs.
   * @param out   the Appendable class object used to write messages.
   */
  public IMEPPMControllerImpl(IMEModel model, IMEViewer view, Readable in, Appendable out) {
    this.model = model;
    this.view = view;
    this.in = in;
    this.out = out;
  }

  /**
   * The constructor of this class, used to initialize variables.
   *
   * @param model the {@link IMEModel} class object.
   * @param view  the {@link IMEViewer} class object.
   */
  IMEPPMControllerImpl(IMEModel model, IMEViewer view) {
    this.model = model;
    this.view = view;
  }

  /**
   * The main file of the program.
   *
   * @param args the arguments of main.
   * @throws IOException if something goes wrong.
   */
  public static void main(String[] args) throws IOException {
    IMEModel model = new IMEPPMModelImpl();
    IMEViewer view = new IMEViewerImpl();
    IMEController controller = new IMEPPMControllerImpl(model, view);
    controller.executeIMEController();
  }

  @Override
  public void executeIMEController() throws IOException {
    this.view.displayMsg(Constants.MAIN_MENU);
    handleCommandLine();
  }

  // helper
  private void handleCommandLine() throws IOException {
    Scanner input;
    //for running the program via test as well as using main method of controller
    if (this.in == null) {
      input = new Scanner(System.in);
    } else {
      input = new Scanner(this.in);
    }

    while (true) {
      if (input.hasNextLine()) {
        String actions = input.nextLine().trim();
        if (actions.length() > 0) {
          if (actions.charAt(0) != '#') {
            runCommand(actions.split("\\s+"));
          }
          if (actions.equals("0")) {
            this.view.displayMsg(Constants.THANK_YOU);
            break;
          }
        } else {
          view.displayMsg(Constants.ENTER_VALID_CMD);
        }
      } else {
        return;
      }
    }
  }

  // helper
  private void handleScriptFile(String filepath) throws IOException {
    InputStream inputStream;
    try {
      inputStream = new FileInputStream(filepath);
    } catch (IOException e) {
      this.view.displayOpMsg(-1, out, Constants.RUNSCRIPT, e.getMessage());
      return;
    }

    // scanning through script file
    boolean run = true;
    try {
      Scanner scanner = new Scanner(inputStream);
      while (run && scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.length() > 0) {
          if (line.charAt(0) != '#') {
            run = runCommand(line.split("\\s+"));
          }
        }
      }
    } catch (IOException e) {
      this.view.displayOpMsg(-1, out, Constants.RUNSCRIPT, e.getMessage());
    }
  }

  // helper
  private boolean runCommand(String[] cmd) throws IOException {
    String action = cmd[0].toLowerCase();
    try {
      switch (action) {
        case Constants.LOAD:
          if (isValid(action, cmd, -1)) {
            boolean loadedImage = model.load(cmd[1], cmd[2]);
            if (loadedImage) {
              this.view.displayOpMsg(1, out, action, Constants.EMPTY_MSG);
            } else {
              this.view.displayOpMsg(-1, out, action, Constants.EMPTY_MSG);
            }
          }
          return true;

        case Constants.SAVE:
          if (isValid(action, cmd, 2)) {
            boolean saved = model.save(cmd[1], cmd[2]);
            if (saved) {
              this.view.displayOpMsg(1, out, action, Constants.SAVED_SUCCESS);
            }
          }
          return true;
        case Constants.GREYSCALE:
          if (Constants.COMPONENT_TYPES.contains(cmd[1])) {
            if (isValid(action, cmd, 2)) {
              model.greyscale(cmd[2], cmd[3], cmd[1]);
              this.view.displayOpMsg(1, out, action, Constants.EMPTY_MSG);
            }
          } else {
            view.displayMsg(Constants.INVALID_COMP);
          }
          return true;
        case Constants.HORIZONTAL:
        case Constants.VERTICAL:
          if (isValid(action, cmd, 1)) {
            model.flip(cmd[1], cmd[2], action);
            this.view.displayOpMsg(1, out, action, Constants.EMPTY_MSG);
          }
          return true;
        case Constants.BRIGHTEN:
          if (isValid(action, cmd, 2)) {
            int brightness = Integer.parseInt(cmd[1]);
            model.brighten(cmd[2], cmd[3], brightness);
            this.view.displayOpMsg(1, out, action, Constants.EMPTY_MSG);
          }
          return true;
        case Constants.SPLIT:
          if (isValid(action, cmd, 1)) {
            model.greyscale(cmd[1], cmd[2], Constants.C_RED);
            model.greyscale(cmd[1], cmd[3], Constants.C_GREEN);
            model.greyscale(cmd[1], cmd[4], Constants.C_BLUE);
            this.view.displayOpMsg(1, out, action, Constants.SPLIT_SUCCESS);
          }
          return true;
        case Constants.COMBINE:
          if (isValid(action, cmd, 2) &&
                  isValid(action, cmd, 3) &&
                  isValid(action, cmd, 4)) {
            model.combine(cmd[2], cmd[3], cmd[4], cmd[1]);
            this.view.displayOpMsg(1, out, action, Constants.EMPTY_MSG);
          }
          return true;
        case Constants.RUN:
          if (isValid(action, cmd, -1)) {
            handleScriptFile(cmd[1]);
          }
          return true;
        case Constants.MENU:
          view.displayMsg(Constants.LIST);
          return true;
        case Constants.EXIT:
          return false;
        default:
          this.view.displayOpMsg(-1, out, action, Constants.INVALID_CMD);
          return true;
      }
    } catch (Exception e) {
      view.displayOpMsg(-1, out, action, e.getMessage());
      return false;
    }
  }

  // helper
  private boolean isValid(String action, String[] cmd, int saveLocCmdIndex) throws IOException {
    int state = -1;
    if (cmd.length > Constants.ARGS_REQUIRED.get(action)) {
      this.view.displayOpMsg(state, out, action, Constants.TOO_MANY_ARGS);
      return false;
    } else if (cmd.length < Constants.ARGS_REQUIRED.get(action)) {
      this.view.displayOpMsg(state, out, action, Constants.TOO_FEW_ARGS);
      return false;
    } else if (saveLocCmdIndex >= 0 && !model.imageExists(cmd[saveLocCmdIndex])) {
      this.view.displayOpMsg(state, out, action, Constants.IMG_NOT_LOADED);
      return false;
    }
    return true;
  }
}

package controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.command.BrightenIMECommand;
import controller.command.CombineIMECommand;
import controller.command.DitherIMECommand;
import controller.command.FilterIMECommand;
import controller.command.FlipIMECommand;
import controller.command.GreyScaleIMECommand;
import controller.command.IMECommand;
import controller.command.LoadIMECommand;
import controller.command.RGBSplitIMECommand;
import controller.command.TransformationIMECommand;
import controller.fileoperations.FileWriteOperations;
import controller.fileoperations.FileWriterImpl;
import controller.fileoperations.PPMFileWriterImpl;
import model.Constants;
import model.IMEModel;
import model.IMEModelExtension;
import model.IMEPPMModelExtensionImpl;
import view.IMEViewer;

/**
 * This class represents the Controller in this project.
 * This class implements the {@link IMEController} class and is responsible
 * for passing user commands to the model class and perform image editing operations.
 * Furthermore, this class displays the preview messages using the {@link IMEViewer} class.
 */
public class IMEControllerCommandImpl implements IMEController {
  private final IMEModelExtension model;
  private final IMEViewer view;
  private final Map<String, Function<String, IMECommand>> knownIMECommands;
  private final IMEModelExtension ppmModel;
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
  public IMEControllerCommandImpl(IMEModelExtension model, IMEViewer view, Readable in,
                                  Appendable out) {
    this.model = model;
    this.view = view;
    this.in = in;
    this.out = out;
    this.ppmModel = new IMEPPMModelExtensionImpl();
    this.knownIMECommands = new HashMap<>();
  }

  /**
   * The constructor of this class, used to initialize variables.
   *
   * @param model the {@link IMEModel} class object.
   * @param view  the {@link IMEViewer} class object.
   */
  public IMEControllerCommandImpl(IMEModelExtension model, IMEViewer view) {
    this.model = model;
    this.view = view;
    this.ppmModel = new IMEPPMModelExtensionImpl();
    this.knownIMECommands = new HashMap<>();
  }

  /**
   * The constructor of this class, used to initialize variables.
   *
   * @param model the {@link IMEModel} class object.
   * @param view  the {@link IMEViewer} class object.
   */
  public IMEControllerCommandImpl(IMEModelExtension model, IMEViewer view, String scriptFileLoc)
          throws IOException {
    this.model = model;
    this.view = view;
    this.ppmModel = new IMEPPMModelExtensionImpl();
    this.knownIMECommands = new HashMap<>();
    initializeCommandMap();
    handleScriptFile(scriptFileLoc);
  }

  @Override
  public void executeIMEController() throws IOException {
    this.view.displayMsg(Constants.MAIN_MENU);
    initializeCommandMap();
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
            runCommand(actions);
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
            run = runCommand(line);
          }
        }
      }
    } catch (IOException e) {
      this.view.displayOpMsg(-1, out, Constants.RUNSCRIPT, e.getMessage());
    }
  }

  private void initializeCommandMap() {
    this.knownIMECommands.put(Constants.LOAD, s -> {
      try {
        return new LoadIMECommand(s);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    });
    this.knownIMECommands.put(Constants.GREYSCALE, GreyScaleIMECommand::new);
    this.knownIMECommands.put(Constants.HORIZONTAL, FlipIMECommand::new);
    this.knownIMECommands.put(Constants.VERTICAL, FlipIMECommand::new);
    this.knownIMECommands.put(Constants.BRIGHTEN, BrightenIMECommand::new);
    this.knownIMECommands.put(Constants.BLUR, FilterIMECommand::new);
    this.knownIMECommands.put(Constants.SHARPEN, FilterIMECommand::new);
    this.knownIMECommands.put(Constants.SPLIT, RGBSplitIMECommand::new);
    this.knownIMECommands.put(Constants.TRANSFORMATION, TransformationIMECommand::new);
    this.knownIMECommands.put(Constants.DITHER, DitherIMECommand::new);
    this.knownIMECommands.put(Constants.COMBINE, CombineIMECommand::new);
  }

  // helper
  private boolean runCommand(String cmd) throws IOException {
    String[] actionList = cmd.split("\\s+");
    String action = actionList[0].toLowerCase();
    IMECommand imeCommand;
    try {
      if (knownIMECommands.get(action) != null) {
        Function<String, IMECommand> cmdM =
                knownIMECommands.getOrDefault(action, null);
        if (cmdM == null) {
          view.displayOpMsg(-1, out, action, Constants.INVALID_CMD);
        } else {
          if (isValid(action, actionList, -1)) {
            imeCommand = cmdM.apply(cmd);
            boolean success;
            if (action.equalsIgnoreCase(Constants.LOAD)) {
              if (actionList[1].split("\\.")[1].equalsIgnoreCase("ppm")) {
                success = imeCommand.executeIMECommand(ppmModel);
              } else {
                success = imeCommand.executeIMECommand(model);
              }
            } else {
              success = imeCommand.executeIMECommand(model);
            }
            if (success) {
              this.view.displayOpMsg(1, out, action, Constants.EMPTY_MSG);
            } else {
              this.view.displayOpMsg(-1, out, action, Constants.RECHECK_CMD);
            }
          }
        }
      } else {
        switch (action) {
          case Constants.SAVE:
            if (isValid(action, actionList, 2)) {
              boolean saved;
              String[] imageFile = actionList[1].split("\\.");
              String type = imageFile[1];
              FileWriteOperations writer;
              ByteArrayOutputStream byteArray;
              if (type.equalsIgnoreCase("ppm")) {
                byteArray = ppmModel.saveImage(actionList[2], type);
                writer = new PPMFileWriterImpl();
              } else {
                byteArray = model.saveImage(actionList[2], type);
                writer = new FileWriterImpl();
              }
              saved = writer.writeFile(byteArray, actionList[1], type);
              if (saved) {
                this.view.displayOpMsg(1, out, action, Constants.SAVED_SUCCESS);
              } else {
                this.view.displayOpMsg(-1, out, action, Constants.SAVED_FAILURE);
              }
            }
            return true;
          case Constants.RUN:
            if (isValid(action, actionList, -1)) {
              handleScriptFile(actionList[1]);
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
      }
    } catch (Exception e) {
      view.displayOpMsg(-1, out, action, e.getMessage());
      return false;
    }
    return true;
  }

  private boolean isValid(String action, String[] cmd, int i) throws IOException {
    if (cmd.length > Constants.ARGS_REQUIRED.get(action)) {
      this.view.displayOpMsg(-1, out, action, Constants.TOO_MANY_ARGS);
      return false;
    } else if (cmd.length < Constants.ARGS_REQUIRED.get(action)) {
      this.view.displayOpMsg(-1, out, action, Constants.TOO_FEW_ARGS);
      return false;
    } else if (i >= 0 && !model.imageExists(cmd[i])) {
      this.view.displayOpMsg(-1, out, action, Constants.IMG_NOT_LOADED);
      return false;
    }
    return true;
  }
}

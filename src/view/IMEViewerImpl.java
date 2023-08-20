package view;

import java.io.IOException;

import model.Constants;

/**
 * This class implements the {@link IMEViewer} interface and represents a
 * viewer class that is used to display operation related messages to
 * the user.
 */
public class IMEViewerImpl implements IMEViewer {
  @Override
  public void displayOpMsg(int state, Appendable out, String action, String message)
          throws IOException {
    String stateMsg = Constants.OPERATION + Constants.ONE_SPACE +
            (state > 0 ? Constants.SUCCESSFUL : Constants.FAILED);
    System.out.println("'" + action + "' " + stateMsg + ". " + message);
    if (out != null) {
      out.append("'").append(action).append("' ").append(stateMsg).append(". ").append(message);
    }
  }

  @Override
  public void displayMsg(String message) {
    System.out.println(message);
  }

}

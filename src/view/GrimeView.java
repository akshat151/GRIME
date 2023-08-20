package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


import controller.Features;
import model.Constants;

/**
 * This class represents the viewer for the GUI operations.
 */
public class GrimeView extends JFrame implements IGRIMEViewer {

  // GUI components
  private JFrame frame;
  private JMenuItem loadMenuItem;
  private JMenuItem saveMenuItem;
  private JMenuItem exitMenuItem;
  private JLabel imageLabel;
  private JPanel histogramPanel;
  private JButton flipButton;
  private JButton grayscaleButton;
  private JButton filterButton;
  private JButton transformButton;
  private JButton ditherButton;
  private JButton brightenButton;
  private JButton rgbSplitButton;
  private JButton rgbCombineButton;

  /**
   * Constructor used to initialize values.
   */
  public GrimeView() {
    createGUI();
  }

  /**
   * This method is used to create the GUI components.
   */
  private void createGUI() {
    // Create frame and set properties
    frame = new JFrame(Constants.G_MAIN_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(256 * 4, 800));

    // Create menu bar and menu items
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu(Constants.G_MENU_FILE);
    loadMenuItem = new JMenuItem(Constants.G_MENU_LOAD);
    saveMenuItem = new JMenuItem(Constants.G_MENU_SAVE);
    exitMenuItem = new JMenuItem(Constants.G_MENU_EXIT);

    // Add menu items to file menu
    fileMenu.add(loadMenuItem);
    fileMenu.add(saveMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(exitMenuItem);

    // Add file menu to menu bar
    menuBar.add(fileMenu);

    // Create image panel and label
    JPanel imagePanel = new JPanel(new BorderLayout());
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    imageLabel.setVerticalAlignment(SwingConstants.CENTER);
    imagePanel.add(imageLabel, BorderLayout.CENTER);

    // Create scroll pane and add image panel
    JScrollPane scrollPane = new JScrollPane(imagePanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    imagePanel.setAutoscrolls(true);
    scrollPane.setPreferredSize(new Dimension(Constants.G_WIDTH, Constants.G_HEIGHT));

    // Create image manipulation buttons
    flipButton = new JButton(Constants.G_FLP_BTN);
    grayscaleButton = new JButton(Constants.G_GRC_BTN);
    filterButton = new JButton(Constants.G_FTR_BTN);
    transformButton = new JButton(Constants.G_CLT_BTN);
    ditherButton = new JButton(Constants.G_DIT_BTN);
    brightenButton = new JButton(Constants.G_BRT_BTN);
    rgbSplitButton = new JButton(Constants.G_SPT_BTN);
    rgbCombineButton = new JButton(Constants.G_COM_BTN);

    // Create histogram panel
    histogramPanel = new JPanel();

    // Create button panel and add buttons
    JPanel buttonPanel = new JPanel(new GridLayout(
            Constants.G_GRD_ROWS,
            Constants.G_GRD_COLS,
            Constants.G_GRD_HGAP,
            Constants.G_GRD_VGAP));
    buttonPanel.add(flipButton);
    buttonPanel.add(grayscaleButton);
    buttonPanel.add(filterButton);
    buttonPanel.add(transformButton);
    buttonPanel.add(ditherButton);
    buttonPanel.add(brightenButton);
    buttonPanel.add(rgbSplitButton);
    buttonPanel.add(rgbCombineButton);

    // Add components to frame
    frame.setJMenuBar(menuBar);
    frame.add(scrollPane, BorderLayout.NORTH);
    frame.add(histogramPanel, BorderLayout.WEST);
    frame.add(buttonPanel, BorderLayout.SOUTH);

    // Display frame
    frame.pack();
    frame.setResizable(true);
    frame.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    loadMenuItem.addActionListener(evt -> {
      try {
        features.loadImage();
      } catch (IOException e) {
        displayError(Constants.G_LOD_FAIL);
      }
    });
    saveMenuItem.addActionListener(evt -> {
      try {
        features.saveImage();
      } catch (IOException e) {
        displayError(Constants.G_SAV_FAIL);
      }
    });
    exitMenuItem.addActionListener(evt -> features.exit());
    flipButton.addActionListener(evt -> {
      try {
        features.flip();
      } catch (IOException e) {
        displayError(Constants.G_OP_FAIL);
      }
    });
    brightenButton.addActionListener(evt -> {
      try {
        features.brighten();
      } catch (IOException e) {
        displayError(Constants.G_INV_BRT);
      }
    });
    ditherButton.addActionListener(evt -> {
      try {
        features.dither();
      } catch (IOException e) {
        displayError(Constants.G_OP_FAIL);
      }
    });
    transformButton.addActionListener(evt -> {
      try {
        features.transform();
      } catch (IOException e) {
        displayError(Constants.G_OP_FAIL);
      }
    });
    grayscaleButton.addActionListener(evt -> {
      try {
        features.greyscale();
      } catch (IOException e) {
        displayError(Constants.G_OP_FAIL);
      }
    });
    filterButton.addActionListener(evt -> {
      try {
        features.filter();
      } catch (IOException e) {
        displayError(Constants.G_OP_FAIL);
      }
    });
    rgbCombineButton.addActionListener(evt -> {
      try {
        features.rgbCombine();
      } catch (IOException e) {
        displayError(Constants.G_OP_FAIL);
      }
    });
    rgbSplitButton.addActionListener(evt -> {
      try {
        features.rgbSplit();
      } catch (IOException e) {
        displayError(Constants.G_OP_FAIL);
      }
    });
  }

  @Override
  public void showHistogram(Map<String, int[]> data) {
    // Create histogram panel and label
    frame.remove(histogramPanel); // remove the previous one
    histogramPanel = new HistogramImpl(data);
    frame.add(histogramPanel, BorderLayout.CENTER);
    frame.setVisible(true);
    frame.revalidate();
    frame.repaint();
  }

  @Override
  public String loadImage() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(frame);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      return file.getPath();
    }
    return Constants.EMPTY_MSG;
  }

  @Override
  public String showOptionsDialog(String[] options, String message, String title) {
    ImageIcon emptyIcon = new ImageIcon();
    Object selectedOption = JOptionPane.showInputDialog(frame,
            message, title,
            JOptionPane.QUESTION_MESSAGE, emptyIcon, options, options[0]);
    if (selectedOption != null) {
      return selectedOption.toString();
    }
    return Constants.EMPTY_MSG;
  }

  @Override
  public String showInputDialog(String message, String title) {
    String selectedOption = JOptionPane.showInputDialog(frame, Constants.G_BRT_MSG,
            JOptionPane.INFORMATION_MESSAGE);
    if (selectedOption != null) {
      return selectedOption;
    }
    return Constants.EMPTY_MSG;
  }

  @Override
  public void exit() {
    System.exit(0);
  }

  @Override
  public String selectImageFileToSave() {
    JFileChooser fileChooser = new JFileChooser();
    String filePath = Constants.EMPTY_MSG;
    int result = fileChooser.showSaveDialog(frame);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      filePath = file.getPath();
    }
    return filePath;
  }

  @Override
  public void displayError(String message) {
    JOptionPane.showMessageDialog(frame, message, Constants.G_MSG, JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showImage(ByteArrayInputStream inStream) {
    try {
      BufferedImage image = ImageIO.read(inStream);
      imageLabel.setIcon(new ImageIcon(image));
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(frame, Constants.G_LOD_FAIL, Constants.G_OP_FAIL,
              JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void displayMsg(String message) {
    JOptionPane.showMessageDialog(frame, message, Constants.G_MSG, JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void displayOpMsg(int state, Appendable out, String action, String message) {
    JOptionPane.showMessageDialog(frame, message, Constants.G_MSG, JOptionPane.PLAIN_MESSAGE);
  }
}
package view;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import model.Constants;

/**
 * This class represents the histogram that is loaded in the program
 * for every image.
 */
public class HistogramImpl extends JPanel {
  private static final int BINS = 256;

  private final int[] histogramRed;
  private final int[] histogramGreen;
  private final int[] histogramBlue;
  private final int[] histogramIntensity;

  /**
   * Constructor for this class that is used to initialize the data.
   *
   * @param data the data the is to be shown as graph.
   */
  public HistogramImpl(Map<String, int[]> data) {

    histogramRed = data.get(Constants.H_RED);
    histogramGreen = data.get(Constants.H_GREEN);
    histogramBlue = data.get(Constants.H_BLUE);
    histogramIntensity = data.get(Constants.H_INTENSITY);

    // Set preferred size of histogram panel
    int width = getWidth() - (getInsets().left + getInsets().right);
    int height = getHeight() - (getInsets().top + getInsets().bottom);

    setPreferredSize(new Dimension(width, height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Font font = new Font("Arial", Font.ITALIC, 13);
    setBorder(
            BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 30),
                    Constants.GRAPH_LABEL_VALUE,
                    TitledBorder.CENTER,
                    TitledBorder.BOTTOM,
                    font
            )
    );

    // Draw histogram
    g.setColor(Color.WHITE);
    setLines(g);

    // Draw axes
    ((Graphics2D) g).setStroke(new BasicStroke(0.5f));
    g.setColor(Color.BLACK);
    g.drawLine( // x axis
            getInsets().left,
            getHeight(),
            getInsets().left,
            0
    );
    g.drawLine( // y axis
            getInsets().left,
            getHeight() - getInsets().bottom,
            getWidth() - getInsets().right,
            getHeight() - getInsets().bottom
    );

    g.setFont(font);
    g.drawString(Constants.GRAPH_LABEL_FREQ, getInsets().left + 10, 50);
  }

  private void setLines(Graphics g) {

    g.fillRect(0, 0, getWidth(), getHeight());
    for (int i = 1; i < BINS; i++) {
      setLine(g, histogramRed, i, Color.RED);
      setLine(g, histogramGreen, i, Color.GREEN);
      setLine(g, histogramBlue, i, Color.BLUE);
      setLine(g, histogramIntensity, i, Color.DARK_GRAY);
    }
  }

  private void setLine(Graphics g, int[] histogram, int index, Color color) {
    int[] prevCoordinate = getCoordinate(histogram, index - 1);
    int[] currCoordinate = getCoordinate(histogram, index);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
    g.setColor(color);
    g.drawLine(
            prevCoordinate[0],
            prevCoordinate[1],
            currCoordinate[0],
            currCoordinate[1]
    );
  }

  private int[] getCoordinate(int[] histogram, int index) {
    return new int[]{
            getXCoordinate(histogram, index), // x-coordinate
            getYCoordinate(histogram, index)  // y-coordinate
    };
  }

  private int getYCoordinate(int[] histogram, int index) {
    int maxValue = Arrays.stream(histogram).max().getAsInt();
    double yScale = ((double) getHeight() - 2 * getInsets().bottom) / (maxValue - 1);
    return (int) ((maxValue - histogram[index]) * yScale + getInsets().bottom);
  }

  private int getXCoordinate(int[] histogram, int index) {
    double xScale = ((double) getWidth() - 2 * getInsets().left) / (histogram.length - 1);
    return (int) (index * xScale + getInsets().left);
  }
}
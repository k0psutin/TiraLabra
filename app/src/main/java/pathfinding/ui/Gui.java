package pathfinding.ui;

import static pathfinding.tools.ImgTools.getPixelColor;
import static pathfinding.tools.ImgTools.resizeImage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputAdapter;
import pathfinding.solvers.Astar;

public class Gui implements Runnable {

  private JFrame frame;
  private int width = 1000;
  private int height = 700;
  private String file = "arena.png";
  public static BufferedImage buffImg = null;
  private JLabel picLabel = null;
  public static ImageIcon imageIcon = null;
  public static JPanel picPanel = null;
  private JLabel solveTime = null;
  private JLabel startPos = null;
  private JLabel endPos = null;
  private static JComboBox<String> comboBox;

  private static boolean addStart = false;
  private static boolean addEnd = false;
  private static int startX = 10;
  private static int startY = 10;
  private static int endY = 100;
  private static int endX = 100;

  private String[] paths = {"A*", "JPS", "IDA*"};

  public final int imgResize = 80;

  public static void updatePic() {
    picPanel.repaint();
  }

  @Override
  public void run() {
    frame = new JFrame("Pathfinding");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    JPanel panel = new JPanel();

    JButton open = new JButton("Open image");
    panel.add(open);

    open.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(".\\src\\main\\resources\\");
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
              File selectedFile = fileChooser.getSelectedFile();
              file = selectedFile.getName();
              buffImg = resizeImage(imgResize, file);
              imageIcon.setImage(buffImg);
              picPanel.repaint();
            }
          }
        });

    JButton start = new JButton("Add start point");
    panel.add(start);
    start.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addStart = true;
            addEnd = false;
          }
        });

    JButton end = new JButton("Add end point");
    panel.add(end);
    end.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addStart = false;
            addEnd = true;
          }
        });

    JButton solve = new JButton("Solve");
    panel.add(solve);
    solve.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            BufferedImage newMap = null;
            if (comboBox.getSelectedItem().equals("A*")) {
              Astar astar = new Astar(startX, startY, endX, endY, buffImg);
              solveTime.setText(astar.findPath());
              newMap = astar.getMap();
            } else if (comboBox.getSelectedItem().equals("JPS")) {
              // Jps jps = new Jps(startX, startY, endX, endY, buffImg, true);
              // solveTime.setText(jps.solve());
              // newMap = jps.getMap();
            } else if (comboBox.getSelectedItem().equals("IDA*")) {
              return;
            }
            if (newMap == null) {
              return;
            }
            imageIcon.setImage(newMap);
            picPanel.repaint();
          }
        });

    comboBox = new JComboBox<String>(paths);
    panel.add(comboBox);

    JButton clear = new JButton("Reset");
    panel.add(clear);
    clear.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            buffImg = resizeImage(imgResize, file);
            imageIcon.setImage(buffImg);
            picPanel.repaint();
          }
        });

    buffImg = resizeImage(imgResize, file);
    imageIcon = new ImageIcon(buffImg);
    picLabel = new JLabel(imageIcon);

    startPos = new JLabel("Start point: (" + startX + "," + startY + ")");
    panel.add(startPos);
    endPos = new JLabel("End point: (" + endX + "," + endY + ")");
    panel.add(endPos);
    solveTime = new JLabel("               ");
    panel.add(solveTime);
    panel.setLayout(new GridLayout(2, 3, 2, 2));
    frame.add(panel);

    picPanel = new JPanel();
    picPanel.addMouseListener(
        new MouseInputAdapter() {
          @Override
          public void mouseReleased(MouseEvent e) {
            Point pos = e.getPoint();
            int posX = pos.x - ((1919 / 2) - (buffImg.getWidth() / 2));
            int posY = pos.y - 5;
            if (Gui.addEnd) {
              if (isSafe(posX, posY)) {
                endX = posX;
                endY = posY;
              }
              addEnd = false;
              endPos.setText("End point: (" + endX + "," + endY + ")");
            } else if (Gui.addStart) {
              if (isSafe(posX, posY)) {
                startX = posX;
                startY = posY;
              }
              addStart = false;
              startPos.setText("Start point: (" + startX + "," + startY + ")");
            }
          }
        });
    picPanel.add(picLabel);
    frame.add(picPanel);
    frame.getContentPane().add(BorderLayout.NORTH, panel);
    frame.getContentPane().add(BorderLayout.CENTER, picPanel);
    frame.pack();
    frame.setVisible(true);
  }

  public boolean isSafe(int x, int y) {
    if (x < 0 || y < 0 || x > buffImg.getWidth() - 1 || y > buffImg.getHeight() - 1) {
      return false;
    }
    return getPixelColor(x, y, buffImg).equals("(229,229,229)");
  }
}

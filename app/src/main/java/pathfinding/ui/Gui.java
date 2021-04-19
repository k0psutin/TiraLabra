package pathfinding.ui;

import static pathfinding.tools.ImgTools.loadImage;
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
import pathfinding.data.PerformanceTests;
import pathfinding.solvers.Astar;
import pathfinding.solvers.Ida;
import pathfinding.solvers.Jps;
import pathfinding.solvers.Pathfinding;

public class Gui implements Runnable {

  private JFrame frame;
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

  private final int imgSize = 800;
  private final int panelImgSize = 800;
  private final float factor = ((float) panelImgSize / (float) imgSize);

  @Override
  public void run() {

    frame = new JFrame("Pathfinding");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    buffImg = loadImage(file, imgSize);

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
              buffImg = resizeImage(panelImgSize, panelImgSize, loadImage(file, imgSize));
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
            Pathfinding solve = null;

            String type = comboBox.getSelectedItem().toString();
            switch (type) {
              case "A*":
                solve =
                    new Astar(startX, startY, endX, endY, resizeImage(imgSize, imgSize, buffImg));
                break;
              case "JPS":
                solve = new Jps(startX, startY, endX, endY, resizeImage(imgSize, imgSize, buffImg));
                break;
              case "IDA*":
                solve = new Ida(startX, startY, endX, endY, resizeImage(imgSize, imgSize, buffImg));
                break;
              default:
                solve = null;
                break;
            }

            if (solve == null) {
              return;
            }

            solveTime.setText(solve.findPath());
            BufferedImage newMap = solve.getMap();

            if (newMap == null) {
              return;
            }
            imageIcon.setImage(resizeImage(panelImgSize, panelImgSize, newMap));
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
            buffImg = resizeImage(panelImgSize, panelImgSize, buffImg);
            imageIcon.setImage(buffImg);
            picPanel.repaint();
          }
        });

    JButton runTests = new JButton("Run tests");
    panel.add(runTests);
    runTests.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            PerformanceTests tests = new PerformanceTests();
            tests.runTests();
          }
        });

    imageIcon = new ImageIcon(resizeImage(panelImgSize, panelImgSize, buffImg));
    picLabel = new JLabel(imageIcon);
    picLabel.setLayout(null);
    picLabel.setBounds(0, 0, panelImgSize, panelImgSize);

    startPos = new JLabel("Start point: (" + startX + "," + startY + ")");
    panel.add(startPos);
    endPos = new JLabel("End point: (" + endX + "," + endY + ")");
    panel.add(endPos);
    solveTime = new JLabel("               ");
    panel.add(solveTime);
    panel.setLayout(new GridLayout(2, 3, 2, 2));
    frame.add(panel);

    picPanel = new JPanel();
    picLabel.addMouseListener(
        new MouseInputAdapter() {
          @Override
          public void mouseReleased(MouseEvent e) {
            Point pos = e.getPoint();
            if (Gui.addEnd) {
              endX = (int) (pos.x / factor);
              endY = (int) (pos.y / factor);
              addEnd = false;
              endPos.setText("End point: (" + endX + "," + endY + ")");
            } else if (Gui.addStart) {
              startX = (int) (pos.x / factor);
              startY = (int) (pos.y / factor);
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
}

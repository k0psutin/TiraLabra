package pathfinding.ui;

import static pathfinding.tools.ImgTools.resizeImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
  private ImageIcon imageIcon = null;
  private JPanel picPanel = null;
  private static JComboBox<String> comboBox;

  private static boolean addStart = false;
  private static boolean addEnd = false;
  private static int startX = 10;
  private static int startY = 10;
  private static int endY = 100;
  private static int endX = 100;

  private String[] paths = {"A*", "JSP", "IDA*"};

  @Override
  public void run() {
    frame = new JFrame("Pathfinding");
    frame.setPreferredSize(new Dimension(width, height));
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
              buffImg = resizeImage(50, file);
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
              Astar astar = new Astar(buffImg);
              astar.findPath(startX, startY, endX, endY);
              newMap = astar.getMap();
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
            buffImg = resizeImage(50, file);
            imageIcon.setImage(buffImg);
            picPanel.repaint();
          }
        });

    buffImg = resizeImage(50, file);
    imageIcon = new ImageIcon(buffImg);
    picLabel = new JLabel(imageIcon);

    frame.add(panel);

    picPanel = new JPanel();
    picPanel.addMouseListener(
        new MouseInputAdapter() {
          @Override
          public void mouseReleased(MouseEvent e) {
            Point pos = e.getPoint();
            if (Gui.addEnd) {
              endX = pos.x - 225;
              endY = pos.y - 4;
              addEnd = false;
              System.out.println("End point: (" + endX + "," + endY + ")");
            } else if (Gui.addStart) {
              startX = pos.x - 225;
              startY = pos.y - 4;
              addStart = false;
              System.out.println("Start point: (" + startX + "," + startY + ")");
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

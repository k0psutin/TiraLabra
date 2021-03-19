package pathfinding.ui;

import static pathfinding.tools.ImgTools.resizeImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class Gui implements Runnable {

  private JFrame frame;
  private int width = 1000;
  private int height = 700;
  private String file = "arena.png";
  private BufferedImage buffImg = null;
  private JLabel picLabel = null;
  private ImageIcon imageIcon = null;
  private JPanel picPanel = null;

  private String[] paths = {"JSP", "IDA*", "A*"};

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

    JButton end = new JButton("Add end point");
    panel.add(end);

    JButton solve = new JButton("Solve");
    panel.add(solve);

    JComboBox<String> comboBox = new JComboBox<String>(paths);
    panel.add(comboBox);

    JButton clear = new JButton("Reset");
    panel.add(clear);

    buffImg = resizeImage(50, file);
    imageIcon = new ImageIcon(buffImg);
    picLabel = new JLabel(imageIcon);
    frame.add(panel);

    picPanel = new JPanel();
    picPanel.add(picLabel);
    frame.add(picPanel);
    frame.getContentPane().add(BorderLayout.NORTH, panel);
    frame.getContentPane().add(BorderLayout.CENTER, picPanel);
    frame.pack();
    frame.setVisible(true);
  }
}

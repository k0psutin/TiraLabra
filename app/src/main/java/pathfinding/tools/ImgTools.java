package pathfinding.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

@SuppressWarnings("checkstyle:MissingJavadocType")
public class ImgTools {

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public static BufferedImage resizeImage(int percentage, String filename) {
    BufferedImage buffImg = null;
    try {
      ImageIcon imageIcon = new ImageIcon(ImgTools.class.getClassLoader().getResource(filename));
      int width = (int) (imageIcon.getIconWidth() * ((float) percentage / 100.0));
      int height = (int) (imageIcon.getIconHeight() * ((float) percentage / 100.0));
      buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2d = (Graphics2D) buffImg.createGraphics();
      g2d.addRenderingHints(
          new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
      g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return buffImg;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public static void drawJumpPoint(int posX, int posY, int r, int g, int b, BufferedImage image) {
    Graphics2D graph = image.createGraphics();
    graph.setColor(new Color(r, g, b));
    graph.drawRect(posX - 1, posY - 1, 2, 2);
    graph.dispose();
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public static String getPixelColor(int x, int y, BufferedImage image) {
    int clr = image.getRGB(x, y);
    int red = (clr & 0x00ff0000) >> 16;
    int green = (clr & 0x0000ff00) >> 8;
    int blue = clr & 0x000000ff;
    return "(" + red + "," + green + "," + blue + ")";
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public static void setPixelColor(
      int x, int y, int red, int green, int blue, BufferedImage image) {
    Color color = new Color(red, green, blue);
    image.setRGB(x, y, color.getRGB());
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public static void drawLine(int x, int y, int x2, int y2, BufferedImage image) {
    Graphics2D graph = image.createGraphics();
    graph.setColor(new Color(255, 0, 0));
    graph.setStroke(new BasicStroke(3));
    graph.drawLine(x, y, x2, y2);
    graph.dispose();
  }
}

package pathfinding.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import pathfinding.interfaces.Generated;

/** Tools for image manipulation. */
public class ImgTools {

  @Generated
  public ImgTools() {}

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public static BufferedImage resizeImage(int width, int height, BufferedImage buffImg) {
    BufferedImage scaledImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D) scaledImg.createGraphics();
    g2d.addRenderingHints(
        new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED));
    g2d.drawImage(buffImg, 0, 0, width, height, null);
    g2d.dispose();
    return scaledImg;
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public static BufferedImage loadImage(String filename, int imgSize) {
    BufferedImage buffImg = null;
    try {
      buffImg = ImageIO.read(ImgTools.class.getClassLoader().getResource(filename));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return resizeImage(imgSize, imgSize, buffImg);
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public static void drawJumpPoint(int posX, int posY, int r, int g, int b, BufferedImage image) {
    Graphics2D graph = image.createGraphics();
    graph.setColor(new Color(r, g, b));
    graph.drawRect(posX - 1, posY - 1, 2, 2);
    graph.dispose();
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public static String getPixelColor(int x, int y, BufferedImage image) {
    if (x < 0 || y < 0 || x > image.getWidth() - 1 || y > image.getHeight() - 1) {
      return "";
    }
    int clr = image.getRGB(x, y);
    int red = (clr & 0x00ff0000) >> 16;
    int green = (clr & 0x0000ff00) >> 8;
    int blue = clr & 0x000000ff;
    return "(" + red + "," + green + "," + blue + ")";
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public static void setPixelColor(
      int x, int y, int red, int green, int blue, BufferedImage image) {
    Color color = new Color(red, green, blue);
    image.setRGB(x, y, color.getRGB());
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public static void drawLine(int x, int y, int x2, int y2, BufferedImage image, Color color) {
    Graphics2D graph = image.createGraphics();
    graph.setColor(color);
    graph.setStroke(new BasicStroke(1));
    graph.drawLine(x, y, x2, y2);
    graph.dispose();
  }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @Generated
  public static void drawCircle(int x, int y, Color color, BufferedImage image) {
    Graphics2D graph = image.createGraphics();
    graph.setColor(color);
    graph.setStroke(new BasicStroke(1));
    graph.fillOval(x - 5, y - 5, 10, 10);
    graph.dispose();
  }
}

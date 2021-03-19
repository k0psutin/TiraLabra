package pathfinding.tools;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImgTools {
  public BufferedImage resizeImage(int percentage, String filename) {
    BufferedImage buffImg = null;
    try {
      ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(filename));
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

  public void getPixelColor(int x, int y, BufferedImage image) {
    int clr = image.getRGB(x, y);
    int red = (clr & 0x00ff0000) >> 16;
    int green = (clr & 0x0000ff00) >> 8;
    int blue = clr & 0x000000ff;
    System.out.println("(" + red + "," + green + "," + blue + ")");
  }
}

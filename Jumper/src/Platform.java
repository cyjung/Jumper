import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Platform
{
  private double x;
  private double y;
  private static int width = 60;
  private static final int height = 15;
  protected static int SCREENWIDTH;
  protected static int SCREENHEIGHT;
  private static Jumper panel;
  private final Rectangle rect;
  protected Image image;
  private Spring spring;
  
  public Platform(double x, double y)
  {
    this.x = x;
    this.y = y;
    this.rect = new Rectangle((int)x, (int)y, width, 15);
    loadImages();
  }
  
  public Platform(double x, double y, Spring spring)
  {
    this.x = x;
    this.y = y;
    this.spring = spring;
    this.rect = new Rectangle((int)x, (int)y, width, 15);
    loadImages();
  }
  
  protected void loadImages()
  {
    this.image = new ImageIcon(getClass().getResource("PlatformImages/GreenPlatform.png")).getImage();
  }
  
  public void draw(Graphics g)
  {
    g.drawImage(this.image, (int)this.x, (int)this.y, width, 15, null);
    if (this.spring != null) {
      this.spring.draw(g);
    }
  }
  
  public double getX()
  {
    return this.x;
  }
  
  public void setX(double x)
  {
    this.x = x;
    this.rect.setLocation((int)x, (int)this.y);
    if (this.spring != null) {
      this.spring.setX(x);
    }
  }
  
  public double getY()
  {
    return this.y;
  }
  
  public void setY(double y)
  {
    this.y = y;
    this.rect.setLocation((int)this.x, (int)y);
    if (this.spring != null) {
      if (this.spring.isSprung()) {
        this.spring.setY(y - 30.0D);
      } else {
        this.spring.setY(y - 15.0D);
      }
    }
  }
  
  public Rectangle getRect()
  {
    return this.rect;
  }
  
  public static int getWidth()
  {
    return width;
  }
  
  public static void setWidth(int width)
  {
    width = width;
  }
  
  public static int getHeight()
  {
    return 15;
  }
  
  public Spring getSpring()
  {
    return this.spring;
  }
  
  public static void setPanel(Jumper jumper)
  {
    panel = jumper;
    SCREENWIDTH = panel.getSCREENWIDTH();
    SCREENHEIGHT = panel.getSCREENHEIGHT();
  }
  
  public void setSprung()
  {
    this.spring.setSprung();
  }
}

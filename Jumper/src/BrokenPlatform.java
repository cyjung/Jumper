import javax.swing.ImageIcon;

public class BrokenPlatform
  extends Platform
{
  boolean broken;
  
  public BrokenPlatform(double x, double y)
  {
    super(x, y);
    this.broken = false;
  }
  
  protected void loadImages()
  {
    this.image = new ImageIcon(getClass().getResource("PlatformImages/BrownPlatform.png")).getImage();
  }
  
  public void setBroken()
  {
    this.broken = true;
    String imagePath = "PlatformImages/";
    this.image = new ImageIcon(getClass().getResource(imagePath + "BrownPlatform2.png")).getImage();
  }
  
  public boolean isBroken()
  {
    return this.broken;
  }
}

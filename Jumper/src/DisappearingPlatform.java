import javax.swing.ImageIcon;

public class DisappearingPlatform
  extends Platform
{
  public DisappearingPlatform(double x, double y)
  {
    super(x, y);
  }
  
  protected void loadImages()
  {
    this.image = new ImageIcon(getClass().getResource("PlatformImages/WhitePlatform.png")).getImage();
  }
}

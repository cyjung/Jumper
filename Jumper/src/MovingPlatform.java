import javax.swing.ImageIcon;

public class MovingPlatform
  extends Platform
{
  private int direction;
  private final int speed;
  private final int LEFT = 0;
  private final int RIGHT = 1;
  
  public MovingPlatform(double x, double y)
  {
    super(x, y);
    this.direction = 0;
    this.speed = ((int)(Math.random() * 3.0D + 1.0D));
  }
  
  public MovingPlatform(double x, double y, Spring spring)
  {
    super(x, y, spring);
    this.direction = 0;
    this.speed = ((int)(Math.random() * 3.0D + 1.0D));
  }
  
  protected void loadImages()
  {
    this.image = new ImageIcon(getClass().getResource("PlatformImages/BluePlatform.png")).getImage();
  }
  
  public void move()
  {
    if (getX() < 0.0D) {
      this.direction = 1;
    } else if (getX() > SCREENWIDTH - getWidth()) {
      this.direction = 0;
    }
    if (this.direction == 0) {
      setX(getX() - this.speed);
    } else if (this.direction == 1) {
      setX(getX() + this.speed);
    }
  }
}

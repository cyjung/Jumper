import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Player
{
	private double x;
	private double y;
	private final int width;
	private final int height;
	private final int feetHeight;
	private final int feetWidth;
	private double velocityX;
	private double velocityY;
	private static JPanel panel;
	private Image CFFallL;
	private Image CFJumpL;
	private Image CFFallR;
	private Image CFJumpR;
	private Rectangle body;
	private final Rectangle feet;

	public double getVelocityX()
	{
		return this.velocityX;
	}

	public void setVelocityX(double velocityX)
	{
		this.velocityX = velocityX;
	}

	public double getVelocityY()
	{
		return this.velocityY;
	}

	public void setVelocityY(double velocityY)
	{
		this.velocityY = velocityY;
	}

	public Player(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 80;
		this.feetHeight = 10;
		this.feetWidth = 30;
		this.body = new Rectangle((int)x, (int)y, this.width, this.height);
		this.feet = new Rectangle((int)x + (this.width - this.feetWidth) / 2, (int)y + 
				this.height - this.feetHeight, this.feetWidth, this.feetHeight);

		loadImages();
	}

	private void loadImages()
	{
		String imagePath = "PlayerImages/";
		this.CFFallL = new ImageIcon(getClass().getResource(
				imagePath + "CFFallL.gif")).getImage();
		this.CFJumpL = new ImageIcon(getClass().getResource(
				imagePath + "CFJumpL.gif")).getImage();
		this.CFFallR = new ImageIcon(getClass().getResource(
				imagePath + "CFFallR.gif")).getImage();
		this.CFJumpR = new ImageIcon(getClass().getResource(
				imagePath + "CFJumpR.gif")).getImage();
	}

	public double getX()
	{
		return this.x;
	}

	public void setX(double x)
	{
		this.x = x;
		setBodyLocation((int)x, (int)this.y);
	}

	public double getY()
	{
		return this.y;
	}

	public void setY(double y)
	{
		this.y = y;
		setBodyLocation((int)this.x, (int)y);
	}

	public Rectangle getBody()
	{
		return this.body;
	}

	public void setBody(Rectangle body)
	{
		this.body = body;
		this.x = body.getX();
		this.y = body.getY();
		this.feet.setLocation((int)body.getX() + (this.width - this.feetWidth) / 2, 
				(int)body.getY() + this.height - this.feetHeight);
	}

	public Rectangle getFeet()
	{
		return this.feet;
	}

	private void setBodyLocation(int x, int y)
	{
		this.body.setLocation(x, y);
		this.feet.setLocation(x + (this.width - this.feetWidth) / 2, y + this.height - this.feetHeight);
	}

	public void accelerate()
	{
		this.velocityY += 0.75D;
	}

	public void bounce()
	{
		this.velocityY = -18.0D;
	}

	public void springBounce()
	{
		this.velocityY = -30.0D;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public void draw(Graphics g)
	{
		if (this.velocityX < 0.0D)
		{
			if (this.velocityY > 0.0D) {
				g.drawImage(this.CFFallL, (int)this.x, (int)this.y, null);
			} else {
				g.drawImage(this.CFJumpL, (int)this.x, (int)this.y, null);
			}
		}
		else if (this.velocityY > 0.0D) {
			g.drawImage(this.CFFallR, (int)this.x, (int)this.y, null);
		} else {
			g.drawImage(this.CFJumpR, (int)this.x, (int)this.y, null);
		}
	}

	public static void setPanel(JPanel jumper)
	{
		panel = jumper;
	}
}

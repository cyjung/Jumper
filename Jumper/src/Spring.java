import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Spring
{
	private double x;
	private double y;
	private static int width = 20;
	private int height = 15;
	protected static int SCREENWIDTH;
	protected static int SCREENHEIGHT;
	private static Jumper panel;
	private final Rectangle rect;
	protected Image image;
	private boolean sprung;

	public Spring(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.rect = new Rectangle((int)x, (int)y, width, this.height);
		loadImages();
		this.sprung = false;
	}

	protected void loadImages()
	{
		this.image = new ImageIcon(getClass().getResource("BoostImages/Spring.png")).getImage();
	}

	public void draw(Graphics g)
	{
		g.drawImage(this.image, (int)this.x, (int)this.y, width, this.height, null);
	}

	public double getX()
	{
		return this.x;
	}

	public void setX(double x)
	{
		this.x = x;
		this.rect.setLocation((int)x, (int)this.y);
	}

	public double getY()
	{
		return this.y;
	}

	public void setY(double y)
	{
		this.y = y;
		this.rect.setLocation((int)this.x, (int)y);
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

	public int getHeight()
	{
		return this.height;
	}

	public boolean isSprung()
	{
		return this.sprung;
	}

	public void setSprung()
	{
		this.image = new ImageIcon(getClass().getResource("BoostImages/Spring2.png")).getImage();
		this.height = 30;
		setY(getY() - 15.0D);
		this.sprung = true;
	}

	public static void setPanel(Jumper jumper)
	{
		panel = jumper;
		SCREENWIDTH = panel.getSCREENWIDTH();
		SCREENHEIGHT = panel.getSCREENHEIGHT();
	}
}

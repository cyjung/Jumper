import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Jumper extends JPanel implements MouseListener,
MouseMotionListener, KeyListener
{

	private final Jumper.MainThread thread;
	private Image dbImage;
	private Graphics dbg;
	private final int SCREENWIDTH = (int) Math.floor(Toolkit
			.getDefaultToolkit().getScreenSize().width / 250) * 100;
	private final int SCREENHEIGHT = (int) Math.floor(Toolkit
			.getDefaultToolkit().getScreenSize().height / 100) * 100;
	private final Player player;
	private ArrayList<Platform> platforms;
	private boolean left;
	private boolean right;
	private double score;
	private double scoreLoop;
	private final Font scoreFont;
	private double probability;
	private boolean pause;
	private boolean main;
	private final PauseMenu pauseMenu;
	private int mouseX;
	private int mouseY;
	private double red;
	private double green;
	private double blue;
	private final MainMenu mainMenu;
	private boolean godMode;
	private boolean up;
	private boolean cheatUsed;
	private boolean gameOver;
	private final GameOverMenu gameOverMenu;
	private final int HIGHSCORESIZE = 5;
	private final String [] ID = new String [5];
	Preferences prefs;

	public Jumper()
	{
		setFocusable(true);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		setBackground(Color.black);
		setPreferredSize(new Dimension(this.SCREENWIDTH - 10,
				this.SCREENHEIGHT - 10));

		Player.setPanel(this);
		this.player = new Player(0.0D, 0.0D);

		Platform.setPanel(this);

		this.scoreFont = new Font("Arial", 1, 20);

		MainMenu.setPanel(this);
		this.mainMenu = new MainMenu();
		this.main = true;

		PauseMenu.setPanel(this);
		this.pauseMenu = new PauseMenu();

		GameOverMenu.setPanel(this);
		this.gameOverMenu = new GameOverMenu();

		this.prefs = Preferences.userRoot().node(getClass().getName());
		for (int i = 0; i < this.ID.length; i++)
		{
			this.ID[i] = i+"";
			this.prefs.getInt(this.ID[i], 0);
		}
		this.thread = new Jumper.MainThread();
		this.thread.start();
	}

	public class MainThread extends Thread
	{

		public MainThread()
		{
		}

		@Override
		public void run()
		{
			try
			{
				for (;;)
				{
					Thread.sleep(30L);
					if ((!Jumper.this.pause) && (!Jumper.this.main)
							&& (!Jumper.this.gameOver))
					{
						Jumper.this.move();
					}
					Jumper.this.repaint();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paint(Graphics gc)
	{
		this.dbImage = createImage(this.SCREENWIDTH, this.SCREENHEIGHT);
		this.dbg = this.dbImage.getGraphics();
		paintComponent(this.dbg);
		gc.drawImage(this.dbImage, 0, 0, this);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		if (!this.main)
		{
			g.setColor(new Color(150, 150, 150));
			g.fillRect(0, 0, this.SCREENWIDTH, this.SCREENHEIGHT);

			g.setColor(Color.gray);
			int gridSize = 10;
			for (int i = 0; i < this.SCREENHEIGHT / gridSize; i++)
			{
				g.drawLine(0, i * gridSize, this.SCREENWIDTH, i * gridSize);
			}
			for (int i = 0; i < this.SCREENWIDTH / gridSize; i++)
			{
				g.drawLine(i * gridSize, 0, i * gridSize, this.SCREENHEIGHT);
			}
			for (Platform p : this.platforms)
			{
				p.draw(g);
			}
			this.player.draw(g);

			g.setFont(this.scoreFont);
			g.setColor(Color.red);
			g.drawString((int) this.score+"", this.SCREENWIDTH - 150, 20);
			if (this.godMode)
			{
				g.drawString("GODMODE", this.SCREENWIDTH - 150, 50);
				if (this.up)
				{
					g.drawString("[ON] OFF", this.SCREENWIDTH - 150, 80);
				}
				else
				{
					g.drawString(" ON [OFF]", this.SCREENWIDTH - 150, 80);
				}
			}
			if (this.gameOver)
			{
				this.gameOverMenu.draw(g);
			}
			else if (this.pause)
			{
				this.pauseMenu.draw(g);
			}
		}
		else
		{
			this.mainMenu.draw(g);
		}
	}

	public void move()
	{
		Platform p;
		for (Iterator<Platform> it = this.platforms.iterator(); it.hasNext();)
		{
			p = it.next();
			if (p.getSpring() != null)
			{
				if ((this.player.getVelocityY() > 0.0D)
						&& (this.player.getFeet().intersects(p.getSpring()
								.getRect())))
				{
					this.player.springBounce();
					p.setSprung();
				}
			}
			if ((this.player.getVelocityY() > 0.0D)
					&& (this.player.getFeet().intersects(p.getRect())))
			{
				if (!(p instanceof BrokenPlatform))
				{
					this.player.setY(p.getRect().getY() + Platform.getHeight()
							- this.player.getHeight());
					this.player.bounce();
					if ((p instanceof DisappearingPlatform))
					{
						it.remove();
					}
				}
				else
				{
					((BrokenPlatform) p).setBroken();
				}
			}
			if (((p instanceof BrokenPlatform))
					&& (((BrokenPlatform) p).isBroken()))
			{
				p.setY(p.getY() + 5.0D);
			}
		}
		if ((this.left) && (this.player.getVelocityX() > -20.0D))
		{
			this.player.setVelocityX(this.player.getVelocityX() - 1.5D);
		}
		else if ((this.right) && (this.player.getVelocityX() < 20.0D))
		{
			this.player.setVelocityX(this.player.getVelocityX() + 1.5D);
		}
		if (this.player.getVelocityY() < 15.0D)
		{
			this.player.accelerate();
		}
		if (this.up)
		{
			this.player.setVelocityY(-15.0D);
		}
		this.player
		.setX((int) (this.player.getX() + this.player.getVelocityX()));
		if (this.player.getX() < -this.player.getWidth() / 2)
		{
			this.player.setX(this.SCREENWIDTH - this.player.getWidth() / 2);
		}
		else if (this.player.getX() > this.SCREENWIDTH - this.player.getWidth()
				/ 2)
		{
			this.player.setX(-this.player.getWidth() / 2);
		}
		if ((this.player.getY() > this.SCREENHEIGHT / 2)
				|| (this.player.getVelocityY() > 0.0D))
		{
			this.player.setY((int) (this.player.getY() + this.player
					.getVelocityY()));
		}
		else
		{
			scroll();
		}
		for (Platform mp : this.platforms)
		{
			if ((mp instanceof MovingPlatform))
			{
				((MovingPlatform) mp).move();
			}
		}
		if (this.player.getY() > this.SCREENHEIGHT)
		{
			endGame();
		}
	}

	private void endGame()
	{
		this.gameOver = true;
		if (!this.cheatUsed)
		{
			for (int i = 0; i < this.ID.length; i++)
			{
				if (this.score > this.prefs.getInt(this.ID[i], 0))
				{
					for (int j = this.ID.length - 1; j > i; j--)
					{
						this.prefs.putInt(this.ID[j],
								this.prefs.getInt(this.ID[(j - 1)], 0));
					}
					this.prefs.putInt(this.ID[i], (int) this.score);
					break;
				}
			}
		}
	}

	private void scroll()
	{
		for (Iterator<Platform> it = this.platforms.iterator(); it.hasNext();)
		{
			Platform p = it.next();
			p.setY(p.getY() - this.player.getVelocityY());
			if (p.getY() > this.SCREENHEIGHT)
			{
				it.remove();
			}
		}
		this.score -= this.player.getVelocityY();
		if (this.blue > 1.0D)
		{
			this.blue += this.player.getVelocityY() / 100.0D;
		}
		if ((this.blue < 40.0D) && (this.blue > 20.0D))
		{
			this.red -= this.player.getVelocityY() / 20.0D;
		}
		if (this.blue < 20.0D)
		{
			if (this.red > 1.0D)
			{
				this.red += this.player.getVelocityY() / 100.0D;
			}
		}
		if ((this.blue < 40.0D) && (this.blue > 20.0D))
		{
			this.green -= this.player.getVelocityY() / 100.0D;
		}
		if ((this.blue < 20.0D) && (this.green > 1.0D))
		{
			this.green += this.player.getVelocityY() / 100.0D;
		}
		this.scoreLoop -= this.player.getVelocityY();
		if (this.scoreLoop > 2000.0D)
		{
			this.scoreLoop -= 2000.0D;
			if (this.probability > 1.0D)
			{
				this.probability *= 0.8D;
			}
			generatePlatforms();
		}
	}

	private void generatePlatforms()
	{
		double onlyGen = Math.random() * this.probability;
		if (onlyGen > 0.9D)
		{
			generateNormal();
		}
		else if (onlyGen > 0.4D)
		{
			generateOnlyMoving();
		}
		else if (onlyGen > 0.0D)
		{
			generateOnlyDisappearing();
		}
		for (int i = 11; i < 21; i++)
		{
			int density = (int) (Math.random() * this.probability) + 1;
			for (int j = 0; j < density; j++)
			{
				double rX = Math.random()
						* (this.SCREENWIDTH - Platform.getWidth());
				double rY = Math.random() * 100.0D - i * 100;
				boolean valid = false;
				while (!valid)
				{
					rX = Math.random()
							* (this.SCREENWIDTH - Platform.getWidth());
					rY = Math.random() * 100.0D - i * 100;
					valid = true;
					for (Platform p : this.platforms)
					{
						if (Math.abs(p.getX() - rX) < Platform.getWidth())
						{
							if (Math.abs(p.getY() - rY) < Platform.getHeight())
							{
								valid = false;
								break;
							}
						}
					}
				}
				double typeGen = Math.random();
				if (typeGen > 0.5D)
				{
					if (Math.random() > 0.9D)
					{
						this.platforms.add(new Platform(rX, rY, new Spring(rX,
								rY - 15.0D)));
					}
					else
					{
						this.platforms.add(new Platform(rX, rY));
					}
				}
				else if (typeGen > 0.1D)
				{
					if (Math.random() > 0.95D)
					{
						this.platforms.add(new MovingPlatform(rX, rY,
								new Spring(rX, rY - 15.0D)));
					}
					else
					{
						this.platforms.add(new MovingPlatform(rX, rY));
					}
				}
				else
				{
					this.platforms.add(new DisappearingPlatform(rX, rY));
				}
			}
			double typeGen = Math.random();
			if (typeGen > 0.5D)
			{
				double rX = Math.random()
						* (this.SCREENWIDTH - Platform.getWidth());
				double rY = Math.random() * 100.0D - i * 100;
				this.platforms.add(new BrokenPlatform(rX, rY));
			}
		}
	}

	private void generateNormal()
	{
		for (int i = 1; i < 11; i++)
		{
			int density = (int) (Math.random() * this.probability) + 1;
			for (int j = 0; j < density; j++)
			{
				double rX = Math.random()
						* (this.SCREENWIDTH - Platform.getWidth());
				double rY = Math.random() * 100.0D - i * 100;
				boolean valid = false;
				while (!valid)
				{
					rX = Math.random()
							* (this.SCREENWIDTH - Platform.getWidth());
					rY = Math.random() * 100.0D - i * 100;
					valid = true;
					for (Platform p : this.platforms)
					{
						if (Math.abs(p.getX() - rX) < Platform.getWidth())
						{
							if (Math.abs(p.getY() - rY) < Platform.getHeight())
							{
								valid = false;
								break;
							}
						}
					}
				}
				double typeGen = Math.random();
				if (typeGen > 0.5D)
				{
					if (Math.random() > 0.9D)
					{
						this.platforms.add(new Platform(rX, rY, new Spring(rX,
								rY - 15.0D)));
					}
					else
					{
						this.platforms.add(new Platform(rX, rY));
					}
				}
				else if (typeGen > 0.1D)
				{
					if (Math.random() > 0.95D)
					{
						this.platforms.add(new MovingPlatform(rX, rY,
								new Spring(rX, rY - 15.0D)));
					}
					else
					{
						this.platforms.add(new MovingPlatform(rX, rY));
					}
				}
				else
				{
					this.platforms.add(new DisappearingPlatform(rX, rY));
				}
			}
		}
	}

	private void generateOnlyMoving()
	{
		for (int i = 1; i < 11; i++)
		{
			int density = (int) (Math.random() * this.probability) + 1;
			if (i < 11)
			{
				for (int j = 0; j < density; j++)
				{
					double rX = Math.random()
							* (this.SCREENWIDTH - Platform.getWidth());
					double rY = Math.random() * 100.0D - i * 100;
					boolean valid = false;
					while (!valid)
					{
						rX = Math.random()
								* (this.SCREENWIDTH - Platform.getWidth());
						rY = Math.random() * 100.0D - i * 100;
						valid = true;
						for (Platform p : this.platforms)
						{
							if (Math.abs(p.getX() - rX) < Platform.getWidth())
							{
								if (Math.abs(p.getY() - rY) < Platform
										.getHeight())
								{
									valid = false;
									break;
								}
							}
						}
					}
					this.platforms.add(new MovingPlatform(rX, rY));
				}
			}
		}
	}

	private void generateOnlyDisappearing()
	{
		for (int i = 1; i < 11; i++)
		{
			int density = (int) (Math.random() * this.probability) + 1;
			if (i < 11)
			{
				for (int j = 0; j < density; j++)
				{
					double rX = Math.random()
							* (this.SCREENWIDTH - Platform.getWidth());
					double rY = Math.random() * 100.0D - i * 100;
					boolean valid = false;
					while (!valid)
					{
						rX = Math.random()
								* (this.SCREENWIDTH - Platform.getWidth());
						rY = Math.random() * 100.0D - i * 100;
						valid = true;
						for (Platform p : this.platforms)
						{
							if (Math.abs(p.getX() - rX) < Platform.getWidth())
							{
								if (Math.abs(p.getY() - rY) < Platform
										.getHeight())
								{
									valid = false;
									break;
								}
							}
						}
					}
					this.platforms.add(new DisappearingPlatform(rX, rY));
				}
			}
		}
	}

	private void reset()
	{
		this.player.setX(this.SCREENWIDTH / 2 - this.player.getWidth() / 2);
		this.player.setY(this.SCREENHEIGHT / 2 + 100);

		this.probability = 8.0D;

		this.platforms = new ArrayList();
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < this.probability; j++)
			{
				double rX = Math.random()
						* (this.SCREENWIDTH - Platform.getWidth());
				double rY = Math.random() * 100.0D + this.SCREENHEIGHT - i
						* 100;
				boolean valid = false;
				while (!valid)
				{
					rX = Math.random()
							* (this.SCREENWIDTH - Platform.getWidth());
					rY = Math.random() * 100.0D + this.SCREENHEIGHT - i * 100;
					valid = true;
					for (Platform p : this.platforms)
					{
						if (Math.abs(p.getX() - rX) < Platform.getWidth())
						{
							if (Math.abs(p.getY() - rY) < Platform.getHeight())
							{
								valid = false;
								break;
							}
						}
					}
				}
				this.platforms.add(new Platform(rX, rY));
			}
		}
		generatePlatforms();
		this.platforms.add(new Platform(this.SCREENWIDTH / 2
				- Platform.getWidth() / 2, this.SCREENHEIGHT / 2 + 300));

		this.score = 0.0D;
		this.scoreLoop = 0.0D;
		this.red = 0.0D;
		this.green = 20.0D;
		this.blue = 150.0D;

		this.player.setVelocityX(0.0D);
		this.player.setVelocityY(0.0D);

		this.godMode = false;
		this.up = false;
		this.cheatUsed = false;

		this.gameOver = false;
		this.main = false;
	}

	@Override
	public void keyPressed(KeyEvent ke)
	{
		if ((ke.getKeyCode() == 37) || (ke.getKeyCode() == 65))
		{
			this.left = true;
			this.right = false;
		}
		else if ((ke.getKeyCode() == 39) || (ke.getKeyCode() == 68))
		{
			this.right = true;
			this.left = false;
		}
		else if ((ke.getKeyCode() == 49) && (this.main))
		{
			Platform.setWidth(100);
			for (Platform p : this.platforms)
			{
				p.getRect().setSize(Platform.getWidth(), Platform.getHeight());
			}
		}
		else if ((ke.getKeyCode() == 50) && (this.main))
		{
			Platform.setWidth(60);
			for (Platform p : this.platforms)
			{
				p.getRect().setSize(Platform.getWidth(), Platform.getHeight());
			}
		}
		else if ((ke.getKeyCode() == 51) && (this.main))
		{
			Platform.setWidth(30);
			for (Platform p : this.platforms)
			{
				p.getRect().setSize(Platform.getWidth(), Platform.getHeight());
			}
		}
		else if ((ke.getKeyCode() == 80) && (!this.main) && (!this.gameOver))
		{
			this.pause = (!this.pause);
		}
		else if (ke.getKeyCode() == 40)
		{
			this.godMode = (!this.godMode);
			if (!this.godMode)
			{
				this.up = false;
			}
		}
		else if ((ke.getKeyCode() == 38) && (this.godMode))
		{
			this.up = (!this.up);
			this.cheatUsed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent ke)
	{
		if ((ke.getKeyCode() == 37) || (ke.getKeyCode() == 65))
		{
			this.left = false;
		}
		else if ((ke.getKeyCode() == 39) || (ke.getKeyCode() == 68))
		{
			this.right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
	}

	@Override
	public void mouseMoved(MouseEvent me)
	{
		this.mouseX = me.getX();
		this.mouseY = me.getY();
	}

	public int getMouseX()
	{
		return this.mouseX;
	}

	public int getMouseY()
	{
		return this.mouseY;
	}

	public int getSCREENWIDTH()
	{
		return this.SCREENWIDTH;
	}

	public int getSCREENHEIGHT()
	{
		return this.SCREENHEIGHT;
	}

	public double getScore()
	{
		return this.score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public Preferences getPrefs()
	{
		return this.prefs;
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
		if (this.gameOver)
		{
			if (this.gameOverMenu.getPlayAgainRect().contains(me.getPoint()))
			{
				this.gameOver = false;
				this.pause = false;
				reset();
			}
			else if (this.gameOverMenu.getMainMenuRect()
					.contains(me.getPoint()))
			{
				this.gameOver = false;
				this.pause = false;
				this.main = true;
			}
			else if (this.gameOverMenu.getQuitRect().contains(me.getPoint()))
			{
				System.exit(0);
			}
		}
		else if (this.pause)
		{
			if (this.pauseMenu.getResumeRect().contains(me.getPoint()))
			{
				this.pause = false;
			}
			else if (this.pauseMenu.getMainMenuRect().contains(me.getPoint()))
			{
				this.pause = false;
				this.main = true;
			}
			else if (this.pauseMenu.getQuitRect().contains(me.getPoint()))
			{
				System.exit(0);
			}
		}
		else if (this.main)
		{
			if ((this.mainMenu.isOnInstructions())
					|| (this.mainMenu.isOnLeaderboard()))
			{
				if (this.mainMenu.getOKRect().contains(me.getPoint()))
				{
					this.mainMenu.setOnInstructions(false);
					this.mainMenu.setOnLeaderboard(false);
				}
			}
			else if (this.mainMenu.getPlayRect().contains(me.getPoint()))
			{
				reset();
			}
			else if (this.mainMenu.getInstructionsRect()
					.contains(me.getPoint()))
			{
				this.mainMenu.setOnInstructions(true);
			}
			else if (this.mainMenu.getLeaderboardRect().contains(me.getPoint()))
			{
				this.mainMenu.setOnLeaderboard(true);
			}
			else if (this.mainMenu.getQuitRect().contains(me.getPoint()))
			{
				System.exit(0);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
	}

	public static void main(String [] args)
	{
		JFrame frame = new JFrame("Jumper");
		frame.setIconImage(new ImageIcon(Jumper.class.getResource("Icon.png"))
		.getImage());
		frame.getContentPane().add(new Jumper());
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}

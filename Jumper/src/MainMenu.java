import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class MainMenu
{
	private Image play;
	private Image play2;
	private Image instructions;
	private Image instructions2;
	private Image leaderboard;
	private Image leaderboard2;
	private Image quit;
	private Image quit2;
	private Image OK;
	private Image OK2;
	private final Rectangle playRect;
	private final Rectangle instructionsRect;
	private final Rectangle leaderboardRect;
	private final Rectangle quitRect;
	private final Rectangle OKRect;
	private Image title;
	private Image dark;
	private Image instructionsPage;
	private Image leaderboardPage;
	private boolean onInstructions;
	private boolean onLeaderboard;
	private static Jumper panel;

	public MainMenu()
	{
		loadImages();
		this.playRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.play.getWidth(null) / 2, panel.getSCREENHEIGHT() - 300, this.play.getWidth(null), this.play.getHeight(null));
		this.instructionsRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.instructions.getWidth(null) / 2, panel.getSCREENHEIGHT() - 240, this.instructions.getWidth(null), this.instructions.getHeight(null));
		this.leaderboardRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.leaderboard.getWidth(null) / 2, panel.getSCREENHEIGHT() - 180, this.instructions.getWidth(null), this.instructions.getHeight(null));
		this.quitRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.quit.getWidth(null) / 2, panel.getSCREENHEIGHT() - 120, this.quit.getWidth(null), this.quit.getHeight(null));
		this.OKRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.OK.getWidth(null) / 2, panel.getSCREENHEIGHT() - 60, this.OK.getWidth(null), this.OK.getHeight(null));
		this.onInstructions = false;
		this.onLeaderboard = false;
	}

	public void draw(Graphics g)
	{
		g.drawImage(this.title, (panel.getSCREENWIDTH() - this.title.getWidth(null)) / 2, (panel.getSCREENHEIGHT() - this.title.getHeight(null)) / 2, null);
		if ((!this.onInstructions) && (!this.onLeaderboard))
		{
			g.drawImage(this.play, this.playRect.x, this.playRect.y, null);
			g.drawImage(this.instructions, this.instructionsRect.x, this.instructionsRect.y, null);
			g.drawImage(this.leaderboard, this.leaderboardRect.x, this.leaderboardRect.y, null);
			g.drawImage(this.quit, this.quitRect.x, this.quitRect.y, null);
			if ((panel.getMouseX() > this.playRect.x) && 
					(panel.getMouseX() < this.play.getWidth(null) + this.playRect.x) && 
					(panel.getMouseY() > this.playRect.y) && 
					(panel.getMouseY() < this.play.getHeight(null) + this.playRect.y))
			{
				g.drawImage(this.play2, this.playRect.x, this.playRect.y, null);
			}
			else
			{
				if (panel.getMouseX() > this.instructionsRect.x) {
					if ((panel.getMouseX() < this.instructions.getWidth(null) + this.instructionsRect.x) && 
							(panel.getMouseY() > this.instructionsRect.y)) {
						if (panel.getMouseY() < this.instructions.getHeight(null) + this.instructionsRect.y)
						{
							g.drawImage(this.instructions2, this.instructionsRect.x, 
									this.instructionsRect.y, null);
							return;
						}
					}
				}
				if (panel.getMouseX() > this.leaderboardRect.x) {
					if ((panel.getMouseX() < this.leaderboard.getWidth(null) + this.leaderboardRect.x) && 
							(panel.getMouseY() > this.leaderboardRect.y)) {
						if (panel.getMouseY() < this.leaderboard.getHeight(null) + this.leaderboardRect.y)
						{
							g.drawImage(this.leaderboard2, this.leaderboardRect.x, 
									this.leaderboardRect.y, null);
							return;
						}
					}
				}
				if ((panel.getMouseX() > this.quitRect.x) && 
						(panel.getMouseX() < this.quit.getWidth(null) + this.quitRect.x) && 
						(panel.getMouseY() > this.quitRect.y) && 
						(panel.getMouseY() < this.quit.getHeight(null) + this.quitRect.y)) {
					g.drawImage(this.quit2, this.quitRect.x, this.quitRect.y, null);
				}
			}
		}
		else if (this.onInstructions)
		{
			g.drawImage(this.dark, 0, 0, null);
			g.drawImage(this.instructionsPage, 0, 0, null);
			g.drawImage(this.OK, this.OKRect.x, this.OKRect.y, null);
			if ((panel.getMouseX() > this.OKRect.x) && 
					(panel.getMouseX() < this.OK.getWidth(null) + this.OKRect.x) && 
					(panel.getMouseY() > this.OKRect.y) && 
					(panel.getMouseY() < this.OK.getHeight(null) + this.OKRect.y)) {
				g.drawImage(this.OK2, this.OKRect.x, this.OKRect.y, null);
			}
		}
		else if (this.onLeaderboard)
		{
			g.drawImage(this.dark, 0, 0, null);
			g.drawImage(this.leaderboardPage, 0, 0, null);

			g.setColor(Color.green);
			g.setFont(new Font("Good Times", 1, 30));
			for (int i = 0; i < 5; i++) {
				g.drawString(i + 1 + "-     " + panel.getPrefs().getInt(new StringBuilder().append(i).toString(), 0), panel.getSCREENWIDTH() / 2 - 75, 100 * i + 300);
			}
			g.drawImage(this.OK, this.OKRect.x, this.OKRect.y, null);
			if ((panel.getMouseX() > this.OKRect.x) && 
					(panel.getMouseX() < this.OK.getWidth(null) + this.OKRect.x) && 
					(panel.getMouseY() > this.OKRect.y) && 
					(panel.getMouseY() < this.OK.getHeight(null) + this.OKRect.y)) {
				g.drawImage(this.OK2, this.OKRect.x, this.OKRect.y, null);
			}
		}
	}

	private void loadImages()
	{
		this.title = new ImageIcon(getClass().getResource("MainMenuImages/Background.png")).getImage().getScaledInstance(panel.getSCREENWIDTH(), panel.getSCREENHEIGHT(), 0);

		this.dark = new ImageIcon(getClass().getResource("MainMenuImages/Dark.png")).getImage().getScaledInstance(panel.getSCREENWIDTH(), panel.getSCREENHEIGHT(), 0);

		this.play = new ImageIcon(getClass().getResource("MainMenuImages/Play.png")).getImage();
		this.instructions = new ImageIcon(getClass().getResource("MainMenuImages/Instructions.png")).getImage();
		this.leaderboard = new ImageIcon(getClass().getResource("MainMenuImages/Leaderboard.png")).getImage();
		this.quit = new ImageIcon(getClass().getResource("MainMenuImages/Quit.png")).getImage();

		this.play2 = new ImageIcon(getClass().getResource("MainMenuImages/Play2.png")).getImage();
		this.instructions2 = new ImageIcon(getClass().getResource("MainMenuImages/Instructions2.png")).getImage();
		this.leaderboard2 = new ImageIcon(getClass().getResource("MainMenuImages/Leaderboard2.png")).getImage();
		this.quit2 = new ImageIcon(getClass().getResource("MainMenuImages/Quit2.png")).getImage();

		this.instructionsPage = new ImageIcon(getClass().getResource("MainMenuImages/InstructionsPage.png")).getImage().getScaledInstance(panel.getSCREENWIDTH(), panel.getSCREENHEIGHT(), 0);
		this.leaderboardPage = new ImageIcon(getClass().getResource("MainMenuImages/LeaderboardPage.png")).getImage().getScaledInstance(panel.getSCREENWIDTH(), panel.getSCREENHEIGHT(), 0);

		this.OK = new ImageIcon(getClass().getResource("MainMenuImages/OK.png")).getImage();
		this.OK2 = new ImageIcon(getClass().getResource("MainMenuImages/OK2.png")).getImage();
	}

	public static void setPanel(Jumper jumper)
	{
		panel = jumper;
	}

	public Rectangle getPlayRect()
	{
		return this.playRect;
	}

	public Rectangle getInstructionsRect()
	{
		return this.instructionsRect;
	}

	public Rectangle getLeaderboardRect()
	{
		return this.leaderboardRect;
	}

	public Rectangle getQuitRect()
	{
		return this.quitRect;
	}

	public Rectangle getOKRect()
	{
		return this.OKRect;
	}

	public boolean isOnInstructions()
	{
		return this.onInstructions;
	}

	public void setOnInstructions(boolean onInstructions)
	{
		this.onInstructions = onInstructions;
	}

	public boolean isOnLeaderboard()
	{
		return this.onLeaderboard;
	}

	public void setOnLeaderboard(boolean onLeaderboard)
	{
		this.onLeaderboard = onLeaderboard;
	}
}

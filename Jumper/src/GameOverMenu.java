import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class GameOverMenu
{
  private Image main;
  private Image playAgain;
  private Image playAgainClicked;
  private Image mainMenu;
  private Image mainMenuClicked;
  private Image quit;
  private Image quitClicked;
  private final Rectangle playAgainRect;
  private final Rectangle mainMenuRect;
  private final Rectangle quitRect;
  private static Jumper panel;
  
  public GameOverMenu()
  {
    loadImages();
    this.playAgainRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.playAgain.getWidth(null) / 2, 300, this.playAgain.getWidth(null), this.playAgain.getHeight(null));
    this.mainMenuRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.mainMenu.getWidth(null) / 2, 500, this.mainMenu.getWidth(null), this.mainMenu.getHeight(null));
    this.quitRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.quit.getWidth(null) / 2, 700, this.quit.getWidth(null), this.quit.getHeight(null));
  }
  
  public void draw(Graphics g)
  {
    g.drawImage(this.main, 0, 0, null);
    g.drawImage(this.playAgain, this.playAgainRect.x, this.playAgainRect.y, null);
    g.drawImage(this.mainMenu, this.mainMenuRect.x, this.mainMenuRect.y, null);
    g.drawImage(this.quit, this.quitRect.x, this.quitRect.y, null);
    if ((panel.getMouseX() > this.playAgainRect.x) && (panel.getMouseX() < this.playAgain.getWidth(null) + this.playAgainRect.x) && 
      (panel.getMouseY() > this.playAgainRect.y) && (panel.getMouseY() < this.playAgain.getHeight(null) + this.playAgainRect.y)) {
      g.drawImage(this.playAgainClicked, this.playAgainRect.x, this.playAgainRect.y, null);
    } else if ((panel.getMouseX() > this.mainMenuRect.x) && (panel.getMouseX() < this.mainMenu.getWidth(null) + this.mainMenuRect.x) && 
      (panel.getMouseY() > this.mainMenuRect.y) && (panel.getMouseY() < this.mainMenu.getHeight(null) + this.mainMenuRect.y)) {
      g.drawImage(this.mainMenuClicked, this.mainMenuRect.x, this.mainMenuRect.y, null);
    } else if ((panel.getMouseX() > this.quitRect.x) && (panel.getMouseX() < this.quit.getWidth(null) + this.quitRect.x) && 
      (panel.getMouseY() > this.quitRect.y) && (panel.getMouseY() < this.quit.getHeight(null) + this.quitRect.y)) {
      g.drawImage(this.quitClicked, this.quitRect.x, this.quitRect.y, null);
    }
    g.setColor(Color.green);
    g.setFont(new Font("Consolas", 1, 50));
    g.drawString("YOUR SCORE:", panel.getSCREENWIDTH() / 2 - 125, 50);
    g.drawString(Integer.toString((int)panel.getScore()), panel.getSCREENWIDTH() / 2 - 50, 150);
  }
  
  private void loadImages()
  {
    this.main = new ImageIcon(getClass().getResource("GameOverMenuImages/main.png")).getImage();
    this.playAgain = new ImageIcon(getClass().getResource("GameOverMenuImages/PlayAgain.png")).getImage();
    this.playAgainClicked = new ImageIcon(getClass().getResource("GameOverMenuImages/PlayAgain2.png")).getImage();
    this.mainMenu = new ImageIcon(getClass().getResource("GameOverMenuImages/MainMenu.png")).getImage();
    this.mainMenuClicked = new ImageIcon(getClass().getResource("GameOverMenuImages/MainMenu2.png")).getImage();
    this.quit = new ImageIcon(getClass().getResource("GameOverMenuImages/Quit.png")).getImage();
    this.quitClicked = new ImageIcon(getClass().getResource("GameOverMenuImages/Quit2.png")).getImage();
  }
  
  public static void setPanel(Jumper jumper)
  {
    panel = jumper;
  }
  
  public Rectangle getPlayAgainRect()
  {
    return this.playAgainRect;
  }
  
  public Rectangle getMainMenuRect()
  {
    return this.mainMenuRect;
  }
  
  public Rectangle getQuitRect()
  {
    return this.quitRect;
  }
}

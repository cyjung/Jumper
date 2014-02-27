import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class PauseMenu
{
  private Image main;
  private Image resume;
  private Image resume2;
  private Image mainMenu;
  private Image mainMenuClicked;
  private Image quit;
  private Image quit2;
  private final Rectangle resumeRect;
  private final Rectangle mainMenuRect;
  private final Rectangle quitRect;
  private static Jumper panel;
  
  public PauseMenu()
  {
    loadImages();
    this.resumeRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.resume.getWidth(null) / 2, panel.getSCREENHEIGHT() / 4 - this.resume.getHeight(null) / 2, this.resume.getWidth(null), this.resume.getHeight(null));
    this.mainMenuRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.mainMenu.getWidth(null) / 2, panel.getSCREENHEIGHT() / 2 - this.mainMenu.getHeight(null) / 2, this.mainMenu.getWidth(null), this.mainMenu.getHeight(null));
    this.quitRect = new Rectangle(panel.getSCREENWIDTH() / 2 - this.quit.getWidth(null) / 2, panel.getSCREENHEIGHT() * 3 / 4 - this.quit.getHeight(null) / 2, this.quit.getWidth(null), this.quit.getHeight(null));
  }
  
  public void draw(Graphics g)
  {
    g.drawImage(this.main, 0, 0, null);
    g.drawImage(this.resume, this.resumeRect.x, this.resumeRect.y, null);
    g.drawImage(this.mainMenu, this.mainMenuRect.x, this.mainMenuRect.y, null);
    g.drawImage(this.quit, this.quitRect.x, this.quitRect.y, null);
    if ((panel.getMouseX() > this.resumeRect.x) && (panel.getMouseX() < this.resume.getWidth(null) + this.resumeRect.x) && 
      (panel.getMouseY() > this.resumeRect.y) && (panel.getMouseY() < this.resume.getHeight(null) + this.resumeRect.y)) {
      g.drawImage(this.resume2, this.resumeRect.x, this.resumeRect.y, null);
    } else if ((panel.getMouseX() > this.mainMenuRect.x) && (panel.getMouseX() < this.mainMenu.getWidth(null) + this.mainMenuRect.x) && 
      (panel.getMouseY() > this.mainMenuRect.y) && (panel.getMouseY() < this.mainMenu.getHeight(null) + this.mainMenuRect.y)) {
      g.drawImage(this.mainMenuClicked, this.mainMenuRect.x, this.mainMenuRect.y, null);
    } else if ((panel.getMouseX() > this.quitRect.x) && (panel.getMouseX() < this.quit.getWidth(null) + this.quitRect.x) && 
      (panel.getMouseY() > this.quitRect.y) && (panel.getMouseY() < this.quit.getHeight(null) + this.quitRect.y)) {
      g.drawImage(this.quit2, this.quitRect.x, this.quitRect.y, null);
    }
  }
  
  private void loadImages()
  {
    this.main = new ImageIcon(getClass().getResource("PauseMenuImages/main.png")).getImage();
    this.resume = new ImageIcon(getClass().getResource("PauseMenuImages/Resume.png")).getImage();
    this.resume2 = new ImageIcon(getClass().getResource("PauseMenuImages/Resume2.png")).getImage();
    this.mainMenu = new ImageIcon(getClass().getResource("PauseMenuImages/MainMenu.png")).getImage();
    this.mainMenuClicked = new ImageIcon(getClass().getResource("PauseMenuImages/MainMenu2.png")).getImage();
    this.quit = new ImageIcon(getClass().getResource("PauseMenuImages/Quit.png")).getImage();
    this.quit2 = new ImageIcon(getClass().getResource("PauseMenuImages/Quit2.png")).getImage();
  }
  
  public static void setPanel(Jumper jumper)
  {
    panel = jumper;
  }
  
  public Rectangle getResumeRect()
  {
    return this.resumeRect;
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

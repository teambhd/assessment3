package states;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;


public class GameOverState extends BasicGameState {
	
    private int stateID;
	private Image menuBackground;
	public static TrueTypeFont titleFont, bodyFont, smallButtonFont;
    private String againString, menuString;
    private int againStringWidth, againStringHeight;
    private int menuStringWidth, menuStringHeight;
    private boolean mouseBeenReleased;
    
	public GameOverState(int state) {
		mouseBeenReleased = false;
	    stateID = state;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		menuBackground = new Image("res/graphics/menu_background.png");
        		        
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/ubuntu-bold.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			titleFont = new TrueTypeFont(awtFont.deriveFont(58f), true);
			bodyFont = new TrueTypeFont(awtFont.deriveFont(20f), true);
            smallButtonFont = new TrueTypeFont(awtFont.deriveFont(30f), true);
		}
        
        catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		menuBackground.draw(0, 0);
        
        // Draw the page title
        titleFont.drawString(17, 10, "Game Over", Color.lightGray);
        
        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL
                                    
        // Draw the play again string
        againString = "Play Again";
        againStringWidth = smallButtonFont.getWidth(againString);
        againStringHeight = smallButtonFont.getHeight(againString);
        
		if (posX >= 20 && posX <= (20 + againStringWidth) && posY >= 550 && posY <= (550 + againStringHeight)) {
			smallButtonFont.drawString(20, 550, againString, Color.white);
		} 
        
        else {
			smallButtonFont.drawString(20, 550, againString, Color.lightGray);
		}
        
        // Draw the menu string
        menuString = "Menu";
        menuStringWidth = smallButtonFont.getWidth(menuString);
        menuStringHeight = smallButtonFont.getHeight(menuString);
        
		if (posX >= (40 + againStringWidth) && posX <= (40 + againStringWidth + menuStringWidth) && posY >= 550 && posY <= (550 + menuStringHeight)) {
			smallButtonFont.drawString(40 + againStringWidth, 550, menuString, Color.white);
		} 
        
        else {
			smallButtonFont.drawString(40 + againStringWidth, 550, menuString, Color.lightGray);
		}     
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL
        
		if (!mouseBeenReleased) {
			if (!Mouse.isButtonDown(0)) {
				mouseBeenReleased=true;
			}
		}
		
        // handle clicking the play again button
		if (posX >= 20 && posX <= (20 + againStringWidth) && posY >= 550 && posY <= (550 + againStringHeight) && Mouse.isButtonDown(0) && mouseBeenReleased) {
			mouseBeenReleased = false;
			sbg.enterState(1); // PlayState
		} 
        
		if (posX >= (40 + againStringWidth) && posX <= (40 + againStringWidth + menuStringWidth) && posY >= 550 && posY <= (550 + menuStringHeight) && Mouse.isButtonDown(0) && mouseBeenReleased) {
			mouseBeenReleased = false;
			sbg.enterState(0); // MenuState
        }
	}

	public int getID() {
		return stateID;
	}
	
}
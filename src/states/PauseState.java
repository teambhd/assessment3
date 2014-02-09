package states;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Mouse;

import java.awt.Font;
import java.io.InputStream;

public class PauseState extends BasicGameState {
	
    private int stateID;
    
	public static TrueTypeFont titleFont, smallButtonFont;
	private Image menuBackground;
    private String backString;
    private int backStringHeight, backStringWidth;
    
	public PauseState(int state) {
	    stateID = state;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		menuBackground = new Image("res/menu_graphics/background.png");
        
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/ubuntu-bold.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			titleFont = new TrueTypeFont(awtFont.deriveFont(58f), true);
            smallButtonFont = new TrueTypeFont(awtFont.deriveFont(30f), true);
		}
        
        catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		menuBackground.draw(0, 0);
        
        // Draw the page title
        titleFont.drawString(17, 10, "Paused", Color.lightGray);
        
        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL
		
        // Draw the resume string
        backString = "Resume";
        backStringWidth = smallButtonFont.getWidth(backString);
        backStringHeight = smallButtonFont.getHeight(backString);
        
		if (posX >= 20 && posX <= (20 + backStringWidth) && posY >= 550 && posY <= (550 + backStringHeight)) {
			smallButtonFont.drawString(20, 550, backString, Color.white);
		} 
        
        else {
			smallButtonFont.drawString(20, 550, backString, Color.lightGray);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // Get keyboard input for reference below
		Input input = gc.getInput();
        
        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL
        		
        // handle clicking the back button
		if (posX >= 20 && posX <= (20 + backStringWidth) && posY >= 550 && posY <= (550 + backStringHeight) && Mouse.isButtonDown(0)) {
			sbg.enterState(1); // PlayState            
		}         		
		
        // handle the P or space keys, which also resume
		if (input.isKeyPressed(Input.KEY_P) || input.isKeyPressed(Input.KEY_SPACE)) {
			sbg.enterState(1);
		}
	}
	
	public int getID() {
		return stateID;
	}
}

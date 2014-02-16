package states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;


public class PauseState extends BasicGameState {
	
    private int stateID;
    
	private static AngelCodeFont titleFont, smallButtonFont;
	private Image menuBackground;
    private String backString;
    private int backStringHeight, backStringWidth;
    
	public PauseState(int state) {
	    stateID = state;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		menuBackground = new Image("res/graphics/menu_background.png");
        
		try {
            titleFont = new AngelCodeFont("res/fonts/fsb60.fnt", "res/fonts/fsb60.png");
            smallButtonFont = new AngelCodeFont("res/fonts/fsb30.fnt", "res/fonts/fsb30.png");
		}
        
        catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		menuBackground.draw(0, 0);
        
        // Draw the page title
        titleFont.drawString(18, 10, "Paused", Color.lightGray);
        
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

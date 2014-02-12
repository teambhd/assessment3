package states;

import java.awt.Font;
import java.io.InputStream;
import java.awt.Desktop;
import java.net.URI;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;


public class MenuState extends BasicGameState {
    
    private int stateID;

	public static TrueTypeFont titleFont, mainButtonFont, smallButtonFont;
	private Image menuBackground;
    private String playString, websiteString, controlsString;
    private int playStringWidth, playStringHeight;
    private int websiteStringWidth, websiteStringHeight;
    private int controlsStringWidth, controlsStringHeight;
    private boolean mouseBeenReleased;

	public MenuState(int state) {
		mouseBeenReleased = false;
	    stateID = state;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		menuBackground = new Image("res/graphics/menu_background.png");
        
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/fira-sans-bold.ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			titleFont = new TrueTypeFont(awtFont.deriveFont(60f), true);
			mainButtonFont = new TrueTypeFont(awtFont.deriveFont(50f), true);
            smallButtonFont = new TrueTypeFont(awtFont.deriveFont(30f), true);
		}
        
        catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		menuBackground.draw(0, 0);
        
        // Draw the title and subtitle
        titleFont.drawString(18, 10, sbg.getTitle(), Color.lightGray);
        smallButtonFont.drawString(20, 80, "by Team BHD", Color.lightGray);

        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL

        // Draw the Play button
        playString = "Play";
        playStringWidth = mainButtonFont.getWidth(playString);
        playStringHeight = mainButtonFont.getHeight(playString);

		if (posX >= 20 && posX <= (20 + playStringWidth) && posY >= 480 && posY <= (480 + playStringHeight)) {
            mainButtonFont.drawString(19, 480, playString, Color.white);
		}
        
		else {
            mainButtonFont.drawString(19, 480, playString, Color.lightGray);
		}
        
        // Draw the controls string
        controlsString = "Help";
        controlsStringWidth = smallButtonFont.getWidth(controlsString);
        controlsStringHeight = smallButtonFont.getHeight(controlsString);
        
		if (posX >= 20 && posX <= (20 + controlsStringWidth) && posY >= 550 && posY <= (550 + controlsStringHeight)) {
			smallButtonFont.drawString(20, 550, controlsString, Color.white);
		} 
        
        else {
			smallButtonFont.drawString(20, 550, controlsString, Color.lightGray);
		}
        
        // Draw the website string
        websiteString = "Team Website";
        websiteStringWidth = smallButtonFont.getWidth(websiteString);
        websiteStringHeight = smallButtonFont.getHeight(websiteString);
        
		if (posX >= (40 + controlsStringWidth) && posX <= (40 + controlsStringWidth + websiteStringWidth) && posY >= 550 && posY <= (550 + websiteStringHeight)) {
			smallButtonFont.drawString(40 + controlsStringWidth, 550, websiteString, Color.white);
		} 
        
        else {
			smallButtonFont.drawString(40 + controlsStringWidth, 550, websiteString, Color.lightGray);
		}     
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL

		// Mapping Mouse coords onto graphics coords
		if (!this.mouseBeenReleased) {
			if (!Mouse.isButtonDown(0)) {
				this.mouseBeenReleased=true;
			}
		}
                
		if (this.mouseBeenReleased) {
            
            // Handle clicking of play button
    		if (posX >= 20 && posX <= (20 + playStringWidth) && posY >= 480 && posY <= (480 + playStringHeight)) {
				if (Mouse.isButtonDown(0)) {
					this.mouseBeenReleased = false;
					sbg.enterState(1); //PlayState
				}
			} 

            // Handle clicking of controls button
    		if (posX >= 20 && posX <= (20 + controlsStringWidth) && posY >= 550 && posY <= (550 + controlsStringHeight)) {
				if (Mouse.isButtonDown(0)) {
					this.mouseBeenReleased = false;
					sbg.enterState(4); //ControlsState
				}
			} 

            // Handle clicking of website button
    		if (posX >= (40 + controlsStringWidth) && posX <= (40 + controlsStringWidth + websiteStringWidth) && posY >= 550 && posY <= (550 + websiteStringHeight)) {
				this.mouseBeenReleased=false;
                // try {
                //     Desktop.getDesktop().browse(new URI("http://bhdwebsite.example/credits.html")); 
                // }
                // catch(Exception ex) {
                //        ex.printStackTrace();
                // }
			}
		}
	}

	public int getID() {
		return stateID;
	}
    
}
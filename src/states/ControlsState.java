package states;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;


public class ControlsState extends BasicGameState {
	
    private int stateID;
	private Image menuBackground;
	public static TrueTypeFont titleFont, bodyFont, smallButtonFont;
    private String backString;
    private int backStringHeight, backStringWidth;
    private boolean mouseBeenReleased;
    
	public ControlsState(int state) {
		mouseBeenReleased = false;
	    stateID = state;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		menuBackground = new Image("res/menu_graphics/background.png");
        		        
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
        titleFont.drawString(17, 10, "Help", Color.lightGray);
        
        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL
                
        String line1 = "The aim of the game is to send each flight to each of";
        String line2 = "the waypoints listed on its flight plan, while avoiding";
        String line3 = "collisions with other aircraft.";
                
        String line4 = "Flights can be selected by left-clicking on them.";
        
        String line5 = "Flights operate in either Navigator Mode or Plan Mode.";
        String line6 = "You can switch between these two modes using the";
        String line7 = "buttons in the left-hand column or by left-clicking on an";
        String line8 = "already-selected flight.";
        
        String line9 = "In Navigator Mode, the selected flight's altitude, speed and heading";
        String line10= "can be adjusted using the buttons in the left-hand column. Flights can";
        String line11= "also be told to take-off or land when in the vicinity of an airport.";
        
        String line12= "It is also possible to adjust a flight's altitude using the up and down";
        String line13= "arrow keys and to change headings by right-clicking within the circle";
        String line14= "surrounding the currently active flight.";
        
        String line15= "In Plan Mode the only possible interaction is to change the current";
        String line16= "flight's flight plan by dragging from one waypoint to another.";
            
        bodyFont.drawString(20, 100, line1, Color.lightGray);
        bodyFont.drawString(20, 120, line2, Color.lightGray);
        bodyFont.drawString(20, 140, line3, Color.lightGray);
            
        bodyFont.drawString(20, 180, line4, Color.lightGray);

        bodyFont.drawString(20, 220, line5, Color.lightGray);
        bodyFont.drawString(20, 240, line6, Color.lightGray);
        bodyFont.drawString(20, 260, line7, Color.lightGray);
        bodyFont.drawString(20, 280, line8, Color.lightGray);
    
        bodyFont.drawString(20, 320, line9, Color.lightGray);
        bodyFont.drawString(20, 340, line10, Color.lightGray);
        bodyFont.drawString(20, 360, line11, Color.lightGray);
        
        bodyFont.drawString(20, 400, line12, Color.lightGray);
        bodyFont.drawString(20, 420, line13, Color.lightGray);
        bodyFont.drawString(20, 440, line14, Color.lightGray);
        
        bodyFont.drawString(20, 480, line15, Color.lightGray);
        bodyFont.drawString(20, 500, line16, Color.lightGray);
        
        // Draw the back string
        backString = "\u00AB Main Menu";
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
        // Get the mouse position for reference below
		int posX = Mouse.getX();
		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL
        
		if (!mouseBeenReleased) {
			if (!Mouse.isButtonDown(0)) {
				mouseBeenReleased=true;
			}
		}
		
        // handle clicking the back button
		if (posX >= 20 && posX <= (20 + backStringWidth) && posY >= 550 && posY <= (550 + backStringHeight) && Mouse.isButtonDown(0) && mouseBeenReleased) {
			mouseBeenReleased = false;
			sbg.enterState(0); // MenuState            
		}         
	}

	public int getID() {
		return stateID;
	}
	
}

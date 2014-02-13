package states;

import java.awt.Font;
import java.io.InputStream;

import logicClasses.Airspace;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Image;


public class PlayState extends BasicGameState {

    private int stateID;
	private Airspace airspace;
	Image cursorImg;
	public static float time;
	private Sound endOfGameSound;
	public static TrueTypeFont font, smallButtonFont, titleFont;
	private Image controlBarImage, clockImage, scoreImage, backgroundImage, difficultyBackground;
	private String stringTime, stringScore;
	private boolean settingDifficulty, gameEnded;
    private String easyString, mediumString, hardString;
    private int easyStringHeight, easyStringWidth; 
    private int mediumStringHeight, mediumStringWidth; 
    private int hardStringHeight, hardStringWidth;

	public PlayState(int state) {
	    stateID = state;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gameEnded = false;
		settingDifficulty = true;
		time = 0;
		airspace = new Airspace();
		this.stringTime="";
		this.stringScore="";
				
		// Font
		try {
			InputStream inputStream1 = ResourceLoader.getResourceAsStream("res/fonts/fira-sans.ttf");
			Font awtFont1 = Font.createFont(Font.TRUETYPE_FONT, inputStream1);
			awtFont1 = awtFont1.deriveFont(16f);
			font = new TrueTypeFont(awtFont1, true);
            
			InputStream inputStream2 = ResourceLoader.getResourceAsStream("res/fonts/fira-sans-bold.ttf");
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
			titleFont = new TrueTypeFont(awtFont2.deriveFont(60f), true);
            smallButtonFont = new TrueTypeFont(awtFont2.deriveFont(30f), true);
		}
        
        catch(Exception e){
			e.printStackTrace();
		}        
		
		// Sound Effects		
		endOfGameSound = new Sound("res/music/175385__digitaldominic__scream.wav");
	
		//Images
		controlBarImage = new Image("res/graphics/control_bar_vertical.png");
		clockImage = new Image("res/icons/clock.png");
		scoreImage = new Image("res/icons/asterisk_orange.png");
		backgroundImage = new Image("res/graphics/background.png");
		difficultyBackground = new Image("res/graphics/menu_background.png");
		
    	// Initialise Waypoints
    	airspace.newWaypoint(350, 150, "A");
    	airspace.newWaypoint(400, 470, "B");
    	airspace.newWaypoint(700, 60,  "C");
    	airspace.newWaypoint(800, 320, "D");
    	airspace.newWaypoint(600, 418, "E");
    	airspace.newWaypoint(500, 260, "F");
    	airspace.newWaypoint(950, 188, "G");
    	airspace.newWaypoint(1050, 272,"H");
    	airspace.newWaypoint(900, 420, "I");
    	airspace.newWaypoint(240, 250, "J");
        
    	// Initialise EntryPoints
    	airspace.newEntryPoint(150, 400);
    	airspace.newEntryPoint(1200, 200);
    	airspace.newEntryPoint(600, 0);
        
    	// Initialise Exit Points
    	airspace.newExitPoint(800, 0, "1");
    	airspace.newExitPoint(150, 200, "2");
    	airspace.newExitPoint(1200, 300, "3");
    	
        airspace.init(gc);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (settingDifficulty) {
			difficultyBackground.draw(0,0);
            
            // Draw the page title
            titleFont.drawString(18, 10, "Set the difficulty", Color.lightGray);
            
            // Get the mouse position for reference below
    		int posX = Mouse.getX();
    		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL

            // Easy
            easyString = "Easy";
            easyStringWidth = smallButtonFont.getWidth(easyString);
            easyStringHeight = smallButtonFont.getHeight(easyString);
        
    		if (posX >= 20 && posX <= (20 + easyStringWidth) && posY >= 550 && posY <= (550 + easyStringHeight)) {
    			smallButtonFont.drawString(20, 550, easyString, Color.white);
    		} 
        
            else {
    			smallButtonFont.drawString(20, 550, easyString, Color.lightGray);
    		}
        
            // Medium
            mediumString = "Medium";
            mediumStringWidth = smallButtonFont.getWidth(mediumString);
            mediumStringHeight = smallButtonFont.getHeight(mediumString);
        
    		if (posX >= (40 + easyStringWidth) && posX <= (40 + easyStringWidth + mediumStringWidth) && posY >= 550 && posY <= (550 + mediumStringHeight)) {
    			smallButtonFont.drawString(40 + easyStringWidth, 550, mediumString, Color.white);
    		} 
        
            else {
    			smallButtonFont.drawString(40 + easyStringWidth, 550, mediumString, Color.lightGray);
    		}         
            
            // Hard
            hardString = "Hard";
            hardStringWidth = smallButtonFont.getWidth(hardString);
            hardStringHeight = smallButtonFont.getHeight(hardString);
        
    		if (posX >= (60 + easyStringWidth + mediumStringWidth) && posX <= (60 + easyStringWidth + mediumStringWidth + hardStringWidth) && posY >= 550 && posY <= (550 + hardStringHeight)) {
    			smallButtonFont.drawString(60 + easyStringWidth + mediumStringWidth, 550, hardString, Color.white);
    		} 
        
            else {
    			smallButtonFont.drawString(60 + easyStringWidth + mediumStringWidth, 550, hardString, Color.lightGray);
    		}         
    	}
		
		else {
			g.setFont(font);
			
			// Drawing Side Images
			backgroundImage.draw(150,0);
			controlBarImage.draw(0,0);
			
			// Drawing Airspace and elements within it
			airspace.render(g, gc);
            
			g.setColor(Color.white);
            
			// Drawing Clock and Time
			clockImage.draw(5, 12);
			g.drawString(this.stringTime, 25, 11);
            
			//Drawing Score
            scoreImage.draw(75, 12);
			g.drawString(stringScore, 95, 11);
		}
			
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// Checks if the game has been retried and if it has resets the airspace
		if (gameEnded) {
			airspace.resetAirspace();
	    	time = 0;
	    	gameEnded = false;
	    	settingDifficulty = true;
		}
		
		// Checks whether the user is still choosing the difficulty
		if (settingDifficulty) {
            // Get the mouse position for reference below
    		int posX = Mouse.getX();
    		int posY = 600 - Mouse.getY(); // Mouse has origin in bottom-left not top-left like OpenGL
						
            // Easy
    		if (posX >= 20 && posX <= (20 + easyStringWidth) && posY >= 550 && posY <= (550 + easyStringHeight) && Mouse.isButtonDown(0)) {
				airspace.setDifficultyValueOfGame(1);
				airspace.getControls().setDifficultyValueOfGame(1);
				airspace.createAndSetSeparationRules();
				settingDifficulty = false;
			}
			
            // Medium
    		if (posX >= (40 + easyStringWidth) && posX <= (40 + easyStringWidth + mediumStringWidth) && posY >= 550 && posY <= (550 + mediumStringHeight) && Mouse.isButtonDown(0)) {
				airspace.setDifficultyValueOfGame(2);
				airspace.getControls().setDifficultyValueOfGame(2);
				airspace.createAndSetSeparationRules();
				settingDifficulty = false;
			}
			
            // Hard
    		if (posX >= (60 + easyStringWidth + mediumStringWidth) && posX <= (60 + easyStringWidth + mediumStringWidth + hardStringWidth) && posY >= 550 && posY <= (550 + hardStringHeight) && Mouse.isButtonDown(0)) {
				airspace.setDifficultyValueOfGame(3);
				airspace.getControls().setDifficultyValueOfGame(3);
				airspace.createAndSetSeparationRules();
				settingDifficulty = false;
			}
		}
		
		else {
			
			// Updating Clock and Time
			
			time += delta;
			float decMins=time/1000/60;
			int mins = (int) decMins;
			float decSecs=decMins-mins;
				
			int secs = Math.round(decSecs*60);
				
			String stringMins="";
			String stringSecs="";
			if(secs==60){
				secs=0;
				mins+=1;
			}
			if(mins<10) {
				stringMins="0"+mins;
			}
			else {
				stringMins=String.valueOf(mins);
			}
			if(secs<10) {
				stringSecs="0"+secs;
			}
			else {
				stringSecs=String.valueOf(secs);
			}
						
			this.stringTime=stringMins+":"+stringSecs;
			this.stringScore=Integer.toString(this.airspace.getScore());
						
						
			// Updating Airspace
						
			airspace.newFlight(gc);
			airspace.update(gc);
			if (airspace.getSeparationRules().getGameOverViolation() == true){
				airspace.getSeparationRules().setGameOverViolation(false);
				airspace.resetAirspace();
				endOfGameSound.play();
				sbg.enterState(2);
				gameEnded = true;
							
			}
						
						
			Input input = gc.getInput();
						
			// Checking For Pause Screen requested in game
			if (input.isKeyPressed(Input.KEY_P) || input.isKeyPressed(Input.KEY_SPACE)) {
				sbg.enterState(3); //PauseState
			}
		}
	}

	public Airspace getAirspace() {
		return airspace;
	}

	public void setAirspace(Airspace airspace) {
		this.airspace = airspace;
	}
    
	public int getID() {
		return stateID;
	}

}
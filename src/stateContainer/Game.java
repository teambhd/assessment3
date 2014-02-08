package stateContainer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.AppGameContainer;

import states.GameOverState;
import states.MenuState;
import states.PauseState;
import states.PlayState;
import states.ControlsState;


public class Game extends StateBasedGame {
    
	public static final String NAME = "Don't Crash";
    
	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 1;
	public static final int GAMEOVER_STATE = 2;
	public static final int PAUSE_STATE = 3;
	public static final int CONTROLS_STATE = 4;
	
	public static final int MAXIMUM_WIDTH = 1200;
	public static final int MAXIMUM_HEIGHT = 600;
	public static final int FRAME_RATE = 60;
	
	public Game(String NAME) {
		super(NAME);
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MenuState(MENU_STATE));
		this.addState(new PlayState(PLAY_STATE));
		this.addState(new GameOverState(GAMEOVER_STATE));
		this.addState(new PauseState(PAUSE_STATE));
		this.addState(new ControlsState(CONTROLS_STATE));
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer appgc;
		appgc = new AppGameContainer(new Game(NAME));
		appgc.setDisplayMode(MAXIMUM_WIDTH, MAXIMUM_HEIGHT, false);
		appgc.setTargetFrameRate(FRAME_RATE);
		appgc.setIcon("res/graphics/icon.png");
		appgc.start(); 
	}
    
}
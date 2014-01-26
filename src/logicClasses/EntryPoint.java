package logicClasses;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class EntryPoint extends Point {
	
	Image entryPointTop, entryPointRight, entryPointLeft;

    public EntryPoint(double xcoord, double ycoord) {
    	super(xcoord, ycoord);
    }
    
    public void init(GameContainer gc) throws SlickException {
    	
    /**
     * init: Initialises the variables and resources required for the EntryPoint object render (Sets EntryPoint Images)
     * @param gc Game container required by Slick2d
     * @throws SlickException
     */
    	
    	this.entryPointTop = new Image("res/graphics/entrypoint_top.png");
		this.entryPointRight = new Image("res/graphics/entrypoint_right.png");
		this.entryPointLeft = new Image("res/graphics/entrypoint_left.png");

	}
    
    /**
	 * render: Render method for the EntryPoint object, position determines orientation of image
	 * @param g Graphics required by Slick2d
	 * @throws SlickException
	 */
    
	public void render(Graphics g) throws SlickException {
		
		if(this.y == 0){
			this.entryPointTop.draw((int)this.x-20, (int) this.y);
		}
		
		else if(this.x == 150){
			this.entryPointLeft.draw((int)this.x, (int) this.y-20);
		}
		
		else if(this.x == 1200){
			this.entryPointRight.draw((int)this.x-40, (int) this.y-20);
		}
    }
	


}

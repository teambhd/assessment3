package logicClasses;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Airport extends Point {
	
	Image airportImage;
	
	public Airport (double xcoord, double ycoord, String name) {
		super(xcoord, ycoord, name);
	    System.out.println("Airport " + pointRef + " set:(" + x + "," + y +").");
	}
	
	/**
	 * init: Initialises the parameters needed to render the airport waypoint.     
	 * @param gc Game container required by Slick2d
     * @throws SlickException 
     */

	
	public void init(GameContainer gc) throws SlickException {
		airportImage = new Image("res/graphics/airport.png");
	}
	
	/**
	 * render: Render method for the Airport object.
	 * @param g Graphics required by Slick2d 
	 * @param airspace Airspace object
	 * @throws SlickException 
	 */

	
    public void render(Graphics g, Airspace airspace) throws SlickException {
        // In order to be centered - the airport.png is a multi-pixel image
    	this.airportImage.draw(451,133);
    }
    
}
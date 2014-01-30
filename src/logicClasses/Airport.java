package logicClasses;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public class Airport extends Point {
	
	Image airportImage;
	
	public Airport (double xcoord, double ycoord, String name) {
		super (xcoord, ycoord, name);
	    System.out.println("Airport " + pointRef + " set:(" + x + "," + y +").");

	}
	
	public void init(GameContainer gc) throws SlickException {
		airportImage = new Image("res/graphics/airport.png");
	}
	
    public void render(Graphics g, Airspace airspace) throws SlickException {
    	
    		this.airportImage.draw(572-121,197-64); //In order to be centered - the airport.png is a multi-pixel image.
	    	
    	g.setColor(Color.red);
    	g.drawString(this.pointRef, 572, 197);
	
    }



}

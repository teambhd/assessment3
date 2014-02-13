package logicClasses;
import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.GameContainer;


public class Controls {

	// FIELDS
	private TrueTypeFont font;

	private TextField headingControlTextBox; //Object for heading control
	private TextField turnRightTextBox; //Object for turn right control
	private TextField turnLeftTextBox; // Object for turn left control

	private boolean selectingHeadingUsingTextBox; // Has the text box been reset?
	private boolean mouseHeldDownOnsideButton, mouseHeldDownOnFlight, headingAlreadyChangedByMouse;

	private final int MAXIMUM_ALTITUDE = 9000;
	private final int MINIMUM_ALTITUDE = 1000;
	private final int MAXIMUM_VELOCITY = 400;
	private final int MINIMUM_VELOCITY = 0;

	private Flight selectedFlight;
	private String text; //Used for parsing textbox inputs
	private Image sideButton, changePlanButton;
	private int difficultyValueOfGame; //Sets the difficulty of the control scheme


	// CONSTRUCTOR
	public Controls() {
		//Initializes all boolean values controlling selections to false
		this.selectingHeadingUsingTextBox = false; 
		this.mouseHeldDownOnsideButton=false;
		this.mouseHeldDownOnFlight = false;
		this.headingAlreadyChangedByMouse = false;
		this.selectedFlight = null;
	}


	// INIT
	public void init(GameContainer gc) throws SlickException {
		Font awtFont = new Font("Courier", Font.BOLD, 15); // Setting up fonts used in text boxes
		font = new TrueTypeFont(awtFont, false);
		this.turnLeftTextBox = new TextField(gc, font, 10, 120, 100, 23); //Creating textboxes
		this.headingControlTextBox = new TextField(gc, font, 10, 170, 100, 23);
		this.turnRightTextBox = new TextField(gc, font, 10, 220, 100, 23);
		this.turnLeftTextBox.setMaxLength(3); //Makes sure that user cannot enter more than three letters as a heading (360 is max)
		this.turnRightTextBox.setMaxLength(3);
		this.headingControlTextBox.setMaxLength(3);
		sideButton = new Image("res/graphics/side_button.png");
		changePlanButton = new Image("res/graphics/side_button.png"); // same as altitude button
	}


	// METHODS

	/**
	 * handleAndUpdatesideButtons: Deals with analysing and updating the selected flights altitude
	 * based on the altitude adjustment buttons
	 */

	public void handleAndUpdatesideButtons() {

		if(this.mouseHeldDownOnsideButton) {
			return;
		}
		else{
			this.mouseHeldDownOnsideButton = true;
		}

		int posX=Mouse.getX(); //Get the mouse positions 
		int posY=Mouse.getY();

		posY = 600 - posY; // Mapping Mouse coords onto graphic coords

		if (this.selectedFlight.getTakeoff()==true && this.selectedFlight.getLanding()==false){ //Make sure that flight
			//has taken off and not landed.

			if(posX>10&&posX<150&&posY<290&&posY>270&&Mouse.isButtonDown(0)) { //Is the mouse position in the area enclosed by the land button and is the button being held down?
				if(this.selectedFlight.getAirspace().getAvailableAirport()) { //Have we recently used airport?
					this.selectedFlight.LandFlight(); //Land
				}
			}

			if(posX>10&&posX<150&&posY<320&&posY>300&&Mouse.isButtonDown(0)) { //Is the mouse position in the area enclosed by the take off button and is the button being held down?
				if(this.selectedFlight.getAirspace().getAvailableAirport()) { //Have we recently used airport?
					this.selectedFlight.TakeOff(); //Take off
				}
			}


			if(posX>10&&posX<150&&posY<350&&posY>330&&Mouse.isButtonDown(0)) { //Is the mouse position in the area enclosed by the increase velocity button and is the button being held down?
				if(this.selectedFlight.getFlightPlan().getTarget() < MAXIMUM_VELOCITY) { //Is the target velocity already at the maximum value?
					this.selectedFlight.changeVelocity(this.selectedFlight.getFlightPlan().getTarget()+25); //Set the target velocity 25 higher
				}
			}


			if(posX>10&&posX<150&&posY<380&&posY>360&&Mouse.isButtonDown(0)) { //Is the mouse position in the area enclosed by the decrease velocity button and is the button being held down?
				if(this.selectedFlight.getFlightPlan().getTarget() > MINIMUM_VELOCITY) { //Is the target velocity already at the min value?
					this.selectedFlight.changeVelocity(this.selectedFlight.getFlightPlan().getTarget()-25); //Set the target velocity 25 lower
				}
			}




			if(posX>10&&posX<150&&posY<410&&posY>390&&Mouse.isButtonDown(0)) { //Is the mouse position in the area enclosed by the increase altitude button and is the button being held down?
				if(this.selectedFlight.getTargetAltitude() < MAXIMUM_ALTITUDE) { //Is the target altitude already at the maximum value?
					this.selectedFlight.setTargetAltitude(this.selectedFlight.getTargetAltitude()+1000); //Set the target altitude 1000 higher
				}
			}

			else if(posX>10&&posX<150&&posY<440&&posY>420&&Mouse.isButtonDown(0)) {//Is the mouse position in the area enclosed by the decrease altitude button and is the button being held down?
				if(this.selectedFlight.getTargetAltitude() > MINIMUM_ALTITUDE) { //Is the target altitude already at the minimum value?
					this.selectedFlight.setTargetAltitude(this.selectedFlight.getTargetAltitude()-1000); //Set the target altitude 1000 lower
				}
			}
		}
	}
	/**
	 * changeModeByClickingOnFlight: Handles changing between plan and nav modes by clicking on the selected flight
	 * @param nearestFlight Flight object
	 */
	public void changeModeByClickingOnFlight(Flight nearestFlight){


		if (this.selectedFlight.getFlightPlan().getChangingPlan() == true){
			nearestFlight.getFlightPlan().setChangingPlan(false);
		}
		else{
			nearestFlight.getFlightPlan().setChangingPlan(true);
		}

	}

	/**
	 * checkSelected: Handles changing the selected flight and ensures that the flight is a valid selection
	 * Also makes sure that if two flights are intersecting that you only select one, not both
	 * @param pointX
	 * @param pointY
	 * @param airspace
	 */
	public void checkSelected(int pointX, int pointY, Airspace airspace ){

		double minimumDistanceBetweenFlightAndMouseClick;//Distance between there you clicked on the airspace and the closest flight
		Flight nearestFlight;
		int indexOfNearestFlightInAirspaceListOfFlights;

		// If mouse is being held down don't change selected flight. 
		if (this.mouseHeldDownOnFlight){
			return;
		}
		else{
			this.mouseHeldDownOnFlight = true;
		}

		// Checking if user is dragging a waypoint they can't change flights
		if (this.selectedFlight != null){
			if (this.selectedFlight.getFlightPlan().getDraggingWaypoint()){
				return;
			}
		}




		// Working out nearest flight to click

		if(airspace.getListOfFlights().size()>=1){ //If there is more than one flight in the airspace
			//DONT PANIC, just pythagoras 
			minimumDistanceBetweenFlightAndMouseClick = Math.sqrt(Math.pow(pointX-airspace.getListOfFlights().get(0).getX(), 2)+Math.pow(pointY-airspace.getListOfFlights().get(0).getY(), 2));
			nearestFlight = airspace.getListOfFlights().get(0);
			indexOfNearestFlightInAirspaceListOfFlights = 0;

			for (int i =0; i< airspace.getListOfFlights().size(); i++){ //Loop through all flights and find the nearest one
				if(Math.sqrt(Math.pow(pointX-airspace.getListOfFlights().get(i).getX(), 2)+Math.pow(pointY-airspace.getListOfFlights().get(i).getY(), 2)) < minimumDistanceBetweenFlightAndMouseClick){
					minimumDistanceBetweenFlightAndMouseClick = Math.sqrt(Math.pow(pointX-airspace.getListOfFlights().get(i).getX(), 2)+Math.pow(pointY-airspace.getListOfFlights().get(i).getY(), 2));
					nearestFlight = airspace.getListOfFlights().get(i);
					indexOfNearestFlightInAirspaceListOfFlights = i;
				}
			}

			// Working out whether the nearest flight to click is close enough
			// to be selected.

			if (minimumDistanceBetweenFlightAndMouseClick <= 50){ // If the mouse if further from the flight than 50 then it cannot be selected

				if (nearestFlight == this.selectedFlight){ //If you are clicking on the currently selected flight then change the airspace mode instead of changing flight
					this.changeModeByClickingOnFlight(nearestFlight);
				}

				nearestFlight.setSelected(true);
				this.setSelectedFlight(nearestFlight);//Change the selected flight for controls
				for (int i =0; i< airspace.getListOfFlights().size(); i++){ //Loop through all flights
					if(i != indexOfNearestFlightInAirspaceListOfFlights){ //If the flight is not the currently selected flight
						airspace.getListOfFlights().get(i).setSelected(false); //Set that flight to not selected
						airspace.getListOfFlights().get(i).getFlightPlan().setChangingPlan(false);//Set the flight to nav mode
						this.turnLeftTextBox.setText(""); //Reset all the control text boxes
						this.turnRightTextBox.setText("");
					}
				}
			}
		}
	}

	/**
	 * giveHeadingWithMouse: Handles updating the currently selected flights heading by clicking in it's
	 * control circle with the left mouse button
	 * @param pointX X Coordinate of the mouse click
	 * @param pointY Y Coordinate of the mouse click
	 * @param airspace
	 */

	public void giveHeadingWithMouse(int pointX, int pointY, Airspace airspace){


		double deltaX, deltaY;
		double distanceBetweenMouseAndPlane;
		if (this.selectedFlight.getTakeoff()==true && this.selectedFlight.getLanding()==false){
			// If mouse is being held down don't change selected flight. 
			if (this.headingAlreadyChangedByMouse){
				return;
			}
			else{
				this.headingAlreadyChangedByMouse = true;
			}

			//More pythag - Finding the distance between the mouse click and the plane
			distanceBetweenMouseAndPlane = Math.sqrt(Math.pow(pointX-this.selectedFlight.getX(), 2)+Math.pow(pointY-this.selectedFlight.getY(), 2));


			if (distanceBetweenMouseAndPlane < 50) //If the distance between the mouse and the plane is greater than 50 then don't do anything
			{
				deltaY = pointY - this.selectedFlight.getY();
				deltaX = pointX - this.selectedFlight.getX();
				double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)); // Find the angle between the current heading and where the mouse was clicked
				angle+=90;
				if (angle < 0) {
					angle += 360;
				}
				this.selectedFlight.giveHeading((int)angle);

			}

		}
	}

	/**
	 * updateHeadingTextBox: Handles updating the currently selected flights heading by typing a value
	 * into a text-box as well as checking the input is valid
	 * @param input Input object
	 */

	public void updateHeadingTextBox(Input input){
		boolean headingTextBoxHasFocus = this.headingControlTextBox.hasFocus();
		if (headingTextBoxHasFocus) {

			// If the user has just selected the textbox, clear the textbox 
			if (!this.selectingHeadingUsingTextBox) {
				this.selectingHeadingUsingTextBox = true;
				this.headingControlTextBox.setText("");
			}
			// When the enter key is pressed retrieve its text and reset the textbox
			if (input.isKeyDown(Input.KEY_ENTER)) {
				this.text = this.headingControlTextBox.getText();
				this.text = this.text.replaceAll("\\D+", "");
				if (!this.text.isEmpty()) { //If there is a value then set the new heading and unselect the text box
					this.selectedFlight.giveHeading(Integer.valueOf(this.text));
					this.headingControlTextBox.setFocus(false);

				}

			}
		}

		if (this.selectingHeadingUsingTextBox && !headingTextBoxHasFocus) {
			this.selectingHeadingUsingTextBox = false;
		}
	}

	/**
	 * updateTurnLeftTextBox: Handles turning the currently selected flight left by typing a value
	 * into a text-box as well as checking the input is valid
	 * @param input Input Object
	 */

	public void updateTurnLeftTextBox(Input input){

		boolean turnLeftTextBoxHasFocus = this.turnLeftTextBox.hasFocus();
		if (turnLeftTextBoxHasFocus) {

			// When the enter key is pressed retrieve its text and reset the textbox
			if (input.isKeyDown(Input.KEY_ENTER)) {
				this.text = this.turnLeftTextBox.getText();
				this.text = this.text.replaceAll("\\D+", "");
				if (!this.text.isEmpty() && Integer.valueOf(this.text) <= 360) {//If there is a valid value then set the new heading and unselect the text box
					this.selectedFlight.turnFlightLeft(Integer.valueOf(this.text));
					this.turnLeftTextBox.setText("");
				}
				this.turnLeftTextBox.setFocus(false);

			}
		}
		else{
			this.turnLeftTextBox.setText("");
		}

	}

	/**
	 * updateTurnRightTextBox: Handles turning the currently selected flight right by typing a value
	 * into a text-box as well as checking the input is valid
	 * @param input
	 */

	public void updateTurnRightTextBox(Input input){

		if (this.turnRightTextBox.hasFocus()) {

			// When the enter key is pressed retrieve its text and reset the textbox
			if (input.isKeyDown(Input.KEY_ENTER)) {
				this.text = this.turnRightTextBox.getText();
				this.text = this.text.replaceAll("\\D+", "");
				if (!this.text.isEmpty() && Integer.valueOf(this.text) <= 360) { //If there is a valid value then set the new heading and unselect the text box
					this.selectedFlight.turnFlightRight(Integer.valueOf(this.text));
					this.turnRightTextBox.setText("");
				}
				this.turnRightTextBox.setFocus(false);

			}
		}
		else{
			this.turnRightTextBox.setText("");
		}

	}


	// RENDER AND UPDATE

	/**
	 * render: Render all of the graphics required by controls
	 * @param g The slick2d graphics object
	 * @param gc The slick2d game container
	 * @throws SlickException
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(this.selectedFlight != null) {
			if(!this.selectedFlight.getFlightPlan().getChangingPlan()){
				g.setColor(Color.white);

				g.drawString("Turn Left:", 10, 100);
				this.turnLeftTextBox.render(gc, g);
				g.drawString("DEG", 114, 125);

				g.drawString("Target Heading:", 10, 150);
				this.headingControlTextBox.render(gc, g);
				g.drawString("DEG", 114, 175);

				g.drawString("Turn Right:", 10, 200);
				this.turnRightTextBox.render(gc, g);
				g.drawString("DEG", 114, 225);

				g.setColor(Color.blue);
				sideButton.draw (0, 270);
				sideButton.draw (0, 300);
				sideButton.draw (0, 330);
				sideButton.draw (0, 360);
				sideButton.draw(0,390);
				sideButton.draw(0,420);

				g.setColor(Color.white);

				if (this.selectedFlight.getTakeoff()==true && this.selectedFlight.getLanding()==false){

					if (this.selectedFlight.getAirspace().getAvailableAirport()==false){

						g.setColor (Color.gray);

						g.drawString("Airport occupied", 10, 270);


					}

					else {
						g.drawString("Take off", 10, 300);
						if ((this.selectedFlight.getAltitude()>1000 || 
								this.selectedFlight.getFlightPlan().getVelocity()<50) &&
								this.selectedFlight.getAirspace().getAvailableAirport()){
							g.drawString ("Cannot land", 10, 270);
						}
						else {
							g.drawString ("Land", 10, 270);
						}	
					}
					if (this.selectedFlight.getFlightPlan().getTarget()+25 < Math.round(400)){
						g.drawString("Accelerate to " + Math.round(this.selectedFlight.getFlightPlan().getTarget()+25), 10, 330);
					}
					else {
						g.drawString ("At max speed", 10, 330);
					}

					if (this.selectedFlight.getFlightPlan().getTarget()-25 > Math.round(0)){
						g.drawString("Decelerate to " + Math.round(this.selectedFlight.getFlightPlan().getTarget()-25), 10, 360);
					}
					else {
						g.drawString ("At min speed", 10, 360);
					}

					if(this.selectedFlight.getTargetAltitude() < MAXIMUM_ALTITUDE){
						g.drawString("Climb to "+ (this.selectedFlight.getTargetAltitude()+1000), 10, 390);
					}
					else {
						g.drawString("At max altitude", 10, 390);
					}
					if(this.selectedFlight.getTargetAltitude() > MINIMUM_ALTITUDE){
						g.drawString("Descend to "+ (this.selectedFlight.getTargetAltitude()-1000), 10, 420);
					}
					else {
						g.drawString("At min altitude", 10, 420);
					}
				}
				else {								//If the flight has not taken off yet or is landing, no navigation.
					g.drawString("Taxi", 10, 270);
					g.drawString("Taxi", 10, 300);
					g.drawString("Taxi", 10, 330);
					g.drawString("Taxi", 10, 360);
					g.drawString("Taxi", 10, 390);
					g.drawString("Taxi", 10, 420);
				}

				changePlanButton.draw(0,45);
				changePlanButton.draw(0, 75);
				g.setColor(Color.white);
				g.drawString("Plan Mode", 10, 45);
				g.setColor(Color.lightGray);
				g.drawString("Navigator Mode", 10, 75);

			}

			if(this.selectedFlight.getFlightPlan().getChangingPlan() == true){
				g.setColor(Color.white);
				g.drawString("Plan Mode", 10, 45);
				g.setColor(Color.lightGray);
				g.drawString("Navigator Mode", 10, 75);
			}

		}
		else{
			g.setColor(Color.white);
			g.drawString("Navigator Mode", 10, 75);
			g.setColor(Color.lightGray);
			g.drawString("Plan Mode", 10, 45);
		}
	}


	public void update(GameContainer gc, Airspace airspace) {
		Input input = gc.getInput();
		int posX=Mouse.getX();
		int posY=Mouse.getY();
		posY = 600-Mouse.getY();

		if (this.selectedFlight != null ){

			// Only allow controls if user isn't changing a plan

			if(!(this.selectedFlight.getFlightPlan().getChangingPlan())){

				if(posX>10&&posX<150&&posY<65&&posY>45&&Mouse.isButtonDown(0)){
					this.selectedFlight.getFlightPlan().setChangingPlan(true);
				}

				if(Mouse.isButtonDown(1) && (this.difficultyValueOfGame != 3)){
					this.giveHeadingWithMouse(posX, posY, airspace);
				}

				this.updateHeadingTextBox(input);
				this.updateTurnLeftTextBox(input);
				this.updateTurnRightTextBox(input);



				// Handle and update Altitude Buttons

				this.handleAndUpdatesideButtons();


				//ALTITUDE KEYS
				if(input.isKeyPressed(Input.KEY_UP)){
					if(this.selectedFlight.getTargetAltitude() < MAXIMUM_ALTITUDE) {
						this.selectedFlight.setTargetAltitude(this.selectedFlight.getTargetAltitude()+1000);
					}
				}
				if(input.isKeyPressed(Input.KEY_DOWN)){
					if(this.selectedFlight.getTargetAltitude() > MINIMUM_ALTITUDE) {
						this.selectedFlight.setTargetAltitude(this.selectedFlight.getTargetAltitude()-1000);
					}
				}


				if (!this.headingControlTextBox.hasFocus()) {
					this.getHeadingControlTB().setText(
							String.valueOf(Math.round(this.selectedFlight.getTargetHeading())));
				}

			}

			else{
				if(posX>10&&posX<150&&posY<95&&posY>75&&Mouse.isButtonDown(0)){
					this.selectedFlight.getFlightPlan().setChangingPlan(false);
				}
			}

		}

		if(Mouse.isButtonDown(0)){
			this.checkSelected(posX,posY,airspace);
		}

		if(!Mouse.isButtonDown(0)){
			this.mouseHeldDownOnFlight = false;
			this.mouseHeldDownOnsideButton = false;
		}

		if (!Mouse.isButtonDown(1)){
			this.headingAlreadyChangedByMouse = false;
		}



	}

	//MUTATORS AND ACCESSORS
	public void setSelectedFlight(Flight flight1) {
		this.selectedFlight = flight1;
	}

	public TextField getHeadingControlTB() {
		return headingControlTextBox;
	}

	public Flight getSelectedFlight() {
		return this.selectedFlight;
	}

	public void setDifficultyValueOfGame(int value) {
		this.difficultyValueOfGame = value;	
	}

}
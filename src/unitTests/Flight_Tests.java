package unitTests;

import static org.junit.Assert.*;
import logicClasses.*;
import org.junit.Test;
import org.junit.Before;

public class Flight_Tests {
	
	private Airspace airspace;
	private  Flight flight1, flight2;
	
	@Before
	public void setUp(){
    	airspace = new Airspace();
    	//Waypoints
    	airspace.newWaypoint(350, 150, "A");
    	airspace.newWaypoint(400, 470, "B");
    	airspace.newWaypoint(700, 60,  "C");
    	airspace.newWaypoint(800, 320, "D");
    	airspace.newWaypoint(600, 418, "E");
    	airspace.newWaypoint(500, 220, "F");
    	airspace.newWaypoint(950, 188, "G");
    	airspace.newWaypoint(1050, 272,"H");
    	airspace.newWaypoint(900, 420, "I");
    	airspace.newWaypoint(240, 250, "J");
    	//EntryPoints
    	airspace.newEntryPoint(150, 400);
    	airspace.newEntryPoint(1200, 200);
    	airspace.newEntryPoint(600, 0);
    	// Exit Points
    	airspace.newExitPoint(800, 0, "1");
    	airspace.newExitPoint(150, 200, "2");
    	airspace.newExitPoint(1200, 300, "3");
    	flight1 = new Flight(airspace, 0);
    	flight2 = new Flight(airspace, 4);
		
	}
	
	
	

	
	
	// Testing generate_altitude()

	@Test
	public void generateAltitudeTest1() {
		// Testing the function returns an altitude within a certain range.
    	int result = flight1.generateAltitude(3);
    	assertTrue(result >=1000 && result<= 10000);
    	
	}
	
	// Testing calculate_heading_to_first_waypoint()
	
	@Test
	public void calculateHeadingToFirstWaypointTest1(){
		//Testing it calculates the heading to the first waypoint.
		flight1.setX(150);
		flight1.setY(400);
		assertEquals(38.65, flight1.calculateHeadingToFirstWaypoint(350,150), 0.01);
		
	}
	
	@Test
	public void calculateHeadingToFirstWaypointTest2(){
		//Testing it calculates the heading to the first waypoint. 
		flight1.setX(350);
		flight1.setY(400);
		assertEquals(321.34, flight1.calculateHeadingToFirstWaypoint(150,150), 0.01);
		
	}
	

	
	
	// Testing turn_flight_left(int degrees_turned_by)
	
	
	@Test
	public void turnFlightLeftTest1(){
		flight1.setTargetHeading(10);
		flight1.setCurrentHeading(10);
		flight1.turnFlightLeft(20);
		assertEquals(350, flight1.getTargetHeading(), 0.1);
	}
	
	@Test
	public void turnFlightLeftTest2(){
		flight1.setTargetHeading(270);
		flight1.setCurrentHeading(270);
		flight1.turnFlightLeft(90);
		assertEquals(180, flight1.getTargetHeading(), 0.1);
	}
	
	@Test
	public void turnFlightLeftTest3(){
		flight1.setTargetHeading(90);
		flight1.setCurrentHeading(90);
		flight1.turnFlightLeft(90);
		assertEquals(0, flight1.getTargetHeading(), 0.1);
	}
	
	
	// Testing turn_flight_right(int degrees_turned_by)
	
	@Test
	public void turnFlightRightTest1(){
		flight1.setTargetHeading(0);
		flight1.setCurrentHeading(0);
		flight1.turnFlightRight(30);
		assertEquals(30, flight1.getTargetHeading(), 0.1);
	}
	
	@Test
	public void turnFlightRightTest2(){
		flight1.setTargetHeading(270);
		flight1.setCurrentHeading(270);
		flight1.turnFlightRight(100);
		assertEquals(10, flight1.getTargetHeading(),0.1);
	}
	
	@Test
	public void turnFlightRightTest3(){
		flight1.setTargetHeading(270);
		flight1.setCurrentHeading(270);
		flight1.turnFlightRight(90);
		assertEquals(0, flight1.getTargetHeading(), 0.1);
	}
	

	
	// Testing give_heading(int new_heading)
	
	@Test
	public void giveHeadingTest1(){
		flight1.setTargetHeading(0);
		flight1.setCurrentHeading(0);
		flight1.giveHeading(0);
		assertEquals(0, flight1.getTargetHeading(), 0.1);
	}
	
	@Test
	public void giveHeadingTest2(){
		flight1.setTargetHeading(0);
		flight1.setCurrentHeading(0);
		flight1.giveHeading(360);
		assertEquals(0, flight1.getTargetHeading(), 0.1);
	}
	
	@Test
	public void giveHeadingTest3(){
		flight1.setTargetHeading(0);
		flight1.setCurrentHeading(0);
		flight1.giveHeading(90);
		assertEquals(90, flight1.getTargetHeading(), 0.1);
	}
	
	@Test
	public void giveHeadingTest4(){
		flight1.setTargetHeading(0);
		flight1.setCurrentHeading(0);
		flight1.giveHeading(3610);
		assertEquals(10, flight1.getTargetHeading(), 0.1);
	}
	
	
	// Testing check_if_flight_at_waypoint()
	
	
	@Test
	public void checkIfFlightAtWaypointTest1(){
		// Test that waypoint detection works at exactly 15 pixels away.
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(335);
		flight1.setY(135);
		assertTrue(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	@Test
	public void checkIfFlightAtWaypointTest2(){
		// Test that waypoint detection works at within 15 pixels .
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(350);
		flight1.setY(150);
		assertTrue(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	@Test
	public void checkIfFlightAtWaypointTest3(){
		// // Test that waypoint detection doesn't work further than 15 pixels away.
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(1000);
		flight1.setY(1000);
		assertFalse(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	@Test
	public void checkIfFlightAtWaypointTest4(){
		// Test that waypoint detection works when close enough in terms of 
		// Y coordinate but too far away in terms of X cooordinate
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(1000);
		flight1.setY(150);
		assertFalse(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	@Test
	public void checkIfFlightAtWaypointTest5(){
		// Test that waypoint detection works when close enough in terms of 
		// X coordinate but too far away in terms of Y cooordinate
		Waypoint waypoint = new Waypoint(350, 150, "TEST");
		flight1.setX(350);
		flight1.setY(1000);
		assertFalse(flight1.checkIfFlightAtWaypoint(waypoint));
	}
	
	
	
	// Testing update_x_y_coordinates()
	
	@Test
	public void updateXYCoordinates(){
		// Testing that it updates the x and y coordinate correctly.
		flight1.getFlightPlan().setVelocity(300);
		flight1.setCurrentHeading(50);
		flight1.setTargetHeading(50);
		flight1.setX(100);
		flight1.setY(100);
		flight1.updateXYCoordinates();
		assertEquals(100.2, flight1.getX(), 0.1);
		assertEquals(99.8, flight1.getY(), 0.1);
		
	}
	
	
	
	//Testing update_altitude()
	
	@Test
	public void updateAltitudeTest1(){
		// Testing that the Flight moves towards the target altitude.
		flight1.setAltitude(5996);
		flight1.setTargetAltitude(6000);
		flight1.updateAltitude();
		assertEquals(6000, flight1.getAltitude(), 0.1);
		
	}
	
	
	
	
	@Test
	public void updateAltitudeTest2(){
		// Testing that the Flight doesn't move when at the target altitude.
		flight1.setCurrentAltitude(4000);
		flight1.setTargetAltitude(4000);
		flight1.updateAltitude();
		assertEquals(4000, flight1.getAltitude(), 0.1);
		
	}
	
	@Test
	public void updateAltitudeTest3(){
		// Testing that the Flight moves towards the target altitude.
		flight1.setCurrentAltitude(2996);
		flight1.setTargetAltitude(3000);
		flight1.updateAltitude();
		assertEquals(3000, flight1.getAltitude(), 0.1);
		
	}
	
	@Test
	public void updateAltitudeTest4(){
		// Testing that the Flight moves towards the target altitude.
		flight1.setCurrentAltitude(5004);
		flight1.setTargetAltitude(5000);
		flight1.updateAltitude();
		assertEquals(5000, flight1.getAltitude(), 0.1);
		
	}
	
	@Test
	public void updateAltitudeTest5(){
		// Testing that the Flight moves towards the target altitude.
		flight1.setCurrentAltitude(8004);
		flight1.setTargetAltitude(8000);
		flight1.updateAltitude();
		assertEquals(8000, flight1.getAltitude(), 0.1);
		
	}
	
	//Testing updateVelocity()
	
	@Test
	public void updateVelocityTest1(){
		// Testing that the Flight moves towards the target velocity.
		flight1.getFlightPlan().setVelocity(200);
		flight1.getFlightPlan().setTargetVelocity(225);
		flight1.updateVelocity();
		assertEquals(200.25, flight1.getFlightPlan().getVelocity(), 0.1);
		
	}
	@Test
	public void updateVelocityTest2(){
		// Testing that the Flight moves towards the target velocity.
		flight1.getFlightPlan().setVelocity(100);
		flight1.getFlightPlan().setTargetVelocity(225);
		flight1.updateVelocity();
		assertEquals(100.25, flight1.getFlightPlan().getVelocity(), 0.1);
		
	}

	@Test
	public void updateVelocityTest3(){
		// Testing that the Flight moves towards the target velocity.
		flight1.getFlightPlan().setVelocity(400);
		flight1.getFlightPlan().setTargetVelocity(225);
		flight1.updateVelocity();
		assertEquals(399.75, flight1.getFlightPlan().getVelocity(), 0.1);
		
	}

	@Test
	public void updateVelocityTest4(){
		// Testing that the Flight moves towards the target velocity.
		flight1.getFlightPlan().setVelocity(200);
		flight1.getFlightPlan().setTargetVelocity(100);
		flight1.updateVelocity();
		assertEquals(199.75, flight1.getFlightPlan().getVelocity(), 0.1);
		
	}

	@Test
	public void updateVelocityTest5(){
		// Testing that the Flight doesn't move when at target velocity.
		flight1.getFlightPlan().setVelocity(225);
		flight1.getFlightPlan().setTargetVelocity(225);
		flight1.updateVelocity();
		assertEquals(225, flight1.getFlightPlan().getVelocity(), 0.1);
		
	}

	

	
	
	//Testing update_current_heading()
	
	@Test
	public void updateCurrentHeadingTest1(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(288);
		flight1.setTargetHeading(0);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningRight());
		assertFalse(flight1.getTurningLeft());
		assertEquals(288.5, flight1.getCurrentHeading(), 0.1);
		
	}
	
	@Test
	public void updateCurrentHeadingTest2(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(288);
		flight1.setTargetHeading(270);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningLeft());
		assertFalse(flight1.getTurningRight());
		assertEquals(287.5, flight1.getCurrentHeading(), 0.1);
		
	}
	
	@Test
	public void updateCurrentHeadingTest3(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(270);
		flight1.setTargetHeading(90);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningRight());
		assertFalse(flight1.getTurningLeft());
		assertEquals(270.5, flight1.getCurrentHeading(), 0.1);
		
	}
	
	@Test
	public void updateCurrentHeadingTest4(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(288);
		flight1.setTargetHeading(300);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningRight());
		assertFalse(flight1.getTurningLeft());
		assertEquals(288.5, flight1.getCurrentHeading(), 0.1);
		
	}
	
	@Test
	public void updateCurrentHeadingTest5(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(150);
		flight1.setTargetHeading(200);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningRight());
		assertFalse(flight1.getTurningLeft());
		assertEquals(150.5, flight1.getCurrentHeading(), 0.1);
		
	}
	
	@Test
	public void updateCurrentHeadingTest6(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(20);
		flight1.setTargetHeading(290);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningLeft());
		assertFalse(flight1.getTurningRight());
		assertEquals(19.5, flight1.getCurrentHeading(), 0.1);
		
	}
	
	@Test
	public void updateCurrentHeadingTest7(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(359.5);
		flight1.setTargetHeading(10);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningRight());
		assertFalse(flight1.getTurningLeft());
		assertEquals(0, flight1.getCurrentHeading(), 0.1);
		
	}
	
	@Test
	public void updateCurrentHeadingTest8(){
		// Test that the current heading moves towards the target heading
		flight1.setCurrentHeading(0.5);
		flight1.setTargetHeading(290);
		flight1.updateCurrentHeading();
		assertTrue(flight1.getTurningLeft());
		assertFalse(flight1.getTurningRight());
		assertEquals(360, flight1.getCurrentHeading(), 0.1);
		
	}
	
	//Testing Takeoff and Land()
	
	@Test
	public void TakeoffTest1(){
		// Testing that the flight takes off properly.
		flight2.TakeOff();
		flight2.update();
		assertEquals(100, flight2.getFlightPlan().getTargetVelocity(), 0.1);
		assertEquals (1000, flight2.getTargetAltitude(), 0.1);
		
	}
	@Test
	public void TakeOffTest2(){
		// Testing that a flight that has takenoff cannot use the TakeOff function.
		flight1.TakeOff();
		flight1.update();
		assertEquals(flight2.getFlightPlan().getVelocity(), flight2.getFlightPlan().getTargetVelocity(), 0.1);
		assertEquals (flight2.getAltitude(), flight2.getTargetAltitude(), 0.1);
		
	}

	@Test
	public void LandTest1(){
		// Testing that a flight not at the airport cannot land.
		flight1.LandFlight();
		flight1.update();
		assertFalse (flight1.getLanding());
	}

	@Test
	public void LandTest2(){
		// Testing that a flight at the airport lands.
		flight1.setX(572);
		flight1.setY(197);
		flight1.setAltitude(0);
		flight1.getFlightPlan().setVelocity(0);
		flight1.LandFlight();
		flight1.update();
		assertTrue (flight1.getLanding());

		
	}



}

// BackUp.java
// 
// An behaviour to back up the robot if the touch sensors detect
// an obstacle.  It exploits the PilotRobot class to make sure that
// calls to the motors are non-blocking (i.e. they return immediately)
// so that if the Arbitrator asks a behaviour to be suppressed, it can
// stop immediately.
//
// Terry Payne
// 8th October 2017
//

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class ObstacleAvoid implements Behavior {
	public boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;

	// Constructor - the robot, and gets access to the pilot class
	// that is managed by the robot (this saves us calling
	// me.getPilot.somemethod() all of the while)
    public ObstacleAvoid(PilotRobot robot){
    	 me = robot;
    	 pilot = me.getPilot();
    }

	// When called, this should stop action()
	public void suppress(){
		suppressed = true;
	}
	
	// When called, determine if this behaviour should start
	public boolean takeControl(){
		if (me.getDistance()<=15) {
			return true;
		}
		return false;
	}

	// This is our action function.  All calls to the motors should be
	// non blocking, so that they can be stopped if suppress is true.
	// If a call is made to move a specific distance or rotate a specific
	// angle etc, then it should return immediately, and monitored until
	// it has completed.  The code below illustrates this, but waiting
	// until the robot stops moving.  An OdometryPoseProvider could also
	// be used for this.
	
	public void action() {
		// Allow this method to run
		suppressed = false;
        me.getPilot().rotate(90);
        me.getPilot().travel(20);
        me.getPilot().rotate(-90);
        me.getPilot().travel(20);
        me.getPilot().rotate(-90);
        me.getPilot().travel(20);
        me.getPilot().rotate(90);
	    while(pilot.isMoving() && !suppressed) {
	        Thread.yield();  // wait till turn is complete or suppressed is called
	    }

	    
	    // Ensure that the motors have stopped.
	    pilot.stop();		
	}
}
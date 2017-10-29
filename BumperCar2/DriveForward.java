// DriveForward.java
// 
// An behaviour to drive the robot forward - this is the simplest 
// behaviour that we consider (and hence the takeControl() action 
// is true.  It exploits the PilotRobot class drive the robot 
// forward using a non-blocking call (i.e. it returns immediately) 
// so that if the Arbitrator asks a behaviour to be suppressed, it 
// can stop immediately.
//
// Terry Payne
// 8th October 2017
//
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {

	public boolean suppressed;
	private PilotRobot me;
	private URotate u;

	// Constructor - store a reference to the robot
	public DriveForward(PilotRobot robot){
    	 me = robot;
    	  u=new URotate(me);
    }

	// When called, this should stop action()
	public void suppress(){
		suppressed = true;
	}

	// This returns true, so will always take control (if
	// no higher priority behaviour also takes control).
	// We could modify this to look for a "finish" variable
	// so that if the robot should stop, then we could simply
	// not take control.  If no behaviour takes control, the
	// Arbritrator will end.
	public boolean takeControl(){
		return true;	
	}

	// This is our action function.  This starts the motor running
	// (which returns immediately).  We then simply run until told
	// to suppress our action, in which case we stop.
	public void action() {
		while(true){
		// Allow this method to run
		suppressed = false;
		
		// Go forward
		me.getPilot().travel(OccupancyGrid.getGridX());
		u.run();
		// While we can run, yield the thread to let other threads run.
		// It is important that no action function blocks any otherf action.
		while (!suppressed) {
			Thread.yield();
		}
		suppressed=true;
	    // Ensure that the motors have stopped.
		me.getPilot().stop();
		}
	}
}
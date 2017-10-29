// PilotRobot.java
// 
// Based on the SimpleRobot class, this provides access to the
// sensors, and constructs a MovePilot to control the robot.
//
// Terry Payne
// 8th October 2017
//

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;

public class PilotRobot {

	private EV3TouchSensor leftBump, rightBump;
	//private EV3IRSensor irSensor;
	private EV3ColorSensor cSensor;
	private EV3UltrasonicSensor uSensor;
	private SampleProvider leftSP, rightSP, distSP, colourSP;	
	private float[] leftSample, rightSample, distSample, colourSample; 
	private MovePilot pilot;
    private OdometryPoseProvider opp;

	public PilotRobot() {
		Brick myEV3 = BrickFinder.getDefault();

		leftBump = new EV3TouchSensor(myEV3.getPort("S2"));
		rightBump = new EV3TouchSensor(myEV3.getPort("S1"));
		//irSensor = new EV3IRSensor(myEV3.getPort("S3"));
        uSensor = new EV3UltrasonicSensor(myEV3.getPort("S3"));
		cSensor = new EV3ColorSensor(myEV3.getPort("S4"));
		leftSP = leftBump.getTouchMode();
		rightSP = rightBump.getTouchMode();
		distSP = uSensor.getDistanceMode();
		//distSP = irSensor.getDistanceMode(); 		// effective range of the sensor in Distance mode is about 5 to 50 centimeters
		colourSP = cSensor.getRGBMode();
		
		leftSample = new float[leftSP.sampleSize()];		// Size is 1
		rightSample = new float[rightSP.sampleSize()];		// Size is 1
		distSample = new float[distSP.sampleSize()];		// Size is 1
		colourSample = new float[colourSP.sampleSize()];	// Size is 3
		
	//	MotorA =new EV3MediumRegulatedMotor(myEV3.getPort("A"));
	//	MotorA.setSpeed(180);
		
		// Set up the wheels by specifying the diameter of the
		// left (and right) wheels in centimeters, i.e. 3.25 cm
		// the offset number is the distance between the center
		// of wheel to the center of robot, i.e. half of track width
		// NOTE: this may require some trial and error to get right!!!
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 3.3).offset(-10.0);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 3.3).offset(10.0);
		Chassis myChassis = new WheeledChassis( new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		
	    pilot = new MovePilot(myChassis);
	     opp = new OdometryPoseProvider(pilot);
	}

	public void closeRobot() {
		leftBump.close();
		rightBump.close();
		uSensor.close();
		//irSensor.close();
		cSensor.close();
	}

	public boolean isLeftBumpPressed() {
    	leftSP.fetchSample(leftSample, 0);
    	return (leftSample[0] == 1.0);
	}
	
	public boolean isRightBumpPressed() {
    	rightSP.fetchSample(rightSample, 0);
    	return (rightSample[0] == 1.0);
	}
	
	public float getDistance() {
    	distSP.fetchSample(distSample, 0);
    	return distSample[0];
	}

	public float[] getColour() {
    	colourSP.fetchSample(colourSample, 0);
    	return colourSample;	// return array of 3 colours
	}
	
	public MovePilot getPilot() {
		return pilot;
	}
	
	public Pose getPose() {
		return opp.getPose();
	}
	
   /* public EV3MediumRegulatedMotor getMotorA() {
    	return MotorA;
    }*/
}
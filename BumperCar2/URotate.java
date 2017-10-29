import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;

public class URotate {
	
//private int delay;
private PilotRobot me;
private Detect d;
public URotate(PilotRobot me) {
	//this.delay=delay;
	this.me= me;
	d=new Detect(this.me);
	//MotorA=me.getMotorA();
	
}

public void run() {
	
		 Motor.A.rotateTo(90);
		 d.execute();
		 Motor.A.rotateTo(0);
		 d.execute();
		 Motor.A.rotateTo(-90);
		 d.execute();		 
		Motor.A.rotateTo(0);
			 
		 
}

}


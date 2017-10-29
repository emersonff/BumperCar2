import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.geometry.Point;
import lejos.robotics.navigation.Pose;

public class Detect  {
	 private static OccupancyGrid grid=new OccupancyGrid(6,6);
	 private PilotRobot me;

	 
	 public Detect(PilotRobot me) {
		 this.me=me;


	 }
 
//get the coordinate of the grid that is occupied by the robot
 // initial state: (0,0) (0,0,0)
 public Coordinate getCurrentGrid() {
	 int x=(int)me.getPose().getX()/OccupancyGrid.getGridX();
	 int y=(int)me.getPose().getY()/OccupancyGrid.getGridY(); 
	 return new Coordinate(x,y);
 }
 
//public boolean 
 
public double getAngle() {
	   double angle=0;
	   double heading=me.getPose().getHeading();
	   double angleMotorA=Motor.A.getLimitAngle();
	   angle=heading+angleMotorA;
	   return angle;
}

public Coordinate getObstcaleGrid() {
	double distance=me.getDistance()*100;
	double angle=getAngle();
	Coordinate current=getCurrentGrid();
	if(distance!=Double.POSITIVE_INFINITY) {
       int x=(int)(Math.sin(angle)*distance)/OccupancyGrid.getGridX();
       int y=(int)(Math.cos(angle)*distance)/OccupancyGrid.getGridY();
       if(x<0||y<0||x>=grid.getX()||y>grid.getY()) {
       return null;
       }
       else {
    	   return new Coordinate(x,y);
       }
	}else {
		return null;
	}

}

public void execute() {
	Coordinate o=getObstcaleGrid();

	if(o!=null) {
		int x=o.getX();
		int y=o.getY();
		grid.addC(x, y);
		grid.addM(x, y);
		//grid.probability(x, y);
	}
}

public void draw(){
	double[][] p=grid.getP();
	double t=0.7;
	GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	lcd.clear();
	lcd.setFont(Font.getSmallFont());
	lcd.drawLine(0, 0, 0, 70);
	lcd.drawLine(0,0,60,0);
	lcd.drawLine(60,0,60,70);
	lcd.drawLine(0, 70, 60,70);
	for(int x=0; x<grid.getX(); x++){
		for(int y=0; y<grid.getY();y++){
		  if(p[x][y]>=t){
			  lcd.drawRect(x, y, 10,10);
			  
		  }
		}
	}
}


}

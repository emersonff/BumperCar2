import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.Keys;

public class Exit extends Thread {

public Exit() {
	
}

public void run() {
 while(true){
	 if(Button.getButtons() == Keys.ID_ESCAPE){
		 System.exit(1);
	 }
 }
}
	
}

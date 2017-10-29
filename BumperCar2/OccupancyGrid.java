

public class OccupancyGrid {
	//model of the occupancy grid
private int M[][];
	//count
private int C[][];
	//probability of a cell that is occupied
private double P[][];
	//size of a single grid
private static final int x=20;
private static final int y=25;
private int sizeX;
private int sizeY;

public OccupancyGrid(int sizeX,int sizeY) {
	this.sizeX=sizeX;
	this.sizeY=sizeY;
	M=new int[sizeX][sizeY];
	C=new int[sizeX][sizeY];
	P=new double[sizeX][sizeY];
	initialise();
}

public static int getGridX(){
	return x;
}
public static int getGridY(){
	return y;
}

public int getX(){
	return sizeX;
}

public int getY(){
	return sizeY;
}

public double[][] getP(){
	return P;
}
private void initialise() {
	for(int x=0; x<this.sizeX; x++) {
		for(int y=0; y<this.sizeY; y++) {
			M[x][y]=0;
			C[x][y]=0;
			P[x][y]=0;
		}
	}
}


public double probability(int x, int y) {
	double pr;
	pr=(M[x][y]+C[x][y])/2*C[x][y];
	P[x][y]=pr;
	return pr;
}

public void addC(int x, int y) {
	C[x][y]++;
}

public void addM(int x, int y) {
	M[x][y]++;
}

public void subM(int x, int y) {
	if(M[x][y]>0) {
		M[x][y]--;
	}
}

}
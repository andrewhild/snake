package main;

public class Food{

	private int xdim,ydim,xpos,ypos;

	public Food (int x, int y){
		xdim=x;
		ydim=y;
		xpos=(int)(Math.random()*xdim);
		ypos=(int)(Math.random()*ydim);
	}

	public int[] getPos(){
		return new int[]{xpos,ypos};
	}
}
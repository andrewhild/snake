package main;

public class Food{

	private int xdim,ydim,size,xpos,ypos;

	public Food (int x, int y, int s){
		xdim=x;
		ydim=y;
		size=s;
		xpos=(int)(Math.random()*(xdim-size));
		ypos=(int)(Math.random()*(ydim-size));
	}

	public int[] getPos(){
		return new int[]{xpos,ypos};
	}
}
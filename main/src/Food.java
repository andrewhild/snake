package main;

public class Food{

	private int xdim,ydim,xpos,ypos;

	public Food (int x, int y, int size){
		xdim=x;
		ydim=y;
		xpos=(int)(size*Math.round((Math.random()*(xdim-size))/size));
		ypos=(int)(size*Math.round((Math.random()*(ydim-size))/size));
	}

	public int[] getPos(){
		return new int[]{xpos,ypos};
	}
}
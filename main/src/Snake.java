package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Snake implements KeyListener{

	private final int UP=1;
	private final int DOWN=2;
	private final int LEFT=3;
	private final int RIGHT=4;
	private int dir=UP, dr=10;
	private ArrayList<int[]> body;

	public Snake(ArrayList<int[]> start){
		body=start;
	}

	//get direction of travel
	public int getDir(){
		return dir;
	}

	//Increment body positions in given direction
	public ArrayList<int[]> move(){
		//Increment head joint position by dr based on dir
		if(dir==UP)
			body.get(0)[1]-=dr;
		else if(dir==DOWN)
			body.get(0)[1]+=dr;
		else if(dir==LEFT)
			body.get(0)[0]-=dr;
		else if(dir==RIGHT)
			body.get(0)[0]+=dr;
		//advance joints by setting position of each to position of preceeding joint
		for(int[] x:body){
			if(body.indexOf(x)>0){
				x[0]=body.get(body.indexOf(x)-1)[0];
				x[1]=body.get(body.indexOf(x)-1)[1];
			}
		}
		return body;
	}

	//Add joint to body after eating food
	public void eat(){
		body.add(new int[]{0,0});
	}

	//Handle player input of directional changes. No instant u-turns.
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_UP)
			if(dir!=DOWN)
				dir=UP;
		else if(code==KeyEvent.VK_DOWN)
			if(dir!=UP)
				dir=DOWN;
		else if(code==KeyEvent.VK_LEFT)
			if(dir!=RIGHT)
				dir=LEFT;
		else if(code==KeyEvent.VK_RIGHT)
			if(dir!=LEFT)
				dir=RIGHT;	
	}

	//Vestigial methods
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
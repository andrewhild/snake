package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Snake implements Directions{

	private int dir=UP, dr=10;
	private ArrayList<int[]> body;

	public Snake(ArrayList<int[]> start){
		body=start;
	}

	//get direction of travel
	public int getDir(){
		return dir;
	}
	
	//set direction of travel
	public int setDir(int d){
		dir=d;
		return dir;
	}

	//return body joint positions
	public ArrayList<int[]> getPos(){
		return body;
	}

	//Increment body positions in given direction
	public ArrayList<int[]> move(){
		//advance joints by setting position of each to position of preceeding joint
		for(int q = body.size()-1;q>0;q--){
				body.get(q)[0]=body.get(q-1)[0];
				body.get(q)[1]=body.get(q-1)[1];
			
		}
		//Increment head joint position by dr based on dir
		if(dir==UP)
			body.get(0)[1]-=dr;
		else if(dir==DOWN)
			body.get(0)[1]+=dr;
		else if(dir==LEFT)
			body.get(0)[0]-=dr;
		else if(dir==RIGHT)
			body.get(0)[0]+=dr;
		
		return body;
	}

	//Add joint to body after eating food
	public void eat(){
		body.add(new int[]{0,0});
	}

	//Vestigial methods
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameObj extends JPanel implements KeyListener,ActionListener,Directions{

	private int xdim, ydim;
	//Timer pings every 17 ms for ~60fps framerate
	private final int TICK=500;
	private final int jointSize = 10;
	//private long t0;
	private boolean active=true;
	private Snake player;
	private Food noms;
	private Timer timer;
	public GameObj(int x, int y){
		xdim=x;
		ydim=y;
		setFocusable(true);
		addKeyListener(this);
		timer = new Timer(TICK,this);
		timer.start();
		//t0=system.timeNano();
		////game objects
		//Specify iniitial position
		ArrayList<int[]> start = new ArrayList<int[]>();
		start.add(new int[]{xdim/2,ydim/2});
		start.add(new int[]{xdim/2,ydim/2+2*jointSize});
		start.add(new int[]{xdim/2,ydim/2+4*jointSize});
		player = new Snake(start);
		//nom nom nom
		noms = new Food(xdim,ydim);

	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		int dir=player.getDir();
		if(code==KeyEvent.VK_UP){
			if(dir!=DOWN){
				dir=UP;
				player.setDir(dir);
			}
		}
		else if(code==KeyEvent.VK_DOWN){
			if(dir!=UP){
				dir=DOWN;
				player.setDir(dir);
			}
		}
		else if(code==KeyEvent.VK_LEFT){
			if(dir!=RIGHT){
				dir=LEFT;
				player.setDir(dir);
			}
		}
		else if(code==KeyEvent.VK_RIGHT){
			if(dir!=LEFT){
				dir=RIGHT;
				player.setDir(dir);
			}
		}
		//Pause
		else if(code==KeyEvent.VK_P)
			active=(!active);
	}


	public void actionPerformed(ActionEvent e){
		if(!active)
			timer.stop();
		else{
			Graphics g = getGraphics();
			paint(g);
		}

	}

	public void paint(Graphics g) {
		//System.out.println("noot noot!");
		super.paint(g);
		int[] foodPos=noms.getPos();
		ArrayList<int[]> snakePos=player.move();
		g.setColor(Color.RED);
		g.fillOval(foodPos[0],foodPos[1],jointSize,jointSize);
		g.setColor(Color.GREEN);
		for(int[] x:snakePos){
			g.fillOval(x[0],x[1],jointSize,jointSize);
		}
		g.dispose();
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}


}
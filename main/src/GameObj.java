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

public class GameObj extends JPanel implements KeyListener,ActionListener{

	private int xdim, ydim;
	//Timer pings every 17 ms for ~60fps framerate
	private final int TICK=500;
	private final int jointSize = 10;
	//private long t0;
	private boolean active=true;
	private Snake player;
	private Food noms;
	public GameObj(int x, int y){
		xdim=x;
		ydim=y;
		setFocusable(true);
		addKeyListener(this);
		Timer timer = new Timer(TICK,this);
		timer.start();
		//t0=system.timeNano();
		//game objects
		ArrayList<int[]> start = new ArrayList<int[]>();
		start.add(new int[]{xdim/2,ydim/2});
		start.add(new int[]{xdim/2,ydim/2+2*jointSize});
		start.add(new int[]{xdim/2,ydim/2+4*jointSize});
		player = new Snake(start);
		noms = new Food(xdim,ydim);

	}

	//Pause game by pressing p
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code=='p')
			active=(!active);
	}


	public void actionPerformed(ActionEvent e){
		if(active){
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
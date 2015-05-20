package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameObj extends JPanel implements KeyListener,ActionListener,Directions{

	private int xdim, ydim;
	//Timer pings every 17 ms for ~60fps framerate
	private final int TICK=100;
	private final int jointSize = 10;
	//private long t0;
	private boolean paused=false,inGame=true,hit=false;
	private Snake player;
	private Food noms;
	private Timer timer;
	public GameObj(int x, int y){
		xdim=x;
		ydim=y;
		setPreferredSize(new Dimension(xdim,ydim));
		setFocusable(true);
		setDoubleBuffered(true);
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

	//check for collisions with self, walls, food
	private boolean checkCollision(){
		ArrayList<int[]> snakePos=player.getPos();
		int[] headPos=snakePos.get(0);
		int[] nomPos = noms.getPos();
		//check for nommed food
		if((Math.abs(headPos[0]-nomPos[0])<=5)&&(Math.abs(headPos[1]-nomPos[1])<=5)){
			player.eat();
			noms=new Food(xdim,ydim);
		}
		//check for wall smack
		if(headPos[0]<0||headPos[0]>xdim||headPos[1]<0||headPos[1]>ydim){
			hit=true;
			inGame=false;
			return hit;
		}
		//check for self smack
		for(int[] x:snakePos){
			if(snakePos.indexOf(x)>0){
				if((headPos[0]==x[0])&&(headPos[1]==x[1])){
					hit=true;
					inGame=false;
					return hit;
				}
			}
		}
		return hit;
	}

	//handle pausing of game
	private boolean pause(){
		if(paused){
			timer.start();
			paused=!paused;
		}
		else{
			timer.stop();
			paused=!paused;
		}
		return paused;
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
			pause();
	}

	private void gameOver(){
		String lose = "Game Over";
		Graphics g = getGraphics();
		Font f = new Font("Courier New", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(f);
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString(lose,(xdim-fm.stringWidth(lose))/2,ydim/2);
	}

	public void actionPerformed(ActionEvent e){
		if(!inGame){
			timer.stop();
			//System.out.println("noot noot!");
			gameOver();
		}
		else{
			hit=checkCollision();
			if(!hit){
				Graphics g = getGraphics();
				paint(g);
			}
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
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
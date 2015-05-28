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

	private int xdim, ydim, points=0;
	//Timer pings every 17 ms for ~60fps framerate
	private final int TICK=100;
	private final int jointSize = 10;
	//private long t0;
	private boolean paused=false,inGame=true,hit=false;
	private Snake player;
	private Food noms;
	private Timer timer;
	private Font f;
	private FontMetrics fm;
	public GameObj(int x, int y){
		xdim=x;
		ydim=y;
		setPreferredSize(new Dimension(xdim,ydim));
		setFocusable(true);
		setDoubleBuffered(true);
		addKeyListener(this);
		//t0=system.timeNano();
		////Graphics objects
		f = new Font("Courier New", Font.BOLD, 14);
		fm = getFontMetrics(f);
		//Link start!
		init();
		//Start the clock!
		timer = new Timer(TICK,this);
		timer.start();
	}

	//check for collisions with self, walls, food
	private boolean checkCollision(){
		ArrayList<int[]> snakePos=player.getPos();
		int[] headPos=snakePos.get(0);
		int[] nomPos = noms.getPos();
		//check for nommed food
		if((Math.abs(headPos[0]-nomPos[0])<=5)&&(Math.abs(headPos[1]-nomPos[1])<=5)){
			player.eat();
			points++;
			noms=new Food(xdim,ydim,jointSize);
		}
		//check for wall smack
		if(headPos[0]<0||headPos[0]>xdim-jointSize||headPos[1]<0||headPos[1]>ydim-jointSize){
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

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//Pause
		if(code==KeyEvent.VK_P){
			pause();
			return;
		}
		else if(code==KeyEvent.VK_R){
			reset();
			return;
		}
		if(!paused){
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
		}
		
	}

	//handle pausing of game
	private boolean pause(){
		if(inGame){
			if(paused){
				timer.start();
				paused=!paused;
			}
			else{
				timer.stop();
				paused=!paused;
			}
		}
		return paused;
	}

	//Draw Game Over strings
	private void gameOver(Graphics g){
		String lose = "Game Over";
		String score = "Final score: " + points + " morsels consumed";
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString(lose,(xdim-fm.stringWidth(lose))/2,ydim/2);
		g.drawString(score,(xdim-fm.stringWidth(score))/2,ydim/2+fm.getHeight()+10);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	//Reset and start new game
	private void reset(){
		points=0;
		inGame=true;
		paused=false;
		hit=false;
		init();
		timer.restart();
	}

	//Respond to timer clicks and render frames
	public void actionPerformed(ActionEvent e){
		Graphics g = getGraphics();
		if(!inGame){
			timer.stop();
			gameOver(g);
		}
		else{
			hit=checkCollision();
			if(!hit){
				paint(g);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		int[] foodPos=noms.getPos();
		ArrayList<int[]> snakePos=player.move();
		String score = "Score: " + points;
		g.setColor(Color.RED);
		g.fillOval(foodPos[0],foodPos[1],jointSize,jointSize);
		g.setColor(Color.GREEN);
		for(int[] x:snakePos){
			g.fillOval(x[0],x[1],jointSize,jointSize);
		}
		g.setFont(f);
		g.setColor(Color.BLACK);
		g.drawString(score,xdim-fm.stringWidth(score)-5,fm.getHeight());
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	//initialize snake and food
	private void init(){
		//Specify iniitial snake position
		ArrayList<int[]> start = new ArrayList<int[]>();
		start.add(new int[]{10*Math.round(((xdim-jointSize)/2)/jointSize),10*Math.round(((ydim-jointSize)/2)/jointSize)});
		start.add(new int[]{(xdim-jointSize)/2,ydim/2+2*jointSize});
		start.add(new int[]{(xdim-jointSize)/2,ydim/2+3*jointSize});
		player = new Snake(start);
		//nom nom nom
		noms = new Food(xdim,ydim,jointSize);
		Toolkit.getDefaultToolkit().sync();
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
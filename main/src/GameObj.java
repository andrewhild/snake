package main;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameObj extends JPanel implements KeyListener,ActionListener{

	private int xdim, ydim;
	public GameObj(int x, int y){
		xdim=x;
		ydim=y;
		setFocusable(true);
		addKeyListener(this);

	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_UP)
			System.out.println("noot noot!");
		else if(code==KeyEvent.VK_DOWN)
			System.out.println("noot noot!");
		else if(code==KeyEvent.VK_RIGHT)
			System.out.println("noot noot!");
		else if(code==KeyEvent.VK_LEFT)
			System.out.println("noot noot!");
	}


	public void actionPerformed(ActionEvent e){

	}

	public void paint(Graphics g) {
		super.paint(g);
		g.dispose();
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}


}
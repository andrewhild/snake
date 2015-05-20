package main;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.BorderLayout;

public class snakeMain extends JFrame{

	//Constants for board size
	private final int xdim = 640;
	private final int ydim = 480;

	public snakMain(String title){
		setTitle(title);
		setSize(xdim,ydim);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		add(new GameObj(xdim,ydim),BorderLayout.CENTER);
		setVisible(true);

	}

	public static void main(String[] args) {
		//Interior class to define a new thread and run it
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame game = new snakeMain("Snake by Andrew Hild");
			}
		});
		
	}

}
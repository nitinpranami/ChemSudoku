import java.awt.*; 
import java.awt.event.*; 

import javax.swing.*; 
import javax.swing.event.*;

import java.util.Random;	
import java.io.*; 
import java.util.Scanner;

import javax.imageio.*;


public class Sudoku{
    JFrame frame;		//The overall frame in which the game is played.
    MyPanel panel;	//The start, instructions, and game panel are created in this class, using a card layout.
	
    public static void main(String [] args){	//Main method.
	Sudoku s = new Sudoku();
	s.run();
    }

    public void run(){		//Creates the main JFrame and creates and adds a card panel to it. 
	frame = new JFrame("Sudoku"); 
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	panel = new MyPanel();
	frame.getContentPane().add(panel); 	
	frame.setSize(616,688);
	frame.setVisible(true);
    }
    
	class MyPanel extends JPanel {
		
		int [] xGrid = new int[9];        
	    int [] yGrid = new int[9];    
	    
	    int width;
		int height;
	    
		 public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			g.setColor(Color.GREEN);
			g.drawString("Welcome to Sudoku", 60, 50);
			
			width = this.getWidth();
			height = this.getHeight();
			
			g.setColor(Color.black);
			
			
			for(int i=0; i<9; i++){           //Drawing the grid. 
			    g.drawLine((int)(width*i/9), 0, (int)(width*i/9), height);
			    xGrid[i] = (int)(width*i/9);
			    g.drawLine(0,(int)(height*i/9), width, (int)(height*i/9));
			    yGrid[i] = (int)(height*i/9);
			}
			
			requestFocus();
		 }
	}
		
}

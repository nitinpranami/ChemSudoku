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
    MyCardPanel myCardPanel;
  
	
    public static void main(String [] args){	//Main method.
    	Sudoku s = new Sudoku();
    	s.run();
    }

    public void run(){		//Creates the main JFrame and creates and adds a card panel to it. 
		frame = new JFrame("Sudoku"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		myCardPanel = new MyCardPanel();
		frame.getContentPane().add(myCardPanel); 	
		frame.setSize(616,688);
		frame.setVisible(true);
	}
    
	class MyCardPanel extends JPanel {
		StartPanel panel1; 
		GamePanel panel2;
		CardLayout cards;  
		
		public MyCardPanel(){
		    //Creating each panel and setting the layout.
		    cards = new CardLayout();
		    setLayout(cards);//default layout
		    panel1 = new StartPanel();
		    panel2 = new GamePanel();

		    add(panel1, "Start"); add(panel2,"Game");
		}
	
		class StartPanel extends JPanel{
			JButton clickToPlay;
		
			public StartPanel(){
				setLayout(null);
				setButtonForInstructions();
				ButtonListenerToInstructions bli = new ButtonListenerToInstructions();
				clickToPlay.addActionListener(bli);	
			}
		
			 public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setFont(new Font("Arial", Font.BOLD, 50));
				g.setColor(Color.GREEN);
				g.drawString("Sudoku", 60, 50);
			
			}
		 
			 public void setButtonForInstructions(){     //This method creates the JButton to continue on to the instructions panel. 
				 clickToPlay = new JButton();
				 clickToPlay.setText("Click to play");
				 clickToPlay.setPreferredSize(new Dimension(50,100));
				 clickToPlay.setBounds(100,550,400,50);
				 add(clickToPlay);
			 }

			class ButtonListenerToInstructions implements ActionListener{       //Pressing the button makes the instructions panel appear. 
				public void actionPerformed(ActionEvent e){
					cards.show(myCardPanel, "Game");
				}
			}
		}
	
		class GamePanel extends JPanel implements ActionListener{
	
			JTextField userRowInput; 
			JTextField userColumnInput; 
			JTextField userNumberInput; 
			
			
			int [] xGrid = new int[10];        
		    int [] yGrid = new int[10];  
		    int[][] Board = new int[][]{
		            {0,6,0,0,0,4,7,0,3},
		            {4,0,0,0,0,7,0,0,0},
		            {0,0,0,6,5,0,0,0,0},
		            {6,0,0,0,3,0,1,0,0},
		            {7,0,5,0,0,0,9,0,4},
		            {0,0,1,0,4,0,0,0,7},
		            {0,0,0,0,7,2,0,0,0},
		            {0,0,0,5,0,0,0,0,9},
		            {2,0,3,9,0,0,0,6,0}
		     };
		    
		    int width;
			int height;
			String number;
			int userRow;
			int userColumn;
			int userNumber;
			
			public GamePanel(){
				
				setLayout(null);
				userRowInput = new JTextField(30);
				userRowInput.setText("Row number:");
				userRowInput.setBounds(85, 630, 100, 25);
				userRowInput.addActionListener(this);
				add(userRowInput);
				userRowInput.requestFocusInWindow();
				
				userColumnInput = new JTextField(30);
				userColumnInput.setText("Column number:");
				userColumnInput.setBounds(200, 630, 100, 25);
				userColumnInput.addActionListener(this);
				add(userColumnInput);
				
				userNumberInput = new JTextField(30);
				userNumberInput.setText("Number:");
				userNumberInput.setBounds(315, 630, 100, 25);
				userNumberInput.addActionListener(this);
				add(userNumberInput);		
				
				
			}
			
		 	public void paintComponent(Graphics g){
				
				width = this.getWidth();
				height = this.getHeight() - 50;
			
				g.setColor(Color.black);
			
			 	Graphics2D g2 = (Graphics2D) g;
			
				 for(int i=0; i<10; i++){           //Drawing the grid. 
						if(i%3==0){
							//System.out.println(i);
							g2.setStroke(new BasicStroke(10));
						    g.drawLine((int)(width*i/9),0, (int)(width*i/9), height);
						    g2.drawLine(0,(int)(height*i/9), width, (int)(height*i/9));
							}
						g2.setStroke(new BasicStroke(1));
					    g.drawLine((int)(width*i/9), 0, (int)(width*i/9), height);  
					    g.drawLine(0,(int)(height*i/9), width, (int)(height*i/9));
						xGrid[i] = (int)(width*i/9);
						yGrid[i] = (int)(height*i/9);
				 }				 
				 for(int i=0; i<9; i++){
					 for(int j=0; j<9; j++){
						 number = Integer.toString(Board[i][j]);
						 if(Board[i][j]!=0){
							 g.setFont(new Font("Arial", Font.BOLD, 30));
							 g.drawString(number,(int)(width*j/9) + 30, (int)(height*i/9) + 50 );
						 }
					 }
				 }
		 	}		
			
		 	public void actionPerformed(ActionEvent e) {
		 		if(e.getSource() == userRowInput)
		 			userRow = Integer.parseInt(userRowInput.getText());  
				
		 		if(e.getSource() == userColumnInput)
		 			userColumn = Integer.parseInt(userColumnInput.getText()); 
					
				if(e.getSource() == userNumberInput)
					userNumber = Integer.parseInt(userNumberInput.getText()); 
						
				System.out.println(userRow);
				System.out.println(userColumn);
				System.out.println(userNumber);
			}
		}
	}	
}
	
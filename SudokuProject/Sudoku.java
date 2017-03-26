import java.awt.*; 
import java.awt.event.*; 

import javax.swing.*; 
import javax.swing.event.*;

import java.util.Random;	
import java.io.*; 
import java.util.Scanner;
import java.util.*;

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

			
	
			JTextField userInput; 
			
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
		    
		    int[][] Key = new int[][]{
		    		{5,6,2,8,9,4,7,1,3},
				    {4,1,8,3,2,7,5,9,6},
				    {3,9,7,6,5,1,8,4,2},
				    {6,4,9,7,3,5,1,2,8},
				    {7,2,5,1,8,6,9,3,4},
				    {8,3,1,2,4,9,6,5,7},
				    {9,5,6,4,7,2,3,8,1},
				    {1,8,4,5,6,3,2,7,9},
				    {2,7,3,9,2,8,4,6,5}
		     };
		    
		    int width;
			int height;
			String number;
			String userInputString = "";
			int userRow;
			int userColumn;
			int userNumber;
			int points = 0;
			int legalCounter = 0;
			int notLegalCounter = 0;
			String [] questions = new String [31];
			String [] answers = new String [31];

			public GamePanel(){
				
				setLayout(null);
				userInput = new JTextField(30);
				userInput.setText("Row number:");
				userInput.setBounds(85, 630, 100, 25);
				userInput.addActionListener(this);
				add(userInput);
				userInput.requestFocusInWindow();
				
				readQuestionsAndAnswers(questions, answers);
				
				
			}
			
			
			 public void readQuestionsAndAnswers(String [] ques, String [] ans) { 
					Scanner questionsFile=null;
					try{				
					    questionsFile = new Scanner(new File("Questions.txt"));
					}catch(FileNotFoundException e){
					    System.err.println("File Questions.txt not found ");
					    System.exit(1);
					}
					int index = 0;	
					while(index < 31 && questionsFile.hasNext()) {
						questions[index] = questionsFile.nextLine();
						System.out.println(questions[index]);
					    index++;
					}
					try {
					    questionsFile.close();
					} catch (Exception e) {
					    System.out.println("Error writing players.txt: " + e);
					    System.exit(1);
					}
					
					Scanner answersFile=null;
					try{				
					    answersFile = new Scanner(new File("Answers.txt"));
					}catch(FileNotFoundException e){
					    System.err.println("File Answers.txt not found ");
					    System.exit(1);
					}
					int counterA = 0;	
					while(counterA < 31 && answersFile.hasNext()) {
						answers[counterA] = answersFile.nextLine();
						System.out.println(answers[counterA]);
					    counterA++;
					}
					try {
					    answersFile.close();
					} catch (Exception e) {
					    System.out.println("Error writing players.txt: " + e);
					    System.exit(1);
					}			
			 }
			
		 	public void paintComponent(Graphics g){
		 		repaint();
				super.paintComponent(g);
				width = this.getWidth();
				height = this.getHeight() - 50;
				
				g.setFont(new Font("Arial", Font.BOLD, 50));
				g.setColor(Color.BLUE);
				g.drawString("Points: " + Integer.toString(points), 350, 660);
				
			
			
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
					
				g.setFont(new Font("Arial", Font.BOLD, 30));
				
				if(userInputString.length() == 3){
					separateInput();
					 
					// if(userRow != 0 && userColumn != 0){
					 	
						g.drawString(Integer.toString(userNumber),(int)(width*(userColumn-1)/9) + 30, (int)(height*(userRow-1)/9) + 50 );
												
					// }
				}
				
					/*userRow = 0;
					userColumn = 0;
					userNumber = 0;
					*/
								 
		 	}	
		 	public void separateInput(){
		 		legalCounter = 0;
		 		notLegalCounter = 0;
		 		userRow = Integer.parseInt(userInputString.substring(0,1));
		 		userColumn = Integer.parseInt(userInputString.substring(1,2));
		 		userNumber = Integer.parseInt(userInputString.substring(2,3));
		 	}
		 	
		 	public boolean checkIfLegal(){
		 		Board[userRow-1][userColumn-1] = userNumber;
		 		System.out.println(Board[userRow-1][userColumn-1] + " " + Key[userRow-1][userColumn-1]);
		 		if(Board[userRow-1][userColumn-1] == Key[userRow-1][userColumn-1]){
		 			
		 			//legalCounter+=1;
		 			return true;
		 			
		 		}
		 		else{
		 			
		 			//notLegalCounter+=1;
		 			return false;
		 		}

		
		 	
		 	}
		 	
		 	public void updatePoints(){
		 		System.out.println(checkIfLegal());
		 		if(checkIfLegal())
		 			points +=10;
		 		else if(!checkIfLegal())
		 			points -= 50;
		 	}
		 	
		 	
		 	
		 	public void actionPerformed(ActionEvent e) {
		 		if(e.getSource() == userInput){
		 			userInputString = userInput.getText();  
		 		}
				separateInput();
		 		updatePoints();
			}		 	
		}
	}	
}
	
		



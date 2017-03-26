import java.io.*;
import java.util.*;

public class SudokuAlgorithm{
    
    static int[][] BoardCopy = new int[][]{
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
    
    static int[][] Key = new int[][]{
    {5,6,2,0,0,4,7,0,3},
    {4,1,8,3,2,7,5,9,6},
    {3,9,7,6,5,1,8,4,2},
    {6,4,9,7,3,5,1,2,8},
    {7,2,5,1,8,6,9,3,4},
    {8,3,1,2,4,9,6,5,7},
    {9,5,6,4,7,2,3,8,1},
    {1,8,4,5,6,3,2,7,9},
    {2,7,3,9,2,8,4,6,5}
    };
    
    
    public static void main(String[] args) throws IOException{
        Random rn = new Random();
        
        BufferedReader br = new BufferedReader(new FileReader("Questions.txt"));
        StringTokenizer st = new StringTokenizer(br.readLine());
       
        
        int n = Integer.parseInt(st.nextToken());
        String[] questions = new String[n];
        for(int i = 0; i < n; i++){
            String qIndex = br.readLine();
            questions[i] = qIndex.toLowerCase();
            System.out.println(questions[i]);
        }
 
        BufferedReader ba = new BufferedReader(new FileReader("Answers.txt"));
        String[] answers = new String[n];
        for(int j = 0; j < n; j++){
            String AIndex = ba.readLine();
            answers[j] = AIndex.toLowerCase();
            System.out.println(answers[j]);
        }
        
        boolean[] questionsB = new boolean[n]; //preset to false
        
        
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
        
        int randomNum = -1;
        while(true){
            Scanner loc = new Scanner(System.in);
            System.out.println("Row of desired location to edit: ");
            int row = loc.nextInt();
            System.out.println("Column of desired location to edit: ");
            int col = loc.nextInt();
            if(CheckPrimitive(BoardCopy, row, col)){ //if chosen field is correct
                System.out.println("Insert Answer: ");
                int ans = loc.nextInt();
                
                if(ans == Key[row-1][col-1]){
                    System.out.println("Correct!");
                    Board[row+1][col+1] = ans;
                }
                
                else{
                    Board[row-1][col-1] = ans;
                    System.out.println("Your answer is incorrect. Please answer the question to continue:");
                //Insert Implementation Here **********
                    boolean stopSearch = true;
                    int numChoose = -1;
                    while(stopSearch){
                        randomNum = rn.nextInt(n);
                        if(questionsB[randomNum] == false){
                            questionsB[randomNum] = true;
                            stopSearch = false;
                        }
                    }
                    System.out.println("trigger");
                    System.out.println(questions[randomNum]);
                    Scanner responseScan = new Scanner(System.in);
                    String response = responseScan.nextLine();  
                    if(response.equals(answers[randomNum])){
                        System.out.println("Correct! The number has now been filled in for you. The number was " + Key[row-1][col-1]);
                        Board[row-1][col-1] = Key[row-1][col-1];
                    }
                    else
                    {
                        System.out.println("Incorrect Answer. The answer was " + answers[randomNum]);
                    }
                    
                }
               
            }
            else{ //if not correct box
                System.out.println("Try again, wrong box chosen");
            }
        } //end while loop
        
    }
    



public static boolean CheckPrimitive(int[][] k, int row, int col)
    {

if(k[row-1][col-1] != 0){
return false; //User is trying to fill out predefined field
}
return true; //User is filling out empty prefield --> PASS
}


}

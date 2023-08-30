package OldStuffs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;


/**
 *
 * @author 
 */
public class Wordle  implements Runnable, ActionListener{

  //methods

  //method to determine where the designated row will be layed out on the panel
  public int yStartPos(int rowNum) {
    int yStartPos = 35 + ((height + gap) * rowNum);
    return yStartPos;
  }

  //method to generate a random word
  public String chooseWord() {
    //range is dependent on dictionary length
    int min = 0; 
    int max = 51;
    int randomNumber = (int)(Math.random( )*(max-min+1));
    //choose a random word in that dictionary
    String word = dictionary[randomNumber];
    return word;
  }
  
  // Class Variables  

  //textfield arrays:
  JTextField[] row1;
  JTextField[] row2;
  JTextField[] row3;
  JTextField[] row4;
  JTextField[] row5;
  JTextField[] row6;

  //textfield parameters
  int width = 50;
  int height = 50;
  int gap = 10;
  int xStartPos = 255;
  int xEndPos = 755;

  //main gui
  JFrame frame;
  JPanel mainPanel;
  JTextField guessText;
  JButton guessButton;
  JLabel fiveLetterWord;
  //STAT GUI
  JFrame statFrame;
  JPanel statPanel;
  JButton playAgain;

  //stat stuff
  JLabel gamesPlayedText;
  JLabel gamesPlayedStat;
  JLabel winPercentageText;
  JLabel winPercentageStat;
  JLabel streakText;
  JLabel streakStat;
  JLabel bestStreakText;
  JLabel bestStreakStat;
  JLabel winOrLose;
  JLabel wordWas;

  //guess distribution, which will have 6 indexes since 6 is the max possible guesses taken 
  JLabel guessTitle;
  JLabel[] guessDistribution;
  JLabel[] guessDistributionAnswer;

  //dimensions for the column
  int guessHeight = 25;
  int guessWidth = 25;
  int guessX = 100;
  int guessY = 200;

  //the amount of times you guess within a certain amount of tries (for guess distribution)
  int[] timesGuessed;

  //alphabet to help the player know which letters he's used/hasn't used
  //label and actual chracter alphabet will be related
  JLabel[] alphabet;
  char[] alphabetChar;

  //control variables
  int rowNumCount = 0;
  //stat control variables
  int gamesPlayed = 0;
  int gamesWon = 0;
  int currentStreak = 0;
  //initially the best streak will be 0
  int bestStreak = 0;
  int attempts = 0;
  
  //font & color
  Color buttonCol = new Color(201, 250, 255);
  Color correct = new Color(145, 255, 180);
  Color almost = new Color(255, 255, 150);
  Color wrong = new Color(180, 180, 180);
  Color darkerCorrect = new Color(0, 217, 108);
  Color darkerAlmost = new Color(255, 217, 0);
  //my font is the font for the textfields i don't know why I named it that and I am too lazy to change it
  Font myFont = new Font("Calibri", Font.BOLD, 27);
  Font buttonFont = new Font("Dialog", Font.BOLD, 15);
  Font wordRevealed = new Font("Dialog", Font.BOLD, 18);
  Font statsFont = new Font("Arial", Font.PLAIN, 13);
  Font guessDistributionFont = new Font("Arial", Font.PLAIN, 16);
  Font guessDistributionAnswerFont = new Font("Arail", Font.BOLD, 16);
  Font letters = new Font("Dialog", Font.BOLD, 26);

  //mayas goofy cat
  JLabel hampter;

  //word dictionary
  //dont set limit so i can keep adding words
  String[] dictionary = {"AWAKE", "BOOZE", "CLIMB", "DREAM", "ENTER", "FLING", "GREEN", "HAPPY", "IMPLY", "JOUST", "KNOWN", "LEASH", "MAGIC",
                         "NAVAL", "OASIS", "PRUNE", "QUEST", "REIGN", "SLICK", "TOTEM", "UNSET", "VOUCH", "WAVER", "XENON", "YEAST", "ZESTY", 
                         "AMBER", "BRICK", "CARVE", "DRAIN", "EXERT", "FAINT", "GLEAM", "HORSE", "IRONY", "JEWEL", "KARMA", "LANCE", "MOIST",
                         "NORTH", "OCEAN", "PLUCK", "QUEEN", "REFER", "SLICE", "TROPE", "USURP", "VENOM", "WORTH", "XEBEC", "YOINK", "ZEBRA" }; 
  //intial word
  String answer = chooseWord();

  //test word
  //String answer = "APPLE";

  // Method to assemble our GUI
  public void run(){
    // Creats a JFrame that is 800 pixels by 600 pixels, and closes when you click on the X
    frame = new JFrame("Wordle");
    // Makes the X button close the program
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // makes the windows 800 pixel wide by 600 pixels tall
    frame.setSize(800, 800);
    // shows the window
    frame.setVisible(true);

    //create a JPanel
    mainPanel = new JPanel();
    //disable layout manager
    mainPanel.setLayout(null);
    //add to jframe
    frame.add(mainPanel);
 
    //textfield grid creation
    //going to group the textfields by rows

    //define all the rows with size 5
    row1 = new JTextField[5];
    row2 = new JTextField[5];
    row3 = new JTextField[5];
    row4 = new JTextField[5];
    row5 = new JTextField[5];
    row6 = new JTextField[5];

    //create all the rows

    //cousin in university showed me how to optimise my code using switch statements

    //rows go 1 through 6
    for(int row = 1; row <= 6; row++) {
      for(int x = xStartPos, i = 0; i < row1.length; x = x + (gap + width), i++) {
        //determine which row number we are on and then go to that case
        switch (row) {
          case 1:
            //make the blank textfield 
            row1[i] = new JTextField();
            //set its position
            row1[i].setBounds(x, yStartPos(1), width, height);
            /* //dont allow them to type in it
            nevermind it looks TERRIBLE
            row1[i].setEnabled(false); */ 
            //add it to panel
            mainPanel.add(row1[i]);
            //skip over other cases
            break;
          case 2:
            row2[i] = new JTextField();
            row2[i].setBounds(x, yStartPos(2), width, height);
            mainPanel.add(row2[i]);
            break; 
          case 3:
            row3[i] = new JTextField();
            row3[i].setBounds(x, yStartPos(3), width, height);
            mainPanel.add(row3[i]);
            break; 
          case 4:
            row4[i] = new JTextField();
            row4[i].setBounds(x, yStartPos(4), width, height);
            mainPanel.add(row4[i]);
            break; 
          case 5:
            row5[i] = new JTextField();
            row5[i].setBounds(x, yStartPos(5), width, height);
            mainPanel.add(row5[i]);
            break; 
          case 6:
            row6[i] = new JTextField();
            row6[i].setBounds(x, yStartPos(6), width, height);
            mainPanel.add(row6[i]);
            break; 
          default:
            //(wont happen but must include a default)
            System.out.println("case unknown.");
        }
      }
    }  

    //old unoptimized code

   /*  row1 = new JTextField[5];

    //control variable i controls the index while x controls the x increment
    for(int x = xStartPos, i = 0; i < row1.length; x = x + (gap + width), i++) {
      //make the blank textfield 
      row1[i] = new JTextField();
      //set its position
      row1[i].setBounds(x, yStartPos(1), width, height);
      //add it to panel
      mainPanel.add(row1[i]);
    }

    //create the second row
    row2 = new JTextField[5];

    for(int x = xStartPos, i = 0; i < row2.length; x = x + (gap + width), i++) {
      row2[i] = new JTextField();
      row2[i].setBounds(x, yStartPos(2), width, height);
      mainPanel.add(row2[i]);
    }

    //create the third row
    row3 = new JTextField[5];

    for(int x = xStartPos, i = 0; i < row3.length; x = x + (gap + width), i++) {
      row3[i] = new JTextField();
      row3[i].setBounds(x, yStartPos(3), width, height);
      mainPanel.add(row3[i]);
    }

    //create the fourth row
    row4 = new JTextField[5];

    for(int x = xStartPos, i = 0; i < row4.length; x = x + (gap + width), i++) {
      row4[i] = new JTextField();
      row4[i].setBounds(x, yStartPos(4), width, height);
      mainPanel.add(row4[i]);
    }
    
    //create the fifth row
    row5 = new JTextField[5];

    for(int x = xStartPos, i = 0; i < row5.length; x = x + (gap + width), i++) {
      row5[i] = new JTextField();
      row5[i].setBounds(x, yStartPos(5), width, height);
      mainPanel.add(row5[i]);
    }

    //create the final row
    row6 = new JTextField[5];

    for(int x = xStartPos, i = 0; i < row6.length; x = x + (gap + width), i++) {
      row6[i] = new JTextField();
      row6[i].setBounds(x, yStartPos(6), width, height);
      mainPanel.add(row6[i]);
    } */

    //Create the textfield to type in
    guessText = new JTextField();
    guessText.setBounds(350, 500, 100, 50);
    mainPanel.add(guessText);
    //action command
    //this literally made the game so much letter i didn't know you could do this
    guessText.addActionListener(this);
    guessText.setActionCommand("guess");

    //dont need you anymore buddy

    /* 
    //create the button to guess
    guessButton = new JButton("guess");
    guessButton.setBounds(360, 475, 80, 50);
    guessButton.setFont(buttonFont);
    guessButton.setBackground(buttonCol);
    mainPanel.add(guessButton);
    //set up the command 
    guessButton.addActionListener(this);
    guessButton.setActionCommand("guess"); 
    */

    //create the label that pops up when they don't input a 5 letter word or other stuff
    fiveLetterWord = new JLabel();
    fiveLetterWord.setBounds(315, 35, 170, 50);
    fiveLetterWord.setFont(buttonFont);
    mainPanel.add(fiveLetterWord);

    //create the alphabet label
    alphabet = new JLabel[26];
    //we can also just utlize this for loop to make our character alphabet array
    alphabetChar = new char[26];

    //starting first letter at A which will go up
    char firstLetter = 'A';

    for(int i = 0, x = 60; i < alphabet.length; i++, x = x + 26) {
      //need to go through alphabet letters
      int charNumber = firstLetter + i;
      char theLetter = (char)charNumber;
      alphabet[i] = new JLabel("" + theLetter);
      alphabet[i].setBounds(x, 650, 40, 40);
      alphabet[i].setFont(letters);
      mainPanel.add(alphabet[i]);

      //add the char to the alphabet array
      alphabetChar[i] = theLetter;
    }

    //statistics GUI

    // Creats a JFrame that is 400 pixels by 400 pixels, and closes when you click on the X
    statFrame = new JFrame("Statistics:");
    // Makes the X button close the program
    statFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // makes the windows 400 by 400
    statFrame.setSize(500, 500);

    //create a JPanel
    statPanel = new JPanel();
    //disable layout manager
    statPanel.setLayout(null);
    //add to jframe
    statFrame.add(statPanel);

    //play again button
    playAgain = new JButton("Play Again?");
    playAgain.setBounds(240, 375, 120, 50);
    playAgain.setFont(buttonFont);
    playAgain.setBackground(buttonCol);
    statPanel.add(playAgain);
    //command
    playAgain.addActionListener(this);
    playAgain.setActionCommand("restart");

    //win or lose label
    winOrLose = new JLabel();
    winOrLose.setBounds(140, 375, 75, 50);
    winOrLose.setFont(buttonFont);
    statPanel.add(winOrLose);

    //stats labels

    //games played
    gamesPlayedText = new JLabel("Played");
    gamesPlayedText.setBounds(135, 115, 50, 25);
    gamesPlayedText.setFont(statsFont);
    statPanel.add(gamesPlayedText);

    gamesPlayedStat = new JLabel();
    gamesPlayedStat.setBounds(150, 75, 50, 50);
    gamesPlayedStat.setFont(myFont);
    statPanel.add(gamesPlayedStat);

    //win %
    winPercentageText = new JLabel("Win %");
    winPercentageText.setBounds(195, 115, 50, 25);
    winPercentageText.setFont(statsFont);
    statPanel.add(winPercentageText);

    winPercentageStat = new JLabel();
    winPercentageStat.setBounds(190, 75, 75, 50);
    winPercentageStat.setFont(myFont);
    statPanel.add(winPercentageStat);

    //current streak
    streakText = new JLabel("Streak");
    streakText.setBounds(260, 115, 50, 25);
    streakText.setFont(statsFont);
    statPanel.add(streakText);

    streakStat = new JLabel();
    streakStat.setBounds(270, 75, 75, 50);
    streakStat.setFont(myFont);
    statPanel.add(streakStat);

    //best streak
    bestStreakText = new JLabel("Max Streak");
    bestStreakText.setBounds(315, 115, 75, 25);
    bestStreakText.setFont(statsFont);
    statPanel.add(bestStreakText);

    bestStreakStat = new JLabel();
    bestStreakStat.setBounds(335, 75, 75, 50);
    bestStreakStat.setFont(myFont);
    statPanel.add(bestStreakStat);

    //label that reveals the word
    wordWas = new JLabel();
    wordWas.setBounds(225, 25, 75, 50);
    wordWas.setFont(wordRevealed);
    wordWas.setForeground(darkerCorrect);
    statPanel.add(wordWas);

    //guess distribution

    //guess distribution title
    guessTitle = new JLabel("GUESS DISTRIBUTION");
    guessTitle.setBounds(160, 150, 180, 50);
    guessTitle.setFont(buttonFont);
    statPanel.add(guessTitle);

    //cat
    hampter = new JLabel();
    hampter.setBounds(250, 200, 150, 150);
    ImageIcon theGreatHampter = new ImageIcon("hampter.jpg");
    hampter.setIcon(theGreatHampter);
    statPanel.add(hampter);


    //the column which displays the guesses it took to guess the word
    guessDistribution = new JLabel[6];

    //y controls y position, i controls index, j controls number being labeled
    for(int y = guessY, i = 0, j = 1; i < guessDistribution.length; y = y + 25, i++, j++) {
      guessDistribution[i] = new JLabel("" + j + ":");
      guessDistribution[i].setBounds(guessX, y, guessWidth, guessHeight);
      guessDistribution[i].setFont(guessDistributionFont);
      statPanel.add(guessDistribution[i]);
    }

    //the column which determines the amount of times they have guessed in a particular set of attempts
    guessDistributionAnswer = new JLabel[6];

    //first set all of the answers to 0
    timesGuessed = new int[6];
    for(int i = 0; i < timesGuessed.length; i++) {
      timesGuessed[i] = 0;
    }

    //y controls y position, i controls index, j controls number being labeled
    for(int y = guessY, i = 0; i < guessDistributionAnswer.length; y = y + 25, i++) {
      guessDistributionAnswer[i] = new JLabel("" + timesGuessed[i]);
      guessDistributionAnswer[i].setBounds(guessX + 50, y, guessWidth, guessHeight);
      guessDistributionAnswer[i].setFont(guessDistributionAnswerFont);
      statPanel.add(guessDistributionAnswer[i]);
    }
}

   
  // method called when a button is pressed
  public void actionPerformed(ActionEvent e) {
    // get the command from the action
    String command = e.getActionCommand();

    //call this command when the guess button is pressed
    if(command.equals("guess")) {
      //all the logic goes here
      //first we want to get their guess and turn it lower case
      String guess = guessText.getText();
      //turn it all lower case
      guess = guess.toUpperCase();

      //turn it into a char array
      char[] guessArray = guess.toCharArray();
      char[] answerArray = answer.toCharArray();

      //important control variables

      //add 1 to the guess counter when guess button is pressed so we know which row we are on
      //do this for attempts aswell
      //determine if its a 5 letter word first though

      //booleans to determine their guess is valid
      //start as false
      boolean validLetters = false;
      boolean validGuess = false;

      //determine if their guess contains a actual letter

      //for loop to go through each character in guess
      for(int i = 0; i < guessArray.length; i++) {
        //loop to go through each letter and  compare it
        for(int j = 0; j < alphabetChar.length; j++) {
          //check through all the letters to see if it matches one
          if(guessArray[i] == alphabetChar[j]) {
            validLetters = true;
            //break and go to the next letter
            break;
          }
          else{
            //false otherwise
            validLetters = false;
          }
        }
        //check if its false and if it is then the word is a no go
        if(validLetters == false) {
          //just stop and don't go through the rest for efficieny, and let them know
          fiveLetterWord.setText("Guess Using Letters");
          break;
        }
      }

      //determine if its 5 letters 
      //validLetters will be true up if it passed the first check
      if(guessArray.length == 5 && validLetters) {
        //proceed as normal
        rowNumCount++;
        attempts++;
        fiveLetterWord.setText("");
        validGuess = true;
      }
      //if the letters are okay but the length is not
      else if(!validGuess && validLetters) {
        //dont let them continue
        fiveLetterWord.setText("Guess a 5 Letter Word");
        validGuess = false;
      }


      //the color states of the letters are: 0 will be grey, 1 will be green, 2 will be yellow
      int[] colorState = new int[5];

      
      //boolean variables start off as true but are subjected to change as the game goes on
      boolean gameFinished = false;
      boolean win = true;

      //now need to compare each character to each character in the word
      //first of all determine which row we are on
      if(rowNumCount == 1 && validGuess) {
        //first thing we have to do is check for the correct letters
        for(int i = 0; i < answerArray.length; i++) {
          //letters are in same index and equal = correct guess
          if(guessArray[i] == answerArray[i]) {
            //make it so that we know this is now a correct letter
            colorState[i] = 1;
            //remove the letter since we already know it is correct, so we don't need to read it again
            answerArray[i] = '!';
          }
          //switch to false if not all letters are correct
          else {
            win = false;
          }
        }
        //first check if we won the game already or not
        if(win) {
          //finish the game
          gameFinished = true;
        }
        //loop which cycles through the guess characters
        for(int i = 0; i < guessArray.length; i++) {
          //compare the current character from the guess to all the characters in the answer
          for(int j = 0; j < answerArray.length; j++) {
            //letter are in different indexes but eaual, and the letter hasn't already been determined as correct = almost correct guess
            if(guessArray[i] == answerArray[j] && colorState[i] != 1) {
              //color is going to be yellow
              colorState[i] = 2;
              //once again hide this letter so we don't compare to it again which would lead to problems
              answerArray[j] = '!';
            }  
          } 
        }
        //loop that goes through each letters color state and then outputs it
        for(int i = 0; i < guessArray.length; i++) {
          if(colorState[i] == 1) {
            //take the character from the guess and convert it to a string
            String guessResult = Character.toString(guessArray[i]);
            //use control variable i as it indicates which row we are on
            row1[i].setFont(myFont);
            //output it to the correct textfield and change background to corresponding color
            row1[i].setText("  " + guessResult);
            row1[i].setBackground(correct);

            //do the same thing but with the alphabet at the bottom
            //need to look through the entire array and determine which letter it is equal to
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerCorrect);
              }
            }
          }
          else if(colorState[i] == 2) {
            String guessResult = Character.toString(guessArray[i]);
            row1[i].setFont(myFont);
            row1[i].setText("  " + guessResult);
            row1[i].setBackground(almost);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerAlmost);
              }
            }
          }
          else {
            String guessResult = Character.toString(guessArray[i]);
            row1[i].setFont(myFont);
            row1[i].setText("  " + guessResult);
            row1[i].setBackground(wrong);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(wrong);
              }
            }
          }
        }   
        //clear the guess textfield
        guessText.setText("");
      }

      //now do the same thing for every row
      if(rowNumCount == 2 && validGuess) {
        for(int i = 0; i < answerArray.length; i++) {
          if(guessArray[i] == answerArray[i]) {
            colorState[i] = 1;
            answerArray[i] = '!';
          }
          else {
            win = false;
          }
        }
        if(win) {
          gameFinished = true;
        }
        for(int i = 0; i < guessArray.length; i++) {
          for(int j = 0; j < answerArray.length; j++) {
            if(guessArray[i] == answerArray[j] && colorState[i] != 1) {
              colorState[i] = 2;
              answerArray[j] = '!';
            }  
          } 
        }
        for(int i = 0; i < guessArray.length; i++) {
          if(colorState[i] == 1) {
            String guessResult = Character.toString(guessArray[i]);
            row2[i].setFont(myFont);
            row2[i].setText("  " + guessResult);
            row2[i].setBackground(correct);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerCorrect);
              }
            }
          }
          else if(colorState[i] == 2) {
            String guessResult = Character.toString(guessArray[i]);
            row2[i].setFont(myFont);
            row2[i].setText("  " + guessResult);
            row2[i].setBackground(almost);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerAlmost);
              }
            }
          }
          else {
            String guessResult = Character.toString(guessArray[i]);
            row2[i].setFont(myFont);
            row2[i].setText("  " + guessResult);
            row2[i].setBackground(wrong);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(wrong);
              }
            }
          }
        }    
        guessText.setText("");
      }
      if(rowNumCount == 3 && validGuess) {
        for(int i = 0; i < answerArray.length; i++) {
          if(guessArray[i] == answerArray[i]) {
            colorState[i] = 1;
            answerArray[i] = '!';
          }
          else {
            win = false;
          }
        }
        if(win) {
          gameFinished = true;
        }
        for(int i = 0; i < guessArray.length; i++) {
          for(int j = 0; j < answerArray.length; j++) {
            if(guessArray[i] == answerArray[j] && colorState[i] != 1) {
              colorState[i] = 2;
              answerArray[j] = '!';
            }  
          } 
        }
        for(int i = 0; i < guessArray.length; i++) {
          if(colorState[i] == 1) {
            String guessResult = Character.toString(guessArray[i]);
            row3[i].setFont(myFont);
            row3[i].setText("  " + guessResult);
            row3[i].setBackground(correct);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerCorrect);
              }
            }
          }
          else if(colorState[i] == 2) {
            String guessResult = Character.toString(guessArray[i]);
            row3[i].setFont(myFont);
            row3[i].setText("  " + guessResult);
            row3[i].setBackground(almost);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerAlmost);
              }
            }
          }
          else {
            String guessResult = Character.toString(guessArray[i]);
            row3[i].setFont(myFont);
            row3[i].setText("  " + guessResult);
            row3[i].setBackground(wrong);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(wrong);
              }
            }
          }
        }    
        guessText.setText("");
      }
      if(rowNumCount == 4 && validGuess) {
        for(int i = 0; i < answerArray.length; i++) {
          if(guessArray[i] == answerArray[i]) {
            colorState[i] = 1;
            answerArray[i] = '!';
          }
          else {
            win = false;
          }
        }
        if(win) {
          gameFinished = true;
        }
        for(int i = 0; i < guessArray.length; i++) {
          for(int j = 0; j < answerArray.length; j++) {
            if(guessArray[i] == answerArray[j] && colorState[i] != 1) {
              colorState[i] = 2;
              answerArray[j] = '!';
            }  
          } 
        }
        for(int i = 0; i < guessArray.length; i++) {
          if(colorState[i] == 1) {
            String guessResult = Character.toString(guessArray[i]);
            row4[i].setFont(myFont);
            row4[i].setText("  " + guessResult);
            row4[i].setBackground(correct);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerCorrect);
              }
            }
          }
          else if(colorState[i] == 2) {
            String guessResult = Character.toString(guessArray[i]);
            row4[i].setFont(myFont);
            row4[i].setText("  " + guessResult);
            row4[i].setBackground(almost);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerAlmost);
              }
            }
          }
          else {
            String guessResult = Character.toString(guessArray[i]);
            row4[i].setFont(myFont);
            row4[i].setText("  " + guessResult);
            row4[i].setBackground(wrong);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(wrong);
              }
            }
          }
        }    
        guessText.setText("");
      }
      if(rowNumCount == 5 && validGuess) {
        for(int i = 0; i < answerArray.length; i++) {
          if(guessArray[i] == answerArray[i]) {
            colorState[i] = 1;
            answerArray[i] = '!';
          }
          else {
            win = false;
          }
        }
        if(win) {
          gameFinished = true;
        }
        for(int i = 0; i < guessArray.length; i++) {
          for(int j = 0; j < answerArray.length; j++) {
            if(guessArray[i] == answerArray[j] && colorState[i] != 1) {
              colorState[i] = 2;
              answerArray[j] = '!';
            }  
          } 
        }
        for(int i = 0; i < guessArray.length; i++) {
          if(colorState[i] == 1) {
            String guessResult = Character.toString(guessArray[i]);
            row5[i].setFont(myFont);
            row5[i].setText("  " + guessResult);
            row5[i].setBackground(correct);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerCorrect);
              }
            }
          }
          else if(colorState[i] == 2) {
            String guessResult = Character.toString(guessArray[i]);
            row5[i].setFont(myFont);
            row5[i].setText("  " + guessResult);
            row5[i].setBackground(almost);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerAlmost);
              }
            }
          }
          else {
            String guessResult = Character.toString(guessArray[i]);
            row5[i].setFont(myFont);
            row5[i].setText("  " + guessResult);
            row5[i].setBackground(wrong);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(wrong);
              }
            }
          }
        }    
        guessText.setText("");
      }
      //final row!
      if(rowNumCount == 6 && validGuess) {
        //this loop determines corectness
        for(int i = 0; i < answerArray.length; i++) {
          if(guessArray[i] == answerArray[i]) {
            colorState[i] = 1;
            answerArray[i] = '!';
          }
          else {
            win = false;
            //they never got right answer, game done
            gameFinished = true;
          }
        }
        if(win) {
          gameFinished = true;
        }
        for(int i = 0; i < guessArray.length; i++) {
          for(int j = 0; j < answerArray.length; j++) {
            if(guessArray[i] == answerArray[j] && colorState[i] != 1) {
              colorState[i] = 2;
              answerArray[j] = '!';
            }  
          } 
        }
        for(int i = 0; i < guessArray.length; i++) {
          if(colorState[i] == 1) {
            String guessResult = Character.toString(guessArray[i]);
            row6[i].setFont(myFont);
            row6[i].setText("  " + guessResult);
            row6[i].setBackground(correct);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerCorrect);
              }
            }
          }
          else if(colorState[i] == 2) {
            String guessResult = Character.toString(guessArray[i]);
            row6[i].setFont(myFont);
            row6[i].setText("  " + guessResult);
            row6[i].setBackground(almost);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(darkerAlmost);
              }
            }
          }
          else {
            String guessResult = Character.toString(guessArray[i]);
            row6[i].setFont(myFont);
            row6[i].setText("  " + guessResult);
            row6[i].setBackground(wrong);
            for(int j = 0; j < alphabetChar.length; j++) {
              if(guessArray[i] == alphabetChar[j]) {
                alphabet[j].setForeground(wrong);
              }
            }
          }
        }  
        guessText.setText("");  
      }

      

      //game is done, display the statistics gui
      if(gameFinished) {
        //shows the stats window
        statFrame.setVisible(true);

        //go through all the stats

        //they just finished a game
        gamesPlayed++;
        String gamesPlayedString = String.valueOf(gamesPlayed);
        gamesPlayedStat.setText(gamesPlayedString);

        //reveal the word
        wordWas.setText(answer);
        
        //did they win?
        if(win) {
          //tell them they won
          winOrLose.setText("You Won!");
          gamesWon++;
          currentStreak++;

          //guess distribution, determine how many attempts they took
          for(int i = 0, j = 1; i < timesGuessed.length; i++, j++) {
            //go through attempt possiblities 1-6
            if(attempts == j) {
              //add one to counter that records the amount of times you've guessed in a certain number of attempets
              timesGuessed[i]++;
              //add it to gui
              guessDistributionAnswer[i].setText("" + timesGuessed[i]);
            }
          }
        }
        else {
          //they lost
          winOrLose.setText("You Lost!");
          //reset streak
          currentStreak = 0;
        }

        //set the current streak
        String currentStreakString = String.valueOf(currentStreak);
        streakStat.setText(currentStreakString);

        //best streak
        if(currentStreak > bestStreak) {
          //set the new record streak
          bestStreak = currentStreak;
        }
        //set the max streak
        String bestStreakString = String.valueOf(bestStreak);
        bestStreakStat.setText(bestStreakString);

        //win percentage

        //convert the values to doubles so we can get ratios
        double gamesPlayedDouble = gamesPlayed;
        double gamesWonDouble = gamesWon;
        //get the percentage, round it, and then cast to int
        int winPercent = (int) Math.round((gamesWonDouble / gamesPlayedDouble) * 100);
        String winPercentString = String.valueOf(winPercent);
        winPercentageStat.setText(winPercentString + "%");
      }
    }

    //reset command
    if(command.equals("restart")) {
      //clear the board

      //for loop that goes through rows
      for(int row = 1; row <= 6; row++) {
        //for loop for indexes
        for(int i = 0; i < 5; i++) {
          //switch statement for efficiency that lets us only use this for loop
          switch (row) {
            case 1:
              //return everything to its regular state
              row1[i].setText("");
              row1[i].setBackground(Color.WHITE);
              break;
            case 2:
              row2[i].setText("");
              row2[i].setBackground(Color.WHITE);
              break;
            case 3:
              row3[i].setText("");
              row3[i].setBackground(Color.WHITE);
              break;
            case 4:
              row4[i].setText("");
              row4[i].setBackground(Color.WHITE);
              break;
            case 5:
              row5[i].setText("");
              row5[i].setBackground(Color.WHITE);
              break;
            case 6:
              row6[i].setText("");
              row6[i].setBackground(Color.WHITE);
              break;
            default:
              System.out.println("this is the default case");
          }
        }
      }

      //old code

      /* 
      for(int i = 0; i < 5; i++) {
        //return everything to its regular state
        row1[i].setText("");
        row1[i].setBackground(Color.WHITE);
      }
      for(int i = 0; i < 5; i++) {
        row2[i].setText("");
        row2[i].setBackground(Color.WHITE);
      }
      for(int i = 0; i < 5; i++) {
        row3[i].setText("");
        row3[i].setBackground(Color.WHITE);
      }
      for(int i = 0; i < 5; i++) {
        row4[i].setText("");
        row4[i].setBackground(Color.WHITE);
      }
      for(int i = 0; i < 5; i++) {
        row5[i].setText("");
        row5[i].setBackground(Color.WHITE);
      }
      for(int i = 0; i < 5; i++) {
        row6[i].setText("");
        row6[i].setBackground(Color.WHITE);
      }
      */

      //return the alphabet to its regular state
      for(int i = 0; i < alphabet.length; i++) {
        alphabet[i].setForeground(Color.BLACK);
      }
      //choose a new word
      String newAnswer = chooseWord();
      answer = newAnswer;

      //reset the row number and attempts
      rowNumCount = 0;
      attempts = 0;

      //hide stats
      statFrame.setVisible(false);
    }
  }

  // Main method to start our program
  public static void main(String[] args){
    // Creates an instance of our program
    Wordle gui = new Wordle ();
    // Lets the computer know to start it in the event thread
    SwingUtilities.invokeLater(gui);
  }
}
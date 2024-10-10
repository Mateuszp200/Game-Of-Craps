import java.util.Scanner;
import java.util.Random;
/**
 * Pseudocode
 * public static void main(String[] args){
 *     Scan in the keyboard
 *     ''My game of craps, author: Mateusz Pluta"
 *     ''how many games ..."
 *     create the variables for games won and lost
 *     
 *     create a loop using for/if/do/while
 *     use the boolean type winning, if won add+1 to games won if lost add+1 to games lost.
 *     print out games won, games lost, total and probability
 *     
 *     add possibility to play another round
 *     Display "do you want to play another round Yes or No"
 *     end the main code
 *     }
 *     
 *     enum Rollnames(ZERO_NA, ONE_NA, SNAKE_EYES, TREY and etc)
 *     
 *     public static boolean playGame(){
 *     switch code to switch between the variable names
 *     }
 *     
 *     private static int[] rollDice(int numberDice) {
 *         roll the dice method
 *         }
 *         
 *     private static void displayDice(int[] diceArray) {
 *         display the dice method
 *         }
 * end the code
 * 
 * 
 * Comment: I was struggling with the counter as i dident know where to start with the counter 
 * however i have gotten a kickstart from looking at Kanvar Murray's code 
 * and after that i was able to do most of the counter myself.
 * I have also added the probability of winning a game for the 
 * counter however i wasnt sure how to change the decimal into percent so i left
 * it as the decimal.
 * 
 * @author Mateusz Pluta 22354107 and Aleksander Siennicki 22363475
 * Group 46
 * @version 3pm, 02 Feb 2023
 */
public class MyGameOfCraps
{
    public static void main( String[] args ) {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("My Game of Craps, Author: Mateusz Pluta 22354107");
    System.out.print("How many games would you like to play? ");
    int numberOfGames = keyboard.nextInt();
    int numberOfGamesWon = 0;
    int numberOfGamesLost = 0;
    
    
    for (int game=1; game<=numberOfGames; ++game ) {           //When you type in the number of games, the game is played that many times
       System.out.printf("\n### Game %d ###\n", game);
       boolean won = playGame();//The game counter begins here also i have used small parts of Kanvar Murray code to complete the counter
       if(won) numberOfGamesWon++;//If won, it will add +1 to games won and if lost it will add +1 to games lost
       else numberOfGamesLost++;
       System.out.println("Games won is " + numberOfGamesWon);//these will display the games won, games lost, total games played and the probability of winning a game.
       System.out.println("Games lost is " + numberOfGamesLost);
       System.out.println("Total games played is " + (numberOfGamesWon+numberOfGamesLost));
       System.out.println("Probability for winning = " + (double) numberOfGamesWon / (numberOfGamesWon + numberOfGamesLost));
    }
 
    // Sentinel controlled iteration, play while user inputs "Y"
    System.out.println("\nNow lets play again while you enter Y or y");
    boolean isPlayAgain;
 
    do {
    System.out.print("\nDo you wish to play another game [y/N]: ");
    isPlayAgain = keyboard.next().toUpperCase().equals("Y");
    if ( isPlayAgain) {
    System.out.printf("\n###Game %d ###\n", ++numberOfGames);
    boolean won = playGame();
     if(won) numberOfGamesWon++; //same thing is done here as done in the code above
       else numberOfGamesLost++;
       System.out.println("Games won is " + numberOfGamesWon);
       System.out.println("Games lost is " + numberOfGamesLost);
       System.out.println("Total games played is " + (numberOfGamesWon+numberOfGamesLost));
       System.out.println("Probability for winning a game = " + (double) numberOfGamesWon / (numberOfGamesWon + numberOfGamesLost));
    }
    } while ( isPlayAgain );

    System.out.printf("You played %d games. Bye!\n", numberOfGames ); 
    } // end of method main
    
    // create random number generator (once only) for use in method rollDice
    private static final Random rng = new Random(); //this will generate random numbers (rng = range)

    /*
     * constants for the strings for won/lost  
     * (include unicode characters for happy/sad faces see: https://unicode-table.com/en/)
     */ 
    private static final String WON_STR = "Congratulations , you won this game";
    private static final String LOST_STR = "Hard luck , you lost this game";

    // enumerated type with enumerators that represent the game status
    private enum Status { WON, LOST, CONTINUE };

    /* 
     * enumerated type for names of rolls with special names replacing basic values
     * 2 is SNAKE EYES, 3 is TREY, 11 is YO_LEVEN, 12 is BOX_CARS
     */ 
    private enum RollName { ZERO_NA,  
        ONE_NA, SNAKE_EYES, TREY, FOUR, FIVE,     SIX,
        SEVEN,  EIGHT,      NINE, TEN,  YO_LEVEN, BOX_CARS };
        
        
        
    /**
     * public static boolean playGame(){
     *     Creat variable called points, starts with 0 points.
     *     Create the roll and display dice method
     *     Input the variables which the dice will have
     *     Create a while/do loop for the dice which will display random numbers on the dice
     *     Create a gamestatus which will tell if you won or lost the game
     */
    public static boolean playGame(){
        Status gameStatus = Status.CONTINUE;   // game will CONTINUE until WON or LOST
        int point = 0;                         // point if not won or lost on first roll  
 
        int rollDice = rollAndDisplayDice(2);  // roll two dice and sum the faces    
        
        RollName rollName = RollName.values()[rollDice]; // Map dice sum to RollName enumerator
        // RollName.values() returns an array of the enumerators of RollName.
        // RollName.values()[roll] maps roll to the corresponding RollName enumerator.
        
        // Based on the rollName enumerator value, update the game status if a won or lost value.
        // If neither then the gameStatus remains Status.CONTINUE and the point is stored
        switch ( rollName ) {        
            case SEVEN: 
            case YO_LEVEN:          
                gameStatus = Status.WON;    // win if 7 or 11 on first roll
                break;

            case SNAKE_EYES: 
            case TREY: 
            case BOX_CARS: 
                gameStatus = Status.LOST;   // lose with 2, 3 or 12 on first roll
                break;

            default:         
                point = rollDice;           // neither won or lost, so store the point
                System.out.printf( "The Point is %s (%d). Roll %d before 7 to win\n", 
                    rollName.name(), point, point );
            break;                      // this break is optional
        } 

        /*
         * Continue playing until player either throws point (won) or seven (lost)
         */
        while ( gameStatus == Status.CONTINUE ) { 
            rollDice = rollAndDisplayDice(2); 

            if ( rollDice == point )             // status becomes win by making point
                gameStatus = Status.WON;
            else if ( rollDice == 7 )            // status lose by rolling 7 before point
                gameStatus = Status.LOST;
        } 

        /*
         * Now if here, the player must have lost or won, 
         *   hence display won or lost details using the WON_STR or LOST_STR
         */
        System.out.println(gameStatus == Status.WON ? WON_STR : LOST_STR);
        return(gameStatus==Status.WON);
    }
    
    /**
     * Roll requested number of dice and store in an array with sum
     * @param numberDice    the number of dice to role
     * @return an array where [0] element is the sum followed by individual rolls
     */
    private static int[] rollDice(int numberDice) {
        int[] diceRoll = new int[numberDice+1];

        for (int i=1; i < diceRoll.length; i++) {
            diceRoll[i] = rng.nextInt( 6 ) + 1; 
            diceRoll[0] += diceRoll[i]; 
        }

        return diceRoll; // return sum of dice
    } // end method rollDice

    /**
     * Display sum of dice rolls and the dice values
     * @param diceArray  array with total in first element and then individual dice values
     */
    private static void displayDice(int[] diceArray) {
        System.out.printf("Rolled %2d  Dice (", diceArray[0]);
        for (int i=1; i < diceArray.length; i++) {
            System.out.printf("%s %d", (i>1) ? "," : "", diceArray[i]);
        }
        System.out.println( " )" );
    } // end method displayDice

    /**
     * Roll requested number of dice and display rolls
     * @param numberDice    the number of dice to role
     * @return sum of dice rolled
     */
    private static int rollAndDisplayDice(int numberDice) {
        int[] rolledDice = rollDice(numberDice);
        displayDice(rolledDice);
        return rolledDice[0];    
    } // end method rollAndDisplayDice
}

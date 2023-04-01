import java.util.ArrayList;

public class DotComGame {
    public static void main(String[] args) {


        // game set up:
        // declare variable to hold num of user guesses
        int numOfGuesses = 0;
        // gameHelper class ref
        GameHelper helper = new GameHelper();
        // make new SimpleDotCom instance
        SimpleDotCom simpleDotCom = new SimpleDotCom();
        // random number between 0 and 5 which will be the new starting cell location
        int startingCell = (int) (Math.random() * 5);
        // declare array of ints to hold the position of the dotcom by incrementing the random number twice
        // and storing each.
        ArrayList <Integer> locations = new ArrayList<>();
        locations.add(startingCell);
        locations.add(startingCell+1);
        locations.add(startingCell+2);
        // invoke setLocationCells() method on SimpleDotCom instance
        simpleDotCom.setLocationCells(locations);
        // declare boolean value which describes the state of the game 'isAlive' and initalise and true.
        boolean isAlive = true;
        // while the dotcom is alive (isAlive == true)
        while (isAlive){
            // get user input from commandline
            // check user guess
            // invoke checkYourself method on simpleDotCom instance
            // increment numOfGuesses variable.
            // check for dotCom death
            // if result is "kill" set isAlive to false and print number of guesses as user score.
            String guess = helper.getUserInput("Enter a number between 0 and 6: ");
            String result = simpleDotCom.checkYourself(guess);
            System.out.println("User input: " + result);
            numOfGuesses++;
            if(result.equals("kill")){
                isAlive = false;
                System.out.println(numOfGuesses + " guesses.");
            }
        }
    }
}

import java.util.ArrayList;

public class SimpleDotCom {
    // declare arraylist to hold location cells.
    // declare checkYourself method which takes string for user's guess.
    // declare setLocationCells() setter method which takes arraylist of integers
    private ArrayList <Integer> locationCells = new ArrayList<>();
    int numOfHits = 0;

    public void setLocationCells(ArrayList<Integer> locs){
        locationCells = locs;
    }
    // method: String checkYourself (String userGuess)
    // get user guess as string parameter
    // convert user guess to int
    // repeat with each cell in int array
    // compare user guess to location cell
    // if user guess is location cell = increment number of hits
    // find out if last location cell by if number of hits is 3
    // = return 'kill' as result end program
    // else return 'hit'
    // else if did not match, return miss
    public String checkYourself(String userInput){
        int index = locationCells.indexOf(Integer.parseInt(userInput));
        String result = "miss";

            if (index >= 0){
                locationCells.remove(index);

                if (locationCells.isEmpty()) {
                    result = "kill";
                    System.out.println(result);
                }
                else{
                    result = "hit";
                    }
            }
        return result;
    }
}

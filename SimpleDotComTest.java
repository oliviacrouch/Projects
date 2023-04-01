import java.util.ArrayList;

public class SimpleDotComTest {
    public static void main(String[] args) {
        SimpleDotCom dot = new SimpleDotCom();
        ArrayList<Integer> locations = new ArrayList<>();
        dot.setLocationCells(locations);
        String userGuess = "2";
        String result = dot.checkYourself(userGuess);
        String testResult = "Failed";
        if (result.equals("hit")){
            testResult = "Passed";
        }
        System.out.println(testResult);
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameHelper {
    public String getUserInput (String prompt){
        String inputLine = "null";
        System.out.println(prompt + " ");
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            inputLine = bufferedReader.readLine();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
        return inputLine;
    }
}

public class StringCases {

    public static String camelCase(String inputString) {
        for (int i = 0; i < inputString.length(); i++){
            char c = inputString.charAt(i);
            if (!Character.isWhitespace(c) && !Character.isAlphabetic(c))
            {
                inputString = inputString.substring(0, i) +
                        inputString.substring(i + 1);
                i--;
            }
        }
        String [] outputString = inputString.split(" ");
        for (int i = 0; i < outputString.length; i++){
            if (outputString[i].isBlank()){
                continue;
            }
            if (i == 0){
                outputString[0] = outputString[0].toLowerCase();
            }
            else {
                outputString[i] = Character.toUpperCase(outputString[i].charAt(0)) +
                        outputString[i].substring(1).toLowerCase();
            }
        }
        return String.join( "", outputString);
    }

    public static String pascalCase(String inputString) {
        //complete this method
        for (int i = 0; i < inputString.length(); i++){
            char c = inputString.charAt(i);
            if (!Character.isWhitespace(c) && !Character.isAlphabetic(c))
            {
                inputString = inputString.substring(0, i) +
                        inputString.substring(i + 1);
                i--;
            }
        }
        String [] outputString = inputString.split(" ");
        for (int i = 0; i < outputString.length; i++)
        {
            if (outputString[i].isBlank())
                continue;
            outputString[i] = Character.toUpperCase(outputString[i].charAt(0))
                    + outputString[i].substring(1).toLowerCase();
        }
        return String.join("", outputString);
    }

    public static String snakeCase(String inputString) {
        //complete this method
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (!Character.isWhitespace(c) && !Character.isAlphabetic(c)) {
                inputString = inputString.substring(0, i) +
                        inputString.substring(i + 1);
                i--;
            }
        }
        inputString = inputString.replaceAll("  ", " ");
        String [] outputString = inputString.split(" ");
        for (int i = 0; i < outputString.length; i++)
        {
            if (outputString[i].isBlank())
                continue;
            outputString[i] = Character.toLowerCase(outputString[i].charAt(0))
                    + outputString[i].substring(1).toLowerCase();
        }
        return String.join("_", outputString);
    }

    public static String screamingSnakeCase(String inputString) {
        //complete this method
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (!Character.isWhitespace(c) && !Character.isAlphabetic(c)) {
                inputString = inputString.substring(0, i) +
                        inputString.substring(i + 1);
                i--;
            }
        }
        inputString = inputString.replaceAll("  ", " ");
        String [] outputString = inputString.split(" ");
        for (int i = 0; i < outputString.length; i++) {
            if (outputString[i].isBlank())
               continue;
        outputString[i] = Character.toUpperCase(outputString[i].charAt(0))
                + outputString[i].substring(1).toUpperCase();
        }
        return String.join("_", outputString);
    }

    public static String alternateCase(String inputString) {
        //complete this method
        for (int i = 0; i < inputString.length(); i++){
            char c = inputString.charAt(i);
            if (!Character.isWhitespace(c) && !Character.isAlphabetic(c))
            {
                inputString = inputString.substring(0, i) +
                        inputString.substring(i + 1);
                i--;
            }
        }
        inputString = inputString.replaceAll(" ", "");
        inputString = inputString.toLowerCase();
        char [] charString = inputString.toCharArray();
        for (int i = 0; i < charString.length; i++)
        {
            for (i = 0; i < charString.length; i = i + 2)
                charString[i] = Character.toUpperCase(charString[i]);
        }
        String outputString = new String(charString);
        return outputString;
    }
}

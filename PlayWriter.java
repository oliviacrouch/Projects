import javafx.util.Pair;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
public class PlayWriter {
    private Romeo  myRomeo  = null;
    private InetAddress RomeoAddress = null;
    private int RomeoPort = 0;
    private Socket RomeoMailbox = null;
    private Juliet myJuliet = null;
    private InetAddress JulietAddress = null;
    private int JulietPort = 0;
    private Socket JulietMailbox = null;
    double[][] theNovel = null;
    int novelLength = 0;
    public PlayWriter()
    {
        novelLength = 500; //Number of verses
        theNovel = new double[novelLength][2];
        theNovel[0][0] = 0;
        theNovel[0][1] = 1;
    }

    //Create the lovers
    public void createCharacters() {
        //Create the lovers
        System.out.println("PlayWriter: Romeo enters the stage.");
        myRomeo = new Romeo(theNovel[0][0]);
        myRomeo.start();
        System.out.println("PlayWriter: Juliet enters the stage.");
        myJuliet = new Juliet(theNovel[0][1]);
        myJuliet.start();
    }
    //Meet the lovers and start letter communication
    public void charactersMakeAcquaintances() {
            // romeo op and port
            Pair<InetAddress, Integer> romeoInfo = myRomeo.getAcquaintance();
            RomeoAddress = romeoInfo.getKey();
            RomeoPort = romeoInfo.getValue();
            System.out.println("PlayWriter: I've made acquaintance with Romeo");
            // juliet ip and port
            Pair<InetAddress, Integer> julietInfo = myJuliet.getAcquaintance();
            JulietAddress = julietInfo.getKey();
            JulietPort = julietInfo.getValue();
            System.out.println("PlayWriter: I've made acquaintance with Juliet");
        }

    // Request next verse: Send letters to lovers communicating the partner's love in previous verse
    public void requestVerseFromRomeo(int verse) {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Romeo.-> ("
                + theNovel[verse-1][1] + ")");
        try {
            RomeoMailbox = new Socket(RomeoAddress, RomeoPort);
            DataOutputStream os = new DataOutputStream(RomeoMailbox.getOutputStream());
            // create writer to write data to the output stream
            OutputStreamWriter osw = new OutputStreamWriter(os);
            // create buffered writer
            BufferedWriter bw = new BufferedWriter(osw);
            // create service request message
            String message = (theNovel[verse-1][1]) + "J\n";
            // write the message to the buffered writer
            bw.write(message);
            // flush the buffered writer to ensure the message is sent
            bw.flush();
        } catch (Exception e) {
            System.out.println("PlayWriter: Failed to request verse from Romeo " + e);
        }

}
    public void requestVerseFromJuliet(int verse)
    {
        System.out.println("PlayWriter: Requesting verse " + verse + " from Juliet. -> ("
                + theNovel[verse-1][0] + ")");
        try {
            JulietMailbox = new Socket(JulietAddress, JulietPort);
            DataOutputStream outputStream = new DataOutputStream(JulietMailbox.getOutputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String message = (theNovel[verse - 1][0]) + "R\n";
            bufferedWriter.write(message);
            bufferedWriter.flush();
        }
        catch (Exception exception)
        {
            System.out.println("PlayWriter: Failed to request verse from Juliet " + exception);
        }

    }
    //Receive letter from Romeo with renovated love for current verse
    public void receiveLetterFromRomeo(int verse) {
        try {
            InputStream in = RomeoMailbox.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            char[] buffer = new char[1024];
            int charsRead = isr.read(buffer);
            String messageReceived = new String(buffer, 0, charsRead);
            String lovePart = messageReceived.substring(0, messageReceived.length() - 1);
            double newLove = Double.parseDouble(lovePart);
            theNovel[verse][0] = newLove;
            System.out.println("PlayWriter: Romeo's verse " + verse + " -> " + theNovel[verse][0]);
            RomeoMailbox.close();
        } catch (IOException e) {
            System.out.println("PlayWriter: Failed to receive letter from Romeo. " + e);
        }
    }
    public void receiveLetterFromJuliet(int verse)
    {
        try {
            InputStream in = JulietMailbox.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            char[] buffer = new char[1024];
            int charsRead = isr.read(buffer);
            String messageReceived = new String(buffer, 0, charsRead);
            String loveString = messageReceived.substring(0, messageReceived.length() - 1);
            double newLove = Double.parseDouble(loveString);
            theNovel[verse][1] = newLove;
            System.out.println("PlayWriter: Juliet's verse " + verse + " (-> " + theNovel[verse][1] + ")");
            JulietMailbox.close();
        } catch (IOException e) {
            System.out.println("PlayWriter: Failed to receive letter from Juliet. " + e);
        }
    }
    // Let the story unfold
    public void storyClimax() {
        for (int verse = 1; verse < novelLength; verse++) {
            // Write verse
            System.out.println("PlayWriter: Writing verse " + verse + ".");

            // Request current verse from Romeo and Juliet
            requestVerseFromRomeo(verse);
            receiveLetterFromRomeo(verse);
            // Receive updated love values from Romeo and Juliet
            requestVerseFromJuliet(verse);
            receiveLetterFromJuliet(verse);

            System.out.println("PlayWriter: Verse " + verse + " finished.");
        }
    }
    //Character's death
    public void charactersDeath() {
        try {
            RomeoMailbox.close();
            JulietMailbox.close();
            System.out.println("PlayWriter: Romeo and Juliet died.");
        } catch (IOException e) {
            System.out.println("PlayWriter: " + e);
        }
    }
    //A novel consists of introduction, conflict, climax and denouement
    public void writeNovel() {
        System.out.println("PlayWriter: The Most Excellent and Lamentable Tragedy " +
                "of Romeo and Juliet.");
                System.out.println("PlayWriter: A play in IV acts.");
        //Introduction,
        System.out.println("PlayWriter: Act I. Introduction.");
        this.createCharacters();
        //Conflict
        System.out.println("PlayWriter: Act II. Conflict.");
        this.charactersMakeAcquaintances();
        //Climax
        System.out.println("PlayWriter: Act III. Climax.");
        this.storyClimax();
        //Denouement
        System.out.println("PlayWriter: Act IV. Denouement.");
        this.charactersDeath();
    }
    //Dump novel to file
    public void dumpNovel() {
        FileWriter Fw = null;
        try {
            Fw = new FileWriter("RomeoAndJuliet.csv");
        } catch (IOException e) {
            System.out.println("PlayWriter: Unable to open novel file. " + e);
        }
        System.out.println("PlayWriter: Dumping novel. ");
        StringBuilder sb = new StringBuilder();
        for (int act = 0; act < novelLength; act++) {
            String tmp = theNovel[act][0] + ", " + theNovel[act][1] + "\n";
            sb.append(tmp);
            System.out.print("PlayWriter [" + act + "]: " + tmp);
        }
        try {
            BufferedWriter br = new BufferedWriter(Fw);
            br.write(sb.toString());
            br.close();
        } catch (Exception e) {
            System.out.println("PlayWriter: Unable to dump novel. " + e);
        }
    }
    public static void main (String[] args) {
        PlayWriter Shakespeare = new PlayWriter();
        Shakespeare.writeNovel();
        Shakespeare.dumpNovel();
        System.exit(0);
    }
}

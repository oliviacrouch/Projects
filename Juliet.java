import javafx.util.Pair;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Juliet extends Thread {
    private ServerSocket ownServerSocket = null; //Juliet's (server) socket
    private Socket serviceMailbox = null; //Juliet's (service) socket
    private double currentLove = 0;
    private double b = 0;
    //Class construtor
    public Juliet(double initialLove) {
        currentLove = initialLove;
        b = 0.01;
        try {
            //TO BE COMPLETED
            ownServerSocket = new ServerSocket(7778, 0,
                    InetAddress.getByName("127.0.0.1"));
            System.out.println("Juliet: Good pilgrim, you do wrong your hand too much, ...");
        } catch(Exception e) {
            System.out.println("Juliet: Failed to create own socket " + e);
        }
    }
    //Get acquaintance with lover;
    // Receives lover's socket information and share's own socket
    public Pair<InetAddress,Integer> getAcquaintance() {
        //TO BE COMPLETED
            System.out.println("Juliet: My bounty is as boundless as the sea,\n" +
                    "       My love as deep; the more I give to thee,\n" +
                    "       The more I have, for both are infinite.");
        InetAddress julietAddress = null;
        try {
            julietAddress = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int julietPort = 7778;
        return new Pair<>(julietAddress, julietPort);
        }
    //Retrieves the lover's love
        public double receiveLoveLetter() {
            try {
                serviceMailbox = this.ownServerSocket.accept();
                InputStream in = serviceMailbox.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(isr);

                String msg = bufferedReader.readLine();

                String love = msg.substring(0, msg.length() - 1);
                double tmp = Double.parseDouble(love);
                System.out.println("Juliet: Romeo, Romeo! Wherefore art thou Romeo? (<-" +
                        tmp + ")");
                return tmp;
            } catch (IOException e) {
                System.out.println("Juliet: Failed to receive love letter. " + e);
                return 0;
            }
    }
    //Love (The ODE system)
    //Given the lover's love at time t, estimate the next love value for Romeo
    public double renovateLove(double partnerLove){
        System.out.println("Juliet: Come, gentle night, come, loving black-browed night,\n" +
                "       Give me my Romeo, and when I shall die,\n" +
                        "       Take him and cut him out in little stars.");
        currentLove = currentLove+(-b*partnerLove);
        return currentLove;
    }
    //Communicate love back to playwriter
    public void declareLove(){
        //TO BE COMPLETED
        System.out.println("Juliet: Good night, good night! Parting is such sweet sorrow," +
                "\nThat I shall say good night till it be morrow." + currentLove + "J");
        try {
            OutputStreamWriter osw = new OutputStreamWriter(serviceMailbox.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(currentLove + "R");
            bw.flush();
        } catch(Exception e) {
            System.out.println("Juliet: Error writing response to PlayWriter. " + e);
        }
    }
    //Execution
    public void run () {
        try {
            while (!this.isInterrupted()) {
                //Retrieve lover's current love
                double RomeoLove = this.receiveLoveLetter();
                //Estimate new love value
                this.renovateLove(RomeoLove);
                //Communicate back to lover, Romeo's love
                this.declareLove();
            }
        }catch (Exception e){
            System.out.println("Juliet: " + e);
        }
        if (this.isInterrupted()) {
            System.out.println("Juliet: I will kiss thy lips.\n" +
                    "Haply some poison yet doth hang on them\n" +
                    "To make me die with a restorative.");
        }
    }
}

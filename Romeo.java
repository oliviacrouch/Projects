import javafx.util.Pair;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Romeo extends Thread {
    private ServerSocket ownServerSocket = null; //Romeo's (server) socket
    private Socket serviceMailbox = null; //Romeo's (service) socket
    private double currentLove = 0;
    private double a = 0; //The ODE constant

    //Class construtor
    public Romeo(double initialLove) {
        currentLove = initialLove;
        a = 0.02;
        try {
            //TO BE COMPLETED
            ownServerSocket = new ServerSocket(7779, 0, InetAddress.getByName("127.0.0.1"));
            System.out.println("Romeo: What lady is that, which doth enrich the hand \n" +
                    "       Of yonder knight?");
        } catch (Exception e) {
            System.out.println("Romeo: Failed to create own socket " + e);
            //System.exit(1);
        }
    }

    // Get acquaintance with lover
    // go over these methods (Romeo and Juliet, the servers should
    // not be communicating with each other, but with the client.
    public Pair<InetAddress,Integer> getAcquaintance() {
        System.out.println("Romeo: Did my heart love till now? forswear it, sight! For I ne'er saw true beauty till this night.");

        InetAddress romeoAddress = null;
        try {
            romeoAddress = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int romeoPort = 7779;

        return new Pair<>(romeoAddress, romeoPort);
    }

    // Retrieves the lover's love
    public double receiveLoveLetter() {
        try {
            serviceMailbox = this.ownServerSocket.accept();
            InputStream in = serviceMailbox.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String msg = bufferedReader.readLine();
            String love = msg.substring(0, msg.length() - 1);
            double tmp = Double.parseDouble(love);
            System.out.println("Romeo: O sweet Juliet... (<-" + tmp + ")");
            return tmp;
        } catch (IOException e) {
            System.out.println("Romeo: Failed to receive love letter. " + e);
            return 0;
        }
    }



    //Love (The ODE system)
    //Given the lover's love at time t, estimate the next love value for Romeo
    public double renovateLove(double partnerLove){
        System.out.println("Romeo: But soft, what light through yonder window breaks?\n" +
                "       It is the east, and Juliet is the sun.");
        currentLove = currentLove+(a*partnerLove);
        return currentLove;
    }
    //Communicate love back to playwriter
    public void declareLove(){
        //TO BE COMPLETED
        System.out.println("Romeo: I would I were thy bird. (->" + currentLove +"R)");
        try {
            OutputStreamWriter osw = new OutputStreamWriter(serviceMailbox.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(currentLove + "J");
            bw.flush();
        } catch(Exception e) {
            System.out.println("Romeo: " + e);
        }
    }
    //Execution
    public void run () {
        try {
            while (!this.isInterrupted()) {
                //Retrieve lover's current love
                double JulietLove = this.receiveLoveLetter();
                //Estimate new love value
                this.renovateLove(JulietLove);
                //Communicate love back to playwriter
                this.declareLove();
            }
        }catch (Exception e){
            System.out.println("Romeo: " + e);
        }
        if (this.isInterrupted()) {
            System.out.println("Romeo: Here's to my love. O true apothecary,\n" +
                    "Thy drugs are quick. Thus with a kiss I die." );
        }
    }
}


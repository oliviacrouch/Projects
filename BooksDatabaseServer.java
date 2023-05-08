package com.example.assignment4_1;/*
 * BooksDatabaseServer.java
 *
 * The server main class.
 * This server provides a service to access the Books database.
 *
 * author: <2009271>
 *
 */

import java.net.ServerSocket;
import java.net.Socket;


public class BooksDatabaseServer {

    private int thePort;
    private String theIPAddress;
    private ServerSocket serverSocket =  null;

    //Support for closing the server
    //private boolean keypressedFlag = false;


    //Class constructor
    public BooksDatabaseServer(){
        //Initialize the TCP socket
        thePort = Credentials.PORT;
        theIPAddress = Credentials.HOST;

        //Initialize the socket and runs the service loop
        System.out.println("Server: Initializing server socket at " + theIPAddress + " with listening port " + thePort);
        System.out.println("Server: Exit server application by pressing Ctrl+C (Windows or Linux) or Opt-Cmd-Shift-Esc (Mac OSX)." );
        try {
            //TO BE COMPLETED
            //Initialize the socket
            serverSocket = new ServerSocket(thePort);

            System.out.println("Server: Server at " + theIPAddress + " is listening on port : " + thePort);
        } catch (Exception e){
            //The creation of the server socket can cause several exceptions;
            //See https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
            System.out.println(e);
            System.exit(1);
        }
    }




    //Runs the service loop
    public void executeServiceLoop() {
        System.out.println("Server: Start service loop.");
        try {
            //Service loop
            while (true) {
                //System.out.println("Server: Waiting for a client connection."); // test
                Socket clientSocket = serverSocket.accept();
                //System.out.println("Server: Accepted a client connection.");
                //System.out.println("Server: Connection from " + clientSocket.getInetAddress());

                // Create a new thread to handle the client request
                Thread requestHandlerThread = new Thread(new BooksDatabaseService(clientSocket));
                //System.out.println("Starting requestHandlerThread"); // test
                requestHandlerThread.start();
            }
        } catch (Exception e) {
            //The creation of the server socket can cause several exceptions;
            //See https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
            System.out.println(e);
        }
        System.out.println("Server: Finished service loop.");
        //System.out.println("Server: Listening for incoming connections on port " + thePort);
    }

/*
	@Override
	protected void finalize() {
		//If this server has to be killed by the launcher with destroyForcibly
		//make sure we also kill the service threads.
		System.exit(0);
	}
*/

    public static void main(String[] args){

        //Run the server
        BooksDatabaseServer server=new BooksDatabaseServer(); //inc. Initializing the socket
        server.executeServiceLoop();
        System.out.println("Server: Finished.");
        System.exit(0);
    }
}
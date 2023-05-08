package com.example.assignment4_1;

/*
 * BooksDatabaseService.java
 *
 * The service threads for the books database server.
 * This class implements the database access service, i.e. opens a JDBC connection
 * to the database, makes and retrieves the query, and sends back the result.
 *
 * author: <2009271>
 *
 */

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Arrays;
//Direct import of the classes CachedRowSet and CachedRowSetImpl will fail becuase
//these clasess are not exported by the module. Instead, one needs to impor
//javax.sql.rowset.* as above.



public class BooksDatabaseService extends Thread{
    //private Socket aSocket;
    private Socket serviceSocket = null;
    private String[] requestStr  = new String[2]; //One slot for author's name and one for library's name.
    private ResultSet outcome   = null;

    //JDBC connection
    private String USERNAME = Credentials.USERNAME;
    private String PASSWORD = Credentials.PASSWORD;
    private String URL      = Credentials.URL;


    // socket represents the connection to the client
    //Class constructor
    public BooksDatabaseService(Socket aSocket){
        //TO BE COMPLETED

        this.serviceSocket = aSocket;
        try {
            System.out.println("Service thread " + this.getId() + " started for client " + serviceSocket.getRemoteSocketAddress());
        } catch (Exception e) {
            System.err.println("Failed to create thread for service: " + e.getMessage());
        }
        System.out.println("BooksDatabaseService constructor called for client: " + serviceSocket.getRemoteSocketAddress());
    }


    //Retrieve the request from the socket
    public String[] retrieveRequest() {
        this.requestStr[0] = ""; // For author
        this.requestStr[1] = ""; // For library

        String tmp = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
            tmp = bufferedReader.readLine();
            //System.out.println("Service thread " + this.getId() + ": Finished reading from BufferedReader.");

            if (tmp.endsWith("#")) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }

            String[] parts = tmp.split(";");
            if (parts.length == 2) {
                this.requestStr[0] = parts[0];
                this.requestStr[1] = parts[1];
                //System.out.println("Service thread " + this.getId() + ": Request string: " + Arrays.toString(this.requestStr));
            } else {
                System.err.println("Invalid request format: " + tmp);
            }
        } catch (IOException e) {
            System.out.println("Service thread " + this.getId() + ": " + e);
        }
        return this.requestStr;
    }



    //Parse the request command and execute the query
    public boolean attendRequest() {
        boolean flagRequestAttended = true;

        this.outcome = null;

        String sql = "SELECT title, publisher, genre, rrp, COUNT(copyid) as num_copies " +
                "FROM author " +
                "JOIN book ON author.authorid = book.authorid " +
                "JOIN bookcopy ON book.bookid = bookcopy.bookid AND bookcopy.onloan = 'f' " +
                "JOIN library ON library.libraryid = bookcopy.libraryid " +
                "WHERE author.familyname = ? AND library.city = ? " +
                "GROUP BY book.title, book.publisher, book.genre, book.rrp";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            System.out.println("Service thread " + this.getId() + ": SQL query parameters: " + requestStr[0] + ", " + requestStr[1]);
            stmt.setString(1, requestStr[0]);
            stmt.setString(2, requestStr[1]);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Service thread " + this.getId() + ": ResultSet after executeQuery(): " + rs);

            if (rs.next()) {
                //System.out.println("Client: Query returned data.");
                System.out.flush();
                RowSetFactory factory = RowSetProvider.newFactory();
                CachedRowSet crs = factory.createCachedRowSet();
                crs.populate(rs);
                this.outcome = crs;
                //System.out.println("Service thread " + this.getId() + ": Outcome after crs.populate(rs): " + this.outcome);

                // print the outcome of the SQL query to check data being sent across:
                System.out.println("Service thread " + this.getId() + " contents:");
                crs.beforeFirst(); // move cursor to position before the first row
                while (crs.next()) {
                    System.out.println("Title: " + crs.getString("title") +
                            ", Publisher: " + crs.getString("publisher") +
                            ", Genre: " + crs.getString("genre") +
                            ", RRP: " + crs.getString("rrp") +
                            ", Num.Copies: " + crs.getInt("num_copies"));
                }
                crs.beforeFirst(); // reset  cursor position to use the CachedRowSet later
            } else {
                System.out.println("Client: Query did not return data.");
            }

        } catch (Exception e) {
            System.out.println(e);
            flagRequestAttended = false;
        }

        return flagRequestAttended;
    }


    //Wrap and return service outcome
    public void returnServiceOutcome() {
        ObjectOutputStream outcomeStreamWriter = null;
        try {
            outcomeStreamWriter = new ObjectOutputStream(serviceSocket.getOutputStream());
            System.out.println("Server: Sending service outcome to the client.");
            outcomeStreamWriter.writeObject(this.outcome);
            outcomeStreamWriter.flush();
            outcomeStreamWriter.close(); // Close the ObjectOutputStream after flush().
            System.out.println("Service thread " + this.getId() + ": Service outcome returned; " + this.outcome);
        } catch (IOException e) {
            System.out.println("Service thread " + this.getId() + ": " + e);
        } finally {
            try {
                if (serviceSocket != null && !serviceSocket.isClosed()) {
                    // serviceSocket.shutdownOutput(); // remove
                    serviceSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Service thread " + this.getId() + ": " + e);
            }
        }
    }



    //The service thread run() method
    public void run()
    {
        System.out.println("Service thread " + this.getId() + ": Entering the run() method");
        try {
            System.out.println("\n============================================\n");
            //Retrieve the service request from the socket
            this.retrieveRequest();
            System.out.println("Service thread " + this.getId() + ": Request array after retrieveRequest(): " + Arrays.toString(this.requestStr)); // test
            System.out.println("Service thread " + this.getId() + ": Request retrieved: "
                    + "author->" + this.requestStr[0] + "; library->" + this.requestStr[1]);

            //Attend the request
            System.out.println("Before attendRequest() call"); // test
            boolean tmp = this.attendRequest();
            System.out.println("After attendRequest() call"); // test
            //Send back the outcome of the request
            if (!tmp)
                System.out.println("Service thread " + this.getId() + ": Unable to provide service.");
            this.returnServiceOutcome();

        }catch (Exception e){
            System.out.println("Service thread " + this.getId() + ": " + e);
        }
        //Terminate service thread (by exiting run() method)
        System.out.println("Service thread " + this.getId() + ": Finished service.");
    }

}


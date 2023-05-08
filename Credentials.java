package com.example.assignment4_1;
public class Credentials {
    //JDBC connection
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "olivia";
    public static final String URL = "jdbc:postgresql://localhost:5433/FSAD2023_Books";
    //Client-server connection
    public static final String HOST = "127.0.0.1"; //localhost
    public static final int PORT = 9995; //This is NOT the port in postgres, but the port at which the BooksDatabaseServer is listening
}

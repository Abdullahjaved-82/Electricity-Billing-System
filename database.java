
package electricity.billing.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class database {
    // Declare the connection and statement objects
    Connection connection;
    Statement statement;

    // Constructor to establish the connection to the database
    database(){
        try{
            // Connect to the MySQL database named 'bill_system' using the provided URL, username, and password
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill_system","root","12345678");

            // Create a statement object to send SQL queries to the database
            statement = connection.createStatement();
        } catch(Exception e){
            // Print any exceptions that occur during connection or statement creation
            e.printStackTrace();
        }
    }
}

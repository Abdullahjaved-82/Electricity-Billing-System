
package electricity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class bill_details extends JFrame {
    String meter;  // To hold the meter number passed during instantiation

    // Constructor to initialize the frame and display the bill details
    bill_details(String meter) {
        this.meter = meter;  // Store the meter number for later use

        // Set the size, location, and layout of the frame
        setSize(700, 600);  // Set the window size
        setLocation(320, 50);  // Set the position of the window on the screen
        setLayout(null);  // No layout manager, manually setting bounds of components
        getContentPane().setBackground(Color.WHITE);  // Set the background color of the content pane

        JTable table = new JTable();  // Create a JTable to display bill details

        try {
            // Create an instance of the 'database' class to execute the query
            database c = new database();

            // Query to fetch bill details for the given meter number
            String query_bill = "select * from bill where meter_no = '" + meter + "'";

            // Execute the query and retrieve the results
            ResultSet resultSet = c.statement.executeQuery(query_bill);

            // Set the result set into the table model using DbUtils for easy display
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();  // Print any exceptions to the console (for debugging)
        }

        // Add the JTable to a JScrollPane to enable scrolling when the table is large
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 0, 700, 650);  // Set bounds of the JScrollPane to fit the frame
        add(sp);  // Add the JScrollPane containing the table to the frame

        // Make the frame visible
        setVisible(true);
    }

    // Main method to test the class (called when the program is run directly)
    public static void main(String[] args) {
        new bill_details("");  // Create an instance of bill_details with an empty meter number
    }
}

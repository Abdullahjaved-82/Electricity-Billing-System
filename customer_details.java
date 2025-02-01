
package electricity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class customer_details extends JFrame implements ActionListener {
    // Declare components for the UI
    Choice searchmetercho, searchnamecho;
    JTable table;
    JButton search, print, close;

    customer_details() {
        // Set the title and basic properties of the frame
        super("Customer Details");
        getContentPane().setBackground(new Color(155, 170, 108));
        setSize(700, 500);
        setLocation(320, 100);
        setLayout(null);

        // Label for the meter number search
        JLabel searchmeter = new JLabel("Search by meter number");
        searchmeter.setBounds(20, 20, 150, 20);
        add(searchmeter);

        // Dropdown for selecting meter number
        searchmetercho = new Choice();
        searchmetercho.setBounds(180, 20, 150, 20);
        add(searchmetercho);

        // Populate the meter number dropdown with values from the 'new_customer' table in the database
        try {
            database c = new database();
            ResultSet resultset = c.statement.executeQuery("select * from new_customer");
            while (resultset.next()) {
                searchmetercho.add(resultset.getString("meterno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Label for the customer name search
        JLabel searchname = new JLabel("Search by name");
        searchname.setBounds(400, 20, 100, 20);
        add(searchname);

        // Dropdown for selecting customer name
        searchnamecho = new Choice();
        searchnamecho.setBounds(520, 20, 150, 20);
        add(searchnamecho);

        // Populate the name dropdown with values from the 'new_customer' table in the database
        try {
            database c = new database();
            ResultSet resultset = c.statement.executeQuery("select * from new_customer");
            while (resultset.next()) {
                searchnamecho.add(resultset.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a table to display customer details
        table = new JTable();
        try {
            database c = new database();
            ResultSet resultset = c.statement.executeQuery("select * from new_customer");
            table.setModel(DbUtils.resultSetToTableModel(resultset));  // Set the table model with data from the database
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Scroll pane to hold the table
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 700, 400);
        jsp.setBackground(Color.white);
        add(jsp);

        // Search button to filter data based on the selected meter and name
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.setBackground(Color.white);
        search.setForeground(Color.black);
        search.addActionListener(this);  // Add action listener
        add(search);

        // Print button to print the table data
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.setBackground(Color.white);
        print.setForeground(Color.black);
        print.addActionListener(this);  // Add action listener
        add(print);

        // Close button to close the frame
        close = new JButton("Close");
        close.setBounds(600, 70, 80, 20);
        close.setBackground(Color.white);
        close.setForeground(Color.black);
        close.addActionListener(this);  // Add action listener
        add(close);

        // Set the frame to be visible
        setVisible(true);
    }

    // Action listener method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        // If the search button is clicked
        if(e.getSource() == search) {
            // Construct SQL query to filter by selected meter number and name
            String query_search = "select * from new_customer where meterno = '" + searchmetercho.getSelectedItem() +  "'and name ='"+searchnamecho.getSelectedItem()+"'";
            try{
                database c = new database();
                ResultSet resultset = c.statement.executeQuery(query_search);
                table.setModel(DbUtils.resultSetToTableModel(resultset));  // Update the table with the search results

            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
        // If the print button is clicked
        else if(e.getSource() == print) {
            try{
                table.print();  // Print the current table content
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
        // If the close button is clicked
        else{
            setVisible(false);  // Close the frame
        }
    }

    // Main method to create and display the customer details frame
    public static void main(String[] args) {
        new customer_details();
    }
}

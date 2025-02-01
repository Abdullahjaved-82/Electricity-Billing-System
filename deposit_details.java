
package electricity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class deposit_details extends JFrame implements ActionListener {
    // Declare components for the UI
    Choice searchmetercho, searchmonthcho;
    JTable table;
    JButton search, print, close;

    deposit_details() {
        // Set the title and basic properties of the frame
        super("Deposit Details");
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

        // Populate the meter number dropdown with values from the database
        try {
            database c = new database();
            ResultSet resultset = c.statement.executeQuery("select * from bill");
            while (resultset.next()) {
                searchmetercho.add(resultset.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Label for the month search
        JLabel searchmonth = new JLabel("Search by month");
        searchmonth.setBounds(400, 20, 100, 20);
        add(searchmonth);

        // Dropdown for selecting the month
        searchmonthcho = new Choice();
        searchmonthcho.setBounds(520, 20, 150, 20);
        add(searchmonthcho);
        // Add month options to the dropdown
        searchmonthcho.add("January");
        searchmonthcho.add("February");
        searchmonthcho.add("March");
        searchmonthcho.add("April");
        searchmonthcho.add("May");
        searchmonthcho.add("June");
        searchmonthcho.add("July");
        searchmonthcho.add("December");

        // Table to display the results
        table = new JTable();
        try {
            database c = new database();
            ResultSet resultset = c.statement.executeQuery("select * from bill");
            table.setModel(DbUtils.resultSetToTableModel(resultset));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Scroll pane for the table
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 700, 400);
        jsp.setBackground(Color.white);
        add(jsp);

        // Search button to filter data based on the selected meter and month
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.setBackground(Color.white);
        search.setForeground(Color.black);
        search.addActionListener(this);
        add(search);

        // Print button to print the table data
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.setBackground(Color.white);
        print.setForeground(Color.black);
        print.addActionListener(this);
        add(print);

        // Close button to close the frame
        close = new JButton("Close");
        close.setBounds(600, 70, 80, 20);
        close.setBackground(Color.white);
        close.setForeground(Color.black);
        close.addActionListener(this);
        add(close);

        // Set the frame to be visible
        setVisible(true);
    }

    // Action listener method for handling button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        // If the search button is clicked
        if(e.getSource() == search) {
            // Construct SQL query to filter by selected meter number and month
            String query_search = "select * from bill where meter_no = '" + searchmetercho.getSelectedItem() +  "'and month ='"+searchmonthcho.getSelectedItem()+"'";
            try{
                database c = new database();
                ResultSet resultset = c.statement.executeQuery(query_search);
                table.setModel(DbUtils.resultSetToTableModel(resultset));  // Update the table with the search results

            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        // If the print button is clicked
        else if(e.getSource() == print) {
            try{
                table.print();  // Print the current table content
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        // If the close button is clicked
        else{
            setVisible(false);  // Close the frame
        }
    }

    public static void main(String[] args) {
        new deposit_details();  // Create and display the deposit details frame
    }
}

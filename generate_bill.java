
package electricity.billing.system;

// Import necessary libraries for GUI, event handling, and database access
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

// Define a class that extends JFrame and implements ActionListener for event handling
public class generate_bill extends JFrame implements ActionListener {
    // Declare components for the GUI
    Choice searchmonthcho;  // Dropdown to select month
    String meter;  // Variable to store the meter number
    JTextArea area;  // Text area to display the bill
    JButton bill, printBill;  // Buttons for generating and printing the bill

    // Constructor to initialize the frame and its components
    generate_bill(String meter) {
        this.meter = meter;  // Store the meter number passed to the constructor

        // Set the size and position of the frame
        setSize(500, 650);
        setLocation(350, 25);

        // Use BorderLayout for the main layout
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();  // Create a panel for the top section of the frame

        // Add a heading label
        JLabel heading = new JLabel("Generate Bill");
        JLabel meter_no = new JLabel(meter);  // Display the meter number

        // Initialize the dropdown menu for selecting a month
        searchmonthcho = new Choice();
        searchmonthcho.add("January");
        searchmonthcho.add("February");
        searchmonthcho.add("March");
        searchmonthcho.add("April");
        searchmonthcho.add("May");
        searchmonthcho.add("June");
        searchmonthcho.add("July");
        searchmonthcho.add("August");
        searchmonthcho.add("September");
        searchmonthcho.add("October");
        searchmonthcho.add("November");
        searchmonthcho.add("December");

        // Create a text area to display the bill details
        area = new JTextArea(50, 15);
        area.setText("\n \n \t ------------------- Click on the ---------------\n \t ------------------- Generate Bill");
        area.setFont(new Font("Senserif", Font.ITALIC, 15));  // Set font style
        JScrollPane pane = new JScrollPane(area);  // Add scroll pane to the text area

        // Initialize the "Generate Bill" and "Print Bill" buttons and add action listeners
        bill = new JButton("Generate Bill");
        bill.addActionListener(this);

        printBill = new JButton("Print Bill");
        printBill.addActionListener(this);

        // Add components to the frame
        add(pane);  // Add the text area to the center of the frame
        panel.add(heading);  // Add the heading to the panel
        panel.add(meter_no);  // Add the meter number to the panel
        panel.add(searchmonthcho);  // Add the dropdown menu to the panel
        add(panel, "North");  // Add the panel to the top (North) of the frame

        // Create another panel for the buttons at the bottom
        JPanel southPanel = new JPanel();
        southPanel.add(bill);  // Add "Generate Bill" button to the panel
        southPanel.add(printBill);  // Add "Print Bill" button to the panel
        add(southPanel, "South");  // Add the panel to the bottom (South) of the frame

        setVisible(true);  // Make the frame visible
    }

    // Event handling for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bill) {  // If "Generate Bill" button is clicked
            try {
                database c = new database();  // Connect to the database
                String smonth = searchmonthcho.getSelectedItem();  // Get the selected month
                area.setText("\n Power Limited \n Electricity Bill For Month of " + smonth + ", 2023\n\n\n");

                // Fetch customer details from the database
                ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meterno ='" + meter + "'");
                if (resultSet.next()) {
                    area.append("\n    Customer Name        : " + resultSet.getString("name"));
                    area.append("\n    Customer Meter Number: " + resultSet.getString("meterno"));
                    area.append("\n    Customer Address     : " + resultSet.getString("adress"));
                    area.append("\n    Customer City        : " + resultSet.getString("city"));
                    area.append("\n    Customer State       : " + resultSet.getString("state"));
                    area.append("\n    Customer Email       : " + resultSet.getString("email"));
                    area.append("\n    Customer Phone Number: " + resultSet.getString("phone_no"));
                }

                // Fetch meter details from the database
                resultSet = c.statement.executeQuery("select * from meter_info where meter_no ='" + meter + "'");
                if (resultSet.next()) {
                    area.append("\n    Customer Meter Location: " + resultSet.getString("meter_location"));
                    area.append("\n    Customer Meter Type: " + resultSet.getString("meter_type"));
                    area.append("\n    Customer Phase Code: " + resultSet.getString("phase_code"));
                    area.append("\n    Customer Bill Type: " + resultSet.getString("bil_type"));
                    area.append("\n    Customer Days: " + resultSet.getString("days"));
                }

                // Fetch tax details from the database
                resultSet = c.statement.executeQuery("select * from tax");
                if (resultSet.next()) {
                    area.append("\n    Cost Per Unit: " + resultSet.getString("cost_per_unit"));
                    area.append("\n   Meter Rent: " + resultSet.getString("meter_rent"));
                    area.append("\n   Service Charge: " + resultSet.getString("service_charge"));
                    area.append("\n   Service Tax: " + resultSet.getString("service_tax"));
                    area.append("\n   tv tax: " + resultSet.getString("tv_tax"));
                    area.append("\n   Fixed Tax: " + resultSet.getString("fixed_tax"));
                }

                // Fetch bill details for the selected month from the database
                resultSet = c.statement.executeQuery("select * from bill where meter_no = '" + meter + "' and month = '" + searchmonthcho.getSelectedItem() + "'");
                if (resultSet.next()) {
                    area.append("\n    Current Month: " + resultSet.getString("month"));
                    area.append("\n   Units Consumed: " + resultSet.getString("unit"));
                    area.append("\n   Total Charges: " + resultSet.getString("totall_bill"));
                    area.append("\n   Total Payable: " + resultSet.getString("totall_bill"));
                }
            } catch (Exception E) {
                E.printStackTrace();  // Print the exception for debugging
            }
        } else if (e.getSource() == printBill) {  // If "Print Bill" button is clicked
            try {
                area.print();  // Print the bill displayed in the text area
            } catch (Exception E) {
                E.printStackTrace();  // Print the exception for debugging
            }
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new generate_bill("");  // Create an instance of the class to display the frame
    }
}

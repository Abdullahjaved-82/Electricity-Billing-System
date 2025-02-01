
package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class calculate_bill extends JFrame implements ActionListener{
    // Declare UI components
    JLabel nameText, addressText;
    TextField unitText;
    Choice meternumCho, monthCho;
    JButton submit, cancel;

    // Constructor to set up the UI and initialize components
    calculate_bill(){
        // Create a panel for the main content of the frame
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(214,195,247));  // Set background color
        add(panel);

        // Heading label
        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(70,10,300,20);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));  // Set font
        panel.add(heading);

        // Meter Number label and Choice for meter selection
        JLabel meternum = new JLabel("Meter Number");
        meternum.setBounds(50,80,100,20);
        panel.add(meternum);

        meternumCho = new Choice();
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer");
            while (resultSet.next()){
                meternumCho.add(resultSet.getString("meterno"));
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
        meternumCho.setBounds(180,80,100,20);
        panel.add(meternumCho);

        // Name label and field to display name based on meter number
        JLabel name = new JLabel("Name");
        name.setBounds(50,120,100,20);
        panel.add(name);

        nameText = new JLabel("");
        nameText.setBounds(180,120,150,20);
        panel.add(nameText);

        // Address label and field to display address based on meter number
        JLabel address = new JLabel("Address");
        address.setBounds(50,160,100,20);
        panel.add(address);

        addressText = new JLabel("");
        addressText.setBounds(180,160,150,20);
        panel.add(addressText);

        // Fetch and display customer details (name and address) based on the selected meter number
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meterno = '"+meternumCho.getSelectedItem()+"' ");
            while (resultSet.next()){
                nameText.setText(resultSet.getString("name"));
                addressText.setText(resultSet.getString("adress"));
            }
        } catch (Exception E) {
            E.printStackTrace();
        }

        // ItemListener to update name and address when the meter number is changed
        meternumCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    database c = new database();
                    ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meterno = '"+meternumCho.getSelectedItem()+"' ");
                    while (resultSet.next()){
                        nameText.setText(resultSet.getString("name"));
                        addressText.setText(resultSet.getString("adress"));
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        // Unit consumed label and input field
        JLabel unitconsumed = new JLabel("Unit Consumed");
        unitconsumed.setBounds(50,200,100,20);
        panel.add(unitconsumed);

        unitText = new TextField();
        unitText.setBounds(180,200,150,20);
        panel.add(unitText);

        // Month label and dropdown for selecting month
        JLabel month = new JLabel("Month");
        month.setBounds(50,240,100,20);
        panel.add(month);

        monthCho = new Choice();
        // Add all months to the dropdown
        monthCho.add("January");
        monthCho.add("February");
        monthCho.add("March");
        monthCho.add("April");
        monthCho.add("May");
        monthCho.add("June");
        monthCho.add("July");
        monthCho.add("August");
        monthCho.add("September");
        monthCho.add("October");
        monthCho.add("November");
        monthCho.add("December");
        monthCho.setBounds(180,240,150,20);
        panel.add(monthCho);

        // Submit button to calculate the bill and save it to the database
        submit = new JButton("Submit");
        submit.setBounds(80,300,100,25);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);  // Add action listener
        panel.add(submit);

        // Cancel button to close the window
        cancel = new JButton("Cancel");
        cancel.setBounds(220,300,100,25);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.white);
        cancel.addActionListener(this);  // Add action listener
        panel.add(cancel);

        // Set the layout and size of the frame
        setLayout(new BorderLayout());
        add(panel, "Center");

        // Add an icon image to the right side of the frame
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/budget.png"));
        Image image = imageIcon.getImage().getScaledInstance(250, 200, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon1);
        add(imageLabel, "East");

        setSize(650, 400);
        setLocation(400, 200);
        setVisible(true);
    }

    // Action listener for handling button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        // If Submit button is clicked, calculate the bill
        if (e.getSource() == submit) {
            String smeterNo = meternumCho.getSelectedItem();
            String sunit = unitText.getText();
            String smonth = monthCho.getSelectedItem();

            int totalBill = 0;
            int units = Integer.parseInt(sunit);  // Convert unit consumed to an integer
            String query_tax = "select * from tax";  // Query to get tax rates
            try {
                database c = new database();
                ResultSet resultSet = c.statement.executeQuery(query_tax);
                while (resultSet.next()) {
                    // Calculate the total bill using unit cost and other taxes
                    totalBill += units * Integer.parseInt(resultSet.getString("cost_per_unit"));
                    totalBill += Integer.parseInt(resultSet.getString("meter_rent"));
                    totalBill += Integer.parseInt(resultSet.getString("service_charge"));
                    totalBill += Integer.parseInt(resultSet.getString("tv_tax"));
                    totalBill += Integer.parseInt(resultSet.getString("fixed_tax"));
                }
            } catch (Exception E) {
                E.printStackTrace();
            }

            // Insert the calculated bill into the 'bill' table
            String query_total_bill = "insert into bill values('" + smeterNo + "', '" + smonth + "','" + sunit + "', '" + totalBill + "','Not Paid')";
            try {
                database c = new database();
                c.statement.executeUpdate(query_total_bill);  // Execute the query

                // Show confirmation message
                JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
                setVisible(false);  // Close the frame after submission
            } catch (Exception E) {
                E.printStackTrace();
            }

        } else {
            setVisible(false);  // Close the frame if Cancel button is clicked
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        new calculate_bill();
    }
}

package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class pay_bill extends JFrame implements ActionListener {
    // Declare UI components and variables
    Choice searchmonthcho; // Dropdown for selecting the month
    String meter; // Stores the meter number
    JButton pay, back; // Buttons for payment and navigation

    // Constructor to initialize the pay_bill frame
    pay_bill(String meter) {
        this.meter = meter;

        // Set frame size, location, and layout
        setSize(800, 550);
        setLocation(270, 90);
        setLayout(null);

        // Heading for the frame
        JLabel heading = new JLabel("Pay Bill");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBounds(120, 5, 400, 30);
        add(heading);

        // Meter Number label and dynamic text
        JLabel meterNumber = new JLabel("Meter Number");
        meterNumber.setBounds(35, 80, 200, 20);
        add(meterNumber);

        JLabel meterNumberText = new JLabel(""); // Dynamic text for meter number
        meterNumberText.setBounds(300, 80, 200, 20);
        add(meterNumberText);

        // Customer Name label and dynamic text
        JLabel name = new JLabel("Name");
        name.setBounds(35, 140, 200, 20);
        add(name);

        JLabel nameText = new JLabel(""); // Dynamic text for name
        nameText.setBounds(300, 140, 200, 20);
        add(nameText);

        // Month selection dropdown
        JLabel month = new JLabel("Month");
        month.setBounds(35, 200, 200, 20);
        add(month);

        searchmonthcho = new Choice();
        // Add all months to the dropdown
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
        searchmonthcho.setBounds(300, 200, 150, 20);
        add(searchmonthcho);

        // Labels for Unit, Total Bill, and Status
        JLabel unit = new JLabel("Unit");
        unit.setBounds(35, 260, 200, 20);
        add(unit);

        JLabel unitText = new JLabel(""); // Dynamic text for units consumed
        unitText.setBounds(300, 260, 200, 20);
        add(unitText);

        JLabel totalBill = new JLabel("Total Bill");
        totalBill.setBounds(35, 320, 200, 20);
        add(totalBill);

        JLabel totalBillText = new JLabel(""); // Dynamic text for total bill
        totalBillText.setBounds(300, 320, 200, 20);
        add(totalBillText);

        JLabel status = new JLabel("Status");
        status.setBounds(35, 380, 200, 20);
        add(status);

        JLabel statusText = new JLabel(""); // Dynamic text for payment status
        statusText.setBounds(300, 380, 200, 20);
        statusText.setForeground(Color.RED);
        add(statusText);

        // Fetch customer details based on the meter number
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meterno = '" + meter + "'");
            while (resultSet.next()) {
                meterNumberText.setText(meter); // Set meter number
                nameText.setText(resultSet.getString("name")); // Set customer name
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add item listener to the dropdown to fetch and display bill details
        searchmonthcho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                database c = new database();
                try {
                    ResultSet resultSet = c.statement.executeQuery(
                            "select * from bill where meter_no = '" + meter + "' and month = '" + searchmonthcho.getSelectedItem() + "'");
                    while (resultSet.next()) {
                        unitText.setText(resultSet.getString("unit")); // Set unit details
                        totalBillText.setText(resultSet.getString("totall_bill")); // Set total bill
                        statusText.setText(resultSet.getString("status")); // Set payment status
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Pay Button to update payment status
        pay = new JButton("Pay");
        pay.setBackground(Color.black);
        pay.setForeground(Color.white);
        pay.setBounds(100, 460, 100, 25);
        pay.addActionListener(this);
        add(pay);

        // Back Button to navigate to the previous screen
        back = new JButton("Back");
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.setBounds(230, 460, 100, 25);
        back.addActionListener(this);
        add(back);

        setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pay) { // Handle Pay button click
            try {
                database c = new database();
                c.statement.executeUpdate(
                        "update bill set status = 'Paid' where meter_no = '" + meter + "' and month = '" + searchmonthcho.getSelectedItem() + "'");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            setVisible(false); // Close the current frame
            new payment_bill(meter); // Navigate to payment confirmation screen
        } else { // Handle Back button click
            setVisible(false); // Close the current frame
        }
    }

    public static void main(String[] args) {
        new pay_bill(""); // Initialize the frame
    }
}

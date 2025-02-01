package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class view_information extends JFrame implements ActionListener {
    String view;
    JButton cancel;

    // Constructor for viewing customer information
    view_information(String view){
        this.view = view;

        // Set the properties of the JFrame
        setBounds(300,50,800,580); // Set position and size of the window
        getContentPane().setBackground(Color.white); // Set background color
        setLayout(null); // No layout manager, manual placement of components

        // Heading label
        JLabel heading = new JLabel("View Customer Information");
        heading.setBounds(250,0,500,40);
        heading.setFont(new Font("serif",Font.BOLD,20)); // Set font style and size
        add(heading); // Add heading to the frame

        // Labels for customer information fields
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(70,80,100,20);
        add(nameLabel);

        JLabel nameLabelText = new JLabel(""); // This will display the customer's name
        nameLabelText.setBounds(200,80,150,20);
        add(nameLabelText);

        JLabel meterno = new JLabel("Meter Number");
        meterno.setBounds(70,140,100,20);
        add(meterno);

        JLabel meternoText = new JLabel(""); // This will display the customer's meter number
        meternoText.setBounds(200,140,150,20);
        add(meternoText);

        JLabel address = new JLabel("Address");
        address.setBounds(70,200,100,20);
        add(address);

        JLabel addressText = new JLabel(""); // This will display the customer's address
        addressText.setBounds(200,200,150,20);
        add(addressText);

        JLabel city = new JLabel("City");
        city.setBounds(70,260,100,20);
        add(city);

        JLabel cityText = new JLabel(""); // This will display the customer's city
        cityText.setBounds(200,260,150,20);
        add(cityText);

        JLabel state = new JLabel("State");
        state.setBounds(500,80,100,20);
        add(state);

        JLabel stateText = new JLabel(""); // This will display the customer's state
        stateText.setBounds(600,80,150,20);
        add(stateText);

        JLabel email = new JLabel("Email");
        email.setBounds(500,140,100,20);
        add(email);

        JLabel emailText = new JLabel(""); // This will display the customer's email
        emailText.setBounds(600,140,150,20);
        add(emailText);

        JLabel phone = new JLabel("Phone");
        phone.setBounds(500,200,100,20);
        add(phone);

        JLabel phoneText = new JLabel(""); // This will display the customer's phone number
        phoneText.setBounds(600,200,150,20);
        add(phoneText);

        // Database query to fetch customer details based on the meter number
        try{
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meterno = '"+view+"'");
            if (resultSet.next()){
                // Set the customer information to the respective labels
                nameLabelText.setText(resultSet.getString("name"));
                meternoText.setText(resultSet.getString("meterno"));
                addressText.setText(resultSet.getString("adress"));
                cityText.setText(resultSet.getString("city"));
                stateText.setText(resultSet.getString("state"));
                emailText.setText(resultSet.getString("email"));
                phoneText.setText(resultSet.getString("phone_no"));
            }
        }catch (Exception e){
            e.printStackTrace(); // Handle exceptions
        }

        // Cancel button to close the window
        cancel = new JButton("Cancel");
        cancel.setBackground(new Color(24,118,242)); // Set button color
        cancel.setForeground(Color.white); // Set button text color
        cancel.setBounds(220,350,120,25);
        cancel.addActionListener(this); // Add action listener for button click
        add(cancel);

        // Add an image to the window
        ImageIcon a1 = new ImageIcon(ClassLoader.getSystemResource("icon/viewInfo.png"));
        Image a2 = a1.getImage().getScaledInstance(600,300,Image.SCALE_DEFAULT); // Scale image
        ImageIcon i3 = new ImageIcon(a2);
        JLabel img = new JLabel(i3);
        img.setBounds(100,320,600,300);
        add(img);

        setVisible(true); // Make the window visible
    }

    // Action listener for the Cancel button
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cancel){
            setVisible(false); // Close the window when cancel is pressed
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new view_information(""); // Create an instance of the view_information class
    }
}

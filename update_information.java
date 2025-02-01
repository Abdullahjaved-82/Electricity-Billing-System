package electricity.billing.system;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class update_information extends JFrame implements ActionListener {

    // Declaring the components for displaying and updating customer information
    JLabel nametext;
    JTextField addressText, cityText, stateText, emailText, phoneText;
    String meter; // To store the meter number for the customer
    JButton update, cancel;

    // Constructor for initializing the update window
    update_information(String meter){
        this.meter = meter; // Assign the passed meter number to the class variable
        setBounds(300,150,777,450); // Set window size and position
        getContentPane().setBackground(new Color(229,255,227)); // Set background color
        setLayout(null); // Set no layout manager, so we will manually set the position of components

        // Heading for the window
        JLabel heading = new JLabel("Update Customer Information");
        heading.setBounds(50,10,400,40);
        heading.setFont(new Font("serif", Font.BOLD, 20)); // Set font style and size
        add(heading); // Add heading to the window

        // Labels and fields for customer information
        JLabel name = new JLabel("Name");
        name.setBounds(30,70,100,20);
        add(name);

        nametext = new JLabel(""); // This will display the customer's name (retrieved from the database)
        nametext.setBounds(150,70,200,20);
        add(nametext);

        JLabel meterNo = new JLabel("Meter Number");
        meterNo.setBounds(30,110,100,20);
        add(meterNo);

        JLabel meterText = new JLabel(""); // This will display the meter number (retrieved from the database)
        meterText.setBounds(150,110,100,20);
        add(meterText);

        // Address input field
        JLabel address = new JLabel("Address");
        address.setBounds(30,150,100,20);
        add(address);

        addressText = new JTextField(); // TextField for address
        addressText.setBounds(150,150,200,20);
        add(addressText);

        // City input field
        JLabel city = new JLabel("City");
        city.setBounds(30,190,100,20);
        add(city);

        cityText = new JTextField(); // TextField for city
        cityText.setBounds(150,190,200,20);
        add(cityText);

        // State input field
        JLabel state = new JLabel("State");
        state.setBounds(30,230,100,20);
        add(state);

        stateText = new JTextField(); // TextField for state
        stateText.setBounds(150,230,200,20);
        add(stateText);

        // Email input field
        JLabel email = new JLabel("Email");
        email.setBounds(30,270,100,20);
        add(email);

        emailText = new JTextField(); // TextField for email
        emailText.setBounds(150,270,200,20);
        add(emailText);

        // Phone number input field
        JLabel phone = new JLabel("Phone");
        phone.setBounds(30,310,100,20);
        add(phone);

        phoneText = new JTextField(); // TextField for phone number
        phoneText.setBounds(150,310,200,20);
        add(phoneText);

        // Fetching the customer details from the database based on meter number
        try{
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meterno = '"+meter+"'");
            if (resultSet.next()){
                // Set the customer details into the labels and text fields
                nametext.setText(resultSet.getString("name"));
                meterText.setText(resultSet.getString("meterno"));
                addressText.setText(resultSet.getString("adress"));
                cityText.setText(resultSet.getString("city"));
                stateText.setText(resultSet.getString("state"));
                emailText.setText(resultSet.getString("email"));
                phoneText.setText(resultSet.getString("phone_no"));
            }
        }catch (Exception e){
            e.printStackTrace(); // Handle exceptions (e.g., database errors)
        }

        // Update button to save the changes
        update = new JButton("Update");
        update.setBackground(new Color(33,106,145)); // Set background color
        update.setForeground(Color.white); // Set text color
        update.setBounds(50,360,120,25);
        update.addActionListener(this); // Add action listener for update button
        add(update);

        // Cancel button to close the window without saving changes
        cancel = new JButton("Cancel");
        cancel.setBackground(new Color(33,106,145)); // Set background color
        cancel.setForeground(Color.white); // Set text color
        cancel.setBounds(200,360,120,25);
        cancel.addActionListener(this); // Add action listener for cancel button
        add(cancel);

        // Adding an image to the window
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/update.png"));
        Image image = imageIcon.getImage().getScaledInstance(400,410,Image.SCALE_DEFAULT); // Scale the image
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel imgLabel = new JLabel(imageIcon1);
        imgLabel.setBounds(360,0,400,410);
        add(imgLabel);

        // Make the window visible
        setVisible(true);
    }

    // Action listener for handling button clicks (Update or Cancel)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update){
            // Get the updated information from the text fields
            String saddress = addressText.getText();
            String scity = cityText.getText();
            String sstate = stateText.getText();
            String semail = emailText.getText();
            String sphone = phoneText.getText();

            try{
                // Update the customer's information in the database
                database c = new database();
                c.statement.executeUpdate("update new_customer set adress ='"+saddress+"', city = '"+scity+"', state = '"+sstate+"', email = '"+semail+"', phone_no ='"+sphone+"' where meterno = '"+meter+"'");

                // Show success message and close the window
                JOptionPane.showMessageDialog(null,"User Information Updated Successfully");
                setVisible(false);
            }catch (Exception E){
                E.printStackTrace(); // Handle exceptions (e.g., database errors)
            }
        } else {
            // Close the window without saving changes
            setVisible(false);
        }
    }

    // Main method to run the update window
    public static void main(String[] args) {
        new update_information(""); // Create and display the update window (empty meter number for testing)
    }
}

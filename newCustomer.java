package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class newCustomer extends JFrame implements ActionListener {
    // Define the labels, text fields, and buttons
    JLabel heading, meternumText, customerName, meterNum, address, city, state, email, phone;
    JButton next, cancel;
    TextField nameText, addressText, cityText, stateText, emailText, phoneText;

    // Constructor to initialize and set up the frame
    newCustomer() {
        super("New Customer");  // Set the title of the frame
        setSize(700, 500);  // Set the size of the frame
        setLocation(350, 100);  // Set the location of the frame on the screen

        // Create a panel to contain the components
        JPanel panel = new JPanel();
        panel.setLayout(null);  // Set layout to null for absolute positioning
        panel.setBackground(new Color(252, 186, 3));  // Set a yellow background color
        add(panel);  // Add the panel to the frame

        // Heading label
        heading = new JLabel("New Customer");
        heading.setBounds(180, 10, 200, 20);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));  // Set the font and size
        panel.add(heading);

        // Label and text field for customer's name
        customerName = new JLabel("New Customer");
        customerName.setBounds(50, 80, 100, 20);
        panel.add(customerName);

        nameText = new TextField();
        nameText.setBounds(180, 80, 150, 20);
        panel.add(nameText);

        // Label and field for Meter Number
        meterNum = new JLabel("Meter Number");
        meterNum.setBounds(50, 120, 100, 20);
        panel.add(meterNum);

        meternumText = new JLabel("");
        meternumText.setBounds(180, 120, 150, 20);
        panel.add(meternumText);

        // Generate a random meter number
        Random ran = new Random();
        long number = ran.nextLong() % 1000000;
        meternumText.setText("" + Math.abs(number));  // Ensure the meter number is positive

        // Labels and text fields for Address, City, State, Email, and Phone
        address = new JLabel("Address");
        address.setBounds(50, 160, 100, 20);
        panel.add(address);

        addressText = new TextField();
        addressText.setBounds(180, 160, 150, 20);
        panel.add(addressText);

        city = new JLabel("City");
        city.setBounds(50, 200, 100, 20);
        panel.add(city);

        cityText = new TextField();
        cityText.setBounds(180, 200, 150, 20);
        panel.add(cityText);

        state = new JLabel("State");
        state.setBounds(50, 240, 100, 20);
        panel.add(state);

        stateText = new TextField();
        stateText.setBounds(180, 240, 150, 20);
        panel.add(stateText);

        email = new JLabel("Email");
        email.setBounds(50, 280, 100, 20);
        panel.add(email);

        emailText = new TextField();
        emailText.setBounds(180, 280, 150, 20);
        panel.add(emailText);

        phone = new JLabel("Phone");
        phone.setBounds(50, 320, 100, 20);
        panel.add(phone);

        phoneText = new TextField();
        phoneText.setBounds(180, 320, 150, 20);
        panel.add(phoneText);

        // "Next" button to submit the details
        next = new JButton("Next");
        next.setBounds(120, 390, 100, 25);
        next.setBackground(Color.black);
        next.setForeground(Color.white);
        next.addActionListener(this);
        panel.add(next);

        // "Cancel" button to cancel the operation
        cancel = new JButton("Cancel");
        cancel.setBounds(230, 390, 100, 25);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.white);
        cancel.addActionListener(this);
        panel.add(cancel);

        // Add the panel to the center of the frame
        setLayout(new BorderLayout());
        add(panel, "Center");

        // Set an image to the left of the panel
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/boy.png"));
        Image i2 = i1.getImage().getScaledInstance(230, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imgLable = new JLabel(i3);
        add(imgLable, "West");

        // Make the frame visible
        setVisible(true);
    }

    // Action handler for buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            // Retrieve input data from text fields
            String sname = nameText.getText();
            String smeter = meternumText.getText();
            String saddress = addressText.getText();
            String scity = cityText.getText();
            String sstate = stateText.getText();
            String eemail = emailText.getText();
            String sphone = phoneText.getText();

            // Create SQL queries to insert the new customer and sign-up details
            String query_customer = "insert into new_customer values('" + sname + "','" + smeter + "','" + saddress + "','" + scity + "','" + sstate + "','" + eemail + "','" + sphone + "')";
            String query_signup = "insert into Signup values('" + smeter + "','','" + sname + "','','')";

            try {
                // Execute SQL queries using the database connection
                database c = new database();
                c.statement.executeUpdate(query_customer);  // Add customer details to the database
                c.statement.executeUpdate(query_signup);  // Add sign-up details to the database

                // Show a confirmation message and proceed to the next screen
                JOptionPane.showMessageDialog(null, "Customer details added successfully");
                setVisible(false);
                new meterinfo(smeter);  // Open the next screen (meterinfo) with the generated meter number
            } catch (Exception E) {
                E.printStackTrace();  // Print any exceptions to the console for debugging
            }
        } else {
            setVisible(false);  // Close the frame if the cancel button is pressed
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new newCustomer();  // Create a new instance of newCustomer and display the form
    }
}

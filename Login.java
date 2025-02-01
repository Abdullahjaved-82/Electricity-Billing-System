
package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    // Declare components used in the login form
    JTextField usertext, userpassword;
    Choice Logginchoice;
    JButton Loginbutton, Cancelbutton, Signupbutton;

    // Constructor to initialize and set up the login form
    Login() {
        super("Login"); // Set the title of the frame
        getContentPane().setBackground(Color.WHITE); // Set background color

        // Use absolute positioning for layout
        setLayout(null);

        // Label for "Username"
        JLabel username = new JLabel("Username");
        username.setBounds(300, 60, 100, 20);
        add(username);

        // Text field for entering username
        usertext = new JTextField();
        usertext.setBounds(400, 60, 150, 20);
        add(usertext);

        // Label for "Password"
        JLabel password = new JLabel("Password");
        password.setBounds(300, 100, 100, 20);
        add(password);

        // Password field for entering password
        userpassword = new JPasswordField();
        userpassword.setBounds(400, 100, 150, 20);
        add(userpassword);

        // Label for "Login in as"
        JLabel login = new JLabel("Login in as");
        login.setBounds(300, 154, 100, 20);
        add(login);

        // Dropdown for selecting user type (Admin or Customer)
        Logginchoice = new Choice();
        Logginchoice.add("Admin");
        Logginchoice.add("Customer");
        Logginchoice.setBounds(400, 154, 150, 20);
        add(Logginchoice);

        // Button for "Login"
        Loginbutton = new JButton("Login");
        Loginbutton.setBounds(330, 200, 100, 20);
        Loginbutton.addActionListener(this);
        add(Loginbutton);

        // Button for "Cancel"
        Cancelbutton = new JButton("Cancel");
        Cancelbutton.setBounds(450, 200, 100, 20);
        Cancelbutton.addActionListener(this);
        add(Cancelbutton);

        // Button for "Sign up"
        Signupbutton = new JButton("Sign up");
        Signupbutton.setBounds(390, 230, 100, 20);
        Signupbutton.addActionListener(this);
        add(Signupbutton);

        // Profile image displayed on the login form
        ImageIcon ProfileOne = new ImageIcon(ClassLoader.getSystemResource("icon/profile.png"));
        Image profiletow = ProfileOne.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon fprofileOne = new ImageIcon(profiletow);
        JLabel ProfileLabel = new JLabel(fprofileOne);
        ProfileLabel.setBounds(5, 5, 250, 250);
        add(ProfileLabel);

        // Set the size of the frame
        setSize(640, 300);
        setLocation(400, 200); // Position the frame on the screen

        // Ensure the application exits when the frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the frame visible
        setVisible(true);
    }

    // Action listener to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Loginbutton) {
            // Handle login logic
            String susername = usertext.getText();
            String spassword = userpassword.getText();
            String suser = Logginchoice.getSelectedItem();

            try {
                database c = new database(); // Connect to the database
                String query = "select * from Signup where username = '" + susername + "' and password = '" + spassword + "' and usertype = '" + suser + "'";
                ResultSet resultSet = c.statement.executeQuery(query);

                if (resultSet.next()) {
                    // If login credentials are valid, proceed to the main class
                    String meter = resultSet.getString("meter_no");
                    setVisible(false); // Close the login form
                    new main_class(suser, meter); // Open the main application
                } else {
                    // Show error message for invalid credentials
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
            } catch (Exception e1) {
                e1.printStackTrace(); // Print the stack trace for debugging
            }
        } else if (e.getSource() == Cancelbutton) {
            // Close the login form
            setVisible(false);
        } else if (e.getSource() == Signupbutton) {
            // Open the signup form
            new Signup();
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new Login(); // Create and display the login form
    }
}


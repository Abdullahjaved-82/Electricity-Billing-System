package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Signup extends JFrame implements ActionListener {
    // Declaring the UI components
    Choice LoginAsCho;
    TextField meterttext;
    TextField employertext;
    TextField usernametext;
    TextField nametext;
    JPasswordField passwordtext;
    JButton create, back;

    // Constructor for the Signup page
    Signup() {
        super("Signup Page");

        // Set the background color of the JFrame
        getContentPane().setBackground(new Color(168,203,255));

        // Set layout to null for custom placement of components
        setLayout(null);

        // Set size and location of the frame
        setSize(600, 380);
        setLocation(400, 200);

        // Label for the account creation choice
        JLabel createAs = new JLabel("Create Account As");
        createAs.setBounds(30, 50, 125, 20);
        add(createAs);

        // Choice to select account type (Admin or Customer)
        LoginAsCho = new Choice();
        LoginAsCho.add("Admin");
        LoginAsCho.add("Customer");
        LoginAsCho.setBounds(170, 50, 120, 20);
        add(LoginAsCho);

        // Label and TextField for Meter Number (visible only if Customer is selected)
        JLabel meterno = new JLabel("Meter Number");
        meterno.setBounds(30, 100, 125, 20);
        meterno.setVisible(false);
        add(meterno);

        meterttext = new TextField();
        meterttext.setBounds(170, 100, 125, 20);
        meterttext.setVisible(false);
        add(meterttext);

        // Label and TextField for Employer (visible only if Admin is selected)
        JLabel employer = new JLabel("Employer");
        employer.setBounds(30, 100, 125, 20);
        employer.setVisible(true);
        add(employer);

        employertext = new TextField();
        employertext.setBounds(170, 100, 125, 20);
        employertext.setVisible(true);
        add(employertext);

        // Label and TextField for Username
        JLabel username = new JLabel("Username");
        username.setBounds(30, 140, 125, 20);
        add(username);

        usernametext = new TextField();
        usernametext.setBounds(170, 140, 125, 20);
        add(usernametext);

        // Label and TextField for Name
        JLabel name = new JLabel("Name");
        name.setBounds(30, 180, 125, 20);
        add(name);

        nametext = new TextField("");
        nametext.setBounds(170, 180, 125, 20);
        add(nametext);

        // Focus listener for Meter TextField (auto-fills name based on meter number)
        meterttext.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                    database c = new database();
                    ResultSet resultSet = c.statement.executeQuery("select * from Signup where meter_no = '"+meterttext.getText()+"'");
                    if(resultSet.next()){
                        nametext.setText(resultSet.getString("name"));
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // Label and JPasswordField for Password
        JLabel password = new JLabel("Password");
        password.setBounds(30, 220, 125, 20);
        add(password);

        passwordtext = new JPasswordField();
        passwordtext.setBounds(170, 220, 125, 20);
        add(passwordtext);

        // ItemListener to toggle fields visibility based on account type (Admin/Customer)
        LoginAsCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = LoginAsCho.getSelectedItem();
                if (user.equals("Customer")) {
                    employer.setVisible(false);      // Hide employer field for Customer
                    nametext.setEditable(false);     // Disable name editing for Customer
                    employertext.setVisible(false);  // Hide employer text field for Customer
                    meterno.setVisible(true);        // Show meter number field for Customer
                    meterttext.setVisible(true);     // Show meter text field for Customer
                } else {
                    employer.setVisible(true);      // Show employer field for Admin
                    employertext.setVisible(true);  // Show employer text field for Admin
                    meterno.setVisible(false);      // Hide meter number field for Admin
                    meterttext.setVisible(false);   // Hide meter text field for Admin
                }
            }
        });

        // Create button to submit the form
        create = new JButton("Create");
        create.setBackground(new Color(66,127,219));
        create.setForeground(Color.black);
        create.setBounds(50, 285, 100, 25);
        create.addActionListener(this);  // Add action listener for button click
        add(create);

        // Back button to navigate back to login page
        back = new JButton("Back");
        back.setBackground(new Color(66,127,219));
        back.setForeground(Color.black);
        back.setBounds(180, 285, 100, 25);
        back.addActionListener(this);  // Add action listener for button click
        add(back);

        // Add an image (icon) to the window
        ImageIcon boyicon = new ImageIcon(ClassLoader.getSystemResource("icon/boy.png"));
        Image boyimage = boyicon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon boyicon1 = new ImageIcon(boyimage);
        JLabel boylabel = new JLabel(boyicon1);
        boylabel.setBounds(320, 30, 250, 250);
        add(boylabel);

        // Set frame visibility
        setVisible(true);
    }

    // Handle button clicks for creating an account or navigating back to login
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            // Get values from form fields
            String sloginAs = LoginAsCho.getSelectedItem();
            String susername = usernametext.getText();
            String sname = nametext.getText();
            String spassword = new String(passwordtext.getPassword());
            String smeter = meterttext.getText();

            // Validate if mandatory fields are filled
            if (susername.isEmpty() || sname.isEmpty() || spassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled for signup.");
                return;
            }

            try {
                // Insert or update account details into the database
                database db = new database();
                String query;
                if (sloginAs.equals("Admin")) {
                    query = "insert into Signup value('" + smeter + "','" + susername + "','" + sname + "','" + spassword + "','" + sloginAs + "')";
                } else {
                    query = "update Signup set username = '" + susername + "',password = '" + spassword + "',usertype = '" + sloginAs + "' where meter_no = '" + smeter + "'";
                }
                db.statement.executeUpdate(query);  // Execute query
                JOptionPane.showMessageDialog(null, "Account Created");

                // Hide the current frame and open the login page
                setVisible(false);
                new Login();
            } catch (Exception e1) {
                e1.printStackTrace();  // Handle any exceptions
            }
        } else if (e.getSource() == back) {
            // Navigate back to the login page
            setVisible(false);
            new Login();
        }
    }

    // Main method to run the Signup frame
    public static void main(String[] args) {
        new Signup();  // Create a new Signup instance
    }
}

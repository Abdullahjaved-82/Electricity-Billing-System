package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class meterinfo extends JFrame implements ActionListener {
    // Define the UI components: Choice dropdowns, labels, and buttons
    Choice meterLocCho, meterTypCho, phaseCodeCho, billtypCho;
    JButton submit;
    String meternumber;

    // Constructor that initializes the frame with meter information fields
    meterinfo(String meternumber) {
        this.meternumber = meternumber;  // Store the meter number passed as argument

        // Panel for holding UI components
        JPanel panel = new JPanel();
        panel.setLayout(null);  // Use absolute positioning for components
        panel.setBackground(new Color(252, 186, 3));  // Set the background color of the panel
        add(panel);

        // Label for the heading
        JLabel Heading = new JLabel("Meter Information");
        Heading.setBounds(180, 10, 200, 20);
        Heading.setFont(new Font("Tahoma", Font.BOLD, 20));  // Set font style and size
        panel.add(Heading);

        // Label for meter number
        JLabel meterNumber = new JLabel("Meter Number");
        meterNumber.setBounds(50, 80, 100, 20);
        panel.add(meterNumber);

        // Display the meter number in a label
        JLabel meterNumberText = new JLabel(meternumber);
        meterNumberText.setBounds(180, 80, 150, 20);
        panel.add(meterNumberText);

        // Label and Choice dropdown for meter location
        JLabel meterLoc = new JLabel("Meter Location");
        meterLoc.setBounds(50, 120, 100, 20);
        panel.add(meterLoc);

        meterLocCho = new Choice();
        meterLocCho.add("Outside");
        meterLocCho.add("Inside");
        meterLocCho.setBounds(180, 120, 150, 20);
        panel.add(meterLocCho);

        // Label and Choice dropdown for meter type
        JLabel meterTyp = new JLabel("Meter Type");
        meterTyp.setBounds(50, 160, 100, 20);
        panel.add(meterTyp);

        meterTypCho = new Choice();
        meterTypCho.add("Electric Meter");
        meterTypCho.add("Green Meter");
        meterTypCho.add("Smart Meter");
        meterTypCho.setBounds(180, 160, 150, 20);
        panel.add(meterTypCho);

        // Label and Choice dropdown for phase code
        JLabel phaseCode = new JLabel("Phase Code");
        phaseCode.setBounds(50, 200, 100, 20);
        panel.add(phaseCode);

        phaseCodeCho = new Choice();
        phaseCodeCho.add("011");
        phaseCodeCho.add("022");
        phaseCodeCho.add("033");
        phaseCodeCho.add("044");
        phaseCodeCho.add("055");
        phaseCodeCho.add("066");
        phaseCodeCho.add("077");
        phaseCodeCho.add("088");
        phaseCodeCho.add("099");
        phaseCodeCho.setBounds(180, 200, 150, 20);
        panel.add(phaseCodeCho);

        // Label and Choice dropdown for bill type
        JLabel billtyp = new JLabel("Bill Type");
        billtyp.setBounds(50, 240, 100, 20);
        panel.add(billtyp);

        billtypCho = new Choice();
        billtypCho.add("Normal");
        billtypCho.add("Industrial");
        billtypCho.setBounds(180, 240, 150, 20);
        panel.add(billtypCho);

        // Labels for information about billing duration
        JLabel day = new JLabel("30 Days Billing Time...");
        day.setBounds(50, 280, 150, 20);
        panel.add(day);

        JLabel note = new JLabel("Note:-");
        note.setBounds(50, 320, 100, 20);
        panel.add(note);

        JLabel note1 = new JLabel("By default bill is calculated for 30 days only");
        note1.setBounds(50, 340, 300, 20);
        panel.add(note1);

        // Submit button to save meter information
        submit = new JButton("Submit");
        submit.setBounds(220, 390, 100, 25);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);  // Add action listener to handle submission
        panel.add(submit);

        // Set the layout and add components
        setLayout(new BorderLayout());
        add(panel, "Center");

        // Set an image for the East side of the frame
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/details.png"));
        Image i2 = i1.getImage().getScaledInstance(230, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imgLabel = new JLabel(i3);
        add(imgLabel, "East");

        // Frame properties
        setSize(700, 500);
        setLocation(350, 100);
        setVisible(true);
    }

    // Action handler for the submit button
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            // Retrieve selected values from the form
            String smeternum = meternumber;
            String Smeterloc = meterLocCho.getSelectedItem();
            String Smetertyp = meterTypCho.getSelectedItem();
            String Sphasecode = phaseCodeCho.getSelectedItem();
            String Sbilltype = billtypCho.getSelectedItem();
            String Sday = "30";  // Default billing time

            // Construct SQL query to insert meter info into the database
            String query_meterinfo = "insert into meter_info values('" + smeternum + "','" + Smeterloc + "','" + Smetertyp + "','" + Sphasecode + "','" + Sbilltype + "','" + Sday + "')";

            try {
                // Execute the SQL query using the database connection
                database c = new database();
                c.statement.executeUpdate(query_meterinfo);

                // Show a success message and close the frame
                JOptionPane.showMessageDialog(null, "Meter information submitted successfully");
                setVisible(false);  // Hide the current window
            } catch (Exception E) {
                E.printStackTrace();  // Print any errors to the console
            }
        } else {
            setVisible(false);  // Hide the frame if the action is canceled
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        new meterinfo(" ");  // Create a new meterinfo frame and pass an empty string for the meter number
    }
}

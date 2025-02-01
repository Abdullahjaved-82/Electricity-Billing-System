package electricity.billing.system;

// Import necessary packages for GUI and event handling
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Define the class payment_bill, which extends JFrame and implements ActionListener
public class payment_bill extends JFrame implements ActionListener {

    // Declare button for navigating back to the previous screen
    JButton back;

    // Declare a string to hold the meter number
    String meter;

    // Constructor for the payment_bill class
    payment_bill(String meter){
        // Initialize the meter variable with the passed argument
        this.meter = meter;

        // Create a JEditorPane to display HTML content
        JEditorPane j = new JEditorPane();

        // Set the editor pane to be non-editable by the user
        j.setEditable(false);

        try{
            // Set the page of the JEditorPane to the payment URL
            j.setPage("https://kuickpay.com/utility-bill-payment/#:~:text=Kuickpay%20is%20your%20go%2Dto,lengthy%20queues%20or%20complicated%20processes.");
            // Set the bounds (position and size) of the editor pane
            j.setBounds(400,150,800,600);
        }catch (Exception E){
            // If there is an error loading the page, display an error message
            E.printStackTrace();
            j.setContentType("text/html");
            j.setText("<html>Errorr! Errorr!  Errorr!  Errorr!  Errorr!  Errorr! </html>");
        }

        // Add the editor pane to a scroll pane to enable scrolling if content overflows
        JScrollPane pane = new JScrollPane(j);
        add(pane);

        // Create a "Back" button and set its bounds
        back = new JButton("Back");
        back.setBounds(640,20,80,30);

        // Add action listener to the button
        back.addActionListener(this);

        // Add the back button to the editor pane
        j.add(back);

        // Set the size and position of the JFrame
        setSize(800,600);
        setLocation(320,80);

        // Make the frame visible
        setVisible(true);
    }

    // Action listener for handling button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        // When the back button is clicked, hide the current window and open a new pay_bill window
        setVisible(false);
        new pay_bill(meter);
    }

    // Main method to launch the payment_bill window
    public static void main(String[] args) {
        // Create a new instance of payment_bill with an empty string for the meter number
        new payment_bill("");
    }
}

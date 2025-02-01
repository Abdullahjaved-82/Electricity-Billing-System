package electricity.billing.system;

// Import necessary packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class extending JFrame and implementing ActionListener for GUI and event handling
public class  main_class extends JFrame implements ActionListener {
    // Variables to store account type and meter password
    String acctype;
    String meter_pass;

    // Constructor to initialize the frame
    main_class(String acctype, String meter_pass) {
        this.acctype = acctype; // Store account type
        this.meter_pass = meter_pass; // Store meter password

        // Set frame to occupy full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Load and set background image
        ImageIcon imageicon = new ImageIcon(ClassLoader.getSystemResource("icon/ebs.png"));
        Image image = imageicon.getImage().getScaledInstance(1300, 830, Image.SCALE_DEFAULT);
        JLabel imagelabel = new JLabel(new ImageIcon(image));
        add(imagelabel);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menu for administrative options
        JMenu menu = new JMenu("Menu");
        menu.setFont(new Font("serif", Font.PLAIN, 15));

        // Add "New Customer" menu item
        JMenuItem newcustomer = new JMenuItem("New Customer");
        newcustomer.setFont(new Font("monospaced", Font.PLAIN, 14));
        newcustomer.setIcon(scaleIcon("icon/newcustomer.png", 20, 20));
        newcustomer.addActionListener(this);
        menu.add(newcustomer);

        // Add "Customer Details" menu item
        JMenuItem customerdetails = new JMenuItem("Customer Details");
        customerdetails.setFont(new Font("monospaced", Font.PLAIN, 14));
        customerdetails.setIcon(scaleIcon("icon/customerDetails.png", 20, 20));
        customerdetails.addActionListener(this);
        menu.add(customerdetails);

        // Add "Calculate Bill" menu item
        JMenuItem calculatebill = new JMenuItem("Calculate Bill");
        calculatebill.setFont(new Font("monospaced", Font.PLAIN, 14));
        calculatebill.setIcon(scaleIcon("icon/calculatorbills.png", 20, 20));
        calculatebill.addActionListener(this);
        menu.add(calculatebill);

        // Add "Deposit Details" menu item
        JMenuItem depositdetails = new JMenuItem("deposit details");
        depositdetails.setFont(new Font("monospaced", Font.PLAIN, 14));
        depositdetails.setIcon(scaleIcon("icon/depositdetails.png", 20, 20));
        depositdetails.addActionListener(this);
        menu.add(depositdetails);

        // Menu for information-related options
        JMenu info = new JMenu("Information");
        info.setFont(new Font("serif", Font.PLAIN, 15));

        // Add "Update Information" menu item
        JMenuItem upinfo = new JMenuItem("Update Information");
        upinfo.setFont(new Font("monospaced", Font.PLAIN, 14));
        upinfo.setIcon(scaleIcon("icon/refresh.png", 20, 20));
        upinfo.addActionListener(this);
        info.add(upinfo);

        // Add "View Information" menu item
        JMenuItem viewInfo = new JMenuItem("View Information");
        viewInfo.setFont(new Font("monospaced", Font.PLAIN, 14));
        viewInfo.setIcon(scaleIcon("icon/information.png", 20, 20));
        viewInfo.addActionListener(this);
        info.add(viewInfo);

        // Menu for user-specific options
        JMenu user = new JMenu("User");
        user.setFont(new Font("serif", Font.PLAIN, 15));

        // Add "Pay Bill" menu item
        JMenuItem paybill = new JMenuItem("Pay Bill");
        paybill.setFont(new Font("monospaced", Font.PLAIN, 14));
        paybill.setIcon(scaleIcon("icon/pay.png", 20, 20));
        paybill.addActionListener(this);
        user.add(paybill);

        // Add "Bill Details" menu item
        JMenuItem billdetails = new JMenuItem("Bill Details");
        billdetails.setFont(new Font("monospaced", Font.PLAIN, 14));
        billdetails.setIcon(scaleIcon("icon/detail.png", 20, 20));
        billdetails.addActionListener(this);
        user.add(billdetails);

        // Menu for bill generation
        JMenu bill = new JMenu("Bill");
        bill.setFont(new Font("serif", Font.PLAIN, 15));

        // Add "Generate Bill" menu item
        JMenuItem genBill = new JMenuItem("Generate Bill");
        genBill.setFont(new Font("monospaced", Font.PLAIN, 14));
        genBill.setIcon(scaleIcon("icon/bill.png", 20, 20));
        genBill.addActionListener(this);
        bill.add(genBill);

        // Menu for utility options
        JMenu utility = new JMenu("Utility");
        utility.setFont(new Font("serif", Font.PLAIN, 15));

        // Add "Notepad" menu item
        JMenuItem notepad = new JMenuItem("Notepad");
        notepad.setFont(new Font("monospaced", Font.PLAIN, 14));
        notepad.setIcon(scaleIcon("icon/notepad.png", 20, 20));
        notepad.addActionListener(this);
        utility.add(notepad);

        // Add "Calculator" menu item
        JMenuItem calculator = new JMenuItem("Calculator");
        calculator.setFont(new Font("monospaced", Font.PLAIN, 14));
        calculator.setIcon(scaleIcon("icon/calculator.png", 20, 20));
        calculator.addActionListener(this);
        utility.add(calculator);

        // Menu for exit options
        JMenu exit = new JMenu("Exit");
        exit.setFont(new Font("serif", Font.PLAIN, 15));

        // Add "Logout" menu item
        JMenuItem eexit = new JMenuItem("Logout");
        eexit.setFont(new Font("monospaced", Font.PLAIN, 14));
        eexit.setIcon(scaleIcon("icon/exit.png", 20, 20));
        eexit.addActionListener(this);
        exit.add(eexit);

        // Add menus to the menu bar based on account type
        if (acctype.equals("Admin")) {
            menuBar.add(menu);
        } else {
            menuBar.add(bill);
            menuBar.add(user);
            menuBar.add(info);
        }

        menuBar.add(utility);
        menuBar.add(exit);

        // Set frame layout and visibility
        setLayout(new FlowLayout());
        setVisible(true);
    }

    // Helper method to scale icons
    private ImageIcon scaleIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }

    // Event handling for menu items
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = e.getActionCommand(); // Get the label of the clicked menu item
        System.out.println(msg); // Print the action for debugging

        // Perform actions based on the menu item clicked
        switch (msg) {
            case "New Customer" -> new newCustomer();
            case "Customer Details" -> new customer_details();
            case "deposit details" -> new deposit_details();
            case "Calculate Bill" -> new calculate_bill();
            case "View Information" -> new view_information(meter_pass);
            case "Update Information" -> new update_information(meter_pass);
            case "Bill Details" -> new bill_details(meter_pass);
            case "Calculator" -> runApplication("calc.exe");
            case "Notepad" -> runApplication("notepad.exe");
            case "Logout" -> {
                setVisible(false);
                new Login();
            }
            case "Generate Bill" -> new generate_bill(meter_pass);
            case "Pay Bill" -> new pay_bill(meter_pass);
        }
    }

    // Helper method to run external applications
    private void runApplication(String app) {
        try {
            Runtime.getRuntime().exec(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        new main_class("", "");
    }
}

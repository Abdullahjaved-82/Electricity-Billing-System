
package electricity.billing.system;

import javax.swing.*;
import java.awt.*;

public class splash extends JFrame {
    splash(){
        // Load the image from resources and create an ImageIcon
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/splash/Splash.jpg"));

        // Scale the image to the desired size (600x400)
        Image imageOne = imageIcon.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);

        // Create a new ImageIcon with the scaled image
        ImageIcon imageIcon2 = new ImageIcon(imageOne);

        // Create a JLabel with the ImageIcon and add it to the JFrame
        JLabel imageLabel = new JLabel(imageIcon2);
        add(imageLabel);

        // Set the size of the JFrame
        setSize(600, 400);

        // Set the location of the JFrame on the screen
        setLocation(340, 170);

        // Make the JFrame visible
        setVisible(true);

        try {
            // Pause the execution for 3 seconds
            Thread.sleep(3000);

            // Hide the splash screen
            setVisible(false);

            // Open the Login window
            new Login();
        } catch (Exception e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create an instance of the splash class to display the splash screen
        new splash();
    }
}

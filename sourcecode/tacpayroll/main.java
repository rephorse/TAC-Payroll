/**
 * 
 */
package tacpayroll;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 04/07/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: None
 * Variables:None
 * Main driver of programm. Tests to see if database is available
 * Creates a database, but can't populate it with data yet
 */

public class main {

	public static void main(String[] args) {
		//Main driver for the program
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				databaseHandler testConnection = new databaseHandler();
				if(testConnection.getSuccess()) {
					testConnection.createTable();
					testConnection.close();
					try {
						mainInterface frame = new mainInterface();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Database not available", "Error", 0);
				}
			}
		});
		
	}

}

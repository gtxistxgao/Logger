package Logger;

import java.awt.EventQueue;

public class Main {
	/**
	 * @description: Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
						Logger frame = new Logger();
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}


/*
 * open program:
 * 	1. test if file existed
 *  	if existed, load file.
 *  	if not existed, create file
 *  2. show the main setup window
 *  3. continuely show reminding window every interval time gap.
 * 
 */
package Logger;

import java.awt.EventQueue;

/*
 *     This file is part of Logger.
    Logger is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Logger is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Logger.  If not, see <http://www.gnu.org/licenses/>.
 * */
/**
 * @author Tianxiang Gao
 *
 */
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
 * open program: 1. test if file existed if existed, load file. if not existed,
 * create file 2. show the main setup window 3. continuely show reminding window
 * every interval time gap.
 * 
 */
package Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
class NoteBook {
	File folder;
	LocalDate today;
	File file;

	NoteBook() {
		folder = new File("notes");
		today = LocalDate.now();
	}

	void write(String input) {
		try {
			file = new File(folder.toString() + "\\" + today.toString() + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(input);
			bufferedWriter.write("\n");
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// return true if we have already build today's notes
	boolean isFirstTimeToday() {
		for (File file : folder.listFiles()) {
			if (file.getName().toString().equals(today.toString() + ".txt")) {
				return false;
			}
		}
		return true;
	}
	//tests
	public static void main(String[] args) {
		String input = "brvwfwef";
		NoteBook n = new NoteBook();
		System.out.println(n.isFirstTimeToday());
		n.write("good");
	}
}

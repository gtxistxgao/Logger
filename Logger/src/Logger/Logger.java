package Logger;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javazoom.jl.decoder.JavaLayerException;

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
 *
 * 2016 Sep 9, 2016 LoggerSetup.java
 * 
 * @author: Tianxiang Gao
 *
 */
public class Logger extends JFrame {
	private int intervalLength;
	private JPanel mainPane;
	private JPanel setupPane;
	private JPanel remindPane;
	private File music;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private PausablePlayer player;
	private boolean isPause = false;
	private NoteBook book;

	/**
	 * 
	 * @description: Create the frame.
	 *
	 */
	public Logger() {
		book = new NoteBook();
		// set up the main frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1314, 829);

		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(null);
		setContentPane(mainPane);

		setupPane = new JPanel();
		setupPane.setBounds(0, 0, 1292, 773);
		setupPane.setLayout(null);
		mainPane.add(setupPane);

		remindPane = new JPanel();
		remindPane.setBounds(0, 0, 1292, 773);
		remindPane.setLayout(null);
		mainPane.add(remindPane);

		setupPane.setVisible(true);
		remindPane.setVisible(false);

		setupPane();
		remindPane();
	}

	/**
	 * setup remind panel's components and their action.
	 */
	void remindPane() {

		JTextArea progressRecord = new JTextArea();
		progressRecord.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 30));
		progressRecord.setBounds(25, 79, 787, 678);
		remindPane.add(progressRecord);

		JPanel btnPanel = new JPanel();
		btnPanel.setBounds(827, 16, 450, 741);
		remindPane.add(btnPanel);

		JButton btnChangeMusic = new JButton("Change Music");
		btnChangeMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseMusic();
			}
		});
		btnChangeMusic.setBounds(36, 151, 250, 139);
		btnChangeMusic.setFont(new Font("Tahoma", Font.PLAIN, 30));

		JButton btnChangeLength = new JButton("Change Break Length");
		btnChangeLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String length = JOptionPane.showInputDialog(remindPane, "How long is the new break?");
				intervalLength = Integer.valueOf(length);
			}
		});
		btnChangeLength.setBounds(36, 362, 375, 139);
		btnChangeLength.setFont(new Font("Tahoma", Font.PLAIN, 30));

		JButton btnContinue = new JButton("Continue Work");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				book.write("----------------- After " + intervalLength + " minutes");
				book.write("Break At Time: " + LocalTime.now().toString());
				book.write(progressRecord.getText());
				book.write("--------------------------To: " + LocalTime.now().toString() + "--------------------------");
				musicStop();
				hideFrame();
				goTimer();
				showFrame();
				musicPlay();
			}
		});
		btnContinue.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnContinue.setBounds(36, 559, 375, 153);
		btnPanel.setLayout(null);
		btnPanel.add(btnChangeMusic);
		btnPanel.add(btnChangeLength);
		btnPanel.add(btnContinue);

		JButton btnPlay_1 = new JButton("Play");
		btnPlay_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicPlay();
			}
		});
		btnPlay_1.setBounds(346, 151, 65, 47);
		btnPanel.add(btnPlay_1);

		JButton btnStop_1 = new JButton("Stop");
		btnStop_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicStop();
			}
		});
		btnStop_1.setBounds(346, 243, 65, 47);
		btnPanel.add(btnStop_1);

		JLabel lblAutherTianxiangGao = new JLabel("Author: Tianxiang Gao");
		lblAutherTianxiangGao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAutherTianxiangGao.setBounds(342, 0, 108, 20);
		btnPanel.add(lblAutherTianxiangGao);

		JLabel lblWhatYouHave = new JLabel("Take a break! Take a Note!");
		lblWhatYouHave.setHorizontalAlignment(SwingConstants.CENTER);
		lblWhatYouHave.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblWhatYouHave.setBounds(25, 16, 787, 50);
		remindPane.add(lblWhatYouHave);

	}

	/**
	 * setup the setupPane and how those components are organized
	 */
	void setupPane() {
		// set up the text area located in the left side
		JTextArea txtrTodaysGoal = new JTextArea();
		txtrTodaysGoal.setBounds(15, 78, 705, 679);
		setupPane.add(txtrTodaysGoal);
		txtrTodaysGoal.setFont(new Font("Monospaced", Font.PLAIN, 25));
		// when click the button, the music will start to play
		JButton btnPlay = new JButton("Play");
		btnPlay.setBounds(806, 408, 119, 79);
		setupPane.add(btnPlay);
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 24));

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicPlay();
			}
		});
		// when click the button, the music will pause if it is playing
		// the music will resume if it is pausing.
		JButton btnPause = new JButton("Pause");
		btnPause.setBounds(940, 408, 119, 79);
		setupPane.add(btnPause);
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 24));
		// when click the button, the music will stop
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(1074, 408, 119, 79);
		setupPane.add(btnStop);
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 24));

		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicStop();
			}
		});
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicPause();
				if (isPause) {
					btnPause.setText("Resume");
				} else {
					btnPause.setText("Pause");
				}
			}
		});
		JLabel lblChooseMusicFile = new JLabel("Click Button To Choose Music File");
		lblChooseMusicFile.setBounds(753, 259, 498, 51);
		setupPane.add(lblChooseMusicFile);
		lblChooseMusicFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseMusicFile.setFont(new Font("Tahoma", Font.PLAIN, 25));

		// set up the label
		JLabel lblHowLongThe = new JLabel("Work Interval length (mins)");
		lblHowLongThe.setBounds(812, 139, 307, 31);
		setupPane.add(lblHowLongThe);
		lblHowLongThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowLongThe.setFont(new Font("Tahoma", Font.PLAIN, 25));

		JButton btnChooseMusic = new JButton("Choose Music File");
		btnChooseMusic.setBounds(806, 326, 387, 45);
		setupPane.add(btnChooseMusic);
		btnChooseMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = chooseMusic();
				lblChooseMusicFile.setText(fileName);
			}
		});
		// click this button to choose the music.
		btnChooseMusic.setFont(new Font("Tahoma", Font.PLAIN, 30));

		// set up the text field for enter how long the duration need to be.
		JTextField howLong = new JTextField();
		howLong.setBounds(1166, 131, 48, 43);
		setupPane.add(howLong);
		howLong.setFont(new Font("Tahoma", Font.PLAIN, 30));
		howLong.setColumns(5);

		JButton btnOkNowStart = new JButton("OK! Now Start Working!");
		btnOkNowStart.setBounds(763, 642, 498, 98);
		setupPane.add(btnOkNowStart);
		btnOkNowStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputLength = howLong.getText();
				intervalLength = Integer.valueOf(inputLength);
				String goal = txtrTodaysGoal.getText();
				if (book.isFirstTimeToday()) {
					book.write("----------------------------------------------------------------");
					book.write("Date: " + LocalDate.now().toString());
					book.write("Start Time: " + LocalTime.now().toString());
					book.write("--------------------------Today's goal--------------------------");
				}
				book.write(goal);
				hideSetupPane();
				musicStop();
				hideFrame();
				book.write("--------------------------Start From: " + LocalTime.now().toString() + "--------------------------");
				goTimer();
				showFrame();
				showRemindPane();
				musicPlay();
			}
		});
		btnOkNowStart.setFont(new Font("Tahoma", Font.PLAIN, 30));

		JLabel lblAuthorTianxiangGao = new JLabel("Author: Tianxiang Gao");
		lblAuthorTianxiangGao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAuthorTianxiangGao.setBounds(1166, 16, 108, 20);
		setupPane.add(lblAuthorTianxiangGao);

		JLabel lblTodaysGoal = new JLabel("Today's Goal");
		lblTodaysGoal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTodaysGoal.setFont(new Font("Inconsolata", Font.PLAIN, 35));
		lblTodaysGoal.setBounds(15, 16, 705, 43);
		setupPane.add(lblTodaysGoal);

	}

	void goTimer() {
		try {
			Thread.sleep(intervalLength * 60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description: setup the music player.
	 *
	 */
	void setupMusicPlayer() {
		try {
			fis = new FileInputStream(this.music);
			bis = new BufferedInputStream(fis);
			player = new PausablePlayer(bis);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Not Found Music File!");
			e1.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// return the new music file's name
	private String chooseMusic() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Choose Your Music");
		fc.setFont(new Font("Monospaced", Font.PLAIN, 25));
		fc.setBounds(100, 100, 1314, 829);
		fc.setMultiSelectionEnabled(false);
		fc.setCurrentDirectory(new File("A:\\Music\\"));
		int i = fc.showOpenDialog(mainPane);
		if (i == JFileChooser.APPROVE_OPTION) {
			this.music = fc.getSelectedFile();
		}
		return this.music.getName();
	}

	/**
	 * 
	 * @description: start to play the music from the beginning.
	 *
	 */
	private void musicPlay() {
		setupMusicPlayer();
		try {
			this.player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description: pause the music if it is playing resume the music if it is
	 *               pausing
	 *
	 */
	private void musicPause() {
		if (this.isPause) {
			player.resume();
			this.isPause = false;
		} else {
			player.pause();
			this.isPause = true;
		}
	}

	/**
	 * 
	 * @description: stop the music
	 *
	 */
	private void musicStop() {
		player.stop();
	}

	private void hideFrame() {
		this.setVisible(false);
	}

	private void showFrame() {
		this.setVisible(true);
	}

	private void hideSetupPane() {
		setupPane.setVisible(false);
	}

	private void showSetupPane() {
		setupPane.setVisible(true);
	}

	private void hideRemindPane() {
		remindPane.setVisible(false);
	}

	private void showRemindPane() {
		remindPane.setVisible(true);
	}

	int getIntervalLength() {
		return intervalLength;
	}

	void setIntervalLength(int intervalLength) {
		this.intervalLength = intervalLength;
	}
}

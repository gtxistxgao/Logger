package Logger;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * 2016 Sep 9, 2016 LoggerSetup.java
 * 
 * @author: Tianxiang Gao
 *
 */
public class Logger extends JFrame {
	private int intervalLength;
	private JPanel setupPane;
	private JTextField howLong;
	private JLabel lblHowLongThe;
	private JLabel lblChooseMusicFile;
	private JButton btnChooseMusic;
	private JButton btnOkNowStart;
	private JTextArea txtrTodaysGoal;
	private File music;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private PausablePlayer player;
	private boolean isPause = false;
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnStop;
	private JPanel remindPane;
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
						int length = frame.getIntervalLength();
						System.out.println(length);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 
	 * @description: Create the frame.
	 *
	 */
	public Logger() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1314, 829);
		setupPaneInitialize();
		setContentPane(setupPane);
//		setupPane.setVisible(false);
//		remindPane = new JPanel();
//		remindPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(remindPane);
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

	/**
	 * 
	 * @description: this function will build the frame and arrange the
	 *               relations between those components.
	 *
	 */
	void setupPaneInitialize() {
		// set up the main frame
		setupPane = new JPanel();
		setupPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// set up the text area located in the left side
		txtrTodaysGoal = new JTextArea();
		txtrTodaysGoal.setBounds(34, 35, 707, 696);
		txtrTodaysGoal.setText("Today's Goal");
		txtrTodaysGoal.setFont(new Font("Monospaced", Font.PLAIN, 25));

		// set up the label
		lblHowLongThe = new JLabel("Work Interval length (mins)");
		lblHowLongThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowLongThe.setBounds(789, 114, 377, 49);
		lblHowLongThe.setFont(new Font("Tahoma", Font.PLAIN, 25));

		// set up the text field for enter how long the duration need to be.
		howLong = new JTextField();
		howLong.setBounds(1201, 113, 63, 50);
		howLong.setFont(new Font("Tahoma", Font.PLAIN, 30));
		howLong.setColumns(5);

		lblChooseMusicFile = new JLabel("Click Button To Choose Music File");
		lblChooseMusicFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseMusicFile.setBounds(789, 230, 458, 76);
		lblChooseMusicFile.setFont(new Font("Tahoma", Font.PLAIN, 25));

		btnChooseMusic = new JButton("Choose Music File");
		btnChooseMusic.setBounds(789, 350, 458, 64);
		btnChooseMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose Your Music");
				fc.setFont(new Font("Monospaced", Font.PLAIN, 25));
				fc.setBounds(100, 100, 1314, 829);
				fc.setMultiSelectionEnabled(false);
				fc.setCurrentDirectory(new File("A:\\Music\\"));
				int i = fc.showOpenDialog(setupPane);
				if (i == JFileChooser.APPROVE_OPTION) {
					music = fc.getSelectedFile();
					String fileName = music.getName();
					lblChooseMusicFile.setText(fileName);
				}
			}
		});
		// click this button to choose the music.
		btnChooseMusic.setFont(new Font("Tahoma", Font.PLAIN, 30));
		// when click the button, the music will start to play
		btnPlay = new JButton("Play");
		btnPlay.setBounds(789, 457, 120, 86);
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupMusicPlayer();
				musicPlay();
			}
		});
		// when click the button, the music will pause if it is playing
		// the music will resume if it is pausing.
		btnPause = new JButton("Pause");
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicPause();
			}
		});
		btnPause.setBounds(965, 457, 120, 86);
		setupPane.add(btnPause);
		// when click the button, the music will stop
		btnStop = new JButton("Stop");
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicStop();
			}
		});
		btnStop.setBounds(1127, 457, 120, 86);
		setupPane.add(btnStop);

		btnOkNowStart = new JButton("OK! Now Start Working!");
		btnOkNowStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputLength = howLong.getText();
				intervalLength = Integer.valueOf(inputLength);
//				System.out.println(intervalLength);
				hideFrame();
			}
		});
		btnOkNowStart.setBounds(789, 628, 458, 103);
		btnOkNowStart.setFont(new Font("Tahoma", Font.PLAIN, 30));
		setupPane.setLayout(null);
		setupPane.add(txtrTodaysGoal);
		setupPane.add(btnPlay);
		setupPane.add(lblChooseMusicFile);
		setupPane.add(lblHowLongThe);
		setupPane.add(btnChooseMusic);
		setupPane.add(howLong);
		setupPane.add(btnOkNowStart);
	}

	/**
	 * 
	 * @description: start to play the music from the beginning.
	 *
	 */
	void musicPlay() {
		try {
			player.play();
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
	void musicPause() {
		if (this.isPause) {
			player.resume();
			this.isPause = false;
			btnPause.setText("Pause");
		} else {
			player.pause();
			btnPause.setText("Resume");
			this.isPause = true;
		}
	}

	/**
	 * 
	 * @description: stop the music
	 *
	 */
	void musicStop() {
		player.stop();
	}

	void hideFrame() {
		this.setVisible(false);
	}

	void showFrame() {
		this.setVisible(true);
	}

	int getIntervalLength() {
		return intervalLength;
	}

	void setIntervalLength(int intervalLength) {
		this.intervalLength = intervalLength;
	}

	JTextArea getTxtrTodaysGoal() {
		return txtrTodaysGoal;
	}

	File getMusic() {
		return music;
	}

	void setMusic(File music) {
		this.music = music;
	}

	PausablePlayer getPlayer() {
		return player;
	}

	void setPlayer(PausablePlayer player) {
		this.player = player;
	}
}

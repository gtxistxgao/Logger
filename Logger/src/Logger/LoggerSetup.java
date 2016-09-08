package Logger;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class LoggerSetup extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoggerSetup frame = new LoggerSetup();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoggerSetup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1314, 829);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		txtrTodaysGoal = new JTextArea();
		txtrTodaysGoal.setBounds(34, 35, 707, 696);
		txtrTodaysGoal.setText("Today's Goal");
		txtrTodaysGoal.setFont(new Font("Monospaced", Font.PLAIN, 25));

		lblHowLongThe = new JLabel("Work Interval length (mins)");
		lblHowLongThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowLongThe.setBounds(789, 114, 377, 49);
		lblHowLongThe.setFont(new Font("Tahoma", Font.PLAIN, 25));

		textField = new JTextField();
		textField.setBounds(1201, 113, 63, 50);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		textField.setColumns(5);


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
				int i = fc.showOpenDialog(contentPane);
				if (i == JFileChooser.APPROVE_OPTION) {
					music = fc.getSelectedFile();
					String fileName = music.getName();
					lblChooseMusicFile.setText(fileName);
				}
			}
		});
		btnChooseMusic.setFont(new Font("Tahoma", Font.PLAIN, 30));

		btnPlay = new JButton("Play");
		btnPlay.setBounds(789, 457, 120, 86);
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupMusicPlayer();
				musicPlay();
			}
		});

		btnPause = new JButton("Pause");
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicPause();
			}
		});
		btnPause.setBounds(965, 457, 120, 86);
		contentPane.add(btnPause);
		
		btnStop = new JButton("Stop");
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				musicStop();
			}
		});
		btnStop.setBounds(1127, 457, 120, 86);
		contentPane.add(btnStop);
		
		
		btnOkNowStart = new JButton("OK! Now Start Working!");
		btnOkNowStart.setBounds(789, 628, 458, 103);
		btnOkNowStart.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.setLayout(null);
		contentPane.add(txtrTodaysGoal);
		contentPane.add(btnPlay);
		contentPane.add(lblChooseMusicFile);
		contentPane.add(lblHowLongThe);
		contentPane.add(btnChooseMusic);
		contentPane.add(textField);
		contentPane.add(btnOkNowStart);
	}

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

	void musicPlay() {
		try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void musicPause() {
		if(this.isPause){
			player.resume();
			this.isPause = false;
			btnPause.setText("Pause");
		}else{
			player.pause();
			btnPause.setText("Resume");
			this.isPause = true;
		}
	}

	void musicStop() {
		player.stop();
	}
}

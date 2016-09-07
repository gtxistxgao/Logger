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
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JSeparator;

public class LoggerSetup extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblHowLongThe;
	private JLabel lblChooseMusicFile;
	private JButton btnChooseMusic;
	private JButton btnOkNowStart;
	private JTextArea txtrTodaysGoal;
	private File music;
	private AdvancedPlayer player;
	private FileInputStream fis;
	private BufferedInputStream bis;
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
		setBounds(100, 100, 1357, 824);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblHowLongThe = new JLabel("How long the time interval need to be?");
		lblHowLongThe.setFont(new Font("Tahoma", Font.PLAIN, 30));

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		textField.setColumns(5);

		lblChooseMusicFile = new JLabel("Choose Music File");
		lblChooseMusicFile.setFont(new Font("Tahoma", Font.PLAIN, 30));

		btnChooseMusic = new JButton("Choose Music File");
		btnChooseMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose Your Music");
				fc.setFont(new Font("Monospaced", Font.PLAIN, 25));
				int i = fc.showOpenDialog(lblChooseMusicFile);
				if (i == JFileChooser.APPROVE_OPTION) {
					music = fc.getSelectedFile();
					String fileName = music.getName();
					lblChooseMusicFile.setText(fileName);
				}
			}
		});
		btnChooseMusic.setFont(new Font("Tahoma", Font.PLAIN, 30));

		txtrTodaysGoal = new JTextArea();
		txtrTodaysGoal.setText("Today's Goal");
		txtrTodaysGoal.setFont(new Font("Monospaced", Font.PLAIN, 25));

		btnOkNowStart = new JButton("OK! Now Start Working!");
		btnOkNowStart.setFont(new Font("Tahoma", Font.PLAIN, 30));

		JButton btnPlaytest = new JButton("PlayTest");
		btnPlaytest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupMusicPlayer();
				play();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(29)
				.addComponent(txtrTodaysGoal, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane
								.createSequentialGroup().addGap(111).addGroup(gl_contentPane
										.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnChooseMusic, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnOkNowStart, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 381,
												Short.MAX_VALUE)
										.addComponent(lblChooseMusicFile, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
												381, GroupLayout.PREFERRED_SIZE))
								.addGap(68).addComponent(btnPlaytest))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(56)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 564,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblHowLongThe))))
				.addContainerGap(1037, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(30)
								.addComponent(txtrTodaysGoal, GroupLayout.PREFERRED_SIZE, 696,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(32, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(133).addComponent(lblHowLongThe)
								.addGap(50)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addGap(57).addComponent(lblChooseMusicFile).addGap(61)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnPlaytest, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnChooseMusic, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
								.addComponent(btnOkNowStart, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
								.addGap(67)));
		contentPane.setLayout(gl_contentPane);
	}
	
	void setupMusicPlayer(){
		try{
			this.music = music;
			fis = new FileInputStream(this.music);
			bis = new BufferedInputStream(fis);
			try{
				player = new AdvancedPlayer(bis);
			}catch(JavaLayerException e){
				e.printStackTrace();
				System.out.println("Failed to play the file.");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	void play(){
		try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void stop(){
		player.close();
	}
}

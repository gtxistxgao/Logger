package Logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Logger {
	private String music;
	private int INTERVAL_LENGTH;
	private File logTodayPath;
	
	private JFrame settingUpWindow;
	private JTextArea dailyGoalTextArea;
	private JButton saveAndStartButton;
	private JButton openSong;
	private JLabel songName;
	private JTextField howLongTheIntervalWillbe;
	private JLabel intervalLength;
	private JPanel leftPanel;
	private int screenHeight;
	private int screenWidth;

	Logger() {
		buildAndArrangePanel();
	}

	private void buildAndArrangePanel() {
		setupFrame();
		setupLeftPanel();
		setupSaveAndStartButton();
		setupDailyGoalArea();
		
		settingUpWindow.add(saveAndStartButton, BorderLayout.SOUTH);
		settingUpWindow.add(leftPanel, BorderLayout.WEST);
		settingUpWindow.add(dailyGoalTextArea, BorderLayout.EAST);
		
		//set all components as visible.
		leftPanel.setVisible(true);
		dailyGoalTextArea.setVisible(true);
		saveAndStartButton.setVisible(true);
		settingUpWindow.setVisible(true);
	}

	private void setupDailyGoalArea() {
		dailyGoalTextArea = new JTextArea(25, 23);
		dailyGoalTextArea.setText("Today's goal\n");
		dailyGoalTextArea.setFont(new Font("Courier", Font.BOLD,50));
		dailyGoalTextArea.setBackground(Color.decode("#006699"));
		dailyGoalTextArea.setForeground(Color.white);
	}

	private void setupSaveAndStartButton() {
		saveAndStartButton = new JButton("OK Let's start working!");
		saveAndStartButton.setFont(new Font("Courier", Font.BOLD,30));
		saveAndStartButton.setLayout(null);
	}

	private void setupLeftPanel() {
		leftPanel = new JPanel(new GridBagLayout());

		intervalLength = new JLabel("Take a break by following minutes time interval");
		intervalLength.setFont(new Font("Courier", Font.BOLD,30));
		intervalLength.setLayout(null);
		leftPanel.add(intervalLength);
		
		howLongTheIntervalWillbe = new JTextField(5);
		howLongTheIntervalWillbe.setLayout(null);
		howLongTheIntervalWillbe.setHorizontalAlignment(JTextField.CENTER);
		howLongTheIntervalWillbe.setFont(new Font("Courier", Font.BOLD,30));
		leftPanel.add(howLongTheIntervalWillbe);
		
		songName = new JLabel("Click Open Music File Button to Chose a Song");
		songName.setFont(new Font("Courier", Font.BOLD,30));
		songName.setLayout(null);
		leftPanel.add(songName);
		
		openSong = new JButton("Open Music File");
		openSong.setFont(new Font("Courier", Font.BOLD,30));
		openSong.setLayout(null);
		leftPanel.add(openSong);
		
		leftPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		leftPanel.setLayout (new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
	}

	private void setupFrame() {
		// add the main frame and set up the configurations like size and
		// location
		settingUpWindow = new JFrame();
		// make the window as half of the screen size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = screen.height / 2;
		screenWidth = screen.width / 2;
		settingUpWindow.setSize(new Dimension(screenWidth, screenHeight));
		// make the window in the center of the screen
		settingUpWindow.setLocationRelativeTo(null);
	}

	private void setUpSaveButtom() {
		
	}

	public static void main(String[] args) {
		Logger l = new Logger();
	}
}
package MultiThreadingLearning;


//Import the Java classes
import java.io.FileInputStream;
import java.io.InputStream;

//Import the JLayer classes
import javazoom.jl.player.*;

/**
 *
 * @author shaines
 */
public class MP3Player {

	private Player player;
	private InputStream is;

	/** Creates a new instance of MP3Player */
	public MP3Player(String filename) {
		try {
			// Create an InputStream to the file
			is = new FileInputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			player = new Player(is);
			PlayerThread pt = new PlayerThread();
			pt.start();
			while (!player.isComplete()) {
				int position = player.getPosition();
				System.out.println("Position: " + position);
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class PlayerThread extends Thread {
		public void run() {
			try {
				player.play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
//		if (args.length == 0) {
//			System.out.println("Usage: MP3Player <filename>");
//			System.exit(0);
//		}

		MP3Player mp3Player = new MP3Player("A:\\Music\\周杰伦\\魔杰座\\02 - 给我一首歌的时间.mp3");
		mp3Player.play();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
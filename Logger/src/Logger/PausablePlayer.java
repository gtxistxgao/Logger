package Logger;

import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

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
 * @author Roman C
 * 
 * @User Tianxiang Gao: I find this codes from stackoverflow.
 *       http://stackoverflow.com/questions/12057214/jlayer-pause-and-resume-song
 * 
 *       this part is what I hope. So I use them. Because I used JLAYER which is
 *       under LGPL licence, if this codes are not permitted to use, please
 *       contact me: zjcxgaoxing@gmail.com. I will remove them.
 *
 */
class PausablePlayer {

	private final static int NOTSTARTED = 0;
	private final static int PLAYING = 1;
	private final static int PAUSED = 2;
	private final static int FINISHED = 3;

	// the player actually doing all the work
	private final Player player;

	// locking object used to communicate with player thread
	private final Object playerLock = new Object();

	// status variable what player thread is doing/supposed to do
	private int playerStatus = NOTSTARTED;

	PausablePlayer(final InputStream inputStream) throws JavaLayerException {
		this.player = new Player(inputStream);
	}

	PausablePlayer(final InputStream inputStream, final AudioDevice audioDevice) throws JavaLayerException {
		this.player = new Player(inputStream, audioDevice);
	}

	/**
	 * Starts playback (resumes if paused)
	 */
	void play() throws JavaLayerException {
		synchronized (playerLock) {
			switch (playerStatus) {
			case NOTSTARTED:
				final Runnable r = new Runnable() {
					public void run() {
						playInternal();
					}
				};
				final Thread t = new Thread(r);
				t.setDaemon(true);
				t.setPriority(Thread.MAX_PRIORITY);
				playerStatus = PLAYING;
				t.start();
				break;
			case PAUSED:
				resume();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Pauses playback. Returns true if new state is PAUSED.
	 */
	boolean pause() {
		synchronized (playerLock) {
			if (playerStatus == PLAYING) {
				playerStatus = PAUSED;
			}
			return playerStatus == PAUSED;
		}
	}

	/**
	 * Resumes playback. Returns true if the new state is PLAYING.
	 */
	boolean resume() {
		synchronized (playerLock) {
			if (playerStatus == PAUSED) {
				playerStatus = PLAYING;
				playerLock.notifyAll();
			}
			return playerStatus == PLAYING;
		}
	}

	/**
	 * Stops playback. If not playing, does nothing
	 */
	void stop() {
		synchronized (playerLock) {
			playerStatus = FINISHED;
			playerLock.notifyAll();
		}
	}

	private void playInternal() {
		while (playerStatus != FINISHED) {
			try {
				if (!player.play(1)) {
					break;
				}
			} catch (final JavaLayerException e) {
				break;
			}
			// check if paused or terminated
			synchronized (playerLock) {
				while (playerStatus == PAUSED) {
					try {
						playerLock.wait();
					} catch (final InterruptedException e) {
						// terminate player
						break;
					}
				}
			}
		}
		close();
	}

	/**
	 * Closes the player, regardless of current state.
	 */
	void close() {
		synchronized (playerLock) {
			playerStatus = FINISHED;
		}
		try {
			player.close();
		} catch (final Exception e) {
			// ignore, we are terminating anyway
		}
	}

	// demo how to use
	public static void main(String[] argv) {
		try {
			FileInputStream input = new FileInputStream("A:\\Music\\周杰伦\\魔杰座\\02 - 给我一首歌的时间.mp3");
			PausablePlayer player = new PausablePlayer(input);

			// start playing
			player.play();

			// after 5 secs, pause
			Thread.sleep(5000);
			player.pause();

			// after 5 secs, resume
			Thread.sleep(5000);
			player.resume();
			// after 5 secs, stop
			Thread.sleep(5000);
			player.close();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
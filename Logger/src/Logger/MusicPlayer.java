package Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

class MusicPlayer extends Thread{
	private AdvancedPlayer player;
	private File music;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private boolean isPlay = false;
	MusicPlayer(File music){
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
	@Override
	public void run() {
		play();
		while(){
		}
		player.close();
	}
	void setMusic(File music){
		this.music = music;
	}
	void play(){
		try{
			this.isPlay = true;
			player.play();
			System.out.println("passed");
		}catch(JavaLayerException e){
			e.printStackTrace();
		}
	}
	public void stop(){
		this.isPlay = false;
	}
	@Override
	public void intrrupt(){
		
	}
	public static void main(String[] args) throws InterruptedException{
		File song = new File("A:\\Music\\周杰伦\\魔杰座\\02 - 给我一首歌的时间.mp3");
		Thread aa = new Thread(new MusicPlayer(song));
		aa.start();
		System.out.println("start to play");
		System.out.println("after");
	}
}

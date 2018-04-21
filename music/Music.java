package music;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	private Clip clip;

	public void startMusic() {
		class MusicThread extends Thread {

			public void run() {
				play();
				start();
			}

			public void play() {
				try {
					File file = new File("src/Resources/waifuRamble.wav");
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();
					Thread.sleep(clip.getMicrosecondLength());
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}

		MusicThread mt = new MusicThread();
		mt.start();
	}

	public void stopMusic() {
		clip.close();
	}
}

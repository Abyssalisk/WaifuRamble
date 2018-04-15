package music;

import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicTest {
	public static void main(String[] args){
		play();
	}

	public static void play() {
		try {
			File file = new File("src/give.wav"); // accepted formats: Audio file formats: AIFF, AU and WAV
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
			Thread.sleep(clip.getMicrosecondLength());
			} catch (Exception e) { 
				System.err.println(e.getMessage()); }
		}
}

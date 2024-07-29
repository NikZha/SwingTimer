package swingtimer;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SwingPlay {
	private static String path = "C:\\Windows\\Media\\Alarm01.wav";

	public static void setPath(String path) {
		SwingPlay.path = path;
	}

	public static void PlaySound()
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		File soundFile = new File(path);

		AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

		Clip clip = AudioSystem.getClip();

		clip.open(ais);

		clip.setFramePosition(0);
		clip.start();

		Thread.sleep(clip.getMicrosecondLength() / 1000);
		clip.stop();
		clip.close();

	}

	public static void main(String[] args)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		String b = "C:\\Windows\\Media\\Alarm01.wav";
		setPath(b);
		PlaySound();
	}
}

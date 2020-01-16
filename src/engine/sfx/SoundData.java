package engine.sfx;

import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundData {


	private Clip clip = null;
	private FloatControl gainControl;
	
	public SoundData(String path) {

		try {
			InputStream audioSrc;
			if(System.getProperty("os.name").contains("Windows")){
				audioSrc =  new FileInputStream(new File(System.getProperty("user.dir") +  File.separator + "res"  + path));

			}else {
				audioSrc = SoundData.class.getResourceAsStream(path);
			}

			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, 
					baseFormat.getSampleRate(),
					16, 
					baseFormat.getChannels(), 
					baseFormat.getChannels() *2,
					baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

	}
	public Clip getClip() {
		return clip;
	}

	public FloatControl getGainControl() {
		return gainControl;
	}
}

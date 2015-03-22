package animus;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat.*;
import java.io.*;

public class AudioManager
{
  Clip clips[];
  

	public AudioManager(String[] filenames){
		super();

    clips = new Clip[filenames.length];

    for(int i=0; i<filenames.length; i++) {
      try {
        File file = new File(filenames[i]);
        clips[i] = buildClip(file);
      } catch (Exception e) {
        System.err.println(e);
      }
    }

	}

  public boolean isPlaying() {
    for(int i=0; i<clips.length; i++) {
      if(clips[i].getMicrosecondLength() != clips[i].getMicrosecondPosition()) return true;
    }
    return false;
  }

  private Clip buildClip(File file) throws Exception {
    AudioInputStream ais = AudioSystem.getAudioInputStream(file);
    Clip c = AudioSystem.getClip();
    c.open(ais);
    c.setFramePosition(0);

    return c;
  }

  public void setGain(int index, float gain) {
    Clip c = clips[index];
    FloatControl gainCtrl = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
    gainCtrl.setValue(gain);
  }

  public Clip playSound(int index, float gain) {
    Clip c = clips[index];

    FloatControl gainCtrl = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
    gainCtrl.setValue(gain);

    c.start();
    return c;
  }

}


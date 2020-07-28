package Game.music;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundLoader {

    public static File sound1;
    public static File sound2;
    Clip clip;
    public void loadclear(){
        sound1 = new File("20/Blockgame/Game/music/Clearrow.wav");
    }
    public void loadmusic(){
        sound2 = new File("20/Blockgame/Game/music/BlockgameMusic.wav");
    }
    public void play(File sound){
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        }catch (Exception e){
        e.printStackTrace();
        }
    }
    public void playloop(File sound){
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void stopsound(File sound){
        clip.stop();
    }

}



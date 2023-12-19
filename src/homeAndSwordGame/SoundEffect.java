package homeAndSwordGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.HashMap;
import java.util.Objects;

public enum SoundEffect {

    SQUICK_SQUICK("audios/dogToy.wav",0.1f),
    DOOR_OPENING("audios/doorOpening.wav",0.4f),
    SKELETON_DEATH("audios/skeletonDeath.wav",0.6f),
    MAIN_MUSIC("audios/mainMusic.wav",0.5f),
    BASIC_ATTACK("audios/basicAttack.wav"),
    ENEMY_HURT("audios/ennemieHurt2.wav",0.1f),
    MURLOC("audios/murloc.wav");

    private String path;
    private float volume = 1f;
    private HashMap<String ,Clip> loopedAudio = new HashMap<>();

    SoundEffect(String path){
        this.path = path;
    }

    SoundEffect(String path, float volume){
        this.path = path;
        this.volume = volume;
    }

    public void play() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(path)));
            clip.open(stream);
            if (volume != 1f) {
                setVolume(clip, volume);
            }
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        Clip detectclip = loopedAudio.get(path);
        if (detectclip != null){
            return;
        }

        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(path)));
            clip.open(stream);
            setVolume(clip,volume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            loopedAudio.put(path,clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLoop(){
        Clip clip = loopedAudio.get(path);
        clip.stop();
        clip.close();
        loopedAudio.remove(path);
    }

    private void setVolume(Clip clip, float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

}

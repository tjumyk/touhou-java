package org.tjuscs.bulletgame.view.component;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {
	private boolean muted;
	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean isMuted) {
		this.muted = isMuted;
	}

	private AudioPlayer() {
		String osName = System.getProperty("os.name");
		if("Windows 8".equalsIgnoreCase(osName))
			setMuted(true);
	}

	private static AudioPlayer instance;

	public static AudioPlayer getInstance() {
		if (instance == null)
			instance = new AudioPlayer();
		return instance;
	}

	public void PlaySound(String soundname, double volume) {
		if(muted)
			return;
		Sound sound = Resources.getInstance().sounds.get(soundname);
		sound.play((float) volume);
	}

	public void PlayMusic(String musicname, double volume) {
		if(muted)
			return;
		for(Music m: Resources.getInstance().musics.values()){
			m.stop();
		}
		Music music = Resources.getInstance().musics.get(musicname);
		music.setVolume((float) volume);
		music.play();
	}

	public void PauseMusic(String musicname) {
		Music music = Resources.getInstance().musics.get(musicname);
		music.pause();
	}

	public void ResumeMusic(String musicname) {
		if(muted)
			return;
		Music music = Resources.getInstance().musics.get(musicname);
		music.play();
	}

	public void StopMusic(String musicname) {
		Music music = Resources.getInstance().musics.get(musicname);
		music.stop();
	}

	public double GetMusicPosition(String musicname) {
		Music music = Resources.getInstance().musics.get(musicname);
		return music.getPosition();
	}

	public boolean IsMusicPlaying(String musicname) {
		Music music = Resources.getInstance().musics.get(musicname);
		return music.isPlaying();
	}

}

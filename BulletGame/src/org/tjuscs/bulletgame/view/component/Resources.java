package org.tjuscs.bulletgame.view.component;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Resources {
	private Resources() {
	}

	private static Resources instance;

	public static Resources getInstance() {
		if (instance == null)
			instance = new Resources();
		return instance;
	}

	public Map<String, Image> images = new HashMap<String, Image>();
	public Map<String, Animation> anims = new HashMap<String, Animation>();
	public Map<String, ParticleEffectPool> particlePools = new HashMap<String, ParticleEffectPool>();
	public Map<String, PooledEffect> particles = new HashMap<String, PooledEffect>();
	public Map<String, BitmapFont> bitmapFonts = new HashMap<String, BitmapFont>();
	public Map<String, Texture> textures = new HashMap<String, Texture>();
	public Map<String, Sound> sounds = new HashMap<String, Sound>();
	public Map<String, Music> musics = new HashMap<String, Music>();

	public void Clear(){
		images.clear();
		anims.clear();
		particlePools.clear();
		particles.clear();
		bitmapFonts.clear();
		textures.clear();
		sounds.clear();
		musics.clear();
		System.gc();
	}
	public void LoadTexture(String texturename, String filename) {
		LoadTexture(texturename, filename, false);
	}

	public void LoadTexture(String texturename, String filename, boolean mipmap) {
		Texture tex = new Texture(Gdx.files.internal(filename));
		// if (mipmap)
		// tex.setFilter(TextureFilter.MipMapLinearNearest,
		// TextureFilter.MipMapLinearNearest);
		textures.put(texturename, tex);
	}

	public void LoadImage(String imagename, String texturename, int x, int y,
			int w, int h) {
		LoadImage(imagename, texturename, x, y, w, h, 0, 0, false);
	}

	public void LoadImage(String imagename, String texturename, int x, int y,
			int w, int h, int a, int b) {
		LoadImage(imagename, texturename, x, y, w, h, a, b, false);
	}

	public void LoadImage(String imagename, String texturename, int x, int y,
			int w, int h, int a, int b, boolean rect) {
		Image region = new Image(textures.get(texturename), x, y, w, h);
		region.setCollideGeometry(a, b, rect);
		images.put(imagename, region);
	}

	public void LoadImageFromFile(String teximgname, String filename) {
		LoadImageFromFile(teximgname, filename, false);
	}

	public void LoadImageFromFile(String teximgname, String filename,
			boolean mipmap) {
		LoadTexture(teximgname, filename, mipmap);
		Rectangle rect = GetTextureSize(teximgname);
		LoadImage(teximgname, teximgname, 0, 0, (int) rect.width,
				(int) rect.height);
	}

	public void LoadImageGroup(String imagenameprefix, String texturename,
			int x, int y, int w, int h, int n, int m) {
		LoadImageGroup(imagenameprefix, texturename, x, y, w, h, n, m, 0, 0,
				false);
	}

	public void LoadImageGroup(String imagenameprefix, String texturename,
			int x, int y, int w, int h, int n, int m, int a, int b) {
		LoadImageGroup(imagenameprefix, texturename, x, y, w, h, n, m, a, b,
				false);
	}

	public void LoadImageGroup(String imagenameprefix, String texturename,
			int x, int y, int w, int h, int n, int m, int a, int b, boolean rect) {
		Texture t = textures.get(texturename);
		int ew = w, eh = h;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				Image region = new Image(t, x + i * ew, y + j * eh, ew, eh);
				region.setCollideGeometry(a, b, rect);
				images.put(imagenameprefix + (i + j * n + 1), region);
			}
		}
	}
	
	public void LoadImageGroupFromFile(String teximgname, String filename,
			boolean mipmap, int n, int m, int a, int b){
		LoadImageGroupFromFile(teximgname, filename, mipmap, n, m, a, b,false);
	}
	
	public void LoadImageGroupFromFile(String teximgname, String filename,
			boolean mipmap, int n, int m, int a, int b, boolean rect) {
		LoadTexture(teximgname, filename, mipmap);
		Rectangle rec = GetTextureSize(teximgname);
		LoadImageGroup(teximgname, teximgname, 0, 0, (int) rec.width,
				(int) rec.height, n, m, a, b, rect);
	}

	public void LoadAnimation(String animationname, String texturename, int x,
			int y, int w, int h, int n, int m, int intv) {
		LoadAnimation(animationname, texturename, x, y, w, h, n, m, intv, 0, 0,
				false);
	}

	public void LoadAnimation(String animationname, String texturename, int x,
			int y, int w, int h, int n, int m, int intv, int a, int b) {
		LoadAnimation(animationname, texturename, x, y, w, h, n, m, intv, a, b,
				false);
	}

	public void LoadAnimation(String animationname, String texturename, int x,
			int y, int w, int h, int n, int m, int intv, int a, int b,
			boolean rect) {
		Animation anim = new Animation();
		LoadImageGroup(animationname, texturename, x, y, w, h, n, m);
		for (int i = 1; i <= n * m; i++) {
			anim.keyFrames.add(images.get(animationname + i));
		}
		anim.intv = intv;
		anim.setCollideGeometry(a, b, rect);
		anims.put(animationname, anim);
	}

	public void LoadAniFromFile(String texaniname, String filename,
			boolean mipmap, int n, int m, int intv, int a, int b) {
		LoadAniFromFile(texaniname, filename, mipmap, n, m, intv, a, b, false);
	}

	public void LoadAniFromFile(String texaniname, String filename,
			boolean mipmap, int n, int m, int intv) {
		LoadAniFromFile(texaniname, filename, mipmap, n, m, intv, 0, 0, false);
	}

	public void LoadAniFromFile(String texaniname, String filename,
			boolean mipmap, int n, int m, int intv, int a, int b, boolean rect) {
		LoadTexture(texaniname, filename);
		Rectangle rec = GetTextureSize(texaniname);
		LoadAnimation(texaniname, texaniname, 0, 0, (int) rec.width,
				(int) rec.height, n, m, intv, a, b, rect);
	}

	public void LoadPSPool(String particlepoolname, String filename,
			String imagename) {
		Image img = images.get(imagename);
		ParticleEffect pe = new ParticleEffect();
		TextureAtlas atlas = new TextureAtlas();
		atlas.addRegion(imagename, img);
		pe.load(Gdx.files.internal(filename), atlas);
		ParticleEffectPool pool = new ParticleEffectPool(pe, 1, 10);
		particlePools.put(particlepoolname, pool);
	}

	public void LoadPS(String particlepoolname, String particlename) {
		ParticleEffectPool pool = particlePools.get(particlepoolname);
		particles.put(particlename, pool.obtain());
	}

	public void LoadFont(String fontname, String filename) {
		BitmapFont font = new BitmapFont(Gdx.files.internal(filename), false);
		bitmapFonts.put(fontname, font);
	}

	public Rectangle GetTextureSize(String texturename) {
		Texture tex = textures.get(texturename);
		return new Rectangle(0, 0, tex.getWidth(), tex.getHeight());
	}

	public void LoadSound(String soundname, String filename) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(filename));
		sounds.put(soundname, sound);
	}

	public void LoadMusic(String musicname, String filename) {
		Music music = Gdx.audio.newMusic(Gdx.files.internal(filename));
		music.setLooping(true);
		musics.put(musicname, music);
	}

	public void SetImageCenter(String name, int cx, int cy) {
		Image img = images.get(name);
		img.setCenter(cx, cy);
	}

	public void SetImageState(String name, String blendmode, double r,
			double g, double b, double a) {
		Image img = images.get(name);
		img.color = new Color((float) r/255, (float) g/255, (float) b/255, (float) a/255);
	}
}

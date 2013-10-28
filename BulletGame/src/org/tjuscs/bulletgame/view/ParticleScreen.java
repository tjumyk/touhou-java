package org.tjuscs.bulletgame.view;

import org.tjuscs.bulletgame.model.GameModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ParticleScreen implements Screen {
	GameModel model;
	ParticleEffectPool EffectPool;
	Array<PooledEffect> effects = new Array<PooledEffect>();
	SpriteBatch batch;
	int effectIndex = 0;

	public ParticleScreen() {
		// model = new GameModel();
		// model.init();
		batch = new SpriteBatch();
		addEffect("marisa_sp_ef", "particles6");
		addEffect("reimu_bullet_ef", "particles16");
		addEffect("reimu_sp_ef", "particles1");
		addEffect("graze", "particles1");
		addEffect("player_death_ef", "particles1");

		GestureDetector gesture = new GestureDetector(new GestureListener() {

			@Override
			public boolean zoom(float initialDistance, float distance) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1,
					Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean longPress(float x, float y) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				if (velocityX > 400) {
					effectIndex++;
					effectIndex %= effects.size;
				} else if (velocityX < -400) {
					effectIndex--;
					effectIndex += effects.size;
					effectIndex %= effects.size;
				}else
					return false;
				
				effects.get(effectIndex).setPosition(
						Gdx.graphics.getWidth() / 2,
						Gdx.graphics.getHeight() / 2);
				return true;
			}
		});

		Gdx.input.setInputProcessor(new InputMultiplexer(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				effects.get(effectIndex).setPosition(screenX,
						Gdx.graphics.getHeight() - screenY);
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				effects.get(effectIndex).setPosition(screenX,
						Gdx.graphics.getHeight() - screenY);
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				effects.get(effectIndex).setPosition(screenX,
						Gdx.graphics.getHeight() - screenY);
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		}, gesture));
	}

	private void addEffect(String effectName, String imgName) {
		ParticleEffect reffect = new ParticleEffect();
		TextureAtlas atlas = new TextureAtlas();
		atlas.addRegion(
				imgName,
				new TextureRegion(new Texture(Gdx.files.internal("particles/"
						+ imgName + ".png"))));
		reffect.load(Gdx.files.internal("particles/" + effectName + ".p"),
				atlas);
		EffectPool = new ParticleEffectPool(reffect, 1, 2);

		// Create effect
		PooledEffect effect = EffectPool.obtain();
		effect.setPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		effects.add(effect);
	}

	@Override
	public void render(float delta) {
		// model.frame();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// Update and draw effects:
		PooledEffect effect = effects.get(effectIndex);
		effect.draw(batch, delta);
		if (effect.isComplete()) {
			effect.free();
			effects.removeIndex(effectIndex);
		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		// Reset all effects:
		for (int i = effects.size - 1; i >= 0; i--)
			effects.get(i).free();
		effects.clear();
	}
}
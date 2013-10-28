package org.tjuscs.bulletgame.view;

import java.util.Calendar;
import java.util.Date;

import org.tjuscs.bulletgame.model.BaseObject;
import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.task.Task;
import org.tjuscs.bulletgame.task.Tasks;
import org.tjuscs.bulletgame.util.MathUtils;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	GameModel model;
	Renderer render;
	Resources res;
	AudioPlayer audio;
	long frameCount = 0;

	public GameScreen() {
		render = Renderer.getInstance();
		res = Resources.getInstance();
		audio = AudioPlayer.getInstance();

		model = GameModel.getInstance();
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		model.setWorldWidth(w);
		model.setWorldHeight(h);
		model.setScreenWidth(w);
		model.setScreenHeight(h);
	}

	@Override
	public void render(float delta) {
//		System.out.println("Rendering[" + frameCount + "]\tFPS="
//				+ Gdx.graphics.getFramesPerSecond());
		Gdx.graphics.setTitle("BulletGame [FPS:"+Gdx.graphics.getFramesPerSecond()+"][Objects:"+model.getObjList().size()+"]");
		frameCount++;
		model.frame();
		render.startRender();
		render.RenderClear(0, 0, 0, 1);
		if(model.getGameBG()!=null)
			model.getGameBG().render();
		
//		Collections.sort(model.getObjList(),new Comparator<BaseObject>() {
//			@Override
//			public int compare(BaseObject o1, BaseObject o2) {
//				if(o1.getLayer() > o2.getLayer())
//					return 1;
//				else if(o1.getLayer() < o2.getLayer())
//					return -1;
//				else
//					return 0;
//			}
//		});
		for (BaseObject obj : model.getObjList()) {
			if (obj.isHide())
				continue;
			obj.render();
		}

		renderText();
		render.endRender();
	}

	private void renderText() {
		render.RenderText("yahei", 
				"Power:"+model.getPower()+
				"  Score:"+model.getScore()
				,0, Gdx.graphics.getHeight());
		
		String rightTxt = "Life:"+model.getLife();
		TextBounds bounds = render.getTextBounds("yahei", rightTxt);
		render.RenderText("yahei", rightTxt,(int) (Gdx.graphics.getWidth()-bounds.width), Gdx.graphics.getHeight());
		
		String stageName = model.getCurrentStage().getTitle();
		bounds = render.getTextBounds("yahei", stageName);
		render.RenderText("yahei", stageName,0,30);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Task task = Tasks.readScript("script/loadRes.xml");
		task.setBinding("res", Resources.getInstance());
		task.act();
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputProc(),
				new GestureDetector(new GestureHandler())));
		model.init();
	}

	@Override
	public void hide() {
		//res.Clear();
		//audio.StopMusic("TH128_02");
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	public class InputProc implements InputProcessor {
		// private int dragx = 0,dragy = 0;
		boolean up, down, left, right;

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			model.setKeyMove(false);
			model.setKeyShoot(false);
			model.setKeySpell(false);
			return true;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			int dx = (int) (screenX - model.getPlayer().getX());
			int dy = (int) (Gdx.graphics.getHeight() - screenY - model
					.getPlayer().getY());
			if (dx == 0 && dy == 0) {
				model.setKeyMove(false);
				return true;
			} else {
				model.setKeyMove(true);
			}
			double angle;
			if (dx == 0)
				angle = dy > 0 ? 90 : 270;
			else if (dy == 0)
				angle = dx > 0 ? 0 : 180;
			else {
				angle = Math.atan(dy / dx) / MathUtils.degRad;
				if (dx < 0)
					angle += 180;
			}
			// System.out.println("dx="+dx+",dy="+dy+",angle="+angle);
			model.setPlayerMoveAngel(angle);
			// model.getPlayer().setX(screenX);
			// model.getPlayer().setY(Gdx.graphics.getHeight() - screenY);
			return true;
		}

		private long lastTime = 0;
		private Calendar cal = Calendar.getInstance();
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// dragx = screenX;
			// dragy = screenY;
			// return true;
			if(button == Input.Buttons.LEFT){
				cal.setTime(new Date());
				long current = cal.getTimeInMillis();
				if(current-lastTime <= 500){
					model.setKeySpell(true);
				}
				lastTime = current;
				model.setKeyShoot(true);
			}
			else if(button == Input.Buttons.RIGHT)
				model.setKeySpell(true);
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			switch (keycode) {
			case Input.Keys.UP:
				up = false;
				break;
			case Input.Keys.DOWN:
				down = false;
				break;
			case Input.Keys.LEFT:
				left = false;
				break;
			case Input.Keys.RIGHT:
				right = false;
				break;
			case Input.Keys.Z:
				model.setKeyShoot(false);
				break;
			case Input.Keys.X:
				model.setKeySpell(false);
				break;
			case Input.Keys.SHIFT_LEFT:
				model.setKeySlow(false);
				break;
			default:
				break;
			}
			updateDirection();
			return true;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
			switch (keycode) {
			case Input.Keys.UP:
				up = true;
				down = false;
				break;
			case Input.Keys.DOWN:
				down = true;
				up = false;
				break;
			case Input.Keys.LEFT:
				left = true;
				right = false;
				break;
			case Input.Keys.RIGHT:
				right = true;
				left = false;
				break;
			case Input.Keys.Z:
				model.setKeyShoot(true);
				break;
			case Input.Keys.X:
				model.setKeySpell(true);
				break;
			case Input.Keys.SHIFT_LEFT:
				model.setKeySlow(true);
				break;
			default:
				break;
			}
			updateDirection();
			return true;
		}

		private void updateDirection() {
			model.setKeyMove(true);
			if (up) {
				if (left)
					model.setPlayerMoveAngel(135);
				else if (right)
					model.setPlayerMoveAngel(45);
				else
					model.setPlayerMoveAngel(90);
			} else if (down) {
				if (left)
					model.setPlayerMoveAngel(225);
				else if (right)
					model.setPlayerMoveAngel(315);
				else
					model.setPlayerMoveAngel(270);
			} else {
				if (left)
					model.setPlayerMoveAngel(180);
				else if (right)
					model.setPlayerMoveAngel(0);
				else
					model.setKeyMove(false);
			}
		}
	}

	public class GestureHandler implements GestureListener {
		@Override
		public boolean zoom(float initialDistance, float distance) {
			return false;
		}

		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			return false;
		}
	}
}
package org.tjuscs.bulletgame;

import org.tjuscs.bulletgame.view.GameScreen;
import org.tjuscs.bulletgame.view.HomeScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class BulletGame implements ApplicationListener {
	public static Screen gameScreen,homeScreen;
	private static Screen currentScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen();
		homeScreen = new HomeScreen();
		setCurrentScreen(homeScreen);
	}

	public static Screen getCurrentScreen() {
		return currentScreen;
	}

	public static void setCurrentScreen(Screen currentScreen) {
		if(BulletGame.currentScreen != null)
			BulletGame.currentScreen.hide();
		BulletGame.currentScreen = currentScreen;
		currentScreen.show();
		currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	@Override
	public void render() {
		currentScreen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		currentScreen.resize(width, height);
	}

	@Override
	public void pause() {
		currentScreen.pause();
	}

	@Override
	public void resume() {
		currentScreen.resume();
	}
}
package org.tjuscs.bulletgame.view;

import org.tjuscs.bulletgame.BulletGame;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class HomeScreen implements Screen {
	private Renderer render;
	private Resources res;
	private AudioPlayer audio; 
	
	private int beginLocY = 600;
	private int scene = 0;
	private int furScene = 0;
	private int mouseOpt = -1;
	private int recOrder = -1;
	private int recScene = -1;
	private int pieceCount;
	private int GAP = 43;
	private MainData mainData;
	
	private String mainUI[][]={{"Start Game","Stage Practice","Spell Practice","View Replay","Exit Game"},
			{"Select Difficulty","","Normal","Difficult"},
			{"Select Stage","","Stage 1","Stage 2"},
			{"Select Player","","Hakurei Reimu","KiriSame Marisa"},
			{"Spell Practice","","Practice 1","Practice 2"},
			{"View Replay",""}
	};
	private int fontOrder[][]={
			{1,3,100},
			{1,2,3,100},
			{4,100},
			{5}
	}; 
	
	public class MainData{
		String difficulty;
		String stage;
		String player;
		String practice;
		String replay;
	}
	
	public HomeScreen(){
		render = Renderer.getInstance();
		res = Resources.getInstance();
		audio = AudioPlayer.getInstance();
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		//System.out.println("Rendering[" + frameCount++ + "]\tFPS="+ Gdx.graphics.getFramesPerSecond());	
		if(furScene == 100){
			BulletGame.setCurrentScreen(BulletGame.gameScreen);
		}else{
			scene = furScene;			
			render.startRender();
			render.RenderClear(0, 0, 0, 1);
			render.Render("bk_main", 256, 256,0,1.2,2.0);
			
			pieceCount = mainUI[scene].length;
			for(int i = 0;i < pieceCount;i++){
				if(i == mouseOpt){
					render.RenderTextCenter("menu_fontRED", mainUI[scene][i], beginLocY-i*GAP);
				}
				else{
					render.RenderTextCenter("menu_font", mainUI[scene][i], beginLocY-i*GAP);
				}
			}	
			render.endRender();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		//add Listener
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputProcMainScr(),
				new GestureDetector(new GestureHandlerMainScr())));
		
		
		
		//load Resource -- Music;		
		res.LoadMusic("menu","THlib/music/menu.mp3");
		audio.PlayMusic("menu", 0.1);		
		//load Resource -- Image;
		res.LoadImageFromFile("bk_main", "THlib/ui/main_back/menu_bg_2.png");
		//load Resource -- Font;
		res.LoadFont("menu_font", "THlib/ui/font/segoe.fnt");
		res.LoadFont("menu_fontRED", "THlib/ui/font/segoeRED.fnt");
		
		//assign data
		beginLocY = 600;
		scene = 0;
		mouseOpt = -1;
		recOrder = -1;
		recScene = -1;
		mainData = new MainData();
	}

	@Override
	public void hide() {
		beginLocY = 600; 
		scene = 0;       
		furScene = 0;    
		mouseOpt = -1;   
		recOrder = -1;   
		recScene = -1;  
		//audio.StopMusic("menu");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		audio.PauseMusic("menu");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		audio.PlayMusic("menu",0.1);
	}

	@Override
	public void dispose() {
	}
	
	public class InputProcMainScr implements InputProcessor {
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {			
			int k = (beginLocY - Gdx.graphics.getHeight() + screenY)/GAP;	
			if(k >= 0 && k < pieceCount ){	
				if(scene == 0){
					if(k == 4){
						//! Quit Game
						//System.out.println("Quite Game");
						Gdx.app.exit();
						return true;
					}
					recOrder = k;
					recScene++;
					furScene = fontOrder[recOrder][recScene];
				}
				else if(scene > 0 && scene <=5 && k>1){
					recScene++;
					furScene = fontOrder[recOrder][recScene];
					switch (scene){
						case 1:
							mainData.difficulty = mainUI[scene][k];
//							System.out.println("a" + mainData.difficulty);
							break;
						case 2:
							mainData.stage = mainUI[scene][k];
//							System.out.println("a" + mainData.stage);
							break;
						case 3:
							mainData.player = mainUI[scene][k];
//							System.out.println("a" + mainData.player);
							break;
						case 4:
							mainData.practice = mainUI[scene][k];
//							System.out.println("a" + mainData.practice);
							break;
						case 5:
							mainData.replay = mainUI[scene][k];
//							System.out.println("a" + mainData.replay);
							break;
					}
				}
			}
			return true;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {			
			int k = (beginLocY - Gdx.graphics.getHeight() + screenY)/GAP;	
			if(k >= 0 && k < pieceCount ){	
				if(scene == 0 ||(scene >=1 && scene <=5 && k>1)) mouseOpt = k;
			}
//			System.out.println("mouseMoved:" + k);
			return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean keyDown(int keycode) {
			//System.out.println("keyDown"+keycode);
			//a-29 z-54 up-19 down- 20 left-21 right-22 enter-66 esc 131
			return true;
		}
	}
	
	public class GestureHandlerMainScr implements GestureListener {
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

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			// TODO 自动生成的方法存根
			return false;
		}
	}
	
}

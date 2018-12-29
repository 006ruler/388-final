package com.mygdx.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.flappy.states.GameStateManager;
import com.mygdx.flappy.states.MenuState;

public class FlappyTut extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "FlappyBird";

	private GameStateManager gsm;
	private SpriteBatch batch;

	private Music music;

	private static boolean playMusic;

	private static Preferences savedPreferences;


	//SpriteBatch batch;
	//Texture img;
	
	@Override
	public void create () {
		playMusic = true;
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		savedPreferences = getPrefs();
		playMusic = savedPreferences.getBoolean("music", true);

		//img = new Texture("badlogic.jpg");
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm, savedPreferences.getInteger("highScore", 0)));
		//gsm.push(new MenuState(gsm));
	}

	protected Preferences getPrefs() {
		if(savedPreferences == null)
		{
			savedPreferences = Gdx.app.getPreferences("My Preferences");
		}
		return savedPreferences;
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
		//img.dispose();
	}

	public static void setMusic(boolean play)
	{
		playMusic = play;
	}

	public static boolean getMusic()
	{
		return playMusic;
	}


}

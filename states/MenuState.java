package com.mygdx.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.flappy.FlappyTut;

public class MenuState extends State {

    private Texture background;
    private Texture playButton;
    private Texture musicButton;

    private boolean playMusic;

    private int score;

    private Preferences savedPreferences;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        score = 0;
        playMusic = true;
        cam.setToOrtho(false, FlappyTut.WIDTH/2, FlappyTut.HEIGHT/2);
        background = new Texture("Title.png");
        playButton = new Texture("PlayButton.png");
        musicButton = new Texture("MusicButton.png");
    }

    public MenuState(GameStateManager gsm, int Score) {
        super(gsm);
        savedPreferences = getPrefs();
        this.score = Score;
        playMusic = true;
        cam.setToOrtho(false, FlappyTut.WIDTH/2, FlappyTut.HEIGHT/2);
        background = new Texture("Title.png");
        playButton = new Texture("PlayButton.png");
        musicButton = new Texture("MusicButton.png");

        if(this.score > savedPreferences.getInteger("HighScore", 0))
        {
            savedPreferences.putInteger("HighScore", this.score);
        }

    }

    protected Preferences getPrefs()
    {
        if(savedPreferences == null)
        {
            savedPreferences = Gdx.app.getPreferences("My Preferences");
        }
        return savedPreferences;
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY() + " " + (Gdx.graphics.getWidth() - cam.position.x  ) + " " + (cam.position.y));
            if((Gdx.input.getX() > (Gdx.graphics.getWidth() / 4 )) && Gdx.input.getX() < ((Gdx.graphics.getWidth() / 4) * 3))
            {
                System.out.println("X is right");
                if(Gdx.input.getY() < Gdx.graphics.getHeight()/2)
                {
                    System.out.println("Going to game");
                    gsm.set(new PlayState(gsm));
                }
                else if(Gdx.input.getY() > Gdx.graphics.getHeight()/2)
                {
                    System.out.println("Music changed");
                     FlappyTut.setMusic(!playMusic);
                     savedPreferences.putBoolean("music", !playMusic);
                }

            }

        }

    }

    @Override
    public void update(float dt) {
        handleInput();



    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playButton, cam.position.x - playButton.getWidth()/2, cam.position.y);
        sb.draw(musicButton, cam.position.x - musicButton.getWidth()/2, cam.position.y - 100);

        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.draw(sb, "Score: " + score, (cam.position.x - (3 * (playButton.getWidth()/4))) + playButton.getWidth()/2, 100);
        font.draw(sb, "High Score: " + savedPreferences.getInteger("HighScore", 0), (cam.position.x - (3 * (playButton.getWidth()/4))) + playButton.getWidth()/2, 75);



        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}

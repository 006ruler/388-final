package com.mygdx.flappy.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.flappy.FlappyTut;
import com.mygdx.flappy.sprites.Coins;
import com.mygdx.flappy.sprites.Platform;
import com.mygdx.flappy.sprites.Player;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class PlayState extends State {
    private Player player;

    private Texture background;

    private static final int PLATFORM_DISTANCE = 100;
    private static final int PLATFORM_COUNT = 3;

    private Array<Platform> platforms;

    private Coins coin;
    private int score;

    private Music music;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        score = 0;
        player = new Player(70, 50);
        coin = new Coins(200, 100);
        platforms = new Array<Platform>();
        platforms.add(new Platform(70, 50));

        for(int i = 1; i <= PLATFORM_COUNT; i++)
        {
            platforms.add(new Platform(i * (PLATFORM_DISTANCE + Platform.PLATFORM_WIDTH), 0));
        }

        background = new Texture("Title.png");

        cam.setToOrtho(false, FlappyTut.WIDTH/2, FlappyTut.HEIGHT/2);
        if(FlappyTut.getMusic())
        {
            music = Gdx.audio.newMusic(Gdx.files.internal("Pacman_Introduction_Music-KP-826387403.mp3"));
            music.setLooping(true);
            music.setVolume(0.1f);
            music.play();
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
        {
            player.jump();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

        player.update(dt);
        cam.position.x = player.getPosition().x + 80;
        cam.update();

        for(Platform platform: platforms)
        {
            if(cam.position.x-(cam.viewportWidth/2) > platform.getPosition().x + platform.getPlatform().getWidth())
            {
                platform.reposition(platform.getPosition().x + ((Platform.PLATFORM_WIDTH + PLATFORM_DISTANCE) * PLATFORM_COUNT));
            }

            if(platform.collides(player.getBounds()))
            {
                player.haltFall(true);
            }
        }
        if(cam.position.x-(cam.viewportWidth/2) > coin.getPosition().x + 16)
        {
            coin.reposition(coin.getPosition().x + 300);
        }
        if(coin.collides(player.getBounds()))
        {
            coin.reposition(coin.getPosition().x  + 300);
            score++;
            System.out.println(score);
        }

        if(player.getPosition().y == 0)
        {
            if(FlappyTut.getMusic())
            music.stop();
            gsm.set(new MenuState(gsm, score));
        }


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - 150, 0, FlappyTut.WIDTH, FlappyTut.HEIGHT);
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
        for(Platform platform : platforms)
        {
            sb.draw(platform.getPlatform(), platform.getPosition().x, platform.getPosition().y);
        }
        sb.draw(coin.getTexture(), coin.getPosition().x, coin.getPosition().y);


        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        player.dispose();
        for(Platform platform : platforms)
        {
            platform.dispose();
        }
        coin.dispose();

    }
}

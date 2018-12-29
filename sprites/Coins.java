package com.mygdx.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Coins {
    private static final int FREQUENCY = 200;
    private static final int LOWEST_LOC = 30;

    private Animation coinAnimation;
    private Texture texture;
    private Rectangle bounds;

    private Random rand;

    private Vector2 position;

    public Coins(float x, float y)
    {
        texture = new Texture("coin.png");
        coinAnimation = new Animation(new TextureRegion(texture), 7, 1);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        rand = new Random();

        position = new Vector2(x, rand.nextInt(FREQUENCY) + LOWEST_LOC);
        if(y != 0)
        {
            position.y = y;
        }

        bounds = new Rectangle(position.x, position.y, texture.getWidth() / 7, texture.getHeight());
    }

    public void reposition(float x)
    {
        position.set(x, rand.nextInt(FREQUENCY) + LOWEST_LOC);
        bounds.setPosition(position.x, position.y);

    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public TextureRegion getTexture() {
        return coinAnimation.getFrame();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose()
    {
        texture.dispose();
    }
}

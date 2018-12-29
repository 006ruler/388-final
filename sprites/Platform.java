package com.mygdx.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Platform {
    private static final int FLUCTUATION = 130;

    private static final int LOWEST_LOC = 20;

    public static final int PLATFORM_WIDTH = 18;

    private Rectangle bounds;

    private Texture platform;

    private Vector2 position;
    private Random rand;

    public Platform(float x, float y)
    {
        platform = new Texture("platform-png-1.png");
        rand = new Random();

        position = new Vector2(x, rand.nextInt(FLUCTUATION) + LOWEST_LOC);
        if(y != 0)
        {
            position.y = y;
        }

        bounds = new Rectangle(position.x, position.y, platform.getWidth(), platform.getHeight());
    }

    public void reposition(float x)
    {
        position.set(x, rand.nextInt(FLUCTUATION) + LOWEST_LOC);
        bounds.setPosition(position.x, position.y);

    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public Texture getPlatform() {
        return platform;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose()
    {
        platform.dispose();
    }
}

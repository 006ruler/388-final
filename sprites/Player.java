package com.mygdx.flappy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private static final int GRAVITY = -10;
    private static final int MOVEMENT = 100;

    private Rectangle bounds;

    private Vector3 position;
    private Vector3 velocity;


    private boolean falling;
    private boolean canJump;

    private Animation playerAnimation;
    private Texture texture;

    private Sound jump;


    public Player(int x, int y)
    {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);

        texture = new Texture("runningWhite.png");
        playerAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        falling = true;
        canJump = false;

        jump = Gdx.audio.newSound(Gdx.files.internal("pac-man-waka-waka.mp3"));
    }

    public void update(float dt) {
        if (position.y > 0 && falling) {
        velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(0, velocity.y, 0);
        position.add(MOVEMENT * dt, velocity.y, 0);

        velocity.scl(1 / dt);

        if (position.y < 0)
            position.y = 0;
        bounds.setPosition(position.x, position.y);

        playerAnimation.update(dt);

    }

    public void jump()
    {
        if(canJump) {
            velocity.y = 300;
            jump.play(0.5f);
        }
        canJump = false;
    }


    public void haltFall(boolean touch)
    {
        this.canJump = touch;
        if(velocity.y < 0)
            velocity.y = 0;
        position.y++;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return playerAnimation.getFrame();
    }

    public void dispose()
    {
        texture.dispose();
        jump.dispose();
    }
}

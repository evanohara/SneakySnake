package com.tweekgames.sneakysnake.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tweekgames.sneakysnake.game.Assets;
import com.tweekgames.sneakysnake.game.objects.AnimatedSprite;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;


/**
 * Created by EvansDesktop on 6/7/2015.
 */
public class SplashScreen extends AbstractGameScreen{

    private SpriteBatch batch;
    private AnimatedSprite animatedSprite;
    private TweenManager tweenManager;
    private Sprite startSprite, finalSprite;
    private boolean tweenTripped;
    private float time;

    public SplashScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        if (time < 2f)
            startSprite.draw(batch);
        else if (time < 4f)
            animatedSprite.draw(batch);
        else {
            finalSprite.draw(batch);
            if (tweenTripped == false)
            {
                tweenTripped = true;
                Tween.set(finalSprite, SpriteAccessor.ALPHA).target(1).start(tweenManager);
                Tween.to(finalSprite, SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
            }
        }
        batch.end();
        time += delta;
        if (time > 6)
        {
            game.setScreen(new MenuScreen(game));
        }

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        time = 0.0f;
        tweenTripped = false;

        Animation animation = new Animation(0.25f, Assets.instance.splash.splash);
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        startSprite = new Sprite(Assets.instance.splash.splash.first());
        startSprite.setPosition(Gdx.graphics.getWidth()/2 - startSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - startSprite.getHeight()/2);
        startSprite.scale(3.0f);
        finalSprite = new Sprite(Assets.instance.splash.splash.get(Assets.instance.splash.splash.size-1));
        finalSprite.setPosition(Gdx.graphics.getWidth()/2 - finalSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - finalSprite.getHeight()/2);
        finalSprite.scale(3.0f);
        animatedSprite = new AnimatedSprite(animation);
        animatedSprite.scale(3.0f);
        animatedSprite.setPosition(Gdx.graphics.getWidth()/2 - animatedSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - animatedSprite.getHeight()/2);

        Tween.set(startSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(startSprite, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}

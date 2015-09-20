package com.tweekgames.sneakysnake;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.tweekgames.sneakysnake.game.Assets;
import com.tweekgames.sneakysnake.screens.SplashScreen;
import com.tweekgames.sneakysnake.util.ActionResolver;


public class SneakySnakeGame extends Game implements GestureListener{
    private ActionResolver actionResolver;

    public SneakySnakeGame(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }

	@Override
	public void create () {
        // Set Libgdx log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        // Load Assets
        Assets.instance.init(new AssetManager());
        // Start game at menu screen
        setScreen(new SplashScreen(this));
	}

    @Override
    public boolean tap(float x, float y, int count, int button) {
        //actionResolver.showOrLoadInterstitial();
        return true;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        // TODO Auto-generated method stub
        return false;
    }
}

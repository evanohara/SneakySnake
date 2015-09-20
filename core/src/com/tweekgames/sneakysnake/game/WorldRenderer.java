package com.tweekgames.sneakysnake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.tweekgames.sneakysnake.util.Constants;

/**
 * Created by EvansDesktop on 3/21/2015.
 */
public class WorldRenderer implements Disposable{
    private OrthographicCamera camera;
    private float desiredHeight;
    private float aspectRatio;

    SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer (WorldController worldController){
        this.worldController = worldController;
        init();
    }

    private void init() {
        batch = new SpriteBatch();
    }

    public void render(){
        renderWorld(batch);
    }

    private void renderWorld (SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.world.render(batch);
        batch.end();
    }

    public void resize (int width, int height) {
        aspectRatio = (float) width / (float) height;
        camera = new OrthographicCamera(width/64f, height/64f);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

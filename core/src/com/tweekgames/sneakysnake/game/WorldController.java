package com.tweekgames.sneakysnake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by EvansDesktop on 3/21/2015.
 */
public class WorldController extends InputAdapter{
    private static final String TAG = WorldController.class.getName();

    private Game game;

    public World world;

    public WorldController(Game game){
        this.game = game;
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        initWorld();
    }

    private void initWorld() {
        world = new World(game);
    }

    public void update(float deltaTime){
        world.update(deltaTime);
    }
}

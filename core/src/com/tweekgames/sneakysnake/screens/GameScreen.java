package com.tweekgames.sneakysnake.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tweekgames.sneakysnake.game.Assets;
import com.tweekgames.sneakysnake.game.WorldController;
import com.tweekgames.sneakysnake.game.WorldRenderer;
import com.tweekgames.sneakysnake.util.AudioManager;
import com.tweekgames.sneakysnake.util.Constants;

/**
 * Created by EvansDesktop on 3/21/2015.
 */
public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private Stage stage;
    private Skin skinUI;

    // UI Controls
    private Button btnLeft;
    private Button btnRight;
    private Button btnDigUp;
    private Button btnDigDown;

    private Image imgShovelIcon;
    private Label labShovelCounter;

    private Label score;
    private Label speed;
    private Label multiplier;
    private Label fpsLab;

    // debug UI
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private float debugRebuildStage;
    private float boardWidth;

    private boolean paused;

    public GameScreen(Game game) {
        super(game);
        AudioManager.instance.play(Assets.instance.music.ssMusicIntro,true);
        boardWidth = 704.0f;
    }



    @Override
    public void render(float deltaTime) {
        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController.update(deltaTime);
            stage.act(deltaTime);
        }
        // Sets the clear screen color to: Black
        //Gdx.gl.glClearColor(0/255.0f, 96/250.0f, 184/255.0f, 1.0f);
        Gdx.gl.glClearColor(0/255.0f,138/255.0f, 207/255.0f, 1.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        worldRenderer.render();
        int fps = Gdx.graphics.getFramesPerSecond();
        score.setText("Pts: " + worldController.world.score);
        speed.setText("Spd: " + String.format("%.0f",60.0f/worldController.world.tick));
        multiplier.setText("x" + String.format("%.1f",worldController.world.multiplier));
        //fpsLab.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
        labShovelCounter.setText("x" + worldController.world.snake.shovels);

        stage.getBatch().begin();
        stage.getBatch().draw(Assets.instance.frame.frameUL, 0, Gdx.graphics.getHeight()-8, 8, 8);
        stage.getBatch().draw(Assets.instance.frame.frameUR, Gdx.graphics.getWidth()-8, Gdx.graphics.getHeight()-8, 8, 8);
        stage.getBatch().draw(Assets.instance.frame.frameBL, 0, 0, 8, 8);
        stage.getBatch().draw(Assets.instance.frame.frameBR, Gdx.graphics.getWidth()-8, 0, 8, 8);
        stage.getBatch().draw(Assets.instance.frame.frameU, 8, Gdx.graphics.getHeight()-8, Gdx.graphics.getWidth()-16, 8);
        stage.getBatch().draw(Assets.instance.frame.frameL, 0, 8, 8, Gdx.graphics.getHeight()-16);
        stage.getBatch().draw(Assets.instance.frame.frameB, 8, 0, Gdx.graphics.getWidth()-16, 8);
        stage.getBatch().draw(Assets.instance.frame.frameR, Gdx.graphics.getWidth()-8, 8, 8, Gdx.graphics.getHeight()-16);
        stage.getBatch().end();

        stage.draw();
    }

    private void rebuildStage() {
        skinUI = new Skin(Gdx.files.internal(Constants.SKIN_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        // build all layers
        Table layerLeftButton = buildLeftButton();
        Table layerRightButton = buildRightButton();
        Table layerDigUpButton = buildDigUpButton();
        Table layerDigDownButton = buildDigDownButton();
        Table layerScore = buildScore();
        Table layerShovelCounter = buildShovelCounterLayer();

        // assemble stage for user interface
        stage.clear();
        //stage.setDebugAll(true);
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stack.add(layerLeftButton);
        stack.add(layerRightButton);
        stack.add(layerScore);
        stack.add(layerDigDownButton);
        stack.add(layerDigUpButton);
        stack.add(layerShovelCounter);
    }

    private Table buildShovelCounterLayer() {
        Table layer = new Table();
        layer.align(Align.topRight);
        imgShovelIcon = new Image(skinUI, "shovel");
        layer.add(imgShovelIcon);
        labShovelCounter = new Label("x" + worldController.world.snake.shovels, skinUI);
        layer.add(labShovelCounter);
        return layer;
    }

    private Table buildDigDownButton() {
        Table layer = new Table();
        layer.align(Align.bottomRight).pad(Gdx.graphics.getWidth()* 0.01f);
        btnDigDown = new Button(skinUI, "digDown");
        btnDigDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (worldController.world.snake.shovels > 0 && worldController.world.snake.head.level < 2) {
                    worldController.world.goDown();
                    worldController.world.snake.digDown();
                }
            }
        });
        layer.add(btnDigDown);
        return layer;
    }

    private Table buildDigUpButton() {
        Table layer = new Table();
        layer.align(Align.bottomLeft).pad(Gdx.graphics.getWidth()* 0.01f);
        btnDigUp = new Button(skinUI, "digUp");
        btnDigUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (worldController.world.snake.shovels > 0 && worldController.world.snake.head.level > 0) {
                    worldController.world.goUp();
                    worldController.world.snake.digUp();
                }
            }
        });
        layer.add(btnDigUp);
        return layer;
    }

    private Table buildScore() {
        Table layer = new Table();
        layer.align(Align.topLeft).padTop(10.0f).padLeft(10.0f).padRight(10.0f);
        score = new Label("Score: " + worldController.world.score, skinUI);
        speed = new Label("Speed: " + worldController.world.tickTime, skinUI);
        multiplier = new Label("x" + worldController.world.multiplier,skinUI);
        //fpsLab = new Label("FPS: " + Gdx.graphics.getFramesPerSecond(), skinUI);
        layer.add(score);
        layer.row();
        layer.add(speed);
        layer.row();
        layer.add(multiplier);
        layer.row();
        layer.add(fpsLab);
        return layer;
    }

    private Table buildLeftButton() {
        Table layer = new Table();
        layer.align(Align.center).align(Align.left);
        // + Buttons
        btnLeft = new Button(skinUI, "left");
        layer.pad(((Gdx.graphics.getWidth() - boardWidth)/2-btnLeft.getWidth())/2);
        layer.add(btnLeft);
        btnLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldController.world.snake.turnLeft();
                AudioManager.instance.play(Assets.instance.sounds.tick);
            }
        });
        layer.row();
        layer.add(btnDigUp);
        return layer;
    }

    private Table buildRightButton() {
        Table layer = new Table();
        layer.align(Align.center).align(Align.right);
        // + Buttons
        btnRight = new Button(skinUI, "right");
        layer.pad(((Gdx.graphics.getWidth() - boardWidth)/2-btnRight.getWidth())/2);
        layer.add(btnRight);
        btnRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldController.world.snake.turnRight();
                AudioManager.instance.play(Assets.instance.sounds.tick);
            }
        });
        return layer;
    }

    public void updateScore(int newScore){
        score.setText(Integer.toString(newScore));
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        stage = new Stage();
        stage.setViewport(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    @Override
    public void hide() {
        stage.dispose();
        skinUI.dispose();
    }

    @Override
    public void resize (int width, int height) {
        worldRenderer.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }
}

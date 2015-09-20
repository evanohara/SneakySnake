package com.tweekgames.sneakysnake.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tweekgames.sneakysnake.connections.LoadScoresConnector;
import com.tweekgames.sneakysnake.game.Assets;
import com.tweekgames.sneakysnake.util.Constants;

/**
 * Created by EvansDesktop on 4/3/2015.
 */
public class NewScoreScreen extends AbstractGameScreen{
    private static final String TAG = NewScoreScreen.class.getName();

    private Stage stage;
    private Skin skinUI;

    private long newScore;

    Label labHighScore;
    Label labNewScore;
    TextButton btnMenu;

    public NewScoreScreen(Game game, long newScore) {
        super(game);
        this.newScore = newScore;
        Assets.allTimeScores.clear();
        Assets.monthlyScores.clear();
        Assets.dailyScores.clear();
        LoadScoresConnector loadScoresConnector = new LoadScoresConnector();
        loadScoresConnector.loadScores(Assets.allTimeScores,Assets.monthlyScores,Assets.dailyScores);
    }

    private void rebuildStage(){
        skinUI = new Skin(Gdx.files.internal(Constants.SKIN_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

        // Build all layers
        Table layerScores = buildScoresLayer();
        Table layerButtons = buildButtonLayer();

        // Assemble Stage
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
        stack.add(layerScores);
        stack.add(layerButtons);
    }

    private Table buildButtonLayer() {
        Table layer = new Table();
        layer.align(Align.bottomRight);
        btnMenu = new TextButton("Main Menu", skinUI);
        btnMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        layer.add(btnMenu);
        return layer;
    }

    private Table buildScoresLayer() {
        Table layer = new Table();
        if (newScore > Assets.highScore) {
            Assets.highScore = newScore;
            Assets.settings.putLong("highScore", newScore);
            Assets.settings.flush();
        }
        labHighScore = new Label("Your high score is: " + Assets.highScore, skinUI);
        labNewScore = new Label("You scored: " + newScore, skinUI);
        layer.add(labHighScore);
        layer.row().pad(50.0f);
        layer.add(labNewScore);
        return layer;
    }

    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setViewport(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH,
                Constants.VIEWPORT_GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }
}

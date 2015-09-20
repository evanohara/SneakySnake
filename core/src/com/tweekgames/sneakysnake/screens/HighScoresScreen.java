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
import com.tweekgames.sneakysnake.game.Assets;
import com.tweekgames.sneakysnake.screens.widgets.HighScoreWidget;
import com.tweekgames.sneakysnake.util.Constants;

/**
 * Created by EvansDesktop on 4/3/2015.
 */
public class HighScoresScreen extends AbstractGameScreen {
    private static final String TAG = NewScoreScreen.class.getName();

    private Stage stage;
    private Skin skinUI;

    private static int scoreCategory = 0;

    // List<Label> highScores;
    TextButton btnMenu;
    TextButton btnAllTime;
    TextButton btnMonthly;
    TextButton btnDaily;

    public HighScoresScreen(Game game) {
        super(game);
    }

    private void rebuildStage() {
        skinUI = new Skin(Gdx.files.internal(Constants.SKIN_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

        //highScores = new ArrayList<Label>();

        Stack stack = new Stack();

        // Build all layers
        Table layerMenuButton = buildMenuButtonLayer();
        Table layerHighScores = buildHighScoresLayer();
        Table layerTimeSelectButtons = buildTimeSelectButtonsLayer();


        // Assemble Stage
        stage.clear();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        stack.add(layerMenuButton);
        stack.add(layerTimeSelectButtons);
        stack.add(layerHighScores);
    }

    private Table buildTimeSelectButtonsLayer() {
        final Table layer = new Table();
        layer.align(Align.left);

        btnAllTime = new TextButton("All Time", skinUI);
        btnAllTime.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scoreCategory = 0;
                rebuildStage();
            }
        });
        btnMonthly = new TextButton("Monthly", skinUI);
        btnMonthly.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scoreCategory = 1;
                rebuildStage();
            }
        });
        btnDaily = new TextButton("Daily", skinUI);
        btnDaily.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scoreCategory = 2;
                rebuildStage();
            }
        });
        layer.add(btnAllTime);
        layer.row().pad(30.0f);
        layer.add(btnMonthly);
        layer.row().pad(30.0f);
        layer.add(btnDaily);
        return layer;
    }

    private Table buildHighScoresLayer() {
        Table tenCommandments = new Table();
        Table leftTab = new Table();
        Table rightTab = new Table();
        tenCommandments.align(Align.top);
        if (scoreCategory == 0) {
            Label title = new Label("Top 20 All Time High Scores", skinUI);
            tenCommandments.add(title).colspan(2);
            tenCommandments.row();
            tenCommandments.add(leftTab);
            tenCommandments.add(rightTab);
            for (int i = 0; i < Assets.allTimeScores.size(); i++) {
                HighScoreWidget widget = new HighScoreWidget(i+1, Assets.allTimeScores.get(i).name,
                        Assets.allTimeScores.get(i).score);
                if (i < 10) {
                    leftTab.add(widget).width(Gdx.graphics.getWidth()*0.3f);
                    leftTab.row();
                } else {
                    rightTab.add(widget).width(Gdx.graphics.getWidth()*0.3f).padLeft(Gdx.graphics.getWidth()*0.02f);
                    rightTab.row();
                }
            }
        } else if (scoreCategory == 1) {
            Label title = new Label("Top 20 Monthly High Scores", skinUI);
            tenCommandments.add(title).colspan(2);
            tenCommandments.row();
            tenCommandments.add(leftTab);
            tenCommandments.add(rightTab);
            for (int i = 0; i < Assets.monthlyScores.size(); i++) {
                HighScoreWidget widget = new HighScoreWidget(i+1, Assets.monthlyScores.get(i).name,
                        Assets.monthlyScores.get(i).score);
                if (i < 10) {
                    leftTab.add(widget).width(Gdx.graphics.getWidth()*0.3f);
                    leftTab.row();
                } else {
                    rightTab.add(widget).width(Gdx.graphics.getWidth()*0.3f).padLeft(Gdx.graphics.getWidth()*0.02f);
                    rightTab.row();
                }
            }
        } else if (scoreCategory == 2) {
            Label title = new Label("Top 20 Daily High Scores", skinUI);
            tenCommandments.add(title).colspan(2);
            tenCommandments.row();
            tenCommandments.add(leftTab);
            tenCommandments.add(rightTab);
            for (int i = 0; i < Assets.dailyScores.size(); i++) {
                HighScoreWidget widget = new HighScoreWidget(i+1, Assets.dailyScores.get(i).name,
                        Assets.dailyScores.get(i).score);
                if (i < 10) {
                    leftTab.add(widget).width(Gdx.graphics.getWidth()*0.3f);
                    leftTab.row();
                } else {
                    rightTab.add(widget).width(Gdx.graphics.getWidth()*0.3f).padLeft(Gdx.graphics.getWidth()*0.02f);
                    rightTab.row();
                }
            }
        }
        return tenCommandments;
    }

    private Table buildMenuButtonLayer() {
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

    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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

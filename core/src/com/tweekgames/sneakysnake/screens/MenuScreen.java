package com.tweekgames.sneakysnake.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tweekgames.sneakysnake.game.Assets;
import com.tweekgames.sneakysnake.util.Constants;

/**
 * Created by EvansDesktop on 3/21/2015.
 */
public class MenuScreen extends AbstractGameScreen{
    private static final String TAG = MenuScreen.class.getName();

    private Stage stage;
    private Skin skinUI;

    // Menu
    private TextButton btnMenuPlay;
    private TextButton btnMenuSettings;
    private TextButton btnMenuHighScores;
    private TextButton btnMenuShop;
    private TextButton btnMenuHelp;
    private TextButton btnMenuQuit;

    // Background
    private Image imgBackground;
    private Image imgTitle;

    // Name
    private TextField tfName;

    // Score Text
    private Label labHighScore;

    // Debug
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private float debugRebuildStage;

    public MenuScreen(Game game) {
        super(game);
    }

    private void rebuildStage(){
        skinUI = new Skin(Gdx.files.internal(Constants.SKIN_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

        // Build All Layers
        Table layerMain = buildMainLayer();
        Table layerScore = buildScoreLayer();
        Table layerName = buildNameLayer();
        Table layerBackground = buildBackgroundLayer();

        // Assemble Stage for Menu Screen
        stage.clear();
        stage.setDebugAll(debugEnabled);

        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        stack.add(layerBackground);
        stack.add(layerScore);
        stack.add(layerName);
        stack.add(layerMain);
    }

    private Table buildMainLayer() {
        Table layer = new Table();
        layer.setSize(Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.8f);
        imgTitle = new Image(skinUI,"title");
        layer.add(imgTitle).width(432).height(144).padBottom(Gdx.graphics.getHeight()*0.05f);
        layer.row();

        Table buttonTable = new Table();
        layer.add(buttonTable);
        btnMenuPlay = new TextButton("Start Game", skinUI);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        buttonTable.add(btnMenuPlay).pad(Gdx.graphics.getHeight() * 0.06f);
        btnMenuSettings = new TextButton("Settings", skinUI);
        btnMenuSettings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        buttonTable.add(btnMenuSettings).pad(Gdx.graphics.getHeight() * 0.06f);
        buttonTable.row();
        btnMenuHighScores = new TextButton("High Scores", skinUI);
        btnMenuHighScores.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onScoresClicked();
            }
        });
        buttonTable.add(btnMenuHighScores).pad(Gdx.graphics.getHeight() * 0.06f);
        btnMenuHelp = new TextButton("Help", skinUI);
        btnMenuHelp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onHelpClicked();
            }
        });
        buttonTable.add(btnMenuHelp).pad(Gdx.graphics.getHeight() * 0.06f);
        buttonTable.row();
        btnMenuShop = new TextButton("Unlock Skins", skinUI);
        btnMenuShop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        buttonTable.add(btnMenuShop).pad(Gdx.graphics.getHeight() * 0.06f);
        btnMenuQuit = new TextButton("Quit", skinUI);
        btnMenuQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onQuitClicked();
            }
        });
        buttonTable.add(btnMenuQuit).pad(Gdx.graphics.getHeight() * 0.06f);
        return layer;
    }

    private Table buildBackgroundLayer() {
        Table layer = new Table();
        imgBackground = new Image(skinUI, "menubackground");
        layer.add(imgBackground).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight());
        layer.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        return layer;
    }

    private Table buildNameLayer() {
        Table layer = new Table();
        layer.align(Align.topRight);
        Label name = new Label("Name: ", skinUI);
        layer.add(name);
        tfName = new TextField(Assets.name, skinUI);
        tfName.setMaxLength(3);
        tfName.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                if ((c > 96) && (c < 123))
                {
                    return true;
                } else if ((c > 64) && (c < 91))
                {
                    return true;
                }
                return false;
            }
        });
        layer.add(tfName);
        return layer;
    }

    private Table buildScoreLayer() {
        Table layer = new Table();
        layer.align(Align.topLeft);
        labHighScore = new Label("Your High Score: " + Assets.highScore, skinUI);
        layer.add(labHighScore);
        return layer;
    }

    private void onScoresClicked() {
        game.setScreen(new HighScoresScreen(game));
    }

    private void onQuitClicked() {
        Gdx.app.exit();
    }

    private void onPlayClicked() {
        Assets.name = tfName.getText();
        Assets.settings.putString("name", Assets.name);
        Assets.settings.flush();
        game.setScreen(new GameScreen(game));
    }

    private void onHelpClicked() {
        game.setScreen(new HelpScreen(game));
    }

    @Override
    public void render (float deltaTime){
        Gdx.gl.glClearColor(0/255.0f, 96/250.0f, 184/255.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        if (debugEnabled) {
//            debugRebuildStage -= deltaTime;
//            if (debugRebuildStage <= 0) {
//                debugRebuildStage = DEBUG_REBUILD_INTERVAL;
//                rebuildStage();
//            }
//        }
        int fps = Gdx.graphics.getFramesPerSecond();
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
        stage.setViewport(new StretchViewport(
                Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }
}

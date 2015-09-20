package com.tweekgames.sneakysnake.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tweekgames.sneakysnake.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

/**
 * Created by EvansDesktop on 4/12/2015.
 */
public class HelpScreen extends AbstractGameScreen {
    private static final String TAG = HelpScreen.class.getName();

    private Stage stage;
    private Skin skinUI;

    private Button btnLeft;
    private Button btnRight;

    private TextButton btnMenu;

    private List<Image> imgHelpList;
    private List<Label> imgHelpTestList;
    private Table helpImageTable;

    private int whichHelpScreen;


    // Debug
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private float debugRebuildStage;

    public HelpScreen(Game game) {
        super(game);
    }

    private void rebuildStage() {
        skinUI = new Skin(Gdx.files.internal(Constants.SKIN_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

        Table leftButtonLayer = buildLeftButtonLayer();
        Table rightButtonLayer = buildRightButtonLayer();
        Table menuButtonLayer = buildMenuButtonLayer();
        Table helpImageLayer = buildHelpImageLayer();
        stage.clear();
        stage.setDebugAll(debugEnabled);

        Stack stack = new Stack();
        Stack stack3x = new Stack();
        stage.addActor(stack);
        stage.addActor(stack3x);
        stack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stack3x.setSize(Gdx.graphics.getWidth()*3.0f, Gdx.graphics.getHeight());
        stack3x.add(helpImageLayer);
        stack.add(leftButtonLayer);
        stack.add(rightButtonLayer);
        stack.add(menuButtonLayer);
    }

    private Table buildHelpImageLayer() {
        helpImageTable = new Table();
        whichHelpScreen = 0;

        imgHelpList = new ArrayList<Image>();
        imgHelpList.add(new Image(skinUI, "help1"));
        imgHelpList.add(new Image(skinUI, "help1"));
        imgHelpList.add(new Image(skinUI, "help1"));
        imgHelpTestList = new ArrayList<Label>();
        imgHelpTestList.add(new Label("Collect the red apples to score points.  Each red apple increases your length.", skinUI));
        imgHelpTestList.add(new Label("Collecting the blue apples also scores points.  Each blue apple increases your length, speed AND your points multiplier!", skinUI));
        imgHelpTestList.add(new Label("Collect shovels to allow travel between different layers", skinUI));

        for(int i = 0; i < imgHelpList.size(); i++){
            Table newTable = new Table();
            newTable.add(imgHelpList.get(i));
            if (i != imgHelpList.size()-1){
                newTable.padRight(Gdx.graphics.getWidth() / 2);
            }
            newTable.row();
            imgHelpTestList.get(i).setWrap(true);
            newTable.add(imgHelpTestList.get(i)).width(Gdx.graphics.getWidth()/2);
            helpImageTable.add(newTable);
        }

        return helpImageTable;
    }


    private Table buildMenuButtonLayer() {
        Table layer = new Table();
        layer.align(Align.bottomRight);
        btnMenu = new TextButton("Menu", skinUI);
        btnMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        layer.add(btnMenu);
        return layer;
    }

    private Table buildRightButtonLayer() {
        Table layer = new Table();
        btnRight = new Button(skinUI, "right");
        layer.align(Align.right);
        layer.add(btnRight).padRight(Gdx.graphics.getWidth()*0.1f);
        btnRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rightButtonPressed();
            }
        });
        return layer;
    }

    private void rightButtonPressed() {
        if (whichHelpScreen < 2) {
            Interpolation moveEasing = Interpolation.swing;
            helpImageTable.addAction(moveBy(-Gdx.graphics.getWidth(), 0.0f, 1.0f, moveEasing));
            whichHelpScreen++;
        }
    }

    private Table buildLeftButtonLayer() {
        Table layer = new Table();
        btnLeft = new Button(skinUI, "left");
        layer.align(Align.left);
        layer.add(btnLeft).padLeft(Gdx.graphics.getWidth()*0.1f);
        btnLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                leftButtonPressed();
            }
        });
        return layer;
    }

    private void leftButtonPressed() {
        if (whichHelpScreen > 0) {
            Interpolation moveEasing = Interpolation.swing;
            helpImageTable.addAction(moveBy(Gdx.graphics.getWidth(), 0.0f, 1.0f, moveEasing));
            whichHelpScreen--;
        }
    }

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

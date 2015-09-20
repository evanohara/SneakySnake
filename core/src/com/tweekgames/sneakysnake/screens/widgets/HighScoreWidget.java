package com.tweekgames.sneakysnake.screens.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.tweekgames.sneakysnake.util.Constants;

/**
 * Created by EvansDesktop on 4/13/2015.
 */
public class HighScoreWidget extends Stack{

    private Image imgBackground;
    private Label labName, labScore;
    private Skin skinUI;

    public HighScoreWidget (int i, String name, long score){
        skinUI = new Skin(Gdx.files.internal(Constants.SKIN_UI),
                new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        imgBackground = new Image(skinUI, "buttonUp");
        labName = new Label(" " + i + "." + name, skinUI);
        labScore = new Label("" + score + " ", skinUI);
        this.add(imgBackground);
        this.add(labName);
        this.add(labScore);
        labScore.setAlignment(Align.right);

    }
}

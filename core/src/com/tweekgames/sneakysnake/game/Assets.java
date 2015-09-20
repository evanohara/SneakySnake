package com.tweekgames.sneakysnake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.tweekgames.sneakysnake.connections.LoadScoresConnector;
import com.tweekgames.sneakysnake.util.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvansDesktop on 3/21/2015.
 */
public class Assets implements Disposable, AssetErrorListener{

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    public static Preferences settings;
    public static String name;
    public static long highScore;
    public static List<UserScore> allTimeScores, monthlyScores, dailyScores;

    public static LoadScoresConnector loadScoresConnector;

    public static class UserScore {
        public String name;
        public long score;

        public UserScore (){
            this("NON", 0);
        }
        public UserScore(String name, long score){
            this.name = name;
            this.score = score;
        }
    }

    private Assets(){
    }

    public AssetSplash splash;

    public AssetBackground background;
    public AssetSnake snake;
    public AssetFruit fruit;
    public AssetShovel shovel;

    public AssetSounds sounds;
    public AssetMusic music;

    public void init(AssetManager assetManager){
        String path = new File("").getAbsolutePath();
        Gdx.app.log("Path: ", path);
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_GAME, TextureAtlas.class);
        // Load the Sounds
        assetManager.load("android/assets/sounds/tick.wav", Sound.class);
        assetManager.load("android/assets/sounds/ssmusicintro.wav", Music.class);
        assetManager.load("android/assets/sounds/ssmusic.wav", Music.class);
        assetManager.load("android/assets/sounds/digoutyourface.wav", Sound.class);
//        assetManager.load("sounds/tick.wav", Sound.class);
//        assetManager.load("sounds/digoutyourface.wav", Sound.class);
//        assetManager.load("sounds/ssmusicintro.wav", Music.class);
//        assetManager.load("sounds/ssmusic.wav", Music.class);

        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_GAME);

        // load settings
        settings = Gdx.app.getPreferences("settings");
        name = settings.getString("name", "SSS");
        highScore = settings.getLong("highScore", 0);

        // create game resource objects
        splash = new AssetSplash(atlas);
        background = new AssetBackground(atlas);
        snake = new AssetSnake(atlas);
        fruit = new AssetFruit(atlas);
        shovel = new AssetShovel(atlas);

        // HighScores
        allTimeScores = new ArrayList<UserScore>();
        monthlyScores = new ArrayList<UserScore>();
        dailyScores = new ArrayList<UserScore>();

        // Load the Scores
        loadScoresConnector = new LoadScoresConnector();
        loadScoresConnector.loadScores(allTimeScores,monthlyScores,dailyScores);

        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);
    }

    public class AssetSplash {
        public final Array<AtlasRegion> splash;

        public AssetSplash (TextureAtlas atlas){
            splash = new Array<AtlasRegion>();

            splash.add(atlas.findRegion("tweek1"));
            splash.add(atlas.findRegion("tweek2"));
            splash.add(atlas.findRegion("tweek3"));
            splash.add(atlas.findRegion("tweek4"));
            splash.add(atlas.findRegion("tweek5"));
            splash.add(atlas.findRegion("tweek6"));
            splash.add(atlas.findRegion("tweek7"));
            splash.add(atlas.findRegion("tweek8"));
        }
    }

    public class AssetBackground {
        public final AtlasRegion background;
        public final AtlasRegion backgroundLevel1;
        public final AtlasRegion backgroundLevel2;

        public AssetBackground (TextureAtlas atlas){
            background = atlas.findRegion("background");
            backgroundLevel1 = atlas.findRegion("backgroundlevel1");
            backgroundLevel2 = atlas.findRegion("backgroundlevel2");
        }
    }

    public class AssetFruit {
        public final AtlasRegion fruit;
        public final AtlasRegion powerFruit;

        public AssetFruit (TextureAtlas atlas){
            fruit = atlas.findRegion("fruit1");
            powerFruit = atlas.findRegion("fruit2");
        }
    }

    public class AssetShovel {
        public final AtlasRegion shovel;

        public AssetShovel (TextureAtlas atlas) {
            shovel = atlas.findRegion("shovel");
        }
    }

    public class AssetSnake {
        public final AtlasRegion snakeHeadStraightUp;
        public final AtlasRegion snakeHeadStraightDown;
        public final AtlasRegion snakeHeadStraightLeft;
        public final AtlasRegion snakeHeadStraightRight;
        public final AtlasRegion snakeHeadTurning1;
        public final AtlasRegion snakeHeadTurning2;
        public final AtlasRegion snakeHeadTurning3;
        public final AtlasRegion snakeHeadTurning4;
        public final AtlasRegion snakeHeadTurning5;
        public final AtlasRegion snakeHeadTurning6;
        public final AtlasRegion snakeHeadTurning7;
        public final AtlasRegion snakeHeadTurning8;
        public final AtlasRegion snakeBodyStraightVert;
        public final AtlasRegion snakeBodyStraightHor;
        public final AtlasRegion snakeBodyTurning1;
        public final AtlasRegion snakeBodyTurning2;
        public final AtlasRegion snakeBodyTurning3;
        public final AtlasRegion snakeBodyTurning4;
        public final AtlasRegion snakeTail;

        public AssetSnake (TextureAtlas atlas){
            snakeHeadStraightUp = atlas.findRegion("snakeHeadStraightUp");
            snakeHeadStraightDown = atlas.findRegion("snakeHeadStraightDown");
            snakeHeadStraightLeft = atlas.findRegion("snakeHeadStraightLeft");
            snakeHeadStraightRight = atlas.findRegion("snakeHeadStraightRight");
            snakeHeadTurning1 = atlas.findRegion("snakeHeadTurning1");
            snakeHeadTurning2 = atlas.findRegion("snakeHeadTurning2");
            snakeHeadTurning3 = atlas.findRegion("snakeHeadTurning3");
            snakeHeadTurning4 = atlas.findRegion("snakeHeadTurning4");
            snakeHeadTurning5 = atlas.findRegion("snakeHeadTurning5");
            snakeHeadTurning6 = atlas.findRegion("snakeHeadTurning6");
            snakeHeadTurning7 = atlas.findRegion("snakeHeadTurning7");
            snakeHeadTurning8 = atlas.findRegion("snakeHeadTurning8");
            snakeBodyStraightVert = atlas.findRegion("snakeBodyStraightVert");
            snakeBodyStraightHor = atlas.findRegion("snakeBodyStraightHor");
            snakeBodyTurning1 = atlas.findRegion("snakeBodyTurning1");
            snakeBodyTurning2 = atlas.findRegion("snakeBodyTurning2");
            snakeBodyTurning3 = atlas.findRegion("snakeBodyTurning3");
            snakeBodyTurning4 = atlas.findRegion("snakeBodyTurning4");
            snakeTail = atlas.findRegion("snake_tail");
        }
    }

    public class AssetSounds {
        public final Sound tick;
        public final Sound digOutYourFace;

        public AssetSounds(AssetManager am) {
            tick = am.get("android/assets/sounds/tick.wav", Sound.class);
            digOutYourFace = am.get("android/assets/sounds/digoutyourface.wav", Sound.class);
        }
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class AssetMusic {
        public final Music ssMusicIntro;
        public final Music ssMusic;

        public AssetMusic(AssetManager am) {
            ssMusicIntro = am.get("android/assets/sounds/ssmusicintro.wav", Music.class);
            ssMusic = am.get("android/assets/sounds/ssmusic.wav", Music.class);
        }
    }
}

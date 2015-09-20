package com.tweekgames.sneakysnake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tweekgames.sneakysnake.connections.HighScoreConnector;
import com.tweekgames.sneakysnake.game.objects.Background;
import com.tweekgames.sneakysnake.game.objects.Fruit;
import com.tweekgames.sneakysnake.game.objects.PowerFruit;
import com.tweekgames.sneakysnake.game.objects.Shovel;
import com.tweekgames.sneakysnake.game.objects.Snake;
import com.tweekgames.sneakysnake.screens.NewScoreScreen;
import com.tweekgames.sneakysnake.util.AudioManager;

/**
 * Created by EvansDesktop on 3/21/2015.
 */
public class World {
    public static final String TAG = World.class.getName();
    public static final float TICK_INITIAL = 1.0f;
    public float tickTime;
    public float tick;
    public float multiplier;
    public Game game;

    // player
    public Snake snake;

    // fruit
    public Fruit fruit;
    public PowerFruit powerFruit;
    public Shovel shovel;

    // decoration
    public Background background;

    public long score;

    public World(Game game){
        this.game = game;
        init();
    }

    private void init() {
        tick = TICK_INITIAL;
        tickTime = 0;
        background = new Background();
        snake = new Snake();
        fruit = new Fruit();
        powerFruit = new PowerFruit();
        shovel = new Shovel();
        score = 0;
        multiplier = 1.0f;
    }

    public void update (float deltaTime) {
        tickTime += deltaTime;
        while (tickTime > tick) {
            tickTime -= tick;
            snake.move();
            if (snake.head.position.x == fruit.position.x && snake.head.position.y == fruit.position.y){
                snake.eat();
                fruit.randomize();
                score += 100*multiplier;
            }
            if (snake.head.position.x == powerFruit.position.x && snake.head.position.y == powerFruit.position.y){
                snake.eat();
                powerFruit.randomize();
                score += 100*multiplier;
                tick -= tick*0.1;
                multiplier += 0.1f;
            }
            if (snake.head.position.x == shovel.position.x && snake.head.position.y == shovel.position.y){
                shovel.randomize();
                snake.getAShovel();
            }
            if (snake.checkBitten()){
                HighScoreConnector highScoreConnector = new HighScoreConnector(Assets.name, score);
                AudioManager.instance.stopMusic();
                game.setScreen(new NewScoreScreen(game, score));
            }
        }
    }

    public void goDown(){
        if (snake.head.level == 0) {
            background.goToLevel1();
        } else if (snake.head.level == 1) {
            background.goToLevel2();
        }
        Gdx.app.log("HeadLevel:" , "" + snake.head.level);
    }

    public void goUp(){
        if (snake.head.level == 1) {
            background.goToLevel0();
        } else if (snake.head.level == 2) {
            background.goToLevel1();
        }
        Gdx.app.log("HeadLevel:" , "" + snake.head.level);
    }

    public void render(SpriteBatch batch) {
        background.render(batch);
        snake.render(batch);
        fruit.render(batch);
        powerFruit.render(batch);
        shovel.render(batch);
    }
}

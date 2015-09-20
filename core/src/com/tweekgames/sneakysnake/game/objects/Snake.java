package com.tweekgames.sneakysnake.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tweekgames.sneakysnake.game.Assets;
import com.tweekgames.sneakysnake.util.AudioManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by EvansDesktop on 3/22/2015.
 */
public class Snake extends AbstractGameObject {

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;

    public int facingDirection;
    public int justCameFromDirection;
    public int cameFromTwoSpotsAgo;
    public boolean hasMoved;

    public int shovels;

    public SnakeHead head;
    public SnakeTail tail;
    public List<SnakePart> snakeParts;


    public Snake() {
        super();
        init();
    }

    private void init() {
        snakeParts = new ArrayList<SnakePart>();
        snakeParts.add(new SnakeHead(0, 1, 0));
        snakeParts.add(new SnakePart(0, 0, 0));
        snakeParts.add(new SnakePart(0, -1, 0));
        snakeParts.add(new SnakeTail(0, -2, 0));
        head = (SnakeHead) snakeParts.get(0);
        tail = (SnakeTail) snakeParts.get(3);
        facingDirection = UP;
        justCameFromDirection = DOWN;
        cameFromTwoSpotsAgo = DOWN;
        hasMoved = false;
        shovels = 0;
    }

    @Override
    public void render(SpriteBatch batch) {
        for (SnakePart part : snakeParts) {
            if (part.level == head.level)
                part.render(batch);
        }
    }

    public void turnLeft() {
        switch (facingDirection) {
            case LEFT:
                if (justCameFromDirection != DOWN) {
                    facingDirection = DOWN;
                    head.directionFacing = DOWN;
                }
                break;
            case UP:
                if (justCameFromDirection != LEFT) {
                    facingDirection = LEFT;
                    head.directionFacing = LEFT;
                }
                break;
            case RIGHT:
                if (justCameFromDirection != UP) {
                    facingDirection = UP;
                    head.directionFacing = UP;
                }
                break;
            case DOWN:
                if (justCameFromDirection != RIGHT) {
                    facingDirection = RIGHT;
                    head.directionFacing = RIGHT;
                }
                break;
        }
        head.updateTexture();
    }

    public void turnRight() {

        switch (facingDirection) {
            case LEFT:
                if (justCameFromDirection != UP) {
                    facingDirection = UP;
                    head.directionFacing = UP;
                }
                break;
            case UP:
                if (justCameFromDirection != RIGHT) {
                    facingDirection = RIGHT;
                    head.directionFacing = RIGHT;
                }
                break;
            case RIGHT:
                if (justCameFromDirection != DOWN) {
                    facingDirection = DOWN;
                    head.directionFacing = DOWN;
                }
                break;
            case DOWN:
                if (justCameFromDirection != LEFT) {
                    facingDirection = LEFT;
                    head.directionFacing = LEFT;
                }
                break;
        }
        head.updateTexture();
    }

    public void move() {
        int len = snakeParts.size() - 1;
        SnakePart before;
        SnakePart part;


        for (int i = len; i > 0; i--) {
            before = snakeParts.get(i - 1);
            part = snakeParts.get(i);
            part.position.x = before.position.x;
            part.position.y = before.position.y;
            part.level = before.level;
            part.directionFrom = before.directionFrom;
            part.directionFacing = before.directionFacing;
            if (part.getClass() != SnakeTail.class)
            part.reg = before.reg;
            if (part.isHidden)
                part.isHidden = false;
        }


        switch (facingDirection) {
            case UP:
                head.position.y += 1;
                break;
            case DOWN:
                head.position.y -= 1;
                break;
            case LEFT:
                head.position.x -= 1;
                break;
            case RIGHT:
                head.position.x += 1;
                break;
        }
        if (head.position.y > 4)
            head.position.y = -5;
        else if (head.position.y < -5)
            head.position.y = 4;
        else if (head.position.x > 4)
            head.position.x = -5;
        else if (head.position.x < -5)
            head.position.x = 4;

        cameFromTwoSpotsAgo = justCameFromDirection;
        head.directionFrom = justCameFromDirection;
        switch (facingDirection) {
            case LEFT:
                justCameFromDirection = RIGHT;
                break;
            case UP:
                justCameFromDirection = DOWN;
                break;
            case RIGHT:
                justCameFromDirection = LEFT;
                break;
            case DOWN:
                justCameFromDirection = UP;
                break;
        }
        tail.updateTexture();
        snakeParts.get(1).updateTexture();
        head.updateTexture();

    }

    public void eat() {
        SnakePart end = snakeParts.get(snakeParts.size() - 1);
        snakeParts.add(snakeParts.size() - 1, new SnakePart((int) end.position.x, (int) end.position.y, end.level));
        snakeParts.get(snakeParts.size() - 2).directionFrom = snakeParts.get(snakeParts.size() - 1).directionFrom;
        snakeParts.get(snakeParts.size() - 2).directionFacing = snakeParts.get(snakeParts.size() - 1).directionFacing;
        snakeParts.get(snakeParts.size() - 2).updateTexture();
        snakeParts.get(snakeParts.size() - 2).hideForATick();
    }

    public void getAShovel() {
        shovels++;
        if (shovels == 3)
            AudioManager.instance.play(Assets.instance.sounds.digOutYourFace);
    }

    public void digDown() {

        if (shovels > 0) {
            head.level += 1;
            shovels--;
        }
    }

    public void digUp() {
        if (shovels > 0) {
            head.level -= 1;
            shovels--;
        }
    }

    public boolean checkBitten() {
        int len = snakeParts.size();
        for (int i = 1; i < len; i++) {
            SnakePart part = snakeParts.get(i);
            if (part.position.x == head.position.x && part.position.y == head.position.y && part.level == head.level)
                return true;
        }
        return false;
    }

    public class SnakePart extends AbstractGameObject {

        public TextureRegion reg;
        public int directionFrom;
        public int directionFacing;
        public int level;
        private boolean isHidden;

        public SnakePart(int x, int y, int level) {
            this.position.x = x;
            this.position.y = y;
            this.origin.x = this.dimension.x / 2;
            this.origin.y = this.dimension.y / 2;
            this.reg = Assets.instance.snake.snakeBodyStraightVert;
            this.directionFacing = UP;
            this.directionFrom = DOWN;
            this.level = level;
            this.isHidden = false;
        }

        public SnakePart() {
            this.reg = Assets.instance.snake.snakeBodyStraightVert;
            this.isHidden = false;
        }


        @Override
        public void render(SpriteBatch batch) {
            if (!isHidden)
                batch.draw(reg.getTexture(),
                        position.x, position.y,
                        origin.x, origin.y,
                        dimension.x, dimension.y,
                        scale.x, scale.y,
                        rotation,
                        reg.getRegionX(), reg.getRegionY(),
                        reg.getRegionWidth(), reg.getRegionHeight(),
                        false, false);
        }

        public void updateTexture() {
            switch (cameFromTwoSpotsAgo) {
                case LEFT:
                    switch (justCameFromDirection) {
                        case UP:
                            reg = Assets.instance.snake.snakeBodyTurning2;
                            break;
                        case DOWN:
                            reg = Assets.instance.snake.snakeBodyTurning4;
                            break;
                        case LEFT:
                            reg = Assets.instance.snake.snakeBodyStraightHor;
                    }
                    break;
                case UP:
                    switch (justCameFromDirection) {
                        case RIGHT:
                            reg = Assets.instance.snake.snakeBodyTurning4;

                            break;
                        case UP:
                            reg = Assets.instance.snake.snakeBodyStraightVert;
                            break;
                        case LEFT:
                            reg = Assets.instance.snake.snakeBodyTurning3;
                            break;
                    }
                    break;
                case RIGHT:
                    switch (justCameFromDirection) {
                        case DOWN:
                            reg = Assets.instance.snake.snakeBodyTurning3;
                            break;
                        case RIGHT:
                            reg = Assets.instance.snake.snakeBodyStraightHor;
                            break;
                        case UP:
                            reg = Assets.instance.snake.snakeBodyTurning1;
                            break;
                    }
                    break;
                case DOWN:
                    switch (justCameFromDirection) {
                        case LEFT:
                            reg = Assets.instance.snake.snakeBodyTurning1;
                            break;
                        case DOWN:
                            reg = Assets.instance.snake.snakeBodyStraightVert;
                            break;
                        case RIGHT:
                            reg = Assets.instance.snake.snakeBodyTurning2;
                            break;
                    }
                    break;
            }
        }

        public void hideForATick() {
            isHidden = true;
        }
    }

    public class SnakeHead extends SnakePart {


        public SnakeHead(int x, int y, int level) {
            this.position.x = x;
            this.position.y = y;
            this.origin.x = this.dimension.x / 2;
            this.origin.y = this.dimension.y / 2;
            this.reg = Assets.instance.snake.snakeHeadStraightUp;
            this.directionFacing = UP;
            this.directionFrom = DOWN;
            this.level = level;
        }


        @Override
        public void render(SpriteBatch batch) {
            batch.draw(reg.getTexture(),
                    position.x, position.y,
                    origin.x, origin.y,
                    dimension.x, dimension.y,
                    scale.x, scale.y,
                    rotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
        }

        @Override
        public void updateTexture() {
            switch (justCameFromDirection) {
                case LEFT:
                    switch (facingDirection) {
                        case UP:
                            reg = Assets.instance.snake.snakeHeadTurning8;
                            break;
                        case RIGHT:
                            reg = Assets.instance.snake.snakeHeadStraightRight;
                            break;
                        case DOWN:
                            reg = Assets.instance.snake.snakeHeadTurning2;
                            break;
                    }
                    break;
                case UP:
                    switch (facingDirection) {
                        case RIGHT:
                            reg = Assets.instance.snake.snakeHeadTurning7;
                            break;
                        case DOWN:
                            reg = Assets.instance.snake.snakeHeadStraightDown;
                            break;
                        case LEFT:
                            reg = Assets.instance.snake.snakeHeadTurning4;
                            break;
                    }
                    break;
                case RIGHT:
                    switch (facingDirection) {
                        case DOWN:
                            reg = Assets.instance.snake.snakeHeadTurning5;
                            break;
                        case LEFT:
                            reg = Assets.instance.snake.snakeHeadStraightLeft;
                            break;
                        case UP:
                            reg = Assets.instance.snake.snakeHeadTurning3;
                            break;
                    }
                    break;
                case DOWN:
                    switch (facingDirection) {
                        case LEFT:
                            reg = Assets.instance.snake.snakeHeadTurning6;
                            break;
                        case UP:
                            reg = Assets.instance.snake.snakeHeadStraightUp;
                            break;
                        case RIGHT:
                            reg = Assets.instance.snake.snakeHeadTurning1;
                            break;
                    }
                    break;
            }
        }
    }

    public class SnakeTail extends SnakePart {


        public SnakeTail(int x, int y, int level) {
            this.position.x = x;
            this.position.y = y;
            this.origin.x = this.dimension.x / 2;
            this.origin.y = this.dimension.y / 2;
            this.reg = Assets.instance.snake.snakeTail;
            this.directionFacing = UP;
            this.directionFrom = DOWN;
            this.level = level;
        }


        @Override
        public void render(SpriteBatch batch) {
            batch.draw(reg.getTexture(),
                    position.x, position.y,
                    origin.x, origin.y,
                    dimension.x, dimension.y,
                    scale.x, scale.y,
                    rotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
        }

        @Override
        public void updateTexture() {
            switch (directionFacing) {
                case LEFT:
                    rotation = 90;
                    Gdx.app.log("The tail...", "Is facing left");
                    break;
                case UP:
                    rotation = 0;
                    Gdx.app.log("The tail...", "Is facing up");
                    break;
                case RIGHT:
                    rotation = -90;
                    Gdx.app.log("The tail...", "Is facing right");
                    break;
                case DOWN:
                    rotation = 180;
                    Gdx.app.log("The tail...", "Is facing down");
                    break;
            }

        }
    }
}

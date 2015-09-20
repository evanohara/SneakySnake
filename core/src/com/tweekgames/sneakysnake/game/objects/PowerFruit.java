package com.tweekgames.sneakysnake.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tweekgames.sneakysnake.game.Assets;

/**
 * Created by EvansDesktop on 3/28/2015.
 */
public class PowerFruit extends AbstractGameObject{
    public TextureRegion reg;

    public PowerFruit(){
        reg = Assets.instance.fruit.powerFruit;
        randomize();
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

    public void randomize() {
        position.x = MathUtils.random(-5,4);
        position.y = MathUtils.random(-5,4);
    }
}

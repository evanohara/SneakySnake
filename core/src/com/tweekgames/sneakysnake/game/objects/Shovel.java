package com.tweekgames.sneakysnake.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tweekgames.sneakysnake.game.Assets;

/**
 * Created by EvansDesktop on 4/5/2015.
 */
public class Shovel extends AbstractGameObject {
    public TextureRegion reg;

    public Shovel() {
        reg = Assets.instance.shovel.shovel;
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
        position.x = MathUtils.random(-5, 4);
        position.y = MathUtils.random(-5, 4);
    }
}

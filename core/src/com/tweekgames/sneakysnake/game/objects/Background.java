package com.tweekgames.sneakysnake.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tweekgames.sneakysnake.game.Assets;

/**
 * Created by EvansDesktop on 3/21/2015.
 */
public class Background extends AbstractGameObject {
    private TextureRegion regBackground;

    public Background() {
        super();
        init();
    }

    private void init() {
        dimension.set(11f, 11f);
        regBackground = Assets.instance.background.background;
        origin.set(dimension.x / 2, dimension.y / 2);
        position.set(-5.5f, -5.5f);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(regBackground.getTexture(),
                position.x, position.y,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                regBackground.getRegionX(), regBackground.getRegionY(),
                regBackground.getRegionWidth(), regBackground.getRegionHeight(),
                false, false);
    }

    public void goToLevel2() {
        regBackground = Assets.instance.background.backgroundLevel2;
    }
    public void goToLevel1(){
        regBackground = Assets.instance.background.backgroundLevel1;
    }

    public void goToLevel0(){
        regBackground = Assets.instance.background.background;
    }
}

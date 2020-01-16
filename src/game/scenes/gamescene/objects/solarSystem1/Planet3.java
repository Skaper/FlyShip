package game.scenes.gamescene.objects.solarSystem1;

import engine.GameEngine;
import engine.Scene;
import engine.gfx.Vector2;
import engine.gfx.Sprite;

public class Planet3 extends Planet{
    public Planet3(Scene scene, Vector2 position) {
        super(scene, position);
    }

    @Override
    public void setup(GameEngine gc) {
        isClear = true;
        setSkin(new Sprite("/images/planets/planet3.png", position));
    }
}

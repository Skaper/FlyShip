package game.scenes;

import engine.Camera;
import engine.GameEngine;
import engine.Renderer;

public class DefaultCamera extends Camera {

    public DefaultCamera(String targetTag) {
        super(targetTag);
    }

    @Override
    public void setup(GameEngine gc) {

    }

    @Override
    public void update(GameEngine gc, float dt) {
        if(target != null) {
            offX = target.getPosX() + target.getWidth() / 2f - gc.getWidth() / 2f;
            offY = target.getPosY() + target.getHeight() / 2f - gc.getHeight() / 2f;
            gc.getRenderer().setCamX(Math.round(offX));

            gc.getRenderer().setCamY(Math.round(offY));
        }

    }

}

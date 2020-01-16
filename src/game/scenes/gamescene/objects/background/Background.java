package game.scenes.gamescene.objects.background;

import engine.*;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;

public class Background extends GameObject {

    private Sprite background;


    public Background(Scene scene, Vector2 position) {
        super(scene,  position);
    }

    @Override
    public void setup(GameEngine gc) {
        layout = Layouts.BACKGROUND;
        background = new Sprite("/images/background_720.png", Vector2.zero());
    
    }
    @Override
    public void update(GameEngine gc, float dt) {

    }


    
    @Override
    public void render(GameEngine gc, Renderer r) {
        r.drawImageUI(background);
    }
}

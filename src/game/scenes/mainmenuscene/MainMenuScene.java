package game.scenes.mainmenuscene;

import engine.GameEngine;
import engine.Renderer;
import engine.Scene;
import engine.gfx.IColor;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import game.scenes.DefaultCamera;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainMenuScene extends Scene {
    Sprite sunButton;
    Sprite background;
    @Override
    public void setup(GameEngine gc) {
        gc.setMainCamera(new DefaultCamera(null));
        sunButton = new Sprite("/images/planets/sun_1.png", Vector2.zero());
        sunButton.scale(0.1f, 0.1f);
        sunButton.setPos(new Vector2(gc.getWidth()/2f - sunButton.getWidth()/2f, gc.getHeight()/2f - sunButton.getHeight()/2f));
        background = new Sprite("/images/background_720.png", Vector2.zero());

    }

    private float rotationSpeed = 10f;
    private float angle = 0f;
    private boolean showText = false;
    @Override
    public void update(GameEngine gc, float dt) {
        if(gc.getInput().isKeyDown(KeyEvent.VK_ENTER)){
            gc.setScene("game");
        }
        angle += rotationSpeed*dt;
        sunButton.rotate((int)angle);
        if(sunButton.inFocus(gc.getInput().getMouseX(), gc.getInput().getMouseY())){
            showText = true;
        }else {
            showText = false;
        }
        if(sunButton.isClicked(gc.getInput().isButtonDown(MouseEvent.BUTTON1), gc.getInput().getMouseX(), gc.getInput().getMouseY())){
            gc.setScene("game");
        }

    }

    @Override
    public void render(GameEngine gc, Renderer r) {
        r.drawImageUI(background);
        r.drawImage(sunButton);
        if(showText)r.drawText("ENTER", sunButton.position.x + 13,
                sunButton.position.y + sunButton.getHeight(), 5f, IColor.WHITE);
    }
}

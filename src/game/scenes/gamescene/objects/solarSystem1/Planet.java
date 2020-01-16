package game.scenes.gamescene.objects.solarSystem1;

import engine.GameEngine;
import engine.Renderer;
import engine.Scene;
import engine.gfx.IColor;
import engine.gfx.Vector2;
import engine.objects.GameObject;
import engine.objects.Layouts;

import java.awt.event.MouseEvent;

public  abstract  class Planet extends GameObject {
    private GameObject target = null;
    private double angle = 0;
    public float speed = 1f;
    public float orbitRadius;
    protected boolean isClear = false;
    public boolean isClear() {
        return isClear;
    }




    public Planet(Scene scene, Vector2 position) {
        super(scene, position);
        layout = Layouts.MAP;
        setTag("planet");
        angle = Math.random() * 360;
        speed = (float)Math.random() * 2f;
    }


    public void setTarget(GameObject target) {
        this.target = target;
    }
    @Override
    public void update(GameEngine gc, float dt) {
        move();
    }

    private void move(){
        if (target != null) {

            double rad = Math.toRadians(angle);
            setPos(new Vector2(Math.round(target.getCenterX()) - getWidth() / 2f + (Math.cos(rad)) * orbitRadius,
                    Math.round(target.getCenterY())- getHeight() / 2f + (Math.sin(rad)) * orbitRadius));
            angle += 0.01f * speed;
            if (angle >= 360) angle = 0;

        }
    }

    @Override
    public void render(GameEngine gc, Renderer r) {
        super.render(gc, r);
        if(isClear)r.drawText("CLEAR", position.x, position.y - 40, 4f, IColor.WHITE);
        else r.drawText("NOT CLEAR", position.x, position.y - 40, 4f, IColor.RED);
    }
}

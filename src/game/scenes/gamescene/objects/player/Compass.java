package game.scenes.gamescene.objects.player;

import engine.GameEngine;
import engine.Renderer;
import engine.Scene;
import engine.Time;
import engine.gfx.IColor;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;

public class Compass extends GameObject {

    private GameObject target = null;
    private Vector2 compassTarget = null;
    private double angle = 0;
    public float orbitRadius = 100f;
    private long lastScaleTime;
    private float currentScale = 0.5f;
    private float scaleChange = 0.1f;

    public Compass(Scene scene, Vector2 position) {
        super(scene, position);
    }

    @Override
    public void setup(GameEngine gc) {
        layout = Layouts.PLAYER;
        setSkin(new Sprite("/images/ship/compass.png", position));
        setScale(0.5f);
        lastScaleTime = Time.getMillis();
    }

    @Override
    public void update(GameEngine gc, float dt) {
        if(Time.getMillis() - lastScaleTime > 50){
            if(currentScale >= 1f){
                scaleChange = -0.1f;
            }
            if(currentScale <= 0.5f){
                scaleChange = 0.1f;
            }
            currentScale +=scaleChange;
            setScale(currentScale);
            lastScaleTime = Time.getMillis();
        }
    }

    @Override
    public void render(GameEngine gc, Renderer r) {
        move();
        super.render(gc, r);
        r.drawCircle(target.getCenterX(), target.getCenterY(), orbitRadius, IColor.WHITE);
    }

    private void move(){

        if (target != null && compassTarget != null) {
            angle = Math.atan2(compassTarget.y - target.getCenterY(), compassTarget.x - target.getCenterX());
            angle = angle * (180/Math.PI);
            if(angle < 0){
                angle = 360 - (-angle);
            }
            double rad = Math.toRadians(angle);
            setPos(new Vector2(Math.round(target.getCenterX()) - getWidth() / 2f + (Math.cos(rad)) * orbitRadius,
                    Math.round(target.getCenterY())- getHeight() / 2f + (Math.sin(rad)) * orbitRadius));

        }
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public void setCompassTarget(Vector2 target) {
        this.compassTarget = target;
    }

}

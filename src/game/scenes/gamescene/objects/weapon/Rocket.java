package game.scenes.gamescene.objects.weapon;

import engine.GameEngine;
import engine.Scene;
import engine.Time;
import engine.components.CollisionBox;
import engine.gfx.Sprite;
import engine.gfx.Vector2;
import engine.objects.GameObject;
import engine.objects.Layouts;

public class Rocket extends GameObject {
    private final int LIVE_TIME = 5000;



    protected float speed = 10f;
    private CollisionBox collisionBox;
    private long startTime;

    public GameObject getParent() {
        return parent;
    }

    protected GameObject parent;
    public Rocket(Scene scene, Vector2 position, GameObject parent) {
        super(scene, position);
        this.parent = parent;
    }

    @Override
    public void setup(GameEngine gc) {
        startTime = Time.getMillis();
        layout = Layouts.PLAYER;
        setSkin(new Sprite("/images/ship/laserPointBlue.png", position));
        position.x -= skin.getWidth()/2f;
        position.y -= skin.getHeight()/2f;
        collisionBox = new CollisionBox(this);
        addComponent(collisionBox);
    }

    @Override
    public void update(GameEngine gc, float dt) {
        moving();
        if(Time.getMillis() - startTime >= LIVE_TIME){
            setDead(true);
        }
    }

    protected int directionAngle = 0;
    public void setDirection(int angle){
        directionAngle = angle;
    }

    public void moving(){
        double rad = Math.toRadians(directionAngle);
        move(new Vector2(speed*Math.cos(rad), speed*Math.sin(rad)));
    }

    public void addSpeed(float value){speed+=value;};
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}

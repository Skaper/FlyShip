package game.scenes.gamescene.objects;

import engine.Scene;
import engine.components.CollisionBox;
import engine.GameEngine;
import engine.Time;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;

public class Rocket extends GameObject {
    private final int LIVE_TIME = 5000;
    public float speed = 10f;
    private CollisionBox collisionBox;
    public Rocket(Scene scene, Vector2 position) {
        super(scene, position);
    }

    private long startTime;


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

    public void moving(){
        double rad = Math.toRadians(directionAngle);
        move(new Vector2(speed*Math.sin(rad), -speed*Math.cos(rad)));
    }

    private int directionAngle = 0;
    public void setDirection(int angle){
        directionAngle = angle;
    }


}

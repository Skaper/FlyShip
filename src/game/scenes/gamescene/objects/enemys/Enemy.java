package game.scenes.gamescene.objects.enemys;

import engine.GameEngine;
import engine.Renderer;
import engine.Scene;
import engine.Time;
import engine.components.CollisionCircle;
import engine.components.Component;
import engine.gfx.Animation;
import engine.gfx.IColor;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;
import game.scenes.gamescene.objects.Rocket;

public abstract class  Enemy extends GameObject {
    private CollisionCircle collisionCircle;
    private Animation exp;
    public int health = 3;
    public float speed = 3;

    protected long lastFireTime;
    protected final long FIRE_TIME_OUT = 850;

    protected double angle = 0;
    protected float targetDist = Float.MAX_VALUE;

    protected GameObject target = null;
    protected boolean isPlayerInFocus = false;
    protected boolean hasSeenPlayer = false;
    protected boolean attackPlayer = false;

    public Enemy(Scene scene, Vector2 position) {
        super(scene, position);
    }

    public abstract void enemySetup(GameEngine gc);
    public abstract void enemyUpdate(GameEngine gc, float dt);

    @Override
    public void setup(GameEngine gc) {
        layout = Layouts.NPC;
        setTag("enemy1");
        setSkin(new Sprite("/images/ship/enemy_smart.png", position));

        exp = new Animation("/animations/explosion",10f, position);
        collisionCircle = new CollisionCircle(this);

        addComponent(collisionCircle);
        lastFireTime = Time.getMillis();

        CollisionCircle sensorCircle = new CollisionCircle(this, 100);
        sensorCircle.isTrigger = true;
        addComponent(sensorCircle);
        enemySetup(gc);
    }

    @Override
    public void update(GameEngine gc, float dt) {
        if(health <= 0){
            removeComponent(collisionCircle.getTag());
            Sprite img = exp.next(dt);
            if(img != null){
                setSkin(img);
            }else{
                setDead(true);
            }
        }else {
            fire(gc);
            enemyUpdate(gc, dt);
        }
    }

    protected double orbitAngle = 0;
    private float orbitDistance = 200;
    public void setOrbitDistance(float dist){
        orbitDistance = dist;
    }
    public float getOrbitDistance(){
        return orbitDistance;
    }
    protected void orbitMovement(float radius){
        if (target != null) {

            double rad = Math.toRadians(orbitAngle);
            setPos(new Vector2(Math.round(target.getCenterX()) - getWidth() / 2f + (Math.cos(rad)) * orbitDistance,
                    Math.round(target.getCenterY())- getHeight() / 2f + (Math.sin(rad)) * orbitDistance));
            orbitAngle += 0.1f * speed;
            if (orbitAngle >= 360) orbitAngle = 0;

        }
    }

    protected void moveToTarget(){
        if(target != null) {

            double rad = Math.toRadians(angle);
            if (Math.round(targetDist) > 305) {
                move(new Vector2(speed * Math.cos(rad), speed * Math.sin(rad)));
                attackPlayer = false;
            } else {
                attackPlayer = true;


                move(new Vector2(-speed * Math.cos(rad), -speed * Math.sin(rad)));
            }
        }else{
            attackPlayer = false;
        }
    }

    protected void fire(GameEngine gc){
        if(attackPlayer) {
            if (Time.getMillis() - lastFireTime > FIRE_TIME_OUT) {
                Rocket rocket = new Rocket(scene, positionCenter) {
                    @Override
                    public void moving() {
                        double rad = Math.toRadians(angle);
                        move(new Vector2(speed * Math.cos(rad), speed * Math.sin(rad)));
                    }
                };
                rocket.setTag("enemyRocket");
                scene.addObject(rocket, gc);
                lastFireTime = Time.getMillis();
            }
        }
    }

    protected boolean moveToPoint(Vector2 vector2){
        float dist = calcDistance(vector2);
        double angle = calcAngle(vector2);
        if(dist > 0.1f){
            rotate((int)angle+90);
            double rad = Math.toRadians(angle);
            move(new Vector2(speed * Math.cos(rad), speed * Math.sin(rad)));
            return true;
        }else {
            return false;
        }
    }

    private double calcAngle(Vector2 vector2)
    {
        double angle = Math.atan2(vector2.y - positionCenter.y, vector2.x - positionCenter.x);
        angle = angle * (180/Math.PI);
        if(angle < 0){
            angle = 360 - (-angle);
        }
        return angle;
    }
    private float calcDistance(Vector2 vector2){
        double vx = Math.abs(positionCenter.x - vector2.x);
        double vy = Math.abs(positionCenter.y - vector2.y);
        return (float)(Math.sqrt(vx * vx + vy * vy));
    }
    protected void updateTarget(){
        if(target == null) return;
        angle = calcAngle(target.positionCenter);
        targetDist = calcDistance(target.positionCenter);
    }
    public void setTarget(GameObject target){
        this.target = target;
    }

    public void removeTarget(){
        this.target = null;
    }


    @Override
    public void render(GameEngine gc, Renderer r) {
        super.render(gc, r);
        r.drawText("H:"+health, position.x, position.y - 20, 2f, IColor.WHITE);
    }

    @Override
    public void onTrigger(Component component) {
        if(component.getParent().getTag().equals("player")) {
            if(!hasSeenPlayer) setTarget(component.getParent());
            isPlayerInFocus = true;
            hasSeenPlayer = true;
        }else {
            isPlayerInFocus = false;
        }

    }

    @Override
    public void collision(Component component) {
        if(component.getParent().getTag().equals("rocket")){
            health--;
            component.getParent().setDead(true);
        }
        if(component.getParent().getTag().equals("enemy1")){
                Vector2 p = position.getMinus(component.getParent().position);
                float length = p.length();
                p.normal();
                p.multiplyBy((float)(Math.sqrt(length/2)));
                move(p);


        }
    }

    public void setAngle(double angle){
        this.angle = angle;
        this.orbitAngle = angle;
    }
}

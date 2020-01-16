package game.scenes.gamescene.objects;

import engine.GameEngine;
import engine.Renderer;
import engine.Scene;
import engine.Time;
import engine.components.CollisionCircle;
import engine.components.Component;
import engine.gfx.Animation;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;


public class ShieldBlue extends GameObject {
    private float shieldPower = 100f;
    private Animation shield;
    public ShieldBlue(Scene scene, Vector2 position) {
        super(scene, position);
    }
    GameObject target = null;
    private final long REGEN_TIME_OUT = 200;
    private long lastRegenTime;
    private CollisionCircle circle;
    private boolean isActive = false;

    public void setTarget(GameObject target){
        this.target = target;
    }

    @Override
    public void setup(GameEngine gc) {
        layout = Layouts.EFFECTS;
        setTag("shield");
        shield = new Animation("/animations/shield3",20f, position);

        circle = new CollisionCircle(this, 100);
        circle.setActive(isActive);
        circle.isTrigger = true;
        circle.setRender(true);
        addComponent(circle);

        lastRegenTime = Time.getMillis();
    }

    @Override
    public void update(GameEngine gc, float dt) {
        if(shieldPower <= 0f) isActive = false;
        circle.setActive(isActive);
        if(isActive) {
            if (target != null) {
                setPos(new Vector2(target.getCenterX() - skin.getWidth() / 2f, target.getCenterY() - skin.getHeight() / 2f));
                Sprite img = shield.nextLoop(dt);
                img.scale(0.7f, 0.7f);

                if (img != null) {

                    setSkin(img);
                }

            }
        }
        if(shieldPower < 100f){
            if(Time.getMillis() - lastRegenTime >= REGEN_TIME_OUT){
                shieldPower += 1f;
                lastRegenTime = Time.getMillis();
            }
        }


    }



    public void setActive(boolean state){
        if(shieldPower <= 25f) return;
        isActive = state;
        circle.setActive(isActive);
    }

    public boolean isActive(){
        return isActive;
    }


    @Override
    public void render(GameEngine gc, Renderer r) {
        if(isActive){
            r.drawImage(skin);
        }


    }

    public int getPower(){
        return (int)shieldPower;
    }

    @Override
    public void onTrigger(Component component) {
        if(component.getParent().getTag().equals("enemyRocket")){
            component.getParent().setDead(true);
            if(shieldPower < 25){
                shieldPower = 0;
            }else {
                shieldPower -= 25f;
            }
        }
    }
}

package game.scenes.gamescene.objects.enemys;

import engine.Scene;
import engine.components.CollisionBox;
import engine.GameEngine;
import engine.Renderer;
import engine.components.Component;
import engine.gfx.Animation;
import engine.gfx.IColor;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;

public class EnemyExample extends GameObject {
    private CollisionBox collisionBox;
    public int health = 3;
    private Animation exp;
    public float speed = 1;
    public EnemyExample(Scene scene, Vector2 position) {
        super(scene, position);
    }

    @Override
    public void setup(GameEngine gc) {
        layout = Layouts.NPC;
        setTag("enemy");
        setSkin(new Sprite("/images/ship/enemy.png", position));
        exp = new Animation("/animations/explosion",10f, position);
        collisionBox = new CollisionBox(this);
        addComponent(collisionBox);

    }


    @Override
    public void update(GameEngine gc, float dt) {
        if(health <= 0){
            removeComponent(collisionBox.getTag());
            Sprite img = exp.next(dt);
            if(img != null){
                setSkin(img);
            }else{
                setDead(true);
            }
        }
        move(new Vector2(0, speed));



    }

    @Override
    public void render(GameEngine gc, Renderer r) {
        super.render(gc, r);
        r.drawText("H:"+health, position.x, position.y - 20, 2f, IColor.WHITE);
    }

    @Override
    public void collision(Component component) {
        if(component.getParent().getTag().equals("rocket")){
            health--;
            component.getParent().setDead(true);
        }
    }
}

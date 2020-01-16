package game.scenes.gamescene.objects.enemys;

import engine.GameEngine;
import engine.Scene;
import engine.Time;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;

public class EnemyBaseStation extends GameObject {
    public EnemyBaseStation(Scene scene, Vector2 position) {
        super(scene, position);
    }

    private long spawnTime = 2000;
    private long lastSpawnTime;
    private int maxSpawn = 5;
    @Override
    public void setup(GameEngine gc) {
        layout = Layouts.NPC;
        setTag("base01");
        setSkin(new Sprite("/images/enemy/spacebase/spacebase1_l1.png", position));
        lastSpawnTime = Time.getMillis();
        EnemyBaseDefender ebs = new EnemyBaseDefender(scene, position);
        ebs.setTarget(this);
        scene.addObject(ebs, gc);
    }

    @Override
    public void update(GameEngine gc, float dt) {
        if(Time.getMillis() - lastSpawnTime >= spawnTime){
        }
    }
}

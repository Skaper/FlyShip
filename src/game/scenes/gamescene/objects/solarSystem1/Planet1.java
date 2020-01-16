package game.scenes.gamescene.objects.solarSystem1;

import engine.GameEngine;
import engine.Scene;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import game.scenes.gamescene.objects.enemys.EnemyBaseDefender;

import java.util.ArrayList;

public class Planet1 extends Planet {
    public Planet1(Scene scene, Vector2 position) {
        super(scene, position);
    }


    ArrayList<GameObject> defenders;
    @Override
    public void setup(GameEngine gc) {
        defenders = new ArrayList<>();
        setSkin(new Sprite("/images/planets/planet1.png", position));
        float startOrbitDist = 100f;
        for(int i = 0; i < 3; i++){
            EnemyBaseDefender ebs = new EnemyBaseDefender(scene, new Vector2(position.x+startOrbitDist, position.y));
            ebs.setTarget(this);
            ebs.setAngle(Math.random() * 360);
            ebs.setOrbitDistance(startOrbitDist);
            startOrbitDist+=150f;
            scene.addObject(ebs, gc);
            defenders.add(ebs);
        }
    }

    @Override
    public void update(GameEngine gc, float dt) {
        super.update(gc, dt);
        if(defenders.size() > 0) {
            isClear = false;
            for (int i = 0; i < defenders.size(); i++) {
                GameObject obj = defenders.get(i);
                if (obj.isDead()) {
                    defenders.remove(i);
                    i--;
                }
            }
        }else {
            isClear = true;
        }
    }


}

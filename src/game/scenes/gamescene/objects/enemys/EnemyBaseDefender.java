package game.scenes.gamescene.objects.enemys;

import engine.GameEngine;
import engine.Scene;
import engine.gfx.Vector2;

public class EnemyBaseDefender extends Enemy {
    public EnemyBaseDefender(Scene scene, Vector2 position) {
        super(scene, position);
    }

    @Override
    public void enemySetup(GameEngine gc) {

    }

    @Override
    public void enemyUpdate(GameEngine gc, float dt) {
        updateTarget();
        if(!hasSeenPlayer) {
            skin.rotate((int) angle);
            orbitMovement(getOrbitDistance());
        }else {
            skin.rotate((int) angle + 90);
            moveToTarget();
        }
    }

}

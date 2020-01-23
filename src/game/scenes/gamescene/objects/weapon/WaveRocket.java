package game.scenes.gamescene.objects.weapon;

import engine.GameEngine;
import engine.Scene;
import engine.gfx.Vector2;
import engine.objects.GameObject;

public class WaveRocket extends Rocket{
    public WaveRocket(Scene scene, Vector2 position, GameObject parent) {
        super(scene, position, parent);
    }
    private float angle = 0;

    @Override
    public void update(GameEngine gc, float dt) {
        super.update(gc, dt);
        angle +=30f*dt;
        if(angle>=360)angle = 0f;

    }
    float amplitude = 14f;
    float frequency = 1f;

    public enum MovementType{
        left, right
    }
    private MovementType type = MovementType.left;
    public void setMovementType(MovementType type){
        this.type = type;
    }
    float x, y;
    @Override
    public void moving() {
        double rad = Math.toRadians(directionAngle);

        x = (float)(Math.cos(rad));
        y = (float)(Math.sin(rad));
        if(type == MovementType.left){
            frequency = 1f;
        }else {
            frequency = -1f;
        }
        float wobble = amplitude * (float)Math.cos(frequency * angle) * frequency;

        move(new Vector2(x * speed - y * wobble, y * speed + x * wobble));
    }
}

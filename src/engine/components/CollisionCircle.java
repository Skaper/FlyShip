package engine.components;

import engine.GameEngine;
import engine.Physics;
import engine.Renderer;
import engine.gfx.IColor;
import engine.objects.GameObject;
import static engine.GameSettings.RENDER_COMPONENTS;
public class CollisionCircle extends engine.components.Component {


    private float centerX, centerY;

    private float radius;

    private boolean isRadiusStatic = false;

    public CollisionCircle(GameObject parent){
        type = Type.CollisionCircle;
        tag = "collisionCircle";
        this.parent = parent;
        isRadiusStatic = false;
        radius =  ((parent.getHeight() >= parent.getWidth()) ? parent.getHeight()/2f : parent.getWidth()/2f);
        centerX = parent.getCenterX();
        centerY = parent.getCenterY();

    }

    public CollisionCircle(GameObject parent, float radius){
        type = Type.CollisionCircle;
        tag = "collisionCircle";
        this.parent = parent;
        isRadiusStatic = true;
        this.radius = radius;
        centerX = parent.getCenterX();
        centerY = parent.getCenterY();

    }

    @Override
    public void setup(GameEngine gc) {}

    @Override
    public void update(GameEngine gc, float dt) {
        if(!isRadiusStatic) radius =  ((parent.getHeight() >= parent.getWidth()) ? parent.getHeight()/2f : parent.getWidth()/2f);
        centerX = parent.getCenterX();
        centerY = parent.getCenterY();
        Physics.addCollisionComponents(this);
    }

    @Override
    public void render(GameEngine gc, Renderer r) {
        if(RENDER_COMPONENTS) {
            if (isRender) {
                r.drawCircle(centerX, centerY, radius, IColor.RED);
            }
        }
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        isRadiusStatic = true;
        this.radius = radius;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

}

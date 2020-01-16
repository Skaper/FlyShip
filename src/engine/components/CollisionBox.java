package engine.components;

import engine.GameEngine;
import engine.Physics;
import engine.Renderer;
import engine.gfx.IColor;
import engine.objects.GameObject;

import static engine.GameSettings.RENDER_COMPONENTS;

public class CollisionBox extends engine.components.Component {

    private int centerX, centerY;
    private int halfWidth;
    private int halfHeight;
    public int paddingLeft, paddingRight, paddingTop, paddingDown;



    public CollisionBox(GameObject parent){
        type = Type.CollisionBox;
        tag = "collisionBox";
        this.parent = parent;
        paddingLeft = 0;
        paddingRight = 0;
        paddingTop = 0;
        paddingDown = 0;
        halfWidth = (parent.getWidth() - paddingLeft - paddingRight)/2;
        halfHeight = (parent.getHeight() - paddingDown - paddingTop)/2;
        centerX = Math.round(parent.getPosX() - paddingLeft + (parent.getWidth() - paddingRight)/2f);
        centerY = Math.round(parent.getPosY() - paddingTop  + (parent.getHeight()- paddingDown)/2f);
    }

    @Override
    public void setup(GameEngine gc) {

    }

    @Override
    public void update(GameEngine gc, float dt) {
        halfWidth = (parent.getWidth() - paddingLeft - paddingRight)/2;
        halfHeight = (parent.getHeight() - paddingDown - paddingTop)/2;
        centerX = Math.round(parent.getPosX() + paddingLeft + (parent.getWidth() - paddingRight)/2f);
        centerY = Math.round(parent.getPosY() + paddingTop  + (parent.getHeight()- paddingDown)/2f);

        Physics.addCollisionComponents(this);
    }

    @Override
    public void render(GameEngine gc, Renderer r) {
        if(RENDER_COMPONENTS) {
            if (isRender) {
                r.drawRect(parent.getPosX() + paddingLeft,
                        parent.getPosY() + paddingTop,
                        parent.getPosX() + parent.getWidth() - paddingRight,
                        parent.getPosY() + parent.getHeight() - paddingDown,
                        IColor.RED);
                r.drawRect(centerX - 2, centerY - 2, centerX + 2, centerY + 2, IColor.BLUE);
            }
        }
    }

    public int getCenterX() {
        return centerX;
    }
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }
    public int getCenterY() {
        return centerY;
    }
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
    public int getWidth(){
        return parent.getWidth();
    }
    public int getHeight(){
        return parent.getHeight();
    }
    public float getPosX(){
        return parent.getPosX();
    }
    public float getPosY(){
        return parent.getPosY();
    }
    public int getHalfHeight() {
        return halfHeight;
    }
    public int getHalfWidth() {
        return halfWidth;
    }
    public void setRender(boolean render) {
        isRender = render;
    }

}

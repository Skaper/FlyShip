package engine.components;

import engine.GameEngine;
import engine.Renderer;
import engine.objects.GameObject;

public abstract class Component {
    public boolean isTrigger = false;
    protected boolean isActive = true;
    protected boolean isRender = true;
    protected String tag;
    protected GameObject parent;
    protected Type type = Type.None;
    public  enum Type{
        CollisionBox,
        CollisionCircle,
        None
    }

    public abstract void setup(GameEngine gc);
    public abstract void update(GameEngine gc, float dt);
    public abstract void render(GameEngine gc, Renderer r);

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public GameObject getParent() {
        return parent;
    }
    public void setParent(GameObject parent) {
        this.parent = parent;
    }
    public boolean isActive(){
        return isActive;
    }
    public void setActive(boolean isActive){
        this.isActive = isActive;
        setRender(isActive);
    }
    public void setRender(boolean render) {
        isRender = render;
    }
    public Type getType() {
        return type;
    }

}

package engine;

import engine.objects.GameObject;

public abstract class Camera {

    protected float offX, offY;
    protected String targetTag;
    protected Scene scene;
    private String lastTargetTag;
    protected GameObject target = null;

    public Camera(String tag) {
        this.targetTag = tag;
        offX = 0;
        offY = 0;
    }

    public void reload(GameEngine gc){
        offX = gc.getWidth() / 2f;
        offY = gc.getHeight() / 2f;
        target = null;
        targetTag = null;
    }

    public void setupContent(Scene scene){
        this.scene = scene;
        if(targetTag != null) target = scene.getGameObject(targetTag);
        else {
            target = null;
        }
    }

    public void updateContent(GameEngine gc, float dt){
        if(targetTag == null){
            gc.getRenderer().setCamX(0);
            gc.getRenderer().setCamY(0);
        }else{
            if(lastTargetTag != targetTag){
                target = scene.getGameObject(targetTag);
                lastTargetTag = this.targetTag;
            }
        }
    }
    public abstract void setup(GameEngine gc);
    public abstract void update(GameEngine gc, float dt);

    public String getTargetTag() {
        return targetTag;
    }
    public void setTargetTag(String targetTag) {
        lastTargetTag = this.targetTag;
        this.targetTag = targetTag;
    }
    public float getOffX() {
        return offX;
    }
    public float getOffY() {
        return offY;
    }
}

package engine.objects;
import engine.Scene;
import engine.GameEngine;
import engine.Renderer;
import engine.components.Component;
import engine.gfx.Vector2;
import engine.gfx.Sprite;

import java.util.ArrayList;

public abstract class GameObject implements Layouts{

	public Vector2 position;
	public Vector2 positionCenter;
	protected Scene scene;
	private long id;
	private String tag = "default";
	public int layout = Layouts.DEFAULT;
	protected Sprite skin;
	private boolean isVisible = false;
	private int width, height;
	private float scale = 1f;
	private float lastScale = 1f;
	private int currentAngle = 0;
	private int targetAngle = 0;
	private boolean dead = false;

	private ArrayList<engine.components.Component> components = new ArrayList<engine.components.Component>();

	public GameObject(Scene scene, Vector2 position){
		this.scene = scene;
		skin = new Sprite(0, 0, position);
		this.position = position.clone();
		positionCenter = position.clone();
	}

	public void setupComponents(GameEngine gc){
		for (engine.components.Component c : components){
			c.setup(gc);
		}
	}

	public void updateComponents(GameEngine gc, float dt){
        for (engine.components.Component c : components) {
            if (!c.isActive()) continue;
            c.update(gc, dt);
        }
	}

	public void renderComponents(GameEngine gc,  Renderer r){
		for (engine.components.Component c : components){
		    if(!c.isActive()) continue;
			c.render(gc, r);
		}
	}

	public void addComponent(engine.components.Component c){
		components.add(c);
	}

	public void removeComponent(String tag){
		for(int i = 0; i < components.size(); i++){
			if(components.get(i).getTag().equalsIgnoreCase(tag)){
				components.remove(i);
				i--;
			}
		}
	}

	public Component findComponent(){
		for(int i = 0; i < components.size(); i++){
			if(components.get(i).getTag().equalsIgnoreCase(tag)){
				return components.get(i);
			}
		}
		return null;
	}

	public abstract void setup(GameEngine gc);
	public abstract void update(GameEngine gc, float dt);

	public void render(GameEngine gc, Renderer r){
		if(skin!=null){
			r.drawImage(skin);
		}
	}

	public void setupContent(GameEngine gc){
		positionCenter.setPos(this.position.x + skin.getWidth()/2f, this.position.y  + skin.getHeight()/2f);

	}
	public void updateContent(GameEngine gc, float dt){
		if(skin!=null) {
			if (scale != lastScale) {
				skin.scale(scale, scale);
				this.height = skin.getHeight();
				this.width = skin.getWidth();
				lastScale = scale;
			}

			if(currentAngle != targetAngle) {
				int camCenterX = gc.getRenderer().getCamCenterX();
				int camCenterY = gc.getRenderer().getCamCenterY();
				int camHalfW = gc.getWidth() / 2;
				int camHalfH = gc.getHeight() / 2;
				if ((Math.abs(positionCenter.x - camCenterX) < width/2f + camHalfW) &&
						(Math.abs(positionCenter.y - camCenterY) < height/2f + camHalfH)) {
					skin.rotate(targetAngle);
					currentAngle = targetAngle;
				}
			}
			positionCenter.setPos(this.position.x + skin.getWidth()/2f, this.position.y  + skin.getHeight()/2f);

		}

	}
	public void renderContent(GameEngine gc, Renderer r){}

	public void collision(Component component){}
	public void onTrigger(Component component){}


	public void move(Vector2 position){
		this.position.plus(position);
		if(skin != null) {
			skin.setPos(this.position);
		}
		positionCenter.setPos(this.position.x + skin.getWidth()/2f, this.position.y  + skin.getHeight()/2f);
	}

	public void setPos(Vector2 position){
		this.position = position;
		if(this.skin != null) {
			this.skin.setPos(position);
		}
		positionCenter.setPos(this.position.x + skin.getWidth()/2f, this.position.y  + skin.getHeight()/2f);
	}

	public void rotate(int angle){
		this.targetAngle = angle;
	}

	protected void setSkin(Sprite skin) {
		this.skin = skin;
		this.height = skin.getHeight();
		this.width = skin.getWidth();
		this.skin.setPos(position);

	}
	public int getAngle() {
		return currentAngle;
	}
	public void setScale(float value) {
		scale = value;
	}
	public float getScale() {
		return scale;
	}
	public String getTag() {
		return tag;
	}
	public float getCenterX() {
		return positionCenter.x;
	}
	public float getCenterY() {
		return positionCenter.y;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public float getPosX() {
		return position.x;
	}
	public void setPosX(float posX) {
		this.position.x = posX;
	}
	public float getPosY() {
		return position.y;
	}
	public void setPosY(float posY) {
		this.position.y = posY;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public long getID() {
		return id;
	}
	public void setID(long id) {
		this.id = id;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	public boolean equals(GameObject obj) {
		if(obj.getID() == id){
			return true;
		}
		return false;
	}
}


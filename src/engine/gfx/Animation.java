package engine.gfx;

import engine.ResourceLoader;
import java.util.ArrayList;

public class Animation {
	public Vector2 position;
	private float speed;
	private int tiles;
	private float currentTile;
	private Sprite currentSprite;
	private ArrayList<Sprite> animationImgs = new ArrayList<Sprite>();
	private String path;

	public Animation() {
		this.speed = Float.MIN_VALUE;
		this.position = new Vector2();
		this.position.x = Float.MIN_VALUE;
		this.position.y = Float.MIN_VALUE;
		this.currentTile = 0;
		this.currentSprite = null;
	}

	public Animation(String dirPath, float speed, Vector2 position){
		this.speed = speed;
		this.position = position;
		this.path = dirPath;
		ArrayList<Image> tempImgs = ResourceLoader.getAnimation(dirPath);
		for(int i = 0; i < tempImgs.size(); i++){
            Sprite sprite = new Sprite(tempImgs.get(i));
            sprite.setPos(position);
			animationImgs.add(sprite);
		}
		currentTile = 0;
		tiles = size();
		currentSprite = animationImgs.get(0);
	}

	public void scale(float value){
		if(tiles > 0) {
			ArrayList<Image> tempImgs = ResourceLoader.getAnimation(path);
			animationImgs.clear();
			for(int i = 0; i < tempImgs.size(); i++){
				Sprite sprite = new Sprite(tempImgs.get(i));
				sprite.scale(value, value);
				animationImgs.add(sprite);
			}
		}else{
			currentSprite.scale(value, value);
		}
	}

	public Sprite nextLoop(float dt) {
		currentTile += dt * speed;
		if(currentTile >= tiles) {
			currentTile = 0;
		}
		if(tiles > 0) {
			return animationImgs.get((int) currentTile);
		}else{
			return currentSprite;
		}
	}
	public Sprite next(float dt) {
		currentTile += dt * speed;
		if(currentTile > tiles-1){
			return null;
		}
 		if(currentTile >= tiles) {
			currentTile = 0;
		}

		if(tiles > 0) {
			return animationImgs.get((int) currentTile);
		}else{
			return currentSprite;
		}
	}

	public void setSpeed(float speed){
		this.speed = speed;
	}
	public void setPos(Vector2 position){
		if(tiles > 0) {
			for(int i = 0; i < animationImgs.size(); i++){
				animationImgs.get(i).setPos(position);
			}
		}else{
			currentSprite.setPos(position);
		}
	}
	public int size(){
		return animationImgs.size();
	}
	
}

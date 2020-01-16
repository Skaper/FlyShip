package engine;

import java.util.ArrayList;

import engine.objects.GameObject;
import java.util.Collections;
import java.util.Comparator;
public abstract class Scene {

	private long lastObjectID;
	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	public abstract void setup(GameEngine gc);
	public abstract void update(GameEngine gc, float dt);
	public abstract void render(GameEngine gc, Renderer r);

	public Scene(){
		lastObjectID = 0;
	}
	
	public void addObject(GameObject obj, GameEngine gc) {
		objects.add(obj);
		obj.setID(lastObjectID);
		obj.setVisible(true);
		obj.setup(gc);
		obj.setupContent(gc);
		obj.setupComponents(gc);
		lastObjectID +=1;
		Collections.sort(objects, new SortLayouts());
	}

	public void removeObject(GameObject obj){
		for(int i = 0; i < objects.size(); i++){
			if(objects.get(i).getID() == obj.getID()){
				obj.setVisible(false);
				objects.remove(i);
			}
		}
	}

	public GameObject getGameObject(String tag){
		for(int i=0; i < objects.size(); i++){
			GameObject gameObject = objects.get(i);
			if(gameObject.getTag().equalsIgnoreCase(tag)) return gameObject;
		}
		return null;
	}


	
	public void setupObjects(GameEngine gc) {
		
		for(int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			object.setupContent(gc);
			object.setup(gc);
			object.setupComponents(gc);
			if(object.isDead()) {
				objects.remove(i);
				i--;
			}
		}
		
	}

	public void updateObjects(GameEngine gc, float dt) {

		for(int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			object.update(gc, dt);
			object.updateContent(gc, dt);
			object.updateComponents(gc, dt);
			if(object.isDead()) {
				objects.remove(i);
				i--;
			}
		}


	}

	public void renderObjects(GameEngine gc, Renderer r) {
		for(GameObject obj : objects) {
			obj.renderContent(gc, r);
			obj.render(gc, r);

			obj.renderComponents(gc, r);
		}

	}

	public void destroy(){
		objects = new ArrayList<>();
	}
}

class SortLayouts implements Comparator<GameObject>{
	
	 public int compare(GameObject a, GameObject b)
	 { 
	     return a.layout - b.layout; 
	 } 
}
package engine;

import java.util.HashMap;

public abstract class ScenesManager {
    private HashMap<String, Scene> sceneList = new HashMap<>();
    private String currentSceneTag;

    public abstract void setup(GameEngine gc);

    public void addScene(String tag, Scene scene){
        sceneList.put(tag, scene);
    }

    public Scene getNextScene(String tag){
        if(sceneList.containsKey(tag)){
            currentSceneTag = tag;
            return sceneList.get(tag);
        }
        return null;
    }

    public Scene getMainScene(){
        if (currentSceneTag != null && sceneList.containsKey(currentSceneTag)) return sceneList.get(currentSceneTag);
        return null;
    }

    public String getCurrentSceneTag(){
        if (currentSceneTag != null) return currentSceneTag;
        return null;
    }

    public void setMainScene(String tag){
        currentSceneTag = tag;
    }

}

package game.scenes;

import engine.*;
import game.scenes.DefaultCamera;
import game.scenes.gamescene.GameScene;
import game.scenes.mainmenuscene.MainMenuScene;

public class ScenesController extends ScenesManager {
    @Override
    public void setup(GameEngine gc) {
        Scene gameScene = new GameScene();
        Scene mainMenu = new MainMenuScene();
        addScene("game", gameScene);
        addScene("menu", mainMenu);
        setMainScene("menu");
    }
}

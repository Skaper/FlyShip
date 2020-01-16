package game;

import engine.GameEngine;
import engine.ResourceLoader;
import engine.SettingLoader;
import game.scenes.ScenesController;

public class GameManager {
    public static void main(String args[]) {
        ResourceLoader.Load();
        SettingLoader.Load();
        GameEngine gc = new GameEngine(new ScenesController());
        gc.start();
    }
}

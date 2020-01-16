package game.scenes.gamescene;

import engine.*;
import engine.gfx.Vector2;
import engine.sfx.SoundClip;
import game.scenes.DefaultCamera;
import game.scenes.gamescene.objects.background.Background;
import game.scenes.gamescene.objects.enemys.EnemyBaseDefender;
import game.scenes.gamescene.objects.enemys.EnemyExample;
import game.scenes.gamescene.objects.enemys.EnemyBaseStation;
import game.scenes.gamescene.objects.player.Player;
import game.scenes.gamescene.objects.solarSystem1.Sun;

import java.awt.event.KeyEvent;

public class GameScene extends Scene {
    private static Camera mainCamera;
    private Background background;
    private Player player;
    private EnemyExample enemyExample;
    private EnemyBaseDefender enemyForTest;
    private SoundClip backLoop;

    @Override
    public void setup(GameEngine gc) {
        gc.setMainCamera(new DefaultCamera("player"));
        //gc.getMainCamera().setTargetTag("player");
        background = new Background(this, Vector2.zero());
        addObject(background, gc);

        Sun sun = new Sun(this, new Vector2(1000, -500));
        sun.setScale(0.5f);
        addObject(sun, gc);

        EnemyBaseStation baseStation = new EnemyBaseStation(this, new Vector2(400, 300));
        addObject(baseStation, gc);

        player = new Player(this,new Vector2(100, -3000)); // 2200, -9700
        player.setTargetPlanets(sun.getPlanets());
        addObject(player, gc);

        enemyExample = new EnemyExample(this,new Vector2(100, 200));
        addObject(enemyExample, gc);


        EnemyBaseDefender ebs = new EnemyBaseDefender(this, Vector2.zero());
        addObject(ebs, gc);

        enemyForTest = new EnemyBaseDefender(this, new Vector2(300, 100)){
            @Override
            public void update(GameEngine gc, float dt) {
                setPos(new Vector2(gc.getInput().getMouseX() + gc.getMainCamera().getOffX(), gc.getInput().getMouseY()+gc.getMainCamera().getOffY()));
            }
        };


        //Звук
        backLoop = new SoundClip("/audio/main_loop.wav");
        //backLoop.loop();
        backLoop.setVolume(80);
        backLoop.loop();
    }

    @Override
    public void update(GameEngine gc, float dt) {
        if(gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)){
            gc.setScene("menu");
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_T)){
            gc.getMainCamera().setTargetTag("enemy");
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_G)){
            gc.getMainCamera().setTargetTag("player");
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_L)){
            if(!enemyForTest.isVisible()){
                addObject(enemyForTest, gc);
            }else{
                removeObject(enemyForTest);
            }

        }
    }

    @Override
    public void render(GameEngine gc, Renderer r) {

    }
}

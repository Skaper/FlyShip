package game.scenes.gamescene.objects.player;

import engine.Scene;
import engine.components.CollisionBox;
import engine.GameEngine;
import engine.Renderer;
import engine.components.Component;
import engine.gfx.Animation;
import engine.gfx.IColor;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;
import engine.sfx.SoundClip;
import game.scenes.gamescene.objects.enemys.EnemyExample;
import game.scenes.gamescene.objects.Rocket;
import game.scenes.gamescene.objects.ShieldBlue;
import game.scenes.gamescene.objects.solarSystem1.Planet;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Player extends GameObject {
	private CollisionBox collisionBox;
	private Animation exp;
	private int health = 300;
	private int angle = 0;
	private int speed = 5;
	private boolean isShieldActive = false;
	private ShieldBlue shield;
	private Compass compass;
	private SoundClip laserSound;

	public Player(Scene scene, Vector2 position) {
		super(scene, position);
	}

	@Override
	public void setup(GameEngine gc) {
		layout = Layouts.PLAYER;
		setTag("player");
		setSkin(new Sprite("/images/ship/ship_0.png", position));
		exp = new Animation("/animations/explosion",10f, position);
		collisionBox = new CollisionBox(this);
		collisionBox.setRender(true);
		laserSound = new SoundClip("/audio/laser.wav");
		addComponent(collisionBox);

		shield = new ShieldBlue(scene, position);
		shield.setTarget(this);
		shield.setTag("shield");
		scene.addObject(shield, gc);

		compass = new Compass(scene, position);
		compass.setTarget(this);
		scene.addObject(compass, gc);

	}

	@Override
	public void update(GameEngine gc, float dt) {
		if(planets.size() > 0){
			for(int i = 0; i < planets.size(); i++){
				Planet planet = planets.get(i);
				if(!planet.isClear()){
					compass.setCompassTarget(planets.get(i).positionCenter);
				}else{
					planets.remove(i);
					i--;
				}
			}
		}else{
			compass.setCompassTarget(Vector2.zero());
		}

		if(health <= 0){
			removeComponent(collisionBox.getTag());
			Sprite img = exp.next(dt);
			if(img != null){
				setSkin(img);
			}else{
				setDead(true);
			}
		}else {
			if (gc.getInput().isKey(KeyEvent.VK_A)) {
				angle -= 3;
				skin.rotate(angle);
			}
			if (gc.getInput().isKey(KeyEvent.VK_D)) {
				angle += 3;
				skin.rotate(angle);
			}
			if (gc.getInput().isKey(KeyEvent.VK_W)) {
				double rad = Math.toRadians(angle);
				move(new Vector2(speed * Math.sin(rad), -speed * Math.cos(rad)));
			}
			if (gc.getInput().isKey(KeyEvent.VK_S)) {
				double rad = Math.toRadians(angle);
				move(new Vector2(-speed * Math.sin(rad), speed * Math.cos(rad)));
			}
			if (gc.getInput().isKeyDown(KeyEvent.VK_F)) {
				shield.setActive(!shield.isActive());
			}
			if(gc.getInput().isKey(KeyEvent.VK_SHIFT)){
				speed = 25;
			}else{
				speed = 5;
			}

			if (gc.getInput().isButton(MouseEvent.BUTTON1)) {
				laserSound.play();
				Rocket rocket = new Rocket(scene, positionCenter);
				rocket.setDirection(angle);
				rocket.setTag("rocket");
				scene.addObject(rocket, gc);
			}
		}
	}

	@Override
	public void render(GameEngine gc, Renderer r) {
		// TODO Auto-generated method stub
		r.drawImage(skin);
		r.drawText("H:"+health, position.x, position.y - 20, 2f, IColor.WHITE);
		r.drawText("S:"+shield.getPower(), position.x, position.y, 3f, IColor.WHITE);

	}

	@Override
	public void collision(Component component) {
		String tag = component.getParent().getTag();
		if(tag.equals("enemy")){
			EnemyExample target = (EnemyExample)component.getParent();
			if(target.health != 0) health --;
			target.health = 0;
		}
		if(tag.equals("enemyRocket")){
			health --;
			component.getParent().setDead(true);
		}
	}

	private ArrayList<Planet> planets;
	public void setTargetPlanets(ArrayList<Planet> planets){
		this.planets = planets;
	}
}

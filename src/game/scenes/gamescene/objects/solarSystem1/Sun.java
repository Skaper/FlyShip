package game.scenes.gamescene.objects.solarSystem1;

import engine.GameEngine;
import engine.Renderer;
import engine.Scene;
import engine.Time;
import engine.components.CollisionBox;
import engine.gfx.IColor;
import engine.gfx.Vector2;
import engine.gfx.Sprite;
import engine.objects.GameObject;
import engine.objects.Layouts;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Sun extends GameObject {
    private long lastRotateTime;


    public Sun(Scene scene, Vector2 position) {
        super(scene, position);
    }

    private float[] orbits = {1000f, 2000f, 2800f, 3950f, 5500f, 7200f, 9500f};
    public ArrayList<Planet> planets;

    @Override
    public void setup(GameEngine gc) {
        layout = Layouts.MAP;
        setTag("sun");
        Sprite sunSprite = new Sprite("/images/planets/sun_1.png", positionCenter);
        setSkin(sunSprite);
        lastRotateTime = Time.getMillis();
        CollisionBox collisionBox = new CollisionBox(this);
        addComponent(collisionBox);


        planets = new ArrayList<>();
        Planet planet1 = new Planet1(scene, new Vector2(orbits[0], -500));
        planet1.setTarget(this);
        planet1.orbitRadius = orbits[0];
        scene.addObject(planet1,gc);

        Planet planet2 = new Planet2(scene, new Vector2(orbits[1], -500));
        planet2.setTarget(this);
        planet2.orbitRadius = orbits[1];
        scene.addObject(planet2,gc);

        Planet planet3 = new Planet3(scene, new Vector2(orbits[2], -500));
        planet3.setTarget(this);
        planet3.orbitRadius = orbits[2];
        scene.addObject(planet3,gc);

        Planet planet4 = new Planet4(scene, new Vector2(orbits[3], -500));
        planet4.setTarget(this);
        planet4.orbitRadius = orbits[3];
        scene.addObject(planet4,gc);

        Planet planet5 = new Planet5(scene, new Vector2(orbits[4], -500));
        planet5.setTarget(this);
        planet5.orbitRadius = orbits[4];
        scene.addObject(planet5,gc);

        Planet planet6 = new Planet6(scene, new Vector2(orbits[5], -500));
        planet6.setTarget(this);
        planet6.orbitRadius = orbits[5];
        scene.addObject(planet6,gc);

        Planet planet7 = new Planet7(scene, new Vector2(orbits[6], -500));
        planet7.setTarget(this);
        planet7.orbitRadius = orbits[6];
        scene.addObject(planet7,gc);

        planets.add(planet1);
        planets.add(planet2);
        planets.add(planet3);
        planets.add(planet4);
        planets.add(planet5);
        planets.add(planet6);
        planets.add(planet7);
    }
    private float currentAngle = 0f;
    private float rotationDirection = 1f;
    @Override
    public void update(GameEngine gc, float dt) {
        if(Time.getMillis() - lastRotateTime > 50){
            currentAngle+=rotationDirection;
            if(currentAngle>=360) currentAngle = 0;
            rotate((int)currentAngle);
            lastRotateTime = Time.getMillis();
        }


    }
    public void setRotationDirection(float dir){
        rotationDirection = dir;
    }

    public ArrayList<Planet> getPlanets(){
        return planets;
    }

    @Override
    public void render(GameEngine gc, Renderer r) {
        super.render(gc, r);
        r.drawCircle(positionCenter.x, positionCenter.y, orbits[0], IColor.BLUE);
        r.drawCircle(positionCenter.x, positionCenter.y, orbits[1], IColor.BLUE);
        r.drawCircle(positionCenter.x, positionCenter.y, orbits[2], IColor.BLUE);
        r.drawCircle(positionCenter.x, positionCenter.y, orbits[3], IColor.BLUE);
        r.drawCircle(positionCenter.x, positionCenter.y, orbits[4], IColor.BLUE);
        r.drawCircle(positionCenter.x, positionCenter.y, orbits[5], IColor.BLUE);
        r.drawCircle(positionCenter.x, positionCenter.y, orbits[6], IColor.BLUE);
    }
}

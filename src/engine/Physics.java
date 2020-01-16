package engine;

import engine.components.CollisionAdapter;
import engine.components.CollisionBox;
import engine.components.Component;

import java.util.ArrayList;

public class Physics {

    private static ArrayList<Component>  collisionList = new ArrayList<Component>();
    public static void addCollisionComponents(Component component){
        collisionList.add(component);
    }

    public static void update() {

        for (int i = 0; i < collisionList.size(); i++) {
            Component c0 = collisionList.get(i);
            if(!c0.isActive())continue;
            if(c0.getParent().isDead())continue;
            for (int j = i + 1; j < collisionList.size(); j++) {

                Component c1 = collisionList.get(j);
                if(c0.getParent().equals(c1.getParent())) continue;
                if(!c1.isActive())continue;
                if(c1.getParent().isDead())continue;
                CollisionAdapter ca0 = new CollisionAdapter(c0);
                if(ca0.isCollision(c1)){
                    if(c0.isTrigger){
                        if(!c1.isTrigger) c0.getParent().onTrigger(c1);
                    }else{
                        if(!c1.isTrigger) c0.getParent().collision(c1);
                    }
                    if(c1.isTrigger){
                        if(!c0.isTrigger) c1.getParent().onTrigger(c0);
                    }else {
                        if(!c0.isTrigger) c1.getParent().collision(c0);
                    }


                }
            }
        }
        collisionList.clear();

    }
}

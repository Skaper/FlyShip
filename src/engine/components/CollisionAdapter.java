package engine.components;

public class CollisionAdapter {

    private CollisionCircle circle0 = null;
    private CollisionBox box0 = null;

    public CollisionAdapter(Component component){
        if(component.getType() == Component.Type.CollisionCircle){
            circle0 = (CollisionCircle)component;
        }
        else if(component.getType() == Component.Type.CollisionBox){
            box0 = (CollisionBox) component;
        }
    }

    public boolean isCollision(Component component){
        if(circle0 != null ){
            if(component.getType() == Component.Type.CollisionCircle){
                return isCircleAndCircleCollision(circle0, (CollisionCircle)component);
            }
            if(component.getType() == Component.Type.CollisionBox){
                return isCircleAndBoxCollision(circle0, (CollisionBox)component);
            }
        }
        if(box0 != null){
            if(component.getType() == Component.Type.CollisionCircle){
                return isCircleAndBoxCollision((CollisionCircle)component, box0);
            }
            if(component.getType() == Component.Type.CollisionBox){
                return isBoxAndBoxCollision(box0, (CollisionBox)component);
            }
        }

        return false;
    }

    private boolean isCircleAndBoxCollision(CollisionCircle circle, CollisionBox box){
        float DeltaX = circle.getCenterX() - Math.max(box.getPosX(), Math.min(circle.getCenterX(), box.getPosX() + box.getWidth()));
        float DeltaY = circle.getCenterY() - Math.max(box.getPosY(), Math.min(circle.getCenterY(), box.getPosY() + box.getHeight()));
        return (DeltaX * DeltaX + DeltaY * DeltaY) < (circle.getRadius() * circle.getRadius() );
    }

    private boolean isCircleAndCircleCollision(CollisionCircle circle0, CollisionCircle circle1){
        float dx = circle0.getCenterX() - circle1.getCenterX();
        float dy = circle0.getCenterY() - circle1.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return (distance < circle0.getRadius() + circle1.getRadius());
    }

    private boolean isBoxAndBoxCollision(CollisionBox box0, CollisionBox box1){
       return  (Math.abs(box0.getCenterX() - box1.getCenterX()) < box0.getHalfWidth() + box1.getHalfWidth()) &&
               (Math.abs(box0.getCenterY() - box1.getCenterY()) < box0.getHalfHeight() + box1.getHalfHeight());

    }
}

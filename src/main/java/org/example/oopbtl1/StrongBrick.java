package org.example.oopbtl1;

import javafx.scene.paint.Color;

public class StrongBrick extends Brick {
    public StrongBrick(double x, double y, double hitPoints) {
        super(x,y);
        super.getRectangle().setFill(Color.DARKGRAY);
        super.setHitPoints(hitPoints);
    }
    public double getHitPoints(){
        return super.getHitPoints();
    }
    public void setHitPoints(double hitPoints){
        super.setHitPoints(hitPoints);
    }
}

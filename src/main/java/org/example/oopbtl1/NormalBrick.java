package org.example.oopbtl1;

import javafx.scene.paint.Color;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y, double hitPoints) {
        super(x, y);
        super.getRectangle().setFill(Color.CRIMSON);
        super.setHitPoints(hitPoints);
    }

}

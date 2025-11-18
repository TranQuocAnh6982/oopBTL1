package org.example.oopbtl1;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class Brick extends GameObject {
    @FXML
    private Rectangle brick;
    private double hitPoints;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Brick() {
    }

    public Brick(double X, double Y) {
        brick = new Rectangle(50, 20);
        brick.setLayoutX(X);
        brick.setLayoutY(Y);
    }

    public Rectangle getRectangle() {
        return brick;
    }

    public void takeHit() {
        hitPoints--;
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public double getX() {
        return brick.getLayoutX();
    }

    public double getY() {
        return brick.getLayoutY();
    }
}

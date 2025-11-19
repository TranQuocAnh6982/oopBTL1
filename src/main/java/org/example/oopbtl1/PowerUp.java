package org.example.oopbtl1;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public abstract class PowerUp extends MovableObject {
    private String type;
    private double duration = 0;
    private Circle shape;

    public PowerUp() {
    }

    public PowerUp(double x, double y) {
        shape = new Circle(10);
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        super.setX(x);
        super.setY(y);
        super.setDy(2);
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }

    public Circle getShape() { return shape; }

    public boolean checkCollision(Paddle paddle) {
        return getShape().getBoundsInParent().intersects(
                paddle.getRectangle().getBoundsInParent());
    }

    @Override
    public void move() {
        double Y = getShape().getLayoutY();
        Y += getDy();
        getShape().setLayoutY(Y);
        super.setY(Y);
    }

    public void applyEffect(GameManager manager) {
    }

    public void removeEffect(GameManager manager) {
    }
}
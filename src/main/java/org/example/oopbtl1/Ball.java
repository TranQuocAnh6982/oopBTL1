package org.example.oopbtl1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends MovableObject {
    private Circle ball;
    private final Color ORIGINAL_COLOR = Color.GREEN;
    private double directionX = 0, directionY = -2.5;
    private double speed = 0.3;

    public Ball(double radius, double X, double Y) {
        ball = new Circle(radius);
        ball.setFill(ORIGINAL_COLOR);
        ball.setLayoutX(X);
        ball.setLayoutY(Y);
        //THEM
        super.setX(X);
        super.setY(Y);
    }

    public Circle getCircle() {
        return ball;
    }
    //THEM
    public void setColor(Color color) {
        ball.setFill(color);
    }

    public void resetColor() {
        ball.setFill(ORIGINAL_COLOR);
    }
    //
    public boolean checkCollision(GameObject other) {
        if (other instanceof Paddle) {
            Paddle paddle = (Paddle) other;
            return getCircle().getBoundsInParent().intersects(
                    paddle.getRectangle().getBoundsInParent());

        }
        if (other instanceof Brick) {
            Brick brick = (Brick) other;
            return getCircle().getBoundsInParent().intersects(
                    brick.getRectangle().getBoundsInParent());
        }
        return false;
    }

    @Override
    public void move() {
        double dx = getDirectionX();
        double dy = getDirectionY();
        double X = ball.getLayoutX();
        double Y = ball.getLayoutY();

        if (X + dx < 0 + ball.getRadius() || X + dx > 640 - ball.getRadius()) {
            if (dx < 0 && dx > -5) dx -= speed;
            if (dx >= 0 && dx < 5) dx += speed;
            dx = -(dx);
            setDirectionX(dx);
        }
        if (Y + dy < 0 + ball.getRadius()) {
            dy = -(dy);
            setDirectionY(dy);
        }

        ball.setLayoutX(X + dx);
        ball.setLayoutY(Y + dy);
        //THEM
        super.setX(X + dx);
        super.setY(Y + dy);
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public double getX() { return ball.getLayoutX();
    }

    @Override
    public double getY() {
        return ball.getLayoutY();
    }

    public void setX(double x) {
        ball.setLayoutX(x);
    }

    public void setY(double y) {
        ball.setLayoutY(y);
    }
}
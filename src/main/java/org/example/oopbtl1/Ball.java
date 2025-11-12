package org.example.oopbtl1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends MovableObject {
    private Circle ball;
    private double directionX=0, directionY=-4;
    private double speed=1;

    public Ball(double radius, double X, double Y) {
        ball = new Circle(radius);
        ball.setFill(Color.GREEN);
        ball.setLayoutX(X);
        ball.setLayoutY(Y);
    }

    public Circle getCircle() {
        return ball;
    }

    public void bounceOff(GameObject other) {
    }

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

    public void move() {
        double dx = getDirectionX();
        double dy = getDirectionY();
        double X = ball.getLayoutX();
        double Y = ball.getLayoutY();
        if (X + dx < 0 + ball.getRadius() || X + dx > 640 - ball.getRadius()) {
            if(dx<0 && dx >-5) dx-=1;
            if(dx>=0 && dx<5) dx+=1;
            dx = -(dx +speed);
            setDirectionX(dx);
        }
        if (Y + dy < 0 + ball.getRadius() ) {
                dy=-(dy+speed);
            setDirectionY(dy);
        }
        ball.setLayoutX(X + dx);
        ball.setLayoutY(Y + dy);
        //  System.out.println("Ball position: " + ball.getLayoutX() + ", " + ball.getLayoutY());

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
    public double getX(){
        return ball.getLayoutX();
    }
    public double getY(){
        return ball.getLayoutY();
    }
    public void setX(double x){
        ball.setLayoutX(x);
    }
    public void setY(double y){
        ball.setLayoutY(y);
    }
}

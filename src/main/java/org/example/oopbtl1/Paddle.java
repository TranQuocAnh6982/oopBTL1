package org.example.oopbtl1;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends MovableObject {
    @FXML
    private Rectangle paddle;
    @FXML
    private Pane gamePane;
    private double speed = 20;
    private double currentPowerup;

    public Paddle(double x, double y) {
        paddle = new Rectangle(100, 20);
        paddle.setFill(Color.BLUE);
        paddle.setLayoutX(x);
        paddle.setLayoutY(y);

    }

    public Rectangle getRectangle(){
        return paddle;
    }

    public void moveLeft() {
        paddle.setLayoutX( Math.max(paddle.getLayoutX() - speed, 0));
    }

    public void moveRight() {
        paddle.setLayoutX(Math.min(paddle.getLayoutX() + speed, 640-100));
    }

    public void applyPowerup() {
    }

}

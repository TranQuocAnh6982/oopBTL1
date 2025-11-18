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
    private double speed = 7;

    private final double ORIGINAL_WIDTH = 100;
    private final Color ORIGINAL_COLOR = Color.BLUE;

    public Paddle(double x, double y) {
        paddle = new Rectangle(ORIGINAL_WIDTH, 20);
        paddle.setFill(ORIGINAL_COLOR);
        paddle.setLayoutX(x);
        paddle.setLayoutY(y);

        super.setWidth(ORIGINAL_WIDTH);
        super.setHeight(20);
        super.setX(x);
        super.setY(y);
    }

    public Rectangle getRectangle() {
        return paddle;
    }

    public void setColor(Color color) {
        paddle.setFill(color);
    }

    public void resetSizeAndColor() {
        paddle.setWidth(ORIGINAL_WIDTH);
        super.setWidth(ORIGINAL_WIDTH);
        paddle.setFill(ORIGINAL_COLOR);
    }

    public void moveLeft() {
        paddle.setLayoutX(Math.max(paddle.getLayoutX() - speed, 0));
        setX(paddle.getLayoutX());
    }

    public void moveRight() {
        paddle.setLayoutX(Math.min(paddle.getLayoutX() + speed, 640 - paddle.getWidth()));
        setX(paddle.getLayoutX());
    }

    public void setWidth(double width) {
        double gameWidth = 640;
        double maxWidth = Math.min(width, gameWidth * 0.4);

        paddle.setWidth(maxWidth);
        super.setWidth(maxWidth);
    }

    @Override
    public double getY() {
        return paddle.getLayoutY();
    }

    public void setY(double y) {
        paddle.setLayoutY(y);
    }

    @Override
    public double getX() {
        return paddle.getLayoutX();
    }

    public void setX(double x) {
        paddle.setLayoutX(x);
    }
}
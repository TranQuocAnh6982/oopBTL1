package org.example.oopbtl1;

import javafx.scene.paint.Color;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y);
        super.getShape().setFill(Color.YELLOWGREEN);
        super.setType("ExpandPaddle");
        super.setDuration(10.0);
    }

    @Override
    public void applyEffect(GameManager manager) {
        double originalWidth = manager.paddle.getRectangle().getWidth();
        double newWidth = originalWidth * 2;
        manager.paddle.setWidth(newWidth);
    }

    @Override
    public void removeEffect(GameManager manager) {
        manager.paddle.resetSizeAndColor();
    }
}
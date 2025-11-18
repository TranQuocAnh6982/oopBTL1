package org.example.oopbtl1;

import javafx.scene.paint.Color;

public class TripleBallPowerUp extends PowerUp {

    public TripleBallPowerUp(double x, double y) {
        super(x, y);
        super.getShape().setFill(Color.DARKORANGE);
        super.setType("TripleBall");
     }

    @Override
    public void applyEffect(GameManager manager) {
        manager.createTripleBall();
    }
}
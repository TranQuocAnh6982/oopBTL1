package org.example.oopbtl1;

import javafx.scene.paint.Color;

public class DoubleDamagePowerUp extends PowerUp {

    public DoubleDamagePowerUp(double x, double y) {
        super(x, y);
        super.getShape().setFill(Color.CRIMSON);
        super.setType("DoubleDamage");
        super.setDuration(15.0);
    }

    @Override
    public void applyEffect(GameManager manager) {
        manager.setDamageMultiplier(2);
        for (Ball ball : manager.getActiveBalls()) {
            ball.setColor(Color.RED);
        }
    }

    @Override
    public void removeEffect(GameManager manager) {
        manager.setDamageMultiplier(1);
        for (Ball ball : manager.getActiveBalls()) {
            ball.resetColor();
        }
    }
}
package org.example.oopbtl1;

public abstract  class MovableObject extends GameObject {
    private double dx, dy;

    public void move(){}

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}

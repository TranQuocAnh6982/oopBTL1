package org.example.oopbtl1;

    import javafx.fxml.FXML;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Rectangle;

    public class Brick extends GameObject {
        @FXML private Rectangle brick;
        private double hitPoints;
        private String type;

        public Brick(){
        }
        public Brick(double X,  double Y){
            brick=new Rectangle(50, 30);
            brick.setFill(Color.RED);
            brick.setLayoutX(X);
            brick.setLayoutY(Y);
        }
        public Rectangle getRectangle(){
            return brick;
        }
        public void takeHit() {
            hitPoints--;
        }
        public boolean isDestroyed() {
            return hitPoints <= 0;
        }


    }

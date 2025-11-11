package org.example.oopbtl1;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    @FXML
    private Pane gamePane;
    @FXML
    private Label gameTitle;
    @FXML
    private Button startButton;
    @FXML
    private Button homeButton;
    @FXML
    private Label scoreLable;
    @FXML
    private Pane Menu;
    private List<Brick> bricks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private int score;
    private int lives;
    private String GameState;
    private AnimationTimer timer;
    private double ballY=420;
    Ball ball=new Ball(12, 320, ballY);
    Paddle  paddle=new Paddle(270, 450);
    public void startGame() {

    }
    public void updateGame() {

    }
    public void onClickButtonStart(){
        Menu.setVisible(false);
        gamePane.setVisible(true);
        score=0;
        lives=3;
        gamePane.setFocusTraversable(true);
        gamePane.setOnKeyPressed(this::handleInput);
        gamePane.requestFocus();
    }
    public void onClickButtonHome(){
        timer.stop();
        Menu.setVisible(true);
        gamePane.setVisible(false);
    }
    public void handleInput(KeyEvent e) {
        if(e.getCode()==KeyCode.A){
            paddle.moveLeft();
        }
        if(e.getCode()==KeyCode.D){
            paddle.moveRight();
        }
        if(e.getCode()==KeyCode.W){
            timer.start();
        }
    }

    public void checkCollisions() {
        double dx = ball.getDirectionX();
        double dy = ball.getDirectionY();
        if(ball.checkCollision(paddle)) {
            if(dx<0 && dx >-5) dx-=1;
            if(dx>=0 && dx<5) dx+=1;
            ball.setDirectionX(-(dx+ball.getSpeed()));
            ball.setDirectionY(-(dy+ball.getSpeed()));
            System.out.println("ball collision paddle");
        }
        List<Brick> bricksToRemove=new ArrayList<>();
        for(Brick brick:bricks){
            if(ball.checkCollision(brick)) {
                if(dx<0 && dx >-5) dx-=1;
                if(dx>=0 && dx<5) dx+=1;
                ball.setDirectionX(-(dx+ball.getSpeed()));
                ball.setDirectionY(-(dy+ball.getSpeed()));
                brick.takeHit();
                if(brick.isDestroyed())bricksToRemove.add(brick);
                score+=10;
                scoreLable.setText("Score: " + score);
                gamePane.getChildren().remove(brick.getRectangle());
                System.out.println("ball collision brick");
            }
        }
        bricks.removeAll(bricksToRemove);
    }

    public void GameOver() {
            gamePane.getChildren().remove(ball.getCircle());
            gamePane.getChildren().remove(paddle.getRectangle());
            for (Brick  brick : bricks) {
                gamePane.getChildren().remove(brick.getRectangle());
            }
    }
    public void initialize() {
        gamePane.getChildren().add(ball.getCircle());

        gamePane.getChildren().add(paddle.getRectangle());

        for (int i = 0; i < 44; i++) {
            if(Math.random()*2<1) {
                Brick normalbrick = new NormalBrick((i % 11) * 55 + 17.5, (i / 11) * 25 + 50, 1);
                bricks.add(normalbrick);
                gamePane.getChildren().add(normalbrick.getRectangle());
            }
            else {
                Brick strongbrick=new StrongBrick((i % 11) * 55 + 17.5, (i / 11) * 25 + 50, 2);
                bricks.add(strongbrick);
                gamePane.getChildren().add(strongbrick.getRectangle());
            }
        }
        gamePane.setVisible(false);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkCollisions();
                ball.move();
                if(ball.getY()>480-ball.getCircle().getRadius()){
                    timer.stop();
                    ball.setX(320);
                    ball.setY(ballY);
                    lives--;
                    System.out.println(lives);
                    if(lives==0){
                        GameOver();
                    }
                }
            }
        };

    }
}

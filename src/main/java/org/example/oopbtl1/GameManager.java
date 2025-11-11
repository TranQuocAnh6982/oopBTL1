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
    private List<Brick> bricks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private int score;
    private int lives;
    private String GameState;
    private AnimationTimer timer;

    Ball ball=new Ball(15, 320, 310);
    Paddle  paddle=new Paddle(270, 450);
    public void startGame() {

    }
    public void updateGame() {

    }
    public void onClickButtonStart(){
        ball.setX(320);
        ball.setY(310);
        paddle=new Paddle(270, 450);

        score=0;
        lives=3;
        scoreLable.setText("Score: " + score);
        gameTitle.setVisible(false);
        startButton.setVisible(false);
        homeButton.setVisible(true);
        scoreLable.setVisible(true);
        score=0;
        gamePane.getChildren().add(ball.getCircle());

        gamePane.getChildren().add(paddle.getRectangle());

        for (int i = 0; i < 44; i++) {
            Brick brick = new Brick((i % 11) * 55 + 17.5, (i / 11) * 40 +50);
            bricks.add(brick);
            gamePane.getChildren().add(brick.getRectangle());
        }
        gamePane.setFocusTraversable(true);
        gamePane.setOnKeyPressed(this::handleInput);
        gamePane.requestFocus();
    }
    public void onClickButtonHome(){
        timer.stop();
        scoreLable.setVisible(false);
        gamePane.getChildren().remove(ball.getCircle());
        gamePane.getChildren().remove(paddle.getRectangle());
        for (Brick  brick : bricks) {
            gamePane.getChildren().remove(brick.getRectangle());
        }
        gameTitle.setVisible(true);
        startButton.setVisible(true);
        homeButton.setVisible(false);
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
        if(ball.checkCollision(paddle)) {
            ball.setDirectionX(-(ball.getDirectionX()+ball.getSpeed()));
            ball.setDirectionY(-(ball.getDirectionY()+ball.getSpeed()));
            System.out.println("ball collision paddle");
        }
        List<Brick> bricksToRemove=new ArrayList<>();
        for(Brick brick:bricks){
            if(ball.checkCollision(brick)) {
                ball.setDirectionX(-(ball.getDirectionX()+ball.getSpeed()));
                ball.setDirectionY(-(ball.getDirectionY()+ball.getSpeed()));
                bricksToRemove.add(brick);
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
        scoreLable.setVisible(false);
        homeButton.setVisible(false);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkCollisions();
                ball.move();
                if(ball.getY()>480-ball.getCircle().getRadius()){
                    timer.stop();
                    ball.setX(320);
                    ball.setY(310);
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

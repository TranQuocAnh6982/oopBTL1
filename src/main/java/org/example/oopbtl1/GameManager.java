package org.example.oopbtl1;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    @FXML
    private Pane gamePane;
    @FXML
    private Button homeButton;
    @FXML
    private Label scoreLable;
    @FXML
    private Pane Menu;
    @FXML
    private Label gameTitle;
    @FXML
    private Button startButton;
    @FXML
    private Pane EndGame;
    @FXML
    private Label GameOverLable, WinLable;
    @FXML
    private Button restartButton;
    @FXML
    private Pane setting;
    @FXML
    private Button settingButton;
    @FXML
    private Button exitButton;
    private int currentLevel = 1;
    private List<Brick> bricks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private int score;
    private int lives;
    private String GameState;
    private AnimationTimer timer;
    private boolean ballAttachToPaddle = true;
    private MediaPlayer backgroundPlayer;
    private MediaPlayer ballPlayer;
    private Media backgroundMedia;
    private Media ballMedia;
    private double ballY = 420;
    Ball ball = new Ball(12, 320, ballY);
    Paddle paddle = new Paddle(270, 450);

    public void startGame() {
    }

    public void updateGame() {
    }

    public void onClickButtonStart() {
        ballAttachToPaddle = true;
        ball.setDirectionX(0);
        ball.setDirectionY(-4);
        for (int i = 0; i < 44; i++) {
            double x = (i % 11) * 55 + 17.5;
            double y = (i / 11) * 25 + 50;
            Brick brick;
            if (currentLevel == 1) {
                brick = new NormalBrick(x, y, 1);
            } else if (currentLevel== 2) {
                brick = Math.random() < 0.5 ? new StrongBrick(x, y, 2)
                        : new NormalBrick(x, y, 2);
            } else {
                brick = new StrongBrick(x, y, 1);
            }
            bricks.add(brick);
            gamePane.getChildren().add(brick.getRectangle());
        }
        timer.stop();
        Menu.setVisible(false);
        gamePane.setVisible(true);
        EndGame.setVisible(false);
        score = 0;
        lives = 3;
        paddle.setX(270);
        paddle.setY(450);
        ball.setX(paddle.getX() + paddle.getRectangle().getWidth() / 2);
        ball.setY(ballY);
        scoreLable.setText("Score: " + score);
        gamePane.setFocusTraversable(true);
        gamePane.setOnKeyPressed(this::handleInput);
        gamePane.requestFocus();
    }
    public void onClickButtonLevel1() {
       currentLevel=1;
    }
    public void onClickButtonLevel2() {
       currentLevel=2;
    }
    public void onClickButtonLevel3() {
       currentLevel=3;
    }
    public void onClickButtonHome() {
        timer.stop();
        Menu.setVisible(true);
        gamePane.setVisible(false);
    }

    public void onClickButtonExit() {
        setting.setVisible(false);
    }

    public void onClickButtonSetting() {
        setting.setVisible(true);
    }

    public void handleInput(KeyEvent e) {
        if (e.getCode() == KeyCode.A) {
            paddle.moveLeft();
            if (ballAttachToPaddle) {
                ball.setX(paddle.getX() + paddle.getRectangle().getWidth() / 2);
            }
        }
        if (e.getCode() == KeyCode.D) {
            paddle.moveRight();
            if (ballAttachToPaddle) {
                ball.setX(paddle.getX() + paddle.getRectangle().getWidth() / 2);
            }
        }
        if (e.getCode() == KeyCode.SPACE && ballAttachToPaddle) {
            timer.start();
            ballAttachToPaddle = false;
        }
    }

    public void checkCollisions() {
        double dx = ball.getDirectionX();
        double dy = ball.getDirectionY();
        if (ball.checkCollision(paddle)) {
            ballCollisionMusic();
            if (dx < 0 && dx > -5) dx -= 1;
            if (dx >= 0 && dx < 5) dx += 1;
            ball.setDirectionX(-(dx + ball.getSpeed()));
            ball.setDirectionY(-(dy + ball.getSpeed()));
            System.out.println("ball collision paddle");
        }
        List<Brick> bricksToRemove = new ArrayList<>();
        for (Brick brick : bricks) {
            if (ball.checkCollision(brick)) {
                ballCollisionMusic();
                if (dx < 0 && dx > -5) dx -= 1;
                if (dx >= 0 && dx < 5) dx += 1;
                ball.setDirectionX(-(dx + ball.getSpeed()));
                ball.setDirectionY(-(dy + ball.getSpeed()));
                brick.takeHit();
                if (brick.isDestroyed()) {
                    bricksToRemove.add(brick);
                    gamePane.getChildren().remove(brick.getRectangle());
                }
                score += 10;
                scoreLable.setText("Score: " + score);
                System.out.println("ball collision brick");

            }
        }
        bricks.removeAll(bricksToRemove);
        if (bricks.isEmpty()) {
            GameOver();
        }
    }

    public void GameOver() {
        gamePane.setVisible(false);
        if (lives == 0) {
            GameOverLable.setVisible(true);
            WinLable.setVisible(false);
        }
        if (lives > 0 && bricks.isEmpty()) {
            WinLable.setVisible(true);
            GameOverLable.setVisible(false);
        }
        EndGame.setVisible(true);
    }

    public void backgroundMusic() {
        URL url = getClass().getResource("/org/example/oopbtl1/sound/backgroundMusic.mp3");
        backgroundMedia = new Media(url.toExternalForm());
        backgroundPlayer = new MediaPlayer(backgroundMedia);
        backgroundPlayer.setVolume(0.2);
        backgroundPlayer.play();
    }

    public void ballCollisionMusic() {
        URL url = getClass().getResource("/org/example/oopbtl1/sound/ballCollisionMusic.mp3");
        ballMedia = new Media(url.toExternalForm());
        ballPlayer = new MediaPlayer(ballMedia);
        ballPlayer.setVolume(2);
        ballPlayer.play();
    }

    public void initialize() {
        backgroundMusic();
        setting.setVisible(false);
        gamePane.getChildren().add(ball.getCircle());
        gamePane.getChildren().add(paddle.getRectangle());

        gamePane.setVisible(false);
        EndGame.setVisible(false);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                checkCollisions();
                ball.move();
                if (ball.getY() > 480 - ball.getCircle().getRadius()) {
                    timer.stop();
                    ballAttachToPaddle = true;
                    ball.setX(paddle.getX() + paddle.getRectangle().getWidth() / 2);
                    ball.setY(ballY);
                    ball.setDirectionX(0);
                    ball.setDirectionY(-4);
                    lives--;
                    System.out.println(lives);
                    if (lives == 0) {
                        GameOver();
                    }
                }
            }
        };
    }
}

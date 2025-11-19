package org.example.oopbtl1;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameManager {
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLable;
    @FXML
    private Pane Menu;
    @FXML
    private Pane EndGame;
    @FXML
    private Label GameOverLable, WinLable;
    @FXML
    private Pane setting;
    @FXML
    private List<SVGPath> svgPaths = new ArrayList<>();
    @FXML
    private SVGPath heart1;
    @FXML
    private SVGPath heart2;
    @FXML
    private SVGPath heart3;
    @FXML
    private Button nextLevelButton;
    private int currentLevel = 1;
    private List<Brick> bricks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private List<Ball> activeBalls = new ArrayList<>();
    private int damageMultiplier = 1;
    private class ActivePowerUp {
        PowerUp powerUp;
        double timeRemaining;

        public ActivePowerUp(PowerUp pu) {
            this.powerUp = pu;
            this.timeRemaining = pu.getDuration();
        }
    }
    private List<ActivePowerUp> activeEffects = new ArrayList<>();
    private long lastUpdateTime = 0;

    private int score;
    private int lives;
    private AnimationTimer timer;
    private boolean ballAttachToPaddle = true;
    private MediaPlayer backgroundPlayer;
    private MediaPlayer ballPlayer;
    private Media backgroundMedia;
    private Media ballMedia;
    private double ballY = 420;

    Paddle paddle = new Paddle(270, 450);

    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;

    public List<Ball> getActiveBalls() {
        return activeBalls;
    }

    public void setDamageMultiplier(int multiplier) {
        this.damageMultiplier = multiplier;
    }

    public void createTripleBall() {
        if (!activeBalls.isEmpty()) {
            Ball originalBall = activeBalls.get(0);
            double currentSpeed = 4;

            Ball newBall1 = new Ball(originalBall.getCircle().getRadius(), originalBall.getX(), originalBall.getY());
            newBall1.setDirectionX(-currentSpeed);
            newBall1.setDirectionY(originalBall.getDirectionY());
            activeBalls.add(newBall1);
            gamePane.getChildren().add(newBall1.getCircle());

            Ball newBall2 = new Ball(originalBall.getCircle().getRadius(), originalBall.getX(), originalBall.getY());
            newBall2.setDirectionX(currentSpeed);
            newBall2.setDirectionY(originalBall.getDirectionY());
            activeBalls.add(newBall2);
            gamePane.getChildren().add(newBall2.getCircle());
        }
    }

    private PowerUp createRandomPowerUp(double x, double y) {
        int type = (int) (Math.random() * 3);

        double centerX = x + 50/2;
        double centerY = y + 10;

        if (type == 0) {
            return new ExpandPaddlePowerUp(centerX, centerY);
        } else if (type == 1) {
            return new DoubleDamagePowerUp(centerX, centerY);
        } else {
            return new TripleBallPowerUp(centerX, centerY);
        }
    }


    public void onClickButtonStart() {
        gamePane.getChildren().removeAll(bricks.stream().map(Brick::getRectangle).collect(Collectors.toList()));
        gamePane.getChildren().removeAll(activeBalls.stream().map(Ball::getCircle).collect(Collectors.toList()));

        gamePane.getChildren().removeAll(powerUps.stream().map(pu -> pu.getShape()).collect(Collectors.toList()));

        bricks.clear();
        activeBalls.clear();
        powerUps.clear();

        activeEffects.clear();
        damageMultiplier = 1;
        paddle.resetSizeAndColor();

        for (int i = 0; i < 44; i++) {
            double x = (i % 11) * 55 + 17.5;
            double y = (i / 11) * 25 + 50;
            Brick brick;
            if (currentLevel == 1) {
                brick = new NormalBrick(x, y, 1);
            } else if (currentLevel == 2) {
                brick = Math.random() < 0.5 ? new StrongBrick(x, y, 2)
                        : new NormalBrick(x, y, 2);
            } else {
                brick = new StrongBrick(x, y, 1);
            }
            bricks.add(brick);
            gamePane.getChildren().add(brick.getRectangle());
        }
        setting.setVisible(false);
        for (SVGPath svgPath : svgPaths) {
            svgPath.setVisible(true);
        }
        lives = svgPaths.size();
        timer.stop();
        Menu.setVisible(false);
        gamePane.setVisible(true);
        EndGame.setVisible(false);

        score = 0;
        lives = 3;

        paddle.setX(270);
        paddle.setY(450);
        Ball initialBall = new Ball(12, paddle.getX() + paddle.getRectangle().getWidth() / 2, ballY);
        activeBalls.add(initialBall);
        gamePane.getChildren().add(initialBall.getCircle());

        ballAttachToPaddle = true;

        scoreLable.setText("Score: " + score);
        gamePane.setFocusTraversable(true);
        gamePane.setOnKeyPressed(this::handleInput);
        gamePane.setOnKeyReleased(this::handleRelease);
        gamePane.requestFocus();

        lastUpdateTime = 0;
    }

    public void onClickButtonLevel1() {
        currentLevel = 1;
        onClickButtonStart();
    }

    public void onClickButtonLevel2() {
        currentLevel = 2;
        onClickButtonStart();
    }

    public void onClickNextLevelButton() {
        currentLevel = currentLevel + 1;
        onClickButtonStart();
    }

    public void onClickButtonLevel3() {
        currentLevel = 3;
        onClickButtonStart();
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
            isMovingLeft = true;
        }
        if (e.getCode() == KeyCode.D) {
            isMovingRight = true;
        }
        if (e.getCode() == KeyCode.SPACE && ballAttachToPaddle) {
            timer.start();
            ballAttachToPaddle = false;

            for (Ball ball : activeBalls) {
                ball.setDirectionX(0);
                ball.setDirectionY(-4);
            }
        }
    }

    public void handleRelease(KeyEvent e) {
        if (e.getCode() == KeyCode.A) {
            isMovingLeft = false;
        }
        if (e.getCode() == KeyCode.D) {
            isMovingRight = false;
        }
    }


    public void checkCollisions() {

        for (Ball ball : activeBalls) {
            double dx = ball.getDirectionX();
            double dy = ball.getDirectionY();

            if (ball.checkCollision(paddle)) {
                ballCollisionMusic();
                if (dx < 0 && dx > -5) dx -= ball.getSpeed();
                if (dx >= 0 && dx < 5) dx += ball.getSpeed();
                ball.setY(paddle.getY() -ball.getCircle().getRadius());
                ball.setDirectionX(-(dx+ball.getSpeed()));
                ball.setDirectionY(-(dy));
            }

            List<Brick> bricksToRemove = new ArrayList<>();
            for (Brick brick : bricks) {
                if (ball.checkCollision(brick)) {
                    ballCollisionMusic();
                    if (dx < 0 && dx > -5) dx -= ball.getSpeed();
                    if (dx >= 0 && dx < 5) dx += ball.getSpeed();
                    ball.setDirectionX(-(dx+ball.getSpeed()));
                    ball.setDirectionY(-(dy));

                    for (int i = 0; i < damageMultiplier; i++) {
                        brick.takeHit();
                    }

                    if (brick.isDestroyed()) {
                        bricksToRemove.add(brick);
                        gamePane.getChildren().remove(brick.getRectangle());
                        score += 10 * damageMultiplier;

                        if (Math.random() < 0.25) {
                            PowerUp newPowerUp = createRandomPowerUp(brick.getX(), brick.getY());
                            powerUps.add(newPowerUp);
                            gamePane.getChildren().add(newPowerUp.getShape());
                        }
                    }
                    scoreLable.setText("Score: " + score);
                }
            }
            bricks.removeAll(bricksToRemove);
        }

        if (bricks.isEmpty()) {
            GameOver();
        }
    }

    public void GameOver() {
        timer.stop();
        gamePane.setVisible(false);
        if (lives == 0) {
            GameOverLable.setVisible(true);
            WinLable.setVisible(false);
            nextLevelButton.setVisible(false);
        }
        if (lives > 0 && bricks.isEmpty()) {
            WinLable.setVisible(true);
            GameOverLable.setVisible(false);
            nextLevelButton.setVisible(true);
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
        svgPaths.add(heart1);
        svgPaths.add(heart2);
        svgPaths.add(heart3);
        lives = svgPaths.size();
        setting.setVisible(false);
        nextLevelButton.setVisible(false);
        gamePane.getChildren().add(paddle.getRectangle());

        gamePane.setVisible(false);
        EndGame.setVisible(false);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = now;
                    return;
                }
                double deltaTime = (now - lastUpdateTime) / 1_000_000_000.0;
                lastUpdateTime = now;

                if (isMovingLeft) {
                    paddle.moveLeft();
                    if (ballAttachToPaddle && !activeBalls.isEmpty()) {
                        activeBalls.get(0).setX(paddle.getX() + paddle.getRectangle().getWidth() / 2);
                    }
                }
                if (isMovingRight) {
                    paddle.moveRight();
                    if (ballAttachToPaddle && !activeBalls.isEmpty()) {
                        activeBalls.get(0).setX(paddle.getX() + paddle.getRectangle().getWidth() / 2);
                    }
                }

                checkCollisions();

                List<PowerUp> powerUpsToRemove = new ArrayList<>();
                for (PowerUp pu : powerUps) {
                    pu.move();

                    if (pu.checkCollision(paddle)) {
                        pu.applyEffect(GameManager.this);
                        powerUpsToRemove.add(pu);

                        if (pu.getDuration() > 0) {
                            activeEffects.removeIf(e -> e.powerUp.getClass().equals(pu.getClass()));
                            activeEffects.add(new ActivePowerUp(pu));
                        }
                    }
                    if (pu.getY() > 480) {
                        powerUpsToRemove.add(pu);
                    }
                }
                gamePane.getChildren().removeAll(powerUpsToRemove.stream().map(pu -> pu.getShape()).collect(Collectors.toList()));
                powerUps.removeAll(powerUpsToRemove);

                List<ActivePowerUp> effectsToStop = new ArrayList<>();
                for (ActivePowerUp effect : activeEffects) {
                    effect.timeRemaining -= deltaTime;

                    if (effect.timeRemaining <= 0) {
                        effect.powerUp.removeEffect(GameManager.this);
                        effectsToStop.add(effect);
                    }
                }
                activeEffects.removeAll(effectsToStop);
                List<Ball> ballsToRemove = new ArrayList<>();
                for (Ball ball : activeBalls) {
                    ball.move();

                    if (ball.getY() > 480 - ball.getCircle().getRadius()) {
                        ballsToRemove.add(ball);
                        gamePane.getChildren().remove(ball.getCircle());
                    }
                }
                activeBalls.removeAll(ballsToRemove);

                if (activeBalls.isEmpty() && !ballAttachToPaddle) {
                    lives--;
                    svgPaths.get(lives).setVisible(false);
                    if (lives == 0) {
                        GameOver();
                    } else {
                        timer.stop();
                        ballAttachToPaddle = true;

                        Ball newBall = new Ball(12, paddle.getX() + paddle.getRectangle().getWidth() / 2, ballY);
                        activeBalls.add(newBall);
                        gamePane.getChildren().add(newBall.getCircle());
                        damageMultiplier = 1;
                        for (ActivePowerUp effect : activeEffects) {
                            if (effect.powerUp instanceof DoubleDamagePowerUp) {
                                effect.powerUp.removeEffect(GameManager.this);
                            }
                        }
                        activeEffects.clear();
                    }
                }
            }
        };
    }
}
package visual;

import fields.AbstractField;
import fields.EmptyField;
import fields.Field;
import fields.SimpleObstaclesField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.*;

import java.awt.*;

public class View extends Application {

    private static final int width = 800;
    private static final int height = 900;
    private static final int rows = 26;
    private static final int cols = rows;
    private static final int squareSize = width / rows;
    private double offsetX;
    private double offsetY;

    private Canvas canvas;
    private GraphicsContext gc;
    private Image snakeHeadImage;
    private Image snakeBodyPartImage;

    Snake snake = new Snake();
    Food food = new Food();
    AbstractField field;
    GameManager gameManager = GameManager.getInstance();
    Color foodColor;

    private static final int right = 0;
    private static final int left = 1;
    private static final int up = 2;
    private static final int down = 3;


    private int currDirection;
    private int lastDirection;
    private boolean directionChanged = false;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(width, height);
        //field = new Field(rows, cols, squareSize);
        field = new SimpleObstaclesField(rows, cols, squareSize);

        Group mainGroup = new Group();
        mainGroup.getChildren().add(canvas);
        Scene scene = new Scene(mainGroup);

        primaryStage.setTitle("wężuu");
        primaryStage.setScene(scene);
        primaryStage.show();

        gc = canvas.getGraphicsContext2D();
        field.updateOffset(canvas.getWidth(), canvas.getHeight());

        //Obstacle obstacle1 = new Obstacle(3, 3, squareSize);
        //Obstacle obstacle2 = new Obstacle(9, 9, squareSize);
        //field.addObstacle(obstacle1);
        //field.addObstacle(obstacle2);

        movement(scene);
        foodColor = food.generate(snake);
        gameManager.setDifficulty("hard");

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> field.draw(gc));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> field.draw(gc));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(gameManager.getDiffTime()),e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        directionChanged = false;
        if (gameManager.isOver()) {
            drawGameOver(gc);
            return;
        }

        field.draw(gc);
        drawFood(gc);
        drawSnake(gc);
        drawScore();

        for (int i = snake.getBody().size() - 1; i >= 1; i--) {
            snake.setBodyX(i,snake.getBody().get(i - 1).x);
            snake.setBodyY(i,snake.getBody().get(i - 1).y);
        }

        switch (currDirection) {
            case right:
                if (!field.isOutOfBounds(snake.getHead().getX() + 1, snake.getHead().getY())) {
                    snake.movementRight();
                } else {
                    gameManager.setOver();
                }
                break;
            case left:
                if (!field.isOutOfBounds(snake.getHead().getX() - 1, snake.getHead().getY())) {
                    snake.movementLeft();
                } else {
                    gameManager.setOver();
                }
                break;
            case up:
                if (!field.isOutOfBounds(snake.getHead().getX(), snake.getHead().getY() - 1)) {
                    snake.movementUp();
                } else {
                    gameManager.setOver();
                }
                break;
            case down:
                if (!field.isOutOfBounds(snake.getHead().getX(), snake.getHead().getY() + 1)) {
                    snake.movementDown();
                } else {
                    gameManager.setOver();
                }
                break;
        }
        gameManager.gameOver(snake,field);
        foodColor = snake.eat(food,foodColor);
    }

    public static void launchGame() {
        launch();
    }

    private void movement(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (directionChanged) return;
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    if (currDirection != left) {
                        currDirection = right;
                        directionChanged = true;
                    }
                }else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    if (currDirection != right) {
                        currDirection = left;
                        directionChanged = true;
                    }
                }else if (code == KeyCode.UP || code == KeyCode.W) {
                    if (currDirection != down) {
                        currDirection = up;
                        directionChanged = true;
                    }
                }else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    if (currDirection != up) {
                        currDirection = down;
                        directionChanged = true;
                    }
                }
            }
        });
    }

    private void drawFood(GraphicsContext gc) {
        double foodX = food.getFoodX() * squareSize + field.getOffsetX();
        double foodY = food.getFoodY() * squareSize + field.getOffsetY();

        gc.setFill(foodColor);
        //gc.fillRect(food.getFoodX() * squareSize, food.getFoodY() * squareSize, squareSize, squareSize);
        gc.fillRoundRect(foodX, foodY, squareSize, squareSize, 40,40);
    }

    private void drawSnake(GraphicsContext gc) {
        double headX = snake.getHead().getX() * squareSize + field.getOffsetX();;
        double headY = snake.getHead().getY() * squareSize + field.getOffsetY();;

        gc.save();

        gc.translate(headX + squareSize / 2, headY + squareSize / 2);
        //System.out.println("srodek  x  " + (headX + squareSize / 2) + "   y   " + (headY + squareSize / 2));

        switch (currDirection) {
            case right:
                gc.rotate(-90);
                break;
            case down:
                gc.rotate(0);
                break;
            case left:
                gc.rotate(90);
                break;
            case up:
                gc.rotate(180);
                break;
        }

        snakeHeadImage = new Image(snake.getSnakeHeadImagePath());
        snakeBodyPartImage = new Image(snake.getSnakeBodyPartImage());
        gc.drawImage(snakeHeadImage, -squareSize / 2, -squareSize / 2, squareSize, squareSize);

        gc.restore();

        //gc.setFill(Color.web("f40fea"));
        for (int i = 1; i < snake.getBody().size() ; i++) {
            Point segment = snake.getBody().get(i); // wytlumaczenie w snake.java
            if (field.isOutOfBounds(segment.x, segment.y)) {
                continue;
            }

            double bodyPartX = snake.getBody().get(i).getX() * squareSize + field.getOffsetX();
            double bodyPartY = snake.getBody().get(i).getY() * squareSize + field.getOffsetY();
            gc.save();

            gc.translate(bodyPartX + squareSize / 2, bodyPartY + squareSize / 2);
            Point currentPart = snake.getBody().get(i);
            Point prevPart = snake.getBody().get(i - 1);

            if (currentPart.x > prevPart.x) {
                gc.rotate(-90); // Poruszanie w prawo
            } else if (currentPart.x < prevPart.x) {
                gc.rotate(90); // Poruszanie w lewo
            } else if (currentPart.y > prevPart.y) {
                gc.rotate(0); // Poruszanie w dół
            } else if (currentPart.y < prevPart.y) {
                gc.rotate(180); // Poruszanie w górę
            }
            gc.drawImage(snakeBodyPartImage, -squareSize / 2, -squareSize / 2, squareSize, squareSize);
            gc.restore();
            //gc.fillRoundRect(snake.getBody().get(i).getX() * squareSize, snake.getBody().get(i).getY() * squareSize, squareSize - 1,squareSize - 1,20,20);
            //gc.drawImage(snakeBodyPartImage,snake.getBody().get(i).getX() * squareSize, snake.getBody().get(i).getY() * squareSize, squareSize - 1,squareSize - 1);

        }
    }

    private void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(new javafx.scene.text.Font("Digital", 70));
        gc.fillText("GAME OVER", width / 3.5, height / 2);
    }

    private void drawScore() {
        double scoreX = (canvas.getWidth() / 2) - 50;
        double scoreY = field.getOffsetY() - 10;

        gc.setFill(Color.WHITE);
        gc.fillRect(scoreX, scoreY - 35, 120, 40);

        gc.setFill(Color.BLACK);
        gc.setFont(new javafx.scene.text.Font("Digital", 30));
        gc.fillText("Score: " + gameManager.getScore(), scoreX, scoreY);
    }

    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }
    public static int getRows() {
        return rows;
    }
    public static int getCols() {
        return cols;
    }
    public static int getSquareSize() {
        return squareSize;
    }
}


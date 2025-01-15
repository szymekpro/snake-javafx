package visual;

import fields.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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

    private Canvas canvas;
    private GraphicsContext gc;
    private Image snakeHeadImage;
    private Image snakeBodyPartImage;
    private Timeline timeline;

    Snake snake;
    Food food;
    AbstractField field;
    GameManager gameManager = GameManager.getInstance();
    Image foodImg;

    private static final int right = 0;
    private static final int left = 1;
    private static final int up = 2;
    private static final int down = 3;

    private double offsetX = (width - (cols * squareSize)) / 2;
    private double offsetY = (height - (rows * squareSize)) / 2;

    private Scene menuScene , gameScene;
    private Stage primaryStage;
    private Group menuGroup = new Group();

    private int currDirection;
    private boolean directionChanged = false;
    private boolean isPaused = false;
    private int currButton = 0;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();


        menuGroup.getStyleClass().add("menu");

        Scene menuScene = new Scene(menuGroup , 800, 900, Color.WHITE);
        menuScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        menuGroup.getChildren().add(canvas);
        drawMenu(gc);


        this.menuScene = menuScene;

        primaryStage.setTitle("Wężuu - Menu");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void startGame() {
        canvas = new Canvas(width, height);

        snake = new Snake();
        food = new Food();

        switch (currButton) {
            case 0:
                field = new EmptyField(rows,cols,squareSize);
                break;
            case 1:
                field = new CrossField(rows,cols,squareSize);
                break;
            case 2:
                field = new CheckersField(rows,cols,squareSize);
                break;
            case 3:
                field = new PassagesField(rows,cols,squareSize);
                break;
        }

        Group gameGroup = new Group();
        gameGroup.getChildren().add(canvas);
        gameScene = new Scene(gameGroup);

        primaryStage.setTitle("Wężuu");
        primaryStage.setScene(gameScene);
        primaryStage.show();

        gc = canvas.getGraphicsContext2D();
        field.updateOffset(canvas.getWidth(), canvas.getHeight());

        foodImg = food.generate(snake, field);
        movement(gameScene);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> field.draw(gc));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> field.draw(gc));

        timeline = new Timeline(new KeyFrame(Duration.millis(gameManager.getDiffTime()), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void drawMenu(GraphicsContext gc) {
        gc.save();

        gc.translate(offsetX, offsetY);


        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit");
        Button easyButton = new Button("Easy");
        Button mediumButton = new Button("Medium");
        Button hardButton = new Button("Hard");
        Button mapSwitch = new Button("Empty field");

        startButton.setLayoutX((rows /2 - 4 ) * squareSize + offsetX);
        startButton.setLayoutY((rows/2  - 6) * squareSize + offsetY);
        startButton.setPrefSize(8 * squareSize, 2 * squareSize);
        startButton.getStyleClass().add("basicButton");

        easyButton.setLayoutX((rows /2 - 4 ) * squareSize + offsetX);
        easyButton.setLayoutY((rows/2  - 3) * squareSize + offsetY);
        easyButton.setPrefSize(2 * squareSize, 2 * squareSize);
        easyButton.getStyleClass().add("difficultyButton");

        mediumButton.setLayoutX((rows /2 - 1 ) * squareSize + offsetX);
        mediumButton.setLayoutY((rows/2  - 3) * squareSize + offsetY);
        mediumButton.setPrefSize(2 * squareSize, 2 * squareSize);
        mediumButton.getStyleClass().add("difficultyButton");

        hardButton.setLayoutX((rows /2 + 2 ) * squareSize + offsetX);
        hardButton.setLayoutY((rows/2  - 3) * squareSize + offsetY);
        hardButton.setPrefSize(2 * squareSize, 2 * squareSize);
        hardButton.getStyleClass().add("difficultyButton");

        exitButton.setLayoutX((rows /2 - 4 ) * squareSize + offsetX);
        exitButton.setLayoutY((rows/2 ) * squareSize + offsetY);
        exitButton.setPrefSize(8 * squareSize, 2 * squareSize);
        exitButton.getStyleClass().add("basicButton");

        mapSwitch.setLayoutX((rows /2 - 3 ) * squareSize + offsetX);
        mapSwitch.setLayoutY((rows/2 + 4) * squareSize + offsetY);
        mapSwitch.setPrefSize(6 * squareSize, 3 * squareSize);
        mapSwitch.getStyleClass().add("mapSwitch");

        menuGroup.getChildren().addAll(startButton, easyButton, mediumButton, hardButton, exitButton, mapSwitch);

        startButton.setOnAction(e -> {
            startGame();
        });

        easyButton.setOnAction(e -> {
            easyButton.getStyleClass().add("selected");
            mediumButton.getStyleClass().remove("selected");
            hardButton.getStyleClass().remove("selected");
            gameManager.setDifficulty("easy");

        });

        mediumButton.setOnAction(e -> {
            mediumButton.getStyleClass().add("selected");
            easyButton.getStyleClass().remove("selected");
            hardButton.getStyleClass().remove("selected");
            gameManager.setDifficulty("medium");
        });

        hardButton.setOnAction(e -> {
            hardButton.getStyleClass().add("selected");
            easyButton.getStyleClass().remove("selected");
            mediumButton.getStyleClass().remove("selected");
            gameManager.setDifficulty("hard");
        });

        mapSwitch.setOnAction(e-> {
            currButton++;
            switch (currButton) {
                case 1:
                    mapSwitch.setText("Cross Field");
                    break;
                case 2:
                    mapSwitch.setText("Checkers Field");
                    break;
                case 3:
                    mapSwitch.setText("Passages Field");
                    break;
                case 4:
                    mapSwitch.setText("Empty Field");
                    currButton = 0;
                    break;
            }


        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        drawMenuBackground(gc);

        gc.restore();
    }

    public void drawMenuBackground(GraphicsContext gc) {

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeRect(0, 0, cols * squareSize, rows * squareSize);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("99cc2e"));
                } else {
                    gc.setFill(Color.web("96c82d"));
                }
                gc.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }

    }

    private void togglePause() {
        if (isPaused) {
            timeline.play();
        } else {
            timeline.pause();
        }
        isPaused = !isPaused;
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
        foodImg = snake.eat(food,foodImg,field);
        foodImg = food.getFoodImage();
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
                if (code == KeyCode.G) {
                    togglePause();
                    drawPause(gc);
                    return;
                }
                if (isPaused) {
                    return;
                }
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

        gc.drawImage(foodImg,foodX, foodY, squareSize, squareSize);

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
                gc.rotate(-90);
            } else if (currentPart.x < prevPart.x) {
                gc.rotate(90);
            } else if (currentPart.y > prevPart.y) {
                gc.rotate(0);
            } else if (currentPart.y < prevPart.y) {
                gc.rotate(180);
            }
            gc.drawImage(snakeBodyPartImage, -squareSize / 2, -squareSize / 2, squareSize, squareSize);
            gc.restore();

        }
    }

    private void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(new javafx.scene.text.Font("Arial", 60));
        gc.fillText("GAME OVER", 7 * squareSize + offsetX, 13 * squareSize + offsetY);

        gc.setFill(Color.WHITE);
        gc.setFont(new javafx.scene.text.Font("Arial", 40));
        gc.fillText("Your score: " + gameManager.getScore(), 9 * squareSize + offsetX, 15 * squareSize + offsetY);

        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                drawMenu(gc);
                primaryStage.setScene(menuScene);
                gameManager.unsetOver();
                togglePause();
                isPaused = false;
                gameManager.resetScore();
            }
        });

    }
    private void drawPause(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new javafx.scene.text.Font("Arial", 50));
        gc.fillText("PAUSED", width / 2.75, height / 2);

    }

    private void drawScore() {
        double scoreX = (canvas.getWidth() / 2) - 50;
        double scoreY = field.getOffsetY() - 10;

        gc.setFill(Color.WHITE);
        gc.fillRect(scoreX, scoreY - 35, 120, 40);

        gc.setFill(Color.BLACK);
        gc.setFont(new javafx.scene.text.Font("Arial", 30));
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


package objects;

import fields.AbstractField;
import javafx.scene.paint.Color;
import visual.View;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Food {
    private int foodX;
    private int foodY;
    private int size = View.getSquareSize();
    private String[] foodImagesPaths;
    private Image foodImage;
    private IFoodState foodState;
    private Timer timer;
    private static final int RELOCATE_DELAY = 10 * 1000;


    public Food() {
        this.foodX = (int)(Math.random() * View.getRows());
        this.foodY = (int)(Math.random() * View.getCols());
        this.foodImagesPaths = new String[] {"/images/apple.png","/images/haaren.png","/images/orange.png","/images/banan.png","/images/ras.png"};
        assignRandomState();
        startRelocationTimer();
    }

    public void startRelocationTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                relocateFood();
            }
        }, RELOCATE_DELAY);
    }

    private void relocateFood() {
        this.foodX = (int)(Math.random() * View.getRows());
        this.foodY = (int)(Math.random() * View.getCols());

        assignRandomState();
        startRelocationTimer();
    }

    private void assignRandomState() {
        Random random = new Random();
        int chance = random.nextInt(100);
        if (chance < 10) {
            this.foodState = new LoseBodyPartState();
            this.foodImage = new Image("/images/szczoch.png");
        } else if (chance < 35) {
            this.foodState = new DoubleFoodState();
            this.foodImage = new Image(this.foodImagesPaths[random.nextInt(this.foodImagesPaths.length)]);
        } else {
            this.foodState = new NormalFoodState();
            this.foodImage = new Image(this.foodImagesPaths[random.nextInt(this.foodImagesPaths.length)]);
        }

    }

    public Image generate(Snake snake, AbstractField field) {

        startRelocationTimer();

        start:
        while(true) {
            this.changeFoodX();
            this.changeFoodY();
            for (Point snakePiece : snake.getBody()) {
                if (snakePiece.getX() == this.getFoodX() && snakePiece.getY() == this.getFoodY()) {

                    continue start;
                }
            }
            Iterator<Obstacle> iterator = field.getObstacleIterator();
            while (iterator.hasNext()) {
                Obstacle obstacle = iterator.next();
                if (obstacle.getX() == this.getFoodX() && obstacle.getY() == this.getFoodY()) {

                    continue start;
                }
            }
            assignRandomState();
            return foodImage;
        }
    }
    public Image getFoodImage() {
        return foodImage;
    }

    public void setFoodState(IFoodState foodState) {
        this.foodState = foodState;
    }

    public IFoodState getFoodState() {
        return this.foodState;
    }
    public void applyEffect(Snake snake) {
        foodState.applyEffect(snake);
    }
    public String[] getFoodImagesPaths() {
        return foodImagesPaths;
    }
    public int getFoodX() {
        return foodX;
    }
    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }
    public int getFoodY() {
        return foodY;
    }
    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }
    public void changeFoodX() {
        this.foodX = (int)(Math.random() * View.getRows());
    }
    public void changeFoodY() {
        this.foodY = (int)(Math.random() * View.getCols());
    }


}

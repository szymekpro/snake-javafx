package objects;

import fields.AbstractField;
import javafx.scene.paint.Color;
import visual.View;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Iterator;

public class Food {
    private int foodX;
    private int foodY;
    private int size = View.getSquareSize();
    private String[] foodImagesPaths;
    private IFoodState foodState;


    public Food() {
        this.foodX = (int)(Math.random() * View.getRows());
        this.foodY = (int)(Math.random() * View.getCols());
        this.foodImagesPaths = new String[] {"/images/apple.png","/images/haaren.png","/images/orange.png","/images/banan.png","/images/ras.png"};
    }

    public Image generate(Snake snake, AbstractField field) {

        double randomValue = Math.random();
        System.out.println(randomValue);
        if (randomValue < 0.10) {
            this.foodState = new LoseBodyPartState();
        } else if (randomValue < 0.35) {
            this.foodState = new DoubleFoodState();
        } else {
            this.foodState = new NormalFoodState();
        }
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

            String foodimagePath;
            if (this.foodState instanceof LoseBodyPartState) {
                foodimagePath = "/images/szczoch.png";
            }
            else
                foodimagePath = this.foodImagesPaths[((int)(Math.random() * this.getFoodImagesPaths().length))];
            return new Image(foodimagePath);
        }
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

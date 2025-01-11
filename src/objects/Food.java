package objects;

import javafx.scene.paint.Color;
import visual.View;

import java.awt.*;

public class Food {
    private int foodX;
    private int foodY;
    private int size = View.getSquareSize();
    private Color[] foodColorArray;

    public Food() {
        this.foodX = (int)(Math.random() * View.getRows());
        this.foodY = (int)(Math.random() * View.getCols());
        this.foodColorArray = new Color[] {Color.RED, Color.BLUE, Color.YELLOW, Color.ORANGE};
    }

    public Color generate(Snake snake) {
        start:
        while(true) {
            this.changeFoodX();
            this.changeFoodY();
            for (Point snakePiece : snake.getBody()) {
                if (snakePiece.getX() == this.getFoodX() && snakePiece.getY() == this.getFoodY()) {
                    //System.out.println(snakePiece.getX() + " " + snakePiece.getY() + " " + this.getFoodX() + " " + this.getFoodY());
                    System.out.println("overlap deteced");
                    continue start;
                }

            }
            Color foodColor;
            foodColor = this.getFoodColorArray()[((int)(Math.random() * this.getFoodColorArray().length))];
            return foodColor;
        }
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
    public Color[] getFoodColorArray() {
        return foodColorArray;
    }
    public void changeFoodX() {
        this.foodX = (int)(Math.random() * View.getRows());
    }
    public void changeFoodY() {
        this.foodY = (int)(Math.random() * View.getCols());
    }


}

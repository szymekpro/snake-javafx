package objects;

import fields.AbstractField;
import visual.View;

import java.awt.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Point> body = new ArrayList<>();
    private Point head;
    private String snakeHeadImagePath = "/images/snakeHead.png";
    private String snakeBodyPartImage = "/images/bodyPart.png";

    public Snake() {
        int initialBodySize = 3;
        for (int i = 0; i < initialBodySize; i++) {
            body.add(new Point(5, View.getRows() / 2));
        }
        this.head = this.body.get(0);
    }

    public Image eat(Food food, Image lastImg, AbstractField field) {
        Image newFoodImg = null;
        if (this.getHead().x == food.getFoodX() && this.getHead().y == food.getFoodY()) {
            food.applyEffect(this);
            //this.addBodyPart(new Point(-1,-1)); // rysuje poza plansza, quick fix wyrzucenie obiektow z poza planszy, ew. TODO
            newFoodImg = food.generate(this,field);
            food.startRelocationTimer();
            GameManager.addScore(1);
            return newFoodImg;
        }
        return lastImg;
    }

    public String getSnakeBodyPartImage() {
        return this.snakeBodyPartImage;
    }
    public String getSnakeHeadImagePath() {
        return this.snakeHeadImagePath;
    }
    public void movementRight() {
        this.head.x++;
    }
    public void movementLeft() {
        this.head.x--;
    }
    public void movementUp() {
        this.head.y--;
    }
    public void movementDown() {
        this.head.y++;
    }

    public void setBodyPart(int index, Point newPoint) {
        body.set(index, newPoint);
    }

    public void addBodyPart(Point newPart) {
        body.add(newPart);
    }
    public ArrayList<Point> getBody() {
        return body;
    }
    public void setNewBody(ArrayList<Point> newBody) {
        this.body = newBody;
    }
    public Point getHead() {
        return head;
    }
    public void setHead(Point head) {
        this.head = head;
    }
    public void setBodyX(int i, int x) {
        body.get(i).x = x;
    }
    public void setBodyY(int i, int y) {
        body.get(i).y = y;
    }
    public void setHeadX(int x) {
        this.head.x = x;
    }
    public void setHeadY(int y) {
        this.head.y = y;
    }

}



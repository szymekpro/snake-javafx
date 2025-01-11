package fields;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Obstacle;
import objects.ObstacleIterator;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractField {

    protected int rows;
    protected int cols;
    protected double squareSize;
    protected double offsetX;
    protected double offsetY;

    public AbstractField(int rows, int cols, double squareSize) {
        this.rows = rows;
        this.cols = cols;
        this.squareSize = squareSize;
    }


    public abstract Iterator<Obstacle> getObstacleIterator();

    public abstract void drawObstacles(GraphicsContext gc, List<Obstacle> obstacles);

    public void updateOffset(double canvasWidth, double canvasHeight) {
        offsetX = (canvasWidth - (cols * squareSize)) / 2;
        offsetY = (canvasHeight - (rows * squareSize)) / 2;
    }


    public abstract void draw(GraphicsContext gc);

    public abstract List<Obstacle> getObstacles();

    public boolean isOutOfBounds(double x, double y) {
        return x < 0 || x >= cols || y < 0 || y >= rows;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }
}

package fields;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Obstacle;
import objects.ObstacleIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleObstaclesField extends AbstractField implements IField{

    private List<Obstacle> obstacles;

    public SimpleObstaclesField(int rows, int cols, double squareSize) {
        super(rows, cols, squareSize);
        this.obstacles = new ArrayList<>();
        this.setObstacles();
    }

    public void setObstacles() {
        Obstacle obstacle1 = new Obstacle(2, 2, squareSize);
        Obstacle obstacle2 = new Obstacle(rows - 3 , 2, squareSize);
        Obstacle obstacle3 = new Obstacle(2, rows - 3, squareSize);
        Obstacle obstacle4 = new Obstacle(rows - 3, rows - 3, squareSize);
        Obstacle obstacle5 = new Obstacle(4, 5, squareSize);
        Obstacle obstacle6 = new Obstacle(rows - 5 , 4, squareSize);
        Obstacle obstacle7 = new Obstacle(5, rows - 5, squareSize);
        Obstacle obstacle8 = new Obstacle(rows - 5, rows - 5, squareSize);
        this.obstacles.add(obstacle1);
        this.obstacles.add(obstacle2);
        this.obstacles.add(obstacle3);
        this.obstacles.add(obstacle4);
        this.obstacles.add(obstacle5);
        this.obstacles.add(obstacle6);
        this.obstacles.add(obstacle7);
        this.obstacles.add(obstacle8);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(offsetX, offsetY);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeRect(0, 0, cols * squareSize, rows * squareSize);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("AAD751"));
                } else {
                    gc.setFill(Color.web("A2D149"));
                }
                gc.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }

        Iterator<Obstacle> iterator = getObstacleIterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.draw(gc);
        }

        gc.restore();
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public Iterator<Obstacle> getObstacleIterator() {
        return new ObstacleIterator(obstacles);
    }

    @Override
    public void drawObstacles(GraphicsContext gc, List<Obstacle> obstacles) {
        Iterator<Obstacle> iterator = getObstacleIterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.draw(gc);
        }
    }
}

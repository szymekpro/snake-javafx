package fields;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Obstacle;
import objects.ObstacleIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CrossField extends AbstractField implements IField{

    private List<Obstacle> obstacles;

    public CrossField(int rows, int cols, double squareSize) {
        super(rows, cols, squareSize);
        this.obstacles = new ArrayList<>();
        this.setObstacles();
    }

    public void setObstacles() {
        this.obstacles.add(new Obstacle(3, 3, squareSize));
        this.obstacles.add(new Obstacle(rows - 4 , 3, squareSize));
        this.obstacles.add(new Obstacle(3, rows - 4, squareSize));
        this.obstacles.add(new Obstacle(rows -4, rows - 4, squareSize));

        this.obstacles.add(new Obstacle(6, 6, squareSize));
        this.obstacles.add(new Obstacle(rows - 7, 6, squareSize));
        this.obstacles.add(new Obstacle(6, rows - 7, squareSize));
        this.obstacles.add(new Obstacle(rows - 7, rows - 7, squareSize));

        this.obstacles.add(new Obstacle(9, 9, squareSize));
        this.obstacles.add(new Obstacle(rows - 10, 9, squareSize));
        this.obstacles.add(new Obstacle(9, rows - 10, squareSize));
        this.obstacles.add(new Obstacle(rows - 10, rows - 10, squareSize));

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

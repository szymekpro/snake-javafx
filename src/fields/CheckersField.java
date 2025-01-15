package fields;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.Obstacle;
import objects.ObstacleIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheckersField extends AbstractField implements IField{

    private List<Obstacle> obstacles;

    public CheckersField(int rows, int cols, double squareSize) {
        super(rows, cols, squareSize);
        this.obstacles = new ArrayList<>();
        this.setObstacles();
    }

    public void setObstacles() {
        for (int x = 2; x < rows; x += 4) {
            for (int y = 2; y < cols; y += 4) {
                obstacles.add(new Obstacle(x, y, squareSize));
            }
        }
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
                    gc.setFill(Color.web("99cc2e"));
                } else {
                    gc.setFill(Color.web("96c82d"));
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

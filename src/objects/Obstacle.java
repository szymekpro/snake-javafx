package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Obstacle {
    private double x;
    private double y;
    private double size;

    public Obstacle(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeRoundRect(x * size, y *size, size, size,20,20);

        gc.setFill(Color.GRAY);  // Change color according to the requirement
        gc.fillRoundRect(x * size, y * size, size, size,20,20);
    }
}

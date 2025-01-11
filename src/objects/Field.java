package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Field {

    private int rows;
    private int cols;
    private double squareSize;
    private double offsetX;
    private double offsetY;

    public Field(int rows, int cols, double squareSize) {
        this.rows = rows;
        this.cols = cols;
        this.squareSize = squareSize;
    }

    public void updateOffset(double canvasWidth, double canvasHeight) {
        offsetX = (canvasWidth - (cols * squareSize)) / 2;
        offsetY = (canvasHeight - (rows * squareSize)) / 2;
    }

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

        gc.restore();
    }

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

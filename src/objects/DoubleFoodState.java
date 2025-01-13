package objects;

import objects.Snake;
import java.awt.Point;

public class DoubleFoodState implements IFoodState {

    @Override
    public void applyEffect(Snake snake) {
        snake.addBodyPart(new Point(-1, -1));
        snake.addBodyPart(new Point(-1, -1));
        GameManager.addScore(1);
    }
}

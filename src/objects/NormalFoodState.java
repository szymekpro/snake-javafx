package objects;

import java.awt.*;

public class NormalFoodState implements IFoodState{
    @Override
    public void applyEffect(Snake snake) {
        snake.addBodyPart(new Point(-1,-1));
    }
}

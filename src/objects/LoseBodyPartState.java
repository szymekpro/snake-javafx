package objects;

import objects.Snake;

public class LoseBodyPartState implements IFoodState {

    @Override
    public void applyEffect(Snake snake) {
        if (snake.getBody().size() > 1) {
            snake.getBody().remove(snake.getBody().size() - 1);
            GameManager.addScore(-1);
        }
    }
}


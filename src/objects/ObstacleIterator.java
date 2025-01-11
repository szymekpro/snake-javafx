package objects;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ObstacleIterator implements Iterator<Obstacle> {
    private List<Obstacle> obstacles;
    private int currentIndex = 0;

    public ObstacleIterator(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < obstacles.size();
    }

    @Override
    public Obstacle next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return obstacles.get(currentIndex++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove operation is not supported.");
    }
}

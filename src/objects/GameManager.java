package objects;

public class GameManager {
    private static GameManager instance;
    static int score = 0;
    private int difficulty = 2;
    private int diffTime = 130;
    private boolean isOver = false;

    private GameManager() {

    }

    public void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy":
                this.difficulty = 1;
                break;
            case "medium":
                this.difficulty = 2;
                break;
            case "hard":
                this.difficulty = 3;
                break;
        }
        this.setDifficultyTime();
    }

    private void setDifficultyTime() {
        switch (difficulty) {
            case 1:
                this.diffTime = 180;
                break;
            case 2:
                this.diffTime = 130;
                break;
            case 3:
                this.diffTime = 80;
                break;
        }
    }

    public void gameOver(Snake snake, Field field) {
        if (snake.getHead().x < 0 || snake.getHead().y < 0 || snake.getHead().x >= field.getCols() || snake.getHead().y  >= field.getRows()) {
            isOver = true;
        }
        for (int i = 1; i < snake.getBody().size() ; i++) {
            if (snake.getHead().x == snake.getBody().get(i).x && snake.getHead().y == snake.getBody().get(i).y) {
                isOver = true;
                break;
            }
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver() {
        this.isOver = true;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public static void addScore(int value) {
        score += value;
    }

    public int getDiffTime() {
        return this.diffTime;
    }

    public void setDiffTime(int diffTime) {
        this.diffTime = diffTime;
    }

}

package boxgame.javafx.model;

public class Square {

    private int squareX;
    private int squareY;
    private int width;
    private int height;

    public Square(int squareX, int squareY, int width, int height) {
        this.squareX = squareX;
        this.squareY = squareY;
        this.width = width;
        this.height = height;
    }

    public int getSquareX() {
        return squareX;
    }

    public int getSquareY() {
        return squareY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOnBoard(Position position) {
        if (position.x >= squareX && position.y >= squareY
                && squareX + width >= position.x && squareY + height >= position.y) {
            return true;
        }
        return false;
    }

}

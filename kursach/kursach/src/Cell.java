public class Cell {
    private int corY;
    private char corX;
    private char ch;

    public Cell(char x, int y, char c) {
        corY = y;
        corX = x;
        ch = c;
    }

    Cell() {
    }

    char getCorX() {
        return corX;
    }

    int getCorY() {
        return corY;
    }

    char getCh() {
        return ch;
    }

    void setPosition(char x, char c, int y) {
        corX = x;
        corY = y;
        ch = c;
    }
}

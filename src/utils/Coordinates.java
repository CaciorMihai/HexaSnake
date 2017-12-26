package utils;

public class Coordinates {
    private int x, y;
    public Coordinates (final int xx, final int yy) {
        this.x = xx;
        this.y = yy;
    }
    public Coordinates () {
        this(0, 0);
    }
    public void setCoodrinates(final int xx, final int yy) {
        this.x = xx;
        this.y = yy;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}

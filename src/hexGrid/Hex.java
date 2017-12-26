package hexGrid;

public class Hex {
    private Hex topL;
    private Hex topR;
    private Hex right;
    private Hex botR;
    private Hex botL;
    private Hex left;
    private int distance;
    private float x;
    private float y;
    private int i;
    private int j;
    public int counter;

    public Hex() {
        topL = null;
        topR = null;
        right = null;
        botR = null;
        botL = null;
        left = null;
        distance = 0;
        x = 0f;
        y = 0f;
        i = 0;
        j = 0;
    }

    public void setX(final float x) {
        this.x = x;
    }
    public void setY(final float y) {
        this.y = y;
    }
    public float getX () {
        return x;
    }
    public float getY () {
        return y;
    }
    
    public void setI(final int i) {
        this.i = i;
    }
    public void setJ(final int j) {
        this.j = j;
    }
    public int getI () {
        return i;
    }
    public int getJ () {
        return j;
    }
    
    public int getDistance() {
        return distance;
    }
    public void setDistance(final int distance) {
        this.distance = distance;
    }

    public void setTopL (Hex topL) {
        this.topL = topL;
    }
    public void setTopR (Hex topR) {
        this.topR = topR;
    }
    public void setRight (Hex right) {
        this.right = right;
    }
    public void setBotR (Hex botR) {
        this.botR = botR;
    }
    public void setBotL (Hex botL) {
        this.botL = botL;
    }
    public void setLeft (Hex left) {
        this.left = left;
    }

    public Hex getTopL() {
        return topL;
    }
    public Hex getTopR() {
        return topR;
    }
    public Hex getRight() {
        return right;
    }
    public Hex getBotR() {
        return botR;
    }
    public Hex getBotL() {
        return botL;
    }
    public Hex getLeft() {
        return left;
    }
}

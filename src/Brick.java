import java.awt.*;

public class Brick {
    public static final int BRICK_WIDTH = 30;
    public static final int BRICK_HEIGHT = 15;
    private int x,y;
    public Brick(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
    public void paint (Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect(this.x, this.y, BRICK_WIDTH, BRICK_HEIGHT);
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

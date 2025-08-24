import java.awt.*;

public class Brick {
    public static final int SIZE=15;
    private int x,y;
    private boolean isAlive;
    public Brick(int x,int y)
    {
        this.x=x;
        this.y=y;
        isAlive=true;
    }
    public void paint (Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect(this.x, this.y, SIZE, SIZE);
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}

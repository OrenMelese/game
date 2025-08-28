import java.awt.*;

public class Ball implements Movable,Paintable {
    public static final int SIZE = 10;
    private int x, y,dx,dy ;
    private boolean isAlive;
    private ScenePanel scenePanel;


    public Ball (int x, int y,int dx,int dy,ScenePanel scenePanel) {
        this.x = x;
        this.y = y;
        this.dx=dx;
        this.dy=dy;
        isAlive=true;
        this.scenePanel=scenePanel;
    }

    public void paint (Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillOval(this.x, this.y, SIZE, SIZE);
    }
    public void move()
    {
        if(this.x+this.dx<0||this.x+2*SIZE+this.dx> scenePanel.getWidth())
            this.dx=-this.dx;
        if(this.y+this.dy<0)
            this.dy=-this.dy;
        if(this.y+SIZE+this.dy> scenePanel.getHeight())
        {
            isAlive=false;
        }
        this.x+=this.dx;
        this.y+=this.dy;
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

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}

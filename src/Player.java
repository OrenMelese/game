import java.awt.*;

public class Player {
    public static final int PLAYER_WIDTH = 60;
    public static final int PLAYER_HEIGHT = 10;
    public static final int PLAYER_SPEED=10;
    private int x, y;
    private ScenePanel scenePanel;

    public Player (ScenePanel scenePanel) {
        this.scenePanel = scenePanel;
        this.x = scenePanel.getWidth() / 2-PLAYER_WIDTH/2;
        this.y = scenePanel.getHeight() - 5*PLAYER_HEIGHT;

    }
    public void moveRight () {
        if (this.x < this.scenePanel.getWidth() - PLAYER_WIDTH) {
            this.x+=PLAYER_SPEED;
        }
    }

    public void moveLeft () {
        if (this.x > 0) {
            this.x-=PLAYER_SPEED;
        }
    }
    public void paint (Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect(this.x, this.y, PLAYER_WIDTH, PLAYER_HEIGHT);
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

import java.awt.*;

public class Player implements Paintable{
    public static final int PLAYER_WIDTH = 60;
    public static final int PLAYER_HEIGHT = 10;
    public static final int PLAYER_SPEED=7;
    private int x, y;


    public Player () {

        this.x = ScenePanel.SCENE_PANEL_WIDTH / 2-PLAYER_WIDTH/2;
        this.y = ScenePanel.SCENE_PANEL_HEIGHT - 5*PLAYER_HEIGHT;

    }
    public void moveRight () {
        if (this.x+PLAYER_WIDTH +PLAYER_SPEED< ScenePanel.SCENE_PANEL_WIDTH ) {
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

public class Player {
    public static final int PLAYER_WIDTH = 45;
    public static final int PLAYER_HEIGHT = 20;

    private int x, y;
    private ScenePanel scenePanel;

    public Player (ScenePanel scenePanel) {
        this.scenePanel = scenePanel;
        this.x = scenePanel.getWidth() / 2-PLAYER_WIDTH/2;
        this.y = scenePanel.getHeight() - 10;
    }
    public void moveRight () {
        if (this.x < this.scenePanel.getWidth() - SIZE) {
            this.x+=40;
        }
    }

    public void moveLeft () {
        if (this.x > 0) {
            this.x-=40;
        }
    }
}

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementListener implements KeyListener {
    private ScenePanel scenePanel;
    private boolean right;
    private boolean left;

    public MovementListener (ScenePanel scenePanel) {
        this.scenePanel = scenePanel;
        this.right = false;
        this.left = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed (KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> this.right = true;
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> this.left = true;
            }
            if (this.right) {
                this.scenePanel.getPlayer().moveRight();
            }
            if (this.left) {
                this.scenePanel.getPlayer().moveLeft();
            }
        }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> this.right = false;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> this.left = false;
        }
    }

}

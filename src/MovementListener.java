import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementListener implements KeyListener {
    private boolean right;
    private boolean left;

    public MovementListener () {

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

    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> this.right = false;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> this.left = false;
        }
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

}

import javax.swing.*;


public class Main {
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 500;
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);

        ScenePanel scenePanel = new ScenePanel(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        window.add(scenePanel);
        window.setVisible(true);
    }
}
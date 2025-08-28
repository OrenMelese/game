import javax.swing.*;


public class Main {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 1000;
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        MenuPanel menuPanel=new MenuPanel(0,0,MenuPanel.MENU_WIDTH,MenuPanel.MENU_HEIGHT);
        ScenePanel scenePanel = new ScenePanel(MenuPanel.MENU_WIDTH, 0, ScenePanel.SCENE_PANEL_WIDTH, ScenePanel.SCENE_PANEL_HEIGHT);
        scenePanel.addKeyListener(new MovementListener());
        window.add(scenePanel);
        window.add(menuPanel);
        scenePanel.setMenuPanel(menuPanel);
        menuPanel.setScenePanel(scenePanel);
        window.setVisible(true);
    }
}
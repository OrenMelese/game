import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public static final int MENU_WIDTH=300;
    public static final int MENU_HEIGHT=Main.WINDOW_HEIGHT;
    public static final int MENU_GRID_ROWS=3;
    public static final int MENU_GRID_COL=1;
    private ScenePanel scenePanel;
    boolean pause;
    private final JLabel score;
    private final JButton pauseButton;

    public void setScenePanel (ScenePanel scenePanel) {
        this.scenePanel = scenePanel;
    }



    public MenuPanel (int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        GridLayout gridLayout = new GridLayout(MENU_GRID_ROWS,MENU_GRID_COL);
        this.setLayout(gridLayout);
        this.pause =true;
        this.score = new JLabel("score: 0");
        JLabel rules = new JLabel("<html><div style='text-align: center;'>game rules <br>to win you need to break all the red bricks! <br> when you break one there is a 10% chance for a power up to drop <br> to get the power up you need to catch it with the platform <br> the black ones split every ball into three <br> and the green one spawns three balls in the middle <br> when you fail to bounce a ball on the platform when it comes down and the ball touches the ground the ball is out of the game <br>to move the platform you need to use the left and right arrow keys or the a and d keys to move left and right respectively<br> you lose when all the balls are out of the game <br>you score one point for each brick you break and each power up you pick up <br> press start to begin</div></html>");
        this.pauseButton = new JButton("start!");

        this.pauseButton.addActionListener((event) -> {


            if(!pauseButton.getText().equals("pause"))
            {
                if(pauseButton.getText().equals("retry"))
                    this.scenePanel.newGame();

                this.scenePanel.setRunning(true);
                this.pauseButton.setText("pause");
                this.scenePanel.mainGameLoop();

            }
            else
            {
                this.pauseButton.setText("resume");
                this.scenePanel.setRunning(false);
                this.scenePanel.getMovementListener().setLeft(false);
                this.scenePanel.getMovementListener().setRight(false);
            }



        });
        this.add(this.score);
        this.add(rules);
        this.add(pauseButton);

    }

    public void SetRetryButton()
    {
        this.pauseButton.setText("retry");
        this.scenePanel.setWin(false);
        this.scenePanel.setLose(false);
    }

    public void setScore(String score) {
        this.score.setText(score);
    }
}

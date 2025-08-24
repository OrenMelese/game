import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public static final int MENU_WIDTH=300;
    private ScenePanel scenePanel;
    boolean pause;
    private JLabel score;
    private JButton pauseButton;

    public void setScenePanel (ScenePanel scenePanel) {
        this.scenePanel = scenePanel;
    }

    public MenuPanel (int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        GridLayout gridLayout = new GridLayout(3, 1);
        this.setLayout(gridLayout);
        this.pause =true;
        this.score = new JLabel("score: 0");
        JLabel rules = new JLabel("<html><div style='text-align: center;'>game rules <br>to win you need to break all the red bricks! <br> when you break one there is a 10% chance for a power up to drop <br> to get the power up you need to catch it with the platform <br> the black ones split every ball into three <br> and the green one spawns three balls in the middle <br> when you fail to bounce a ball on the platform when it comes down and the ball touches the ground the ball is out of the game <br>to move the platform you need to use the left and right arrow keys or the a and d keys to move left and right respectively<br> you lose when all the balls are out of the game <br>you score one point for each brick you break and each power up you pick up <br> press start to begin</div></html>");
        pauseButton = new JButton("start!");

        pauseButton.addActionListener((event) -> {

            if(pauseButton.getText().equals("start!")){
                scenePanel.mainGameLoop();
                pauseButton.setText("pause");
                scenePanel.setRunning(true);
                this.pause=false;
            }

            else if (this.scenePanel != null &&pauseButton.getText().equals("pause")) {
                this.pause=true;
                pauseButton.setText("resume");
                scenePanel.setRunning(false);
            }
            else if (this.scenePanel != null&&pauseButton.getText().equals("resume")) {
                this.pause=false;
                scenePanel.setRunning(true);
                pauseButton.setText("pause");
                scenePanel.mainGameLoop();
            } else if (this.scenePanel != null&&pauseButton.getText().equals("retry")) {
                this.scenePanel.newGame();
                scenePanel.setRunning(true);
                pauseButton.setText("pause");
                scenePanel.mainGameLoop();
            }


        });
        this.add(this.score);
        this.add(rules);
        this.add(pauseButton);

    }

    public JLabel getScore() {
        return score;
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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ScenePanel extends JPanel {
    public static final int BRICKS_ROWS=24;
    public static final int BRICKS_COLUMNS=10;
    private Brick[][] bricks;
    private Player player;
    private ArrayList<Ball> balls;
    private ArrayList<Drop> drops;
    private boolean running;
    private Set<Movable> movables;
    private Set<Paintable> paintables;
    private int points;
    private MenuPanel menuPanel;
    private boolean win ;
    private boolean lose ;

    public void setMenuPanel (MenuPanel menuPanel) {
        this.menuPanel=menuPanel;
    }
    public ScenePanel (int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        this.running=true;
        this.newGame();

    }
    public void mainGameLoop () {

        new Thread(() -> {

            this.setFocusable(true);
            this.requestFocus();


            while(this.running)
            {
                this.checkCollisions();

                this.move();

                this.menuPanel.setScore("score: "+points);

                WinOrLose();// if won win=true if lost lose=true
                if(this.win||this.lose)
                {
                    String winOrLose=(this.win)?("win"):("lose");
                    this.menuPanel.setScore("<html><div style='text-align: center;'>you "+winOrLose+"! <br> your score was: "+points+"<br> press retry to play again maybe you will get a better score!</div></html>");
                    this.menuPanel.SetRetryButton();
                    running=false;

                }

                this.repaint();

                try {
                    Thread.sleep(16);
                } catch (InterruptedException _) {
                }
            }
            Thread.currentThread().interrupt();

        }).start();
        new Thread(() -> {
            while (this.running) {
                MovementListener movementListener=(MovementListener)this.getKeyListeners()[0];
                if (movementListener.isRight()) {
                    this.player.moveRight();
                }
                if (movementListener.isLeft()) {
                    this.player.moveLeft();
                }

                try {
                    Thread.sleep(16);
                } catch (InterruptedException _) {

                }
            }
            Thread.currentThread().interrupt();
        }).start();
    }

    private void checkCollisions() {
        this.drops.add(brickAndBallCollision());//when a ball hits a brick the brick breaks and there is a 10% chance for a drop to drop
        ArrayList<Drop> dropTemp=new ArrayList<>();


        for (Drop drop:this.drops) {
            if(drop!=null){
                if (checkCollision(drop)) {
                    Drop.dropPowerUp(drop.getType(), this.balls, this);
                    dropTemp.add(drop);
                }
                if(drop.getY()>this.getHeight())
                {
                    dropTemp.add(drop);
                }


            }

        }


        this.drops.removeAll(dropTemp);


        for (Ball ball:this.balls)
            if (checkCollision(ball))
                speedAndAngle(ball);

    }
    private void move(){

        this.movables.addAll(this.balls);
        this.movables.addAll(this.drops);
        for (Movable movable:this.movables)
        {
            if(movable!=null)
                movable.move();
        }
    }

    public void newGame()
    {
        this.player = new Player(this);
        this.drops=new ArrayList<>();
        this.balls=new ArrayList<>();
        this.movables = new HashSet<>();
        this.paintables = new HashSet<>();
        this.paintables.add(this.player);
        this.balls.add(new Ball(this.getWidth()/2-Ball.SIZE/2,this.getHeight()/2-Ball.SIZE/2,0,5,this));
        this.bricks=new Brick[BRICKS_ROWS][BRICKS_COLUMNS];
        for (int i = 0; i < BRICKS_ROWS; i++) {
            for (int j = 0; j < BRICKS_COLUMNS; j++) {
                this.bricks[i][j]=new Brick(i*20+5,(j+1)*20+5);
                this.paintables.add(this.bricks[i][j]);
            }
        }
        this.points=0;
        this.win=false;
        this.lose=false;
    }
    private Drop brickAndBallCollision() {
        for (Brick[] bricks1:this.bricks)
        {
            for (Brick brick:bricks1)
            {
                if(brick.isAlive())
                    for(Ball ball:this.balls)
                        if (this.checkCollision(brick,ball)) {
                            this.speedAndAngle(brick,ball);
                            brick.setAlive(false);
                            return Drop.dropGeneration(brick,this);//fix for if there is more than one collision
                        }
            }
        }

        return null;
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);//want to remove if not in balls or drops
        this.paintables.removeIf(paintable -> {
            if(paintable != null)
            {

                Class<?> instance=paintable.getClass();
                if((instance.equals(Ball.class) && !this.balls.contains((Ball)paintable))||(instance.equals(Drop.class) && !this.drops.contains((Drop)paintable)))
                {
                    return true;
                }

            }
            return false;
        });
        this.paintables.addAll(this.balls);
        this.paintables.addAll(this.drops);

        for(Paintable paintable:this.paintables)
            if(paintable!=null)
                paintable.paint(graphics);


    }
    private void WinOrLose()
    {
        Ball temp=null;
        for (Ball ball:this.balls) {
            if(!ball.isAlive())
            {
                temp=ball;

            }
        }
        this.balls.remove(temp);
        if(this.balls.isEmpty())
        {
            this.lose=true;
            return;
        }


        for(Brick[] bricks1:this.bricks)
            for (Brick brick:bricks1)
                if(brick!=null)
                    if(brick.isAlive())
                        return;

        this.win=true;
    }


    private void speedAndAngle(Ball ball)
    {//calculate an angle for the ball direction according to where the ball hit the player
        int ballCenter= ball.getX()+Ball.SIZE/2;
        int playerCenter = this.getPlayer().getX()+Player.PLAYER_WIDTH/2;
        int relativeHit = ballCenter-playerCenter;
        if(relativeHit<-(Player.PLAYER_WIDTH/6))//if the ball lands on the left third of the player
        {
            ball.setDx(-5);

        } else if (relativeHit>Player.PLAYER_WIDTH/6) {//if the ball lands on the right third of the player
            ball.setDx(5);

        }
        else{
            ball.setDx(0);

        }
        ball.setDy(-5);
    }
    private void speedAndAngle(Brick brick,Ball ball)
    {//flips the direction the ball came from
        int ballCenterX=ball.getX()+Ball.SIZE/2;
        int brickCenterX= brick.getX()+Brick.SIZE/2;
        int ballCenterY=ball.getY()+Ball.SIZE/2;
        int brickCenterY= brick.getY()+Brick.SIZE/2;
        if(brickCenterX+Brick.SIZE<ballCenterX ||brickCenterX-Brick.SIZE>ballCenterX)
        {
            ball.setDx(-ball.getDx());
        }
        if(brickCenterY+Brick.SIZE>ballCenterY ||brickCenterY-Brick.SIZE<ballCenterY)
        {
            ball.setDy(-ball.getDy());
        }
    }

    private boolean checkCollision (Brick brick, Ball ball) {
        Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), Ball.SIZE, Ball.SIZE);
        Rectangle brickRect = new Rectangle(brick.getX(), brick.getY(), Brick.SIZE,Brick.SIZE);
        if (ballRect.intersects(brickRect)) {
            this.points++;

            return true;
        } else {
            return false;
        }
    }
    private boolean checkCollision (Ball ball) {
        Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), Ball.SIZE, Ball.SIZE);
        Rectangle playerRect = new Rectangle(this.player.getX(), this.player.getY(), Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        return ballRect.intersects(playerRect);
    }
    private boolean checkCollision (Drop drop) {
        Rectangle dropRect = new Rectangle(drop.getX(), drop.getY(), Drop.SIZE, Drop.SIZE);
        Rectangle playerRect = new Rectangle(this.player.getX(), this.player.getY(), Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        if (dropRect.intersects(playerRect)) {
            this.points++;
            return true;
        } else {
            return false;
        }
    }










    public Player getPlayer () {
        return this.player;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }
}

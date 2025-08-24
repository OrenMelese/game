import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScenePanel extends JPanel {
    public static final int BRICKS_ROWS=25;
    public static final int BRICKS_COLUMNS=10;
    private Brick[][] bricks;
    private Player player;
    private ArrayList<Ball> balls;
    private ArrayList<Drop> drops;

    public ScenePanel (int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        this.player = new Player(this);
        this.drops=new ArrayList<>();
        this.balls=new ArrayList<>();
        this.balls.add(new Ball(width/2-Ball.SIZE/2,height/2-Ball.SIZE/2,0,5,this));
        this.bricks=new Brick[BRICKS_ROWS][BRICKS_COLUMNS];
        for (int i = 0; i < BRICKS_ROWS; i++) {
            for (int j = 0; j < BRICKS_COLUMNS; j++) {
                bricks[i][j]=new Brick(i*20+5,(j+1)*20+5);
            }
        }

        this.mainGameLoop();
    }
    private void mainGameLoop () {

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            boolean running = true;
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(new MovementListener(this));

            while(running)
            {
                if(isGameOver(this.balls))// if all the balls touch the ground its game over
                    running=false;

                this.drops.add(brickAndBallCollision(this.bricks,this.balls));//when a ball hits a brick the brick breaks and there is a 10% chance for a drop to drop
                ArrayList<Drop> temp=new ArrayList<>();
                for (Drop drop:this.drops) {
                    if(drop!=null){
                        if (checkCollision(drop)) {
                            Drop.dropPowerUp(drop.getType(), this.balls, this);
                            temp.add(drop);
                        }
                        if(drop.getY()>this.getHeight())
                        {
                            temp.add(drop);
                        }

                        drop.fall();
                    }

                }

                for(Drop drop:temp)
                {
                    this.drops.removeAll(temp);
                }

                for (Ball ball:this.balls) {
                    if (checkCollision(ball)) {
                        speedAndAngle(ball);
                    }
                    ball.move();
                }


                this.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }


        }).start();
    }

    private Drop brickAndBallCollision(Brick[][] bricks, ArrayList<Ball> balls) {
        for (Brick[] bricks1:this.bricks)
        {
            for (Brick brick:bricks1)
            {
                for(Ball ball:this.balls)
                    if (brick.isAlive()&&this.checkCollision(brick,ball)) {
                        this.speedAndAngle(brick,ball);
                        brick.setAlive(false);
                        return Drop.dropGeneration(brick,this);
                    }
            }
        }
        return null;
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        this.player.paint(graphics);
        for (Brick[] bricks1:this.bricks)
        {
            for (Brick brick:bricks1)
            {
                if(brick.isAlive())
                    brick.paint(graphics);
            }
        }
        for (Ball ball:this.balls) {
            ball.paint(graphics);
        }
        for (Drop drop:this.drops) {
            if(drop!=null)
                drop.paint(graphics);
        }

    }
    public static boolean isGameOver(ArrayList<Ball> balls)
    {
        Ball temp=null;
        for (Ball ball:balls) {
            if(!ball.isAlive())
            {
                temp=ball;

            }
        }
        balls.remove(temp);
        if (balls.isEmpty())
        {
            return true;
        }
        return false;
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
            return true;
        } else {
            return false;
        }
    }
    private boolean checkCollision (Ball ball) {
        Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), Ball.SIZE, Ball.SIZE);
        Rectangle playerRect = new Rectangle(this.player.getX(), this.player.getY(), Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        if (ballRect.intersects(playerRect)) {
            return true;
        } else {
            return false;
        }
    }
    private boolean checkCollision (Drop drop) {
        Rectangle dropRect = new Rectangle(drop.getX(), drop.getY(), Drop.SIZE, Drop.SIZE);
        Rectangle playerRect = new Rectangle(this.player.getX(), this.player.getY(), Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        if (dropRect.intersects(playerRect)) {
            return true;
        } else {
            return false;
        }
    }

    public Player getPlayer () {
        return this.player;
    }
}

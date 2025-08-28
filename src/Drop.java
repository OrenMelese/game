import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Drop implements Movable, Paintable {
    //split - create 2 more balls at each ball location that go to random locations
    //add - create 3 balls in the center of the screen that fall downwards
    public static final int SIZE = 15;
    private int x, y;
    private DropType type;



    public Drop (int x, int y ,DropType type) {
        this.x = x;
        this.y = y;
        this.type=type;

    }

    public void move () {
        this.y+=3;
    }

    public void paint (Graphics graphics) {
        Color color = null;
        switch(this.type)
        {
            case SPLIT -> color=Color.GREEN;
            case ADD -> color=Color.BLACK;
        }


        graphics.setColor(color);
        graphics.fillOval(this.x, this.y, SIZE, SIZE);
    }

    public static Drop dropGeneration(Brick brick)
    {//10% chance to get a drop when you destroy a brick and equal chance to get either drop
        DropType type1=null;
        Random rnd=new Random();
        int number=rnd.nextInt(100);
        if(number<10)
        {
            int zeroOrOne=number%2;
            switch(zeroOrOne)
            {
                case 0 ->type1=DropType.SPLIT;
                case 1 ->type1=DropType.ADD;
            }
            return new Drop(brick.getX(), brick.getY(), type1);
        }
        return null;
    }
    public static void dropPowerUp(DropType type,ArrayList<Ball> balls)
    {
        switch (type)
        {
            case ADD -> {
                balls.add(new Ball(ScenePanel.SCENE_PANEL_WIDTH/2-Ball.SIZE/2,ScenePanel.SCENE_PANEL_HEIGHT/2-Ball.SIZE/2,0,5));
                balls.add(new Ball(ScenePanel.SCENE_PANEL_WIDTH/2-Ball.SIZE/2+2*Ball.SIZE,ScenePanel.SCENE_PANEL_HEIGHT/2-Ball.SIZE/2,0,5));
                balls.add(new Ball(ScenePanel.SCENE_PANEL_WIDTH/2-Ball.SIZE/2-2*Ball.SIZE,ScenePanel.SCENE_PANEL_HEIGHT/2-Ball.SIZE/2,0,5));
            }
            case SPLIT -> {
                ArrayList<Ball> splitBalls=new ArrayList<>();
                for (Ball ball:balls)
                {
                    if(ball.getDx()==0 ||ball.getDx()==-5)
                    {
                        splitBalls.add(new Ball(ball.getX(),ball.getY(),5,ball.getDy()));
                    }
                    if(ball.getDx()==0 ||ball.getDx()==5)
                    {
                        splitBalls.add(new Ball(ball.getX(),ball.getY(),-5,ball.getDy()));
                    }
                    if(ball.getDx()==5 ||ball.getDx()==-5)
                    {
                        splitBalls.add(new Ball(ball.getX(),ball.getY(),0,ball.getDy()));
                    }

                }
                balls.addAll(splitBalls);
            }

        }
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

    public DropType getType() {
        return type;
    }

    public void setType(DropType type) {
        this.type = type;
    }
}

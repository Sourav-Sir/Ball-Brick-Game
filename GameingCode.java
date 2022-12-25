import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public class GameingCode extends JPanel implements ActionListener, KeyListener {
    private boolean play = false;
    private int totalBricks = 21;
    private Timer timer ;
    private int delay = 8;
    private int score = 0;
    private int ballPoX = 120;
    private int ballPoY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int playerX = 350;
    private MapGenerator  map;

    //constructor
    public GameingCode() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        

        timer = new Timer(delay, this);
        timer.start();

        map = new MapGenerator(3, 7);
    }
    // method for painting the frame (grapics and color)
    public void paint (Graphics g) {
        //Black Canvas
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //border
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(0, 3, 3, 592);
        g.fillRect(691, 3, 3, 592);

        //bricks
        map.draw((Graphics2D) g);
        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        
        
        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPoX, ballPoY, 20, 20);



        // Score
        g.setColor(Color.green);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Score: "+ score, 550, 30);



        // Game Over
        if (ballPoY>=570) {
            play = false;
            ballXdir =0;
            ballYdir = 0;

            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GameOver!! Score:"+score, 200,300);


            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("Press Enter to Restart!!", 150,350);

        }

        if (totalBricks<=0) {
            play = false;
            ballXdir =0;
            ballYdir = 0;

            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won!! Score:"+score, 200,300);


            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("Press Enter to Restart!!", 150,350);
        }
        
    }
    // Move left key method
    private void moveLeft() {
        play = true;
        playerX -=20;
    }
    // Move Right key method
    private void moveRight() {
        play = true;
        playerX +=20;
    }
   
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_LEFT) {
            if (playerX<=0) {
                playerX=0;     
            } else
            moveLeft();
        }
        if (e.getKeyCode()== KeyEvent.VK_RIGHT) {
            if(playerX>=600){
                playerX=600;
            }
            else
            moveRight();
        }
        if (e.getKeyCode()== KeyEvent.VK_ENTER) {
            if(!play){
                score=0;
                totalBricks = 21;
                ballPoX = 120;
                ballPoY = 320;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 320;

                map = new MapGenerator(3, 7);
            }
        }
        repaint();
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {

            if (ballPoX<=0) {
                ballXdir=-ballXdir;
            }
            if (ballPoX>=670) {
                ballXdir=-ballXdir;
            }
            if (ballPoY<=0) {
                ballYdir=-ballYdir;
            }

            Rectangle ballRect = new Rectangle(ballPoX,ballPoY,20,20);
            Rectangle paddleRect = new Rectangle(playerX,550,100,8);
            
            if (ballRect.intersects(paddleRect) ) {
                ballYdir =- ballYdir;
            }

            A:for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[i].length; j++) {
                    if(map.map[i][j]>0) {
                        int width = map.brickWidth;
                        int height = map.brickHeight;
                        int brickXpos = 80 + j*width;
                        int brickYpos = 50+ i *height;
                        
                        Rectangle brickRect = new Rectangle(brickXpos,brickYpos,width,height);
                        if (ballRect.intersects(brickRect)) {
                            map.setBrick(0, i, j); 
                            totalBricks --;
                            score+=5;
                            if (ballPoX+19<=brickXpos || ballPoX+1>brickXpos+width) {
                                ballXdir =- ballXdir;
                            }
                            else {
                                ballYdir =- ballYdir;
                            }
                            break A;
                        }
                    }
                }
                
            }


            ballPoX += ballXdir;
            ballPoY += ballYdir;
        }
        repaint();

    }
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
   
    
  
}


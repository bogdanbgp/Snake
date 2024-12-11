import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

    // Class GamePanel:
public class GamePanel extends JPanel implements ActionListener {
    
    
// =======================================================================================================
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
// =======================================================================================================


    

// =======================================================================================================
        // * UNITS: SOME SORT OF MATRIX FOR THE GAME - HOW BIG THE OBJECTS OF THE GAME TO BE *
    static final int UNIT_SIZE = 25; // UNIT_SIZE --> size of objects in the game
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; // GAME_UNITS --> components
                                                                         // (objects, like the snake body
                                                                         // parts, apple) that fit in screen
    final int x[] = new int[GAME_UNITS]; // X coordinates for entire snake to fit in frame. body parts+head
    final int y[] = new int[GAME_UNITS]; // Y coordinates for entire snake to fit in frame. body parts+head
// =======================================================================================================


    

// =======================================================================================================
    static final int DELAY = 75; // the higher the number, the slower the game
// =======================================================================================================

    

    
// =======================================================================================================
    int bodyParts = 6; // body parts of snake (initially) - the size of the snake. It will grow by eating.
    int applesEaten; // initially 0
    int appleX; // coordinate - where the apple is located on X axis (after eaten, appears in random place)
    int appleY; // coordinate - where the apple is located on Y axis (after eaten, appears in random place)
    char direction = 'R'; // U up, D down, L left, R right. Game starts with snake going to right (R).
    boolean running = false;
// =======================================================================================================
    

    

// =======================================================================================================
    Timer timer; // * Fires one or more ActionEvents at specified intervals
    Random random; // * An instance of this class is used to generate a stream of pseudorandom numbers
// =======================================================================================================
    
   
   // Constructor: 
    GamePanel() {
        random = new Random(); // instance of the random Class - random appearance of apples.
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MySnakeKeyAdapter());
        startGame(); // first things first, start game method




    }

    // Methods:
// .......................................................................................................
        public void startGame(){
            //when game starts........
        newApple(); // apple appears
        running = true; // running starts
        timer = new Timer(DELAY,this); // this = using the action listener interface
        timer.start(); // timer starts
        }
// .......................................................................................................






// .......................................................................................................
        public void paintComponent(Graphics g) { // Graphics --> draw onto components
            super.paintComponent(g); // paintComponent --> calls the UI delegate's paint method
            draw(g); // g -->the Graphics object to protect

        }
// .......................................................................................................






// .......................................................................................................
        public void draw(Graphics g) {
            //game overscreen:
            if (running) { // if game is running......... settings:

            /*
                // draw lines across the game panel (OPTIONAL)
                for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                    //g.DrawLine(x1, y1, x2, y2 ) --> draw lines across X axis and Y axis
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
            */

                // draw the apple:
                g.setColor(Color.RED);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // apple position on x and Y axis + size

                // draw the snake:
                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.GREEN); // Color of snake's HEAD
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); // fillRect = fill Rectangle
                    }
                    /*if i != 0, we're dealing with the body of the snake.... so: */
                    else {
                        g.setColor(new Color(45, 180, 0)); // Color of snake's BODY
                        // cool -> change color of snake's body to multicolor:
                        g.setColor(new Color(random.nextInt(255),
                                             random.nextInt(255),
                                             random.nextInt(255)));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }

                // Show SCORE on the upper side of the game, while game is running:
                g.setColor(Color.RED);
                g.setFont(new Font("Charm", Font.BOLD, 40));
                // FontMetrics for aligning up text in the upper side of the screen:
                FontMetrics fontMetricsScorePlay = getFontMetrics(g.getFont());
                g.drawString("Score: "+applesEaten,
                              (SCREEN_WIDTH-fontMetricsScorePlay.stringWidth("Score: "+applesEaten))/2,
                             /*y:*/  g.getFont().getSize());

            }
            else { // if game is no longer running.........
                gameOver(g);
            }
        }
// .......................................................................................................






// .......................................................................................................
        public void newApple(){
            // generate the coordinates of a new apple (random locations)
            appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; // random on X axis
            appleY = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; // random on Y axis
        }
// .......................................................................................................






// .......................................................................................................
        public void move() {
        // move the snake
            //for loop to iterate through all body parts of the snake - shifting them:
            for(int i = bodyParts; i>0; i--) {
                x[i] = x[i-1]; // shifting all coordinates in the array over at one spot
                y[i] = y[i-1]; // same on y axis
            }
            // cases for directions:
          switch(direction) {
              case 'U':
                  y[0] = y[0] - UNIT_SIZE; // move to the next position, y axis (vertical), " - " = UP
                  break;
              case 'D':
                  y[0] = y[0] + UNIT_SIZE; // move to the next position, y axis (vertical), " + " = DOWN
                  break;
              case 'L':
                  x[0] = x[0] - UNIT_SIZE; // move to the next position, x axis (horizontal), " - " = LEFT
                  break;
              case 'R':
                  x[0] = x[0] + UNIT_SIZE; // move to the next position, x axis (horizontal), " + " = RIGHT
                  break;
            }   }
// .......................................................................................................






// .......................................................................................................
      public void checkApple() { //x[0] = x position of snake's head horizontally.
                                 // y[0] = y position of snake's head vertically
            if((x[0] == appleX) && (y[0] == appleY)) {
                bodyParts++; // body grows by 1 body part when the snake eats an apple
                applesEaten++; // the score
                newApple(); // generates a new apple (random position) after the previous one is eaten


          }

        }
// .......................................................................................................






// .......................................................................................................
        public void checkCollisions() {
         //check to see if the head of the snake collides with the body (iterating through the body parts):
            for (int i = bodyParts; i > 0; i--) { // * 0 = head of snake; "i" refers to the body parts
                if ((x[0] == x[i]) && (y[0] == y[i])) { // --> this means the head collided with the body
                    running = false; // the running stops since the head collided with the body (game over)
                }   }

                //check if the head of the snake touches left border:
                if (x[0] < 0) {
                    running = false; }
                //check if the head of the snake touches right border:
                if (x[0] > SCREEN_WIDTH) {
                    running = false; }
                //check if the head of the snake touches top border:
                if (y[0] < 0) {
                    running = false; }
                //check if the head of the snake touches bottom border:
                if (y[0] > SCREEN_HEIGHT) {
                    running = false; }

                        //if game not running, timer stops
                        if (!running) {
                            timer.stop(); }
        }
// .......................................................................................................






// .......................................................................................................
        public void gameOver(Graphics g) {
            // Display Score when the game is over:
            g.setColor(Color.RED);
            g.setFont(new Font("Charm", Font.BOLD, 40));
            // FontMetrics for aligning up text in the upper side of the screen:
            FontMetrics fontMetricsScoreEnd = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,
                    (SCREEN_WIDTH-fontMetricsScoreEnd.stringWidth("Score: "+applesEaten))/2,
                    /*y:*/  g.getFont().getSize());


            // Display Game Over text when the game is over:
                g.setColor(Color.RED);
                g.setFont(new Font("Charm", Font.BOLD, 75));
            // FontMetrics for aligning up text in the center of the screen:
                FontMetrics fontMetricsGameOver = getFontMetrics(g.getFont());
                g.drawString("Game Over",
                              (SCREEN_WIDTH-fontMetricsGameOver.stringWidth("Game Over"))/2,
                              SCREEN_HEIGHT/2);
                              // --> Game Over in the middle of the screen
        }
// .......................................................................................................






// .......................................................................................................
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) { // if game is running...
            move(); // method - start moving the snake
            checkApple(); // method - check to see if the snake ran into the apple
            checkCollisions(); // method - check to see if the snake ran into a collision
        }          /*else*/ repaint(); // if the game is no longer running, call the repaint method to restart
    }
// .......................................................................................................






    // Inner Class:
    public class MySnakeKeyAdapter extends KeyAdapter{
        @Override   public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                // 4 cases, one for each of the Arrow keys
                    // Move to LEFT:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    // only do 90 degree moves:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    if(direction != 'R') {
                       direction = 'L';
                    }
                    break;
        //--------------------------------------------------
                    // Move to RIGHT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    // only do 90 degree moves:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
        //--------------------------------------------------
                    // Move UP:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    // only do 90 degree moves:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
        //--------------------------------------------------
                    // Move DOWN:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    // only do 90 degree moves:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    if(direction != 'U') {
                        direction = 'D';
                    }
            }
        }
    }
}

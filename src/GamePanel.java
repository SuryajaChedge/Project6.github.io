import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int width = 1000;
    static final int height = 600;
    static final int unit_size = 30;
    static final int units = (width*height)/(unit_size*unit_size);
    static final int delay = 150;
    final int[] a = new int[units];
    final int[] b = new int[units];
    int parts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame()
    {
        newApple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(running)
        {
            for(int i=0; i<height/unit_size; i++)
            {
                g.drawLine(0, i*unit_size, width, i*unit_size);
            }
            g.setColor(Color.yellow);
            g.fillOval(appleX, appleY, unit_size, unit_size);
            for(int i=0; i<parts; i++)
            {
                if(i==0)
                {
                    g.setColor(Color.black);
                    g.fillRect(a[i], b[i], unit_size, unit_size);
                }
                else {
                    g.setColor(new Color(0,0,0));
                    g.fillRect(a[i], b[i], unit_size, unit_size);
                }
            }
            g.setColor(Color.yellow);
            g.setFont(new Font("Hi", Font.ROMAN_BASELINE,30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + applesEaten, (width-metrics.stringWidth("Score:" + applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }
    public void newApple()
    {
        appleX = random.nextInt((int)(width/unit_size))*unit_size;
        appleY = random.nextInt((int)(height/unit_size))*unit_size;
    }
    public void move()
    {
        for (int i = parts; i>0; i--)
        {
            a[i] = a[i-1];
            b[i] = b[i-1];
        }
        switch (direction) {
            case 'U': {
                b[0] = b[0] - unit_size;
                break;
            }
            case 'D': {
                b[0] = b[0] + unit_size;
                break;
            }
            case 'L': {
                a[0] = a[0] - unit_size;
                break;
            }
            case 'R': {
                a[0] = a[0] + unit_size;
                break;
            }
        }
    }
    public void checkApple()
    {
        if((a[0]==appleX)&&(b[0]==appleY))
        {
            parts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions()
    {
        for (int i = parts; i>0; i--)
        {
            if((a[0]==a[i])&&(b[0]==b[i]))
            {
                running = false;
            }
            if(a[0]<0)
            {
                running = false;
            }
            if(a[0]>width)
            {
                running = false;
            }
            if(b[0]<0)
            {
                running = false;
            }
            if(b[0]>height)
            {
                running = false;
            }
            if(!running)
            {
                timer.stop();
            }
        }
    }
    public void gameOver(Graphics g)
    {
        g.setColor(Color.yellow);
        g.setFont(new Font("Hi", Font.ROMAN_BASELINE, 30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score:" + applesEaten, (width-metrics1.stringWidth("Score:" + applesEaten))/2, g.getFont().getSize());

        g.setColor(Color.yellow);
        g.setFont(new Font("Hi", Font.ROMAN_BASELINE, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (width-metrics2.stringWidth("Game Over"))/2, height/2);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(running)
        {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                }
                case KeyEvent.VK_UP: {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                }
            }
        }
    }
}


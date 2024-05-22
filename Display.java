import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

// The Display is the region in the window where drawing occurs.
public class Display extends JComponent implements KeyListener, //need for keyboard input
        MouseMotionListener, MouseListener //need for mouse input
{
    //main method for testing
    public static void main(String[] args) {
        Display display = new Display();
        display.run();
    }

    private static final int WHITE = 0;
    private static final int BLUE = 1;
    private static final int YELLOW = 2;
    private static final int RED = 3;
    private static final int GREEN = 4;
    private static final Color[] colors = {Color.WHITE, Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN};
    private static final Color[] lightColors = {Color.WHITE, Color.BLUE.brighter(), Color.YELLOW.brighter(), Color.RED.brighter(), Color.GREEN.brighter()};

    private int grid[][];
    private Map<Integer, Tile> hover;

    private Tile hoverTile;
    private JFrame frame;
    private int width;
    private int height;

    private int tileBar[][];

    public Display() {
        width = 400;
        height = 400;

        frame = new JFrame(); //create window
        frame.setTitle("blokus"); //set title of window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing window will exit program
        setPreferredSize(new Dimension(width, height + 200)); //set size of drawing region

        //need for keyboard input
        setFocusable(true); //indicates that Display can process key presses
        addKeyListener(this); //will notify Display when a key is pressed

        //need for mouse input
        addMouseMotionListener(this); //will notify Display when the mouse is pressed
        addMouseListener(this);

        frame.getContentPane().add(this); //add drawing region to window
        frame.pack(); //adjust window size to fit drawing region
        frame.setVisible(true); //show window

        grid = new int[20][20];
        hover = new TreeMap<Integer, Tile>();

        tileBar = new int[5][125];

        for(int i = 0; i < 11; ++i) {
            Tile temp = new Tile(i * (width / 11), height + 100, i);
            temp.updateGrid(tileBar);
        }
    }

    //called automatically when Java needs to draw the Display
    public void paintComponent(Graphics g) {

        g.setColor(Color.WHITE); //set pen color to white
        g.fillRect(0, 0, width, height); //fill with white rectangle

        for(int i = 0; i < width / 20; ++i) {
            for(int j = 0; j < height / 20; ++j) {
                g.setColor(colors[grid[i][j]]);
                g.fillRect(i * (width / 20), j * (height / 20), (width / 20), (height / 20));
            }
        }

        for(int i : hover.keySet()) {
            g.setColor(lightColors[i + 1]);
            for(int j[] : hover.get(i).getCoordinates()) {
                g.fillRect((hover.get(i).getLocation()[0] + j[0]) * (width / 20), (hover.get(i).getLocation()[1] + j[1]) * (height / 20), (width / 20), (height / 20));
            }
        }

        
        for(int i = 0; i < tileBar.length; ++i) {
            for(int j = 0; j < tileBar[i].length; ++j) {
                g.setColor(colors[tileBar[i][j]]);
                g.fillRect(400 / 5 * i, 400 + 100, 400 / 5, 400 / 5);
            }
        }
        
    }

    //need for keyboard input
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); //indicates which key was pressed
        System.out.println("key pressed:  " + key); //shows you key code values for other keys
        if(key == 38) //tests if "up" arrow was pressed
        {
            hover.get(0).rotate();
            //hoverTile.updateGrid(hover);
        }
        if(key == 39) {
            hover.get(0).flip();
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    //need for mouse input
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int gridX = mouseX / (width / 20);
        int gridY = mouseY / (height / 20);
        System.out.println("mouse clicked:  " + mouseX + ", " + mouseY);
        //Tile tile = new Tile(gridX, gridY, 0);
        if(hover.get(0).canPlace())
            hover.get(0).updateGrid(grid);
        repaint(); //indicates Display must be redrawn (Java will call paintComponent)
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    //need for automation (graphical changes not prompted by the keyboard or mouse)
    public void run() {
        while(true) {
            repaint(); //indicates Display must be redrawn (Java will call paintComponent)
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            } //give Java 100ms to run paintComponent
        }
    }

    public void mouseDragged(MouseEvent e) {
        
    }

    public void mouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int gridX = mouseX / (width / 20);
        int gridY = mouseY / (height / 20);

        if(hover.get(0) == null)
            hover.put(0, new Tile(gridX, gridY, 0));

        if(gridY < 20)
            hover.get(0).setLocation(gridX, gridY);

        repaint();
    }
}

import java.awt.*;
import javax.swing.*;

public class Tile {
    int x;
    int y;
    int width;
    int height;
    Color color;
    int type;

    int[][][] tiles = {{{0, 0}}, //.
        {{0, 0}, {0, 1}}, //..
        {{0, -1}, {0, 0}, {0, 1}}, //...
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, //....
        {{0, -2}, {0, -1}, {0, 0}, {0, 1}, {0, 2}}, //.....
        {{0, 0}, {0, 1}, {1, 0}, {1, 1}}, //O
        {{0, 0}, {0, 1}, {1, 0}, {-1, 1}}, //.|-
        {{0, 0}, {0, -1}, {1, 0}, {-1, 0}}, //.|.
        {{0, 0}, {-1, 0}, {1, 0}, {1, -1}}, //..|
        {{1, 0}, {0, 0}, {0, -1}, {0, 1}, {1, 1}}, //|*
        {{-1, 0}, {0, 0}, {0, -1}, {0, 1}, {1, 1}}}; //*|_


    public Tile(int x, int y, int type) {
        this.x = x;
        this.y = y;

        this.type = type;
    }

    public void updateGrid(int[][] grid) {
        for(int i = 0; i < tiles[type].length; ++i) {
            if(x + tiles[type][i][0] < 20 && y + tiles[type][i][1] < 20 && x + tiles[type][i][0] >= 0 && y + tiles[type][i][1] >= 0)
                grid[x + tiles[type][i][0]][y + tiles[type][i][1]] = 1;
        }
    }
    
    public boolean canPlace() {
        for(int i = 0; i < tiles[type].length; ++i) {
            if(!(x + tiles[type][i][0] < 20 && y + tiles[type][i][1] < 20 && x + tiles[type][i][0] >= 0 && y + tiles[type][i][1] >= 0))
                return false;
        }
        return true;
    }

    public void removeTile(int[][] grid) {
        for(int i = 0; i < tiles[type].length; ++i) {
            if(x + tiles[type][i][0] < 20 && y + tiles[type][i][1] < 20 && x + tiles[type][i][0] >= 0 && y + tiles[type][i][1] >= 0)
                grid[x + tiles[type][i][0]][y + tiles[type][i][1]] = 0;
        }
    }

    public int[][] getCoordinates() {
        return tiles[type];
    }

    public void rotate() {
        for(int i = 0; i < tiles[type].length; ++i) {
            int temp = tiles[type][i][0];
            tiles[type][i][0] = tiles[type][i][1];
            tiles[type][i][1] = -temp;
        }
    }

    public void flip() {
        for(int i = 0; i < tiles[type].length; ++i) {
            tiles[type][i][0] = -tiles[type][i][0];
        }
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getLocation() {
        int[] location = {x, y};
        return location;
    }
}
//change to foreach loops
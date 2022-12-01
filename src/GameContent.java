import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;

public class GameContent extends JPanel {

    final static int FIELD_HEIGHT = 15;
    final static int FIELD_WIDTH = 15;
    final static int BLOCK_SIZE = 20;
    final static int WINDOW_BORDERX=15;
    final static int WINDOW_BORDERY=39;

    final int countOfMines = 35;

    final int COVERED_CELL = 10;
    final int EMPTY_CELL = 0;
    final int MARKED_CELL = 10;
    final int MINE_CELL = 9;
    final int COVERED_MINE_CELL = 19;
    final int MARKED_MINE_CELL = 29;

    final int DRAW_COVER = 10;
    final int DRAW_MARK = 11;
    final int DRAW_WRONG_MARK=12;

    int[][] gameMatrix = new int[FIELD_HEIGHT][FIELD_WIDTH];
    Image[] img = new Image[12];

    boolean inGame = true;

    public GameContent() {
        initGame();

    }

    public void initGame() {

        loadImages();
        generateMines();
        placeNumbersAroundMines();

    }
  
    public void loadImages() {
        for (int i = 0; i < 12; i++) {
            var path = "src/images/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

    }

    void fillTheGameMatrix() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                gameMatrix[y][x]=COVERED_CELL;
            }
        }
    }

    void generateMines() {
        int mineX;
        int mineY;
        Random random = new Random();
        int i = 0;
        while (i < countOfMines) {
            mineY = random.nextInt(FIELD_HEIGHT);
            mineX = random.nextInt(FIELD_WIDTH);
            if (gameMatrix[mineY][mineX]!= COVERED_MINE_CELL) {
                gameMatrix[mineY][mineX] = COVERED_MINE_CELL;
                i++;
            }

        }

    }

    void placeNumbersAroundMines() {
        int cell;
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (gameMatrix[y][x]==COVERED_MINE_CELL) {
                    if (y==0&&x==0) {
                        if (gameMatrix[0][1]!=COVERED_MINE_CELL) gameMatrix[0][1]+=1;
                        if (gameMatrix[1][0]!=COVERED_MINE_CELL) gameMatrix[1][0]+=1;
                        if (gameMatrix[1][1]!=COVERED_MINE_CELL) gameMatrix[1][1]+=1;
                    }
                    else if (y==0&&(x>0)&&(x<FIELD_WIDTH-1)) {
                        if (gameMatrix[0][x-1]!=COVERED_MINE_CELL) gameMatrix[0][x-1]+=1;
                        if (gameMatrix[0][x+1]!=COVERED_MINE_CELL) gameMatrix[0][x+1]+=1;
                        for (int k = x-1; k < x+2; k++) {
                            if (gameMatrix[y+1][k]!=COVERED_MINE_CELL) gameMatrix[y+1][k]+=1;
                        }
                    }
                    else if (y==0&&x==FIELD_WIDTH - 1) {
                        if (gameMatrix[0][FIELD_WIDTH-2]!=COVERED_MINE_CELL) gameMatrix[0][FIELD_WIDTH-2]+=1;
                        if (gameMatrix[1][FIELD_WIDTH-2]!=COVERED_MINE_CELL) gameMatrix[1][FIELD_WIDTH-2]+=1;
                        if (gameMatrix[1][FIELD_WIDTH-1]!=COVERED_MINE_CELL) gameMatrix[1][FIELD_WIDTH-1]+=1;
                    }

                    else if (y==FIELD_HEIGHT-1&&x==0) {
                        if (gameMatrix[FIELD_HEIGHT-2][0]!=COVERED_MINE_CELL) gameMatrix[FIELD_HEIGHT-2][0]+=1;
                        if (gameMatrix[FIELD_HEIGHT-2][1]!=COVERED_MINE_CELL) gameMatrix[FIELD_HEIGHT-2][1]+=1;
                        if (gameMatrix[FIELD_HEIGHT-1][1]!=COVERED_MINE_CELL) gameMatrix[FIELD_HEIGHT-1][1]+=1;
                    }
                    else if (y==FIELD_HEIGHT-1&&(x>0)&&(x<FIELD_WIDTH-1)) {
                        if (gameMatrix[FIELD_HEIGHT-1][x-1]!=COVERED_MINE_CELL) gameMatrix[FIELD_HEIGHT-1][x-1]+=1;
                        if (gameMatrix[FIELD_HEIGHT-1][x+1]!=COVERED_MINE_CELL) gameMatrix[FIELD_HEIGHT-1][x+1]+=1;
                        for (int k = x-1; k < x+2; k++) {
                            if (gameMatrix[FIELD_HEIGHT-2][k]!=COVERED_MINE_CELL) gameMatrix[FIELD_HEIGHT-2][k]+=1;
                        }
                    }

                    else if (y==FIELD_HEIGHT-1&&x==FIELD_WIDTH-1) {
                        if (gameMatrix[FIELD_HEIGHT-2][FIELD_WIDTH-1]!=COVERED_MINE_CELL)
                            gameMatrix[FIELD_HEIGHT-2][FIELD_WIDTH-1]+=1;
                        if (gameMatrix[FIELD_HEIGHT-2][FIELD_WIDTH-2]!=COVERED_MINE_CELL)
                            gameMatrix[FIELD_HEIGHT-2][FIELD_WIDTH-2]+=1;
                        if (gameMatrix[FIELD_HEIGHT-1][FIELD_WIDTH-2]!=COVERED_MINE_CELL)
                            gameMatrix[FIELD_HEIGHT-1][FIELD_WIDTH-2]+=1;
                    }
                    else if (x==0&&(y>0)&&(y<FIELD_HEIGHT-1)) {
                        if (gameMatrix[y+1][0]!=COVERED_MINE_CELL) gameMatrix[y+1][0]+=1;
                        if (gameMatrix[y-1][0]!=COVERED_MINE_CELL) gameMatrix[y-1][0]+=1;
                        for (int k = y-1; k < y+2; k++) {
                            if (gameMatrix[k][x+1]!=COVERED_MINE_CELL) gameMatrix[k][x+1]=+1;
                        }
                    }
                    else if (x==FIELD_WIDTH-1&&(y>0)&&(y<FIELD_HEIGHT-1)) {
                        if (gameMatrix[y+1][FIELD_WIDTH-1]!=COVERED_MINE_CELL) gameMatrix[y+1][FIELD_WIDTH-1]+=1;
                        if (gameMatrix[y+1][FIELD_WIDTH-1]!=COVERED_MINE_CELL) gameMatrix[y-1][FIELD_WIDTH-1]+=1;
                        for (int k = y-1; k < y+2; k++) {
                            if (gameMatrix[k][x-1]!=COVERED_MINE_CELL) gameMatrix[k][x-1]+=1;
                        }
                    }
                    else {
                        for (int j = y-1; j < y+2; j++) {
                            for (int k = x-1; k < x+2; k++) {
                                if (gameMatrix[j][k]!=COVERED_MINE_CELL) gameMatrix[j][k]+=1;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                int cell = gameMatrix[y][x];

                if (cell > COVERED_MINE_CELL) {
                    cell = DRAW_MARK;
                } else if (cell > MINE_CELL) {
                    cell = DRAW_COVER;
                }

                if (inGame) {
                    g.drawImage(img[cell], x*BLOCK_SIZE, y*BLOCK_SIZE, this);
                }
            }
        }

    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
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

    final int countOfMines = 40;

    final int COVERED_CELL = 10;
    final int EMPTY_CELL = 0;
    final int MARKED_CELL = 10;
    final int MINE_CELL = 9;
    final int COVERED_MINE_CELL = 19;
    final int MARKED_MINE_CELL = 29;

    final int DRAW_MINE = 9;
    final int DRAW_COVER = 10;
    final int DRAW_MARK = 11;
    final int DRAW_WRONG_MARK = 12;
    final int MINE_WITH_RED_BACKGROUND = 8;

    int[][] gameMatrix = new int[FIELD_HEIGHT][FIELD_WIDTH];
    Image[] img = new Image[13];

    boolean inGame = true;

    public GameContent() {
        initGame();

    }

    public void initGame() {

        loadImages();
        fillTheGameMatrix();
        generateMines();
        placeNumbersAroundMines();
        addMouseListener(new MouseListener());
        checkMines();
    }

    private void checkMines() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                System.out.print(gameMatrix[y][x] + ",");
            }
            System.out.println();
        }
    }

    public void loadImages() {
        for (int i = 0; i < 13; i++) {
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

        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (gameMatrix[y][x]==COVERED_MINE_CELL) {
                    for (int i = y-1; i < y+2; i++) {
                        for (int j = x-1; j < x+2; j++) {
                            if ((i>=0&&i<FIELD_HEIGHT)&&(j>=0&&j<FIELD_WIDTH)) {
                                if (gameMatrix[i][j]!=COVERED_MINE_CELL)
                                    gameMatrix[i][j]+=1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void findEmptyCells(int y, int x) {
        for (int i = y-1; i < y+2; i++) {
            for (int j = x-1; j < x+2; j++) {
                if ((i>=0&&i<FIELD_HEIGHT)&&(j>=0&&j<FIELD_WIDTH)) {
                    if(gameMatrix[i][j]>MINE_CELL&&gameMatrix[i][j]<COVERED_MINE_CELL) {
                        gameMatrix[i][j]-=COVERED_CELL;
                        if (gameMatrix[i][j]==EMPTY_CELL) {
                            findEmptyCells(i,j);
                        }

                    }

                }
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {


        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                int cell = gameMatrix[y][x];


                if (inGame && cell == MINE_CELL) {

                    inGame = false;
                }

                if (inGame) {
                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }
                }

                else    {

                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    }
                    else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    }

                    else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    }

                    else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }

                }

                g.drawImage(img[cell], x * BLOCK_SIZE, y * BLOCK_SIZE, this);
            }

        }

    }

    public class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            boolean doRepaint = false;

            int x = e.getX()/BLOCK_SIZE;
            int y = e.getY()/BLOCK_SIZE;

            if (y<FIELD_WIDTH*BLOCK_SIZE&&x<FIELD_WIDTH*BLOCK_SIZE&&inGame) {
                if (e.getButton()==MouseEvent.BUTTON1) {
                    if (gameMatrix[y][x]>MINE_CELL&&gameMatrix[y][x]<MARKED_MINE_CELL) {
                        gameMatrix[y][x]=gameMatrix[y][x]-COVERED_CELL;
                        doRepaint = true;
                    }
                    if (gameMatrix[y][x]==MINE_CELL) {
                        gameMatrix[y][x]=MINE_WITH_RED_BACKGROUND;
                        inGame = false;
                    }
                    if (gameMatrix[y][x]==EMPTY_CELL) {
                        findEmptyCells(y,x);
                    }
                }
                else if (e.getButton()==MouseEvent.BUTTON3) {
                    doRepaint = true;
                    if (gameMatrix[y][x]<=COVERED_MINE_CELL&&gameMatrix[y][x]>MINE_CELL) {
                        gameMatrix[y][x]+=MARKED_CELL;
                    }
                    else if (gameMatrix[y][x]>COVERED_MINE_CELL) {
                        gameMatrix[y][x]-=MARKED_CELL;
                    }
                }

            }
            if (doRepaint) {
                repaint();
            }


        }
    }
}

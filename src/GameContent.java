import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;

public class GameContent {

    final int FIELD_HEIGHT = 15;
    final int FIELD_WIDTH = 15;
    final int BLOCK_SIZE = 20;
    final int WINDOW_BORDERX=15;
    final int WINDOW_BORDERY=39;

    int currentCountOfMines = 0;
    int countOfMines = 25;
    int checkMines=0;

    int[][] gameMatrix = new int[FIELD_HEIGHT][FIELD_WIDTH];
    boolean[][] verificationMatrix = new boolean[FIELD_HEIGHT][FIELD_WIDTH];

    JFrame frame = new JFrame();
    DrawPane drawPane = new DrawPane();

    public Image image1;



    public static void main(String[] args) {
        GameContent gameContent = new GameContent();
        gameContent.fillTheGameMatrix();
        gameContent.generateMines();
        gameContent.placeNumberOfMines();
        gameContent.clearExtraNumbers();
        gameContent.checkMinesInConcole();
        gameContent.frameInitialization();
    }

    void frameInitialization () {
        frame.setTitle("Game MineSweeper");
        frame.setLocation(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH*BLOCK_SIZE + WINDOW_BORDERX, FIELD_HEIGHT*BLOCK_SIZE + WINDOW_BORDERY);
        frame.add(drawPane);
        frame.setVisible(true);


    }

    public void loadImages() {
        ImageIcon icon1 = new ImageIcon("src/images/1.png");
        image1 = icon1.getImage();

    }

    void fillTheGameMatrix() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                gameMatrix[y][x]=0;
            }
        }
    }

    void generateMines() {
        int mineX;
        int mineY;
        Random random = new Random();
        while (currentCountOfMines < countOfMines) {
            mineY = random.nextInt(FIELD_HEIGHT);
            mineX = random.nextInt(FIELD_WIDTH);
            if (gameMatrix[mineY][mineX]!=9) {
                gameMatrix[mineY][mineX] = 9;
                currentCountOfMines++;
            }
            else {
                generateMines();
            }
        }

    }

    void clearExtraNumbers() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (gameMatrix[y][x]>=9) {
                    gameMatrix[y][x]=9;
                }
            }
        }
    }

    void placeNumberOfMines() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (gameMatrix[y][x]>=9) {
                    if (y==0&&x==0) {
                        gameMatrix[0][1]+=1;
                        gameMatrix[1][0]+=1;
                        gameMatrix[1][1]+=1;
                    }
                    else if (y==0&&(x>0)&&(x<FIELD_WIDTH-1)) {
                        gameMatrix[0][x-1]+=1;
                        gameMatrix[0][x+1]+=1;
                        for (int k = x-1; k < x+2; k++) {
                            gameMatrix[y+1][k]+=1;
                        }
                    }
                    else if (y==0&&x==FIELD_WIDTH - 1) {
                        gameMatrix[0][FIELD_WIDTH-2]+=1;
                        gameMatrix[1][FIELD_WIDTH-2]+=1;
                        gameMatrix[1][FIELD_WIDTH-1]+=1;
                    }

                    else if (y==FIELD_HEIGHT-1&&x==0) {
                        gameMatrix[FIELD_HEIGHT-2][0]+=1;
                        gameMatrix[FIELD_HEIGHT-2][1]+=1;
                        gameMatrix[FIELD_HEIGHT-1][1]+=1;
                    }
                    else if (y==FIELD_HEIGHT-1&&(x>0)&&(x<FIELD_WIDTH-1)) {
                        gameMatrix[FIELD_HEIGHT-1][x-1]+=1;
                        gameMatrix[FIELD_HEIGHT-1][x+1]+=1;
                        for (int k = x-1; k < x+2; k++) {
                            gameMatrix[FIELD_HEIGHT-2][k]+=1;
                        }
                    }

                    else if (y==FIELD_HEIGHT-1&&x==FIELD_WIDTH-1) {
                        gameMatrix[FIELD_HEIGHT-2][FIELD_WIDTH-1]+=1;
                        gameMatrix[FIELD_HEIGHT-2][FIELD_WIDTH-2]+=1;
                        gameMatrix[FIELD_HEIGHT-1][FIELD_WIDTH-2]+=1;
                    }
                    else if (x==0&&(y>0)&&(y<FIELD_HEIGHT-1)) {
                        gameMatrix[y+1][0]+=1;
                        gameMatrix[y-1][0]+=1;
                        for (int k = y-1; k < y+2; k++) {
                            gameMatrix[k][x+1]=+1;
                        }
                    }
                    else if (x==FIELD_WIDTH-1&&(y>0)&&(y<FIELD_HEIGHT-1)) {
                        gameMatrix[y+1][FIELD_WIDTH-1]+=1;
                        gameMatrix[y-1][FIELD_WIDTH-1]+=1;
                        for (int k = y-1; k < y+2; k++) {
                            gameMatrix[k][x-1]+=1;
                        }
                    }
                    else {
                        for (int j = y-1; j < y+2; j++) {
                            for (int k = x-1; k < x+2; k++) {
                                gameMatrix[j][k]+=1;
                            }
                        }
                    }
                }
            }
        }
    }

    void checkMinesInConcole() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                System.out.print(gameMatrix[y][x]);
            }
            System.out.println();
        }

    }

    public class DrawPane extends JPanel implements MouseListener {

        int mouseX;
        int mouseY;

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(0,0,FIELD_WIDTH*BLOCK_SIZE,0);
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    if (verificationMatrix[y][x]==false) {
                        g.setColor(Color.WHITE);
                        g.fill3DRect(x*BLOCK_SIZE,y*BLOCK_SIZE+1,BLOCK_SIZE, BLOCK_SIZE,true);

                    }
                    if (verificationMatrix[y][x]==true&&gameMatrix[y][x]==1) {
                        g.drawImage(image1,x*BLOCK_SIZE,y*BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE,this);
                    }
                }
            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    if (verificationMatrix[mouseX*BLOCK_SIZE][mouseY*BLOCK_SIZE]==false) {
                        verificationMatrix[mouseX*BLOCK_SIZE][mouseY*BLOCK_SIZE]=true;
                    }
                }
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}

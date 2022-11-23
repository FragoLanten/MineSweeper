import java.sql.SQLOutput;
import java.util.Random;

public class GameContent {

    final int FIELD_HEIGHT = 15;
    final int FIELD_WIDTH = 15;
    final int BLOCK_SIZE = 20;
    int currentCountOfMines = 0;
    int countOfMines = 25;
    int checkMines=0;

    int[][] gameMatrix = new int[FIELD_HEIGHT][FIELD_WIDTH];

    public static void main(String[] args) {
        GameContent gameContent = new GameContent();
        gameContent.generateMines();
        gameContent.checkMinesInConcole();

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
    void checkMinesInConcole() {
        for (int y = 0; y < FIELD_HEIGHT; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (gameMatrix[y][x]==9) {
                    System.out.print("9");
                    checkMines++;
                }
                else System.out.print("0");
            }
            System.out.println();
        }
        System.out.println("число мин: "+ checkMines);
    }

}

import javax.swing.*;

public class MainFrame extends JFrame {

    public void initUI() {
        setTitle("Game MineSweeper");
        setLocation(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new GameContent());
        setSize(GameContent.FIELD_WIDTH*GameContent.BLOCK_SIZE+GameContent.WINDOW_BORDERX,
                GameContent.FIELD_HEIGHT*GameContent.BLOCK_SIZE+GameContent.WINDOW_BORDERY);
        setVisible(true);

    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.initUI();
    }


}

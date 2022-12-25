import javax.swing.JFrame;

public class App {

    public static void main(String[] args) throws Exception {

        JFrame f = new JFrame("Brick Breaker");
        f.setSize(700, 600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);

        GameingCode gameingCode = new GameingCode();
        f.add(gameingCode);

    }
}

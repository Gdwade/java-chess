import javax.swing.JFrame;

public class Frame {

    public static void main(String[] args) {
        JFrame win = new JFrame();
        
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        win.setTitle("Java Chess 2.0");

        Chess board = new Chess();
        win.add(board);
        win.pack();

        win.setLocationRelativeTo(null);
        win.setVisible(true);
        //gamePanel.startGameThread();
    }

}

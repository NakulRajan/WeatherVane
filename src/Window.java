import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Window extends JFrame{
        
    private Window()
    {
        this.setTitle("Game title");
        
        if(false) // Full screen mode
        {
            this.setUndecorated(true);
            this.setExtendedState(this.MAXIMIZED_BOTH);
        }
        else // Window mode
        {
            this.setSize(400, 600);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
        }
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new Framework());
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}

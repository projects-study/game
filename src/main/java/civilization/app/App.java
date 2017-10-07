package civilization.app;

import javax.swing.JFrame;

public class App 
{
    public static void main( String[] args )
    {
    	JFrame window = new JFrame("Civilization");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setContentPane(new GameMain());
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}

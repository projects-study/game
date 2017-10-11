package someGame.main;

import javax.swing.JFrame;

public class App 
{
    public static void main( String[] args )
    {
    	JFrame window = new JFrame("Save Your Life");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setContentPane(new GameMain());
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}

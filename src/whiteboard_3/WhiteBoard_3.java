package whiteboard_3;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * Ben Forgy
 * May 4, 2014
 */

public class WhiteBoard_3 implements Serializable {

    private MainFrame mainFrame;
    
    public static void main(String[] args) {
        WhiteBoard_3 wb3 = new WhiteBoard_3();
        wb3.start(new Canvas());
    }
    
    protected void start(Canvas canvas){
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        mainFrame = new MainFrame(canvas);
    }

}

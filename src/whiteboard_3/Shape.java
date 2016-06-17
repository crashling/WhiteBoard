package whiteboard_3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 * Ben Forgy
 * May 4, 2014
 */

public interface Shape extends Serializable{   
    
    public void start(int x, int y);
    public void setCorner(int x, int y);           
    public void paint(Graphics g); 
    public boolean contains(Point toTest);
    public boolean contains(int pointX, int pointY);
    public boolean isResizable();
    Shape getAbove();
    Shape getBelow();
    void setAbove(Shape newAbove);
    void setBelow(Shape newBelow);    
    byte getLineThickness();
    void setLineThickness(byte newThickness);
    public void setColor(Color color);
    public void setFill(boolean filled);
}

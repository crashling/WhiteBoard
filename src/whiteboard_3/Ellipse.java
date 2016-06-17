package whiteboard_3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/*
 * Ben Forgy
 * May 4, 2014
 */

public class Ellipse extends Rectangle implements Shape{
    public Ellipse(byte thickness, boolean isFilled, Color color){
        super(thickness, isFilled, color);
    }
    public Ellipse(int x, int y, int width, int height, byte thickness,
                                                 boolean isFilled, Color color)
    {
        super(x, y, width, height, thickness, isFilled, color);
    }
  
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(lineThickness));
        if(fill){
            g2.fillOval(x, y, width, height);
        }
        else{
            g2.drawOval(x, y, width, height);
        }
    }

    @Override
    public boolean contains(int pointX, int pointY) {
        // simple fail fast part.
        if(pointX < x - lineThickness || pointY < y - lineThickness){
            return false;
        }
        if(pointX > x+width + lineThickness || pointY > y+height + lineThickness){
            return false;
        }
        
        // super crazy math part.
        float radiusX = width/2;
        float radiusY = height/2;
        int centerX = (int)(this.x + radiusX + .5);
        int centerY = (int)(this.y + radiusY + .5);
        double factor = ((pointX - centerX)*(pointX - centerX) / (radiusX*radiusX)) +
            ((pointY - centerY)*(pointY - centerY) / (radiusY*radiusY));
        if(fill){
        return factor <= 1.0 + lineThickness/20.0;
        }
        else{
            return factor <= 1.0 + lineThickness/20.0 && factor > .95;
        }
    }    
    @Override
    public boolean contains(Point toTest){
        return contains(toTest.x, toTest.y);
    }
}

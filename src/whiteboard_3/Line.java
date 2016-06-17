package whiteboard_3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/*
 * Ben Forgy
 * May 4, 2014
 */

public class Line implements Shape{
    private Color color;
    private int x1, x2, y1, y2;
    private byte thickness;
    private Shape below, above;
    
    public Line(byte lineThickness, Color color){
        thickness = lineThickness;
        this.color = color;
    }
    
    public Line(int x1, int y1, int x2, int y2, byte lineThickness, Color color)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.thickness = lineThickness;
        this.color = color;
    }
    public Line(Line line)
    {
        this.x1 = line.x1;
        this.x2 = line.x2;
        this.y1 = line.y1;
        this.y2 = line.y2;
        this.thickness = line.thickness;
        this.color = line.color;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawLine(x1, y1, x2, y2);       
    }

    @Override
    public boolean contains(int pointX, int pointY) {
        return 
         Line2D.ptSegDist(x1, y1, x2, y2, pointX, pointY) < Math.ceil(thickness);
    }
    @Override
    public boolean contains(Point toTest) {
        return 
         Line2D.ptSegDist(x1, y1, x2, y2, toTest.x, toTest.y) < thickness;
    }

    @Override
    public void setCorner(int newX, int newY) {
        if(Point.distance(x1, y1, newX, newY) < Point.distance(x1, y1, x2, y2)/2
                && x2 + y2 > 0)
        {
            x1 = newX;
            y1 = newY;            
        }
        else{
            x2 = newX;
            y2 = newY;             
        }
    }

    @Override
    public boolean isResizable() {
        return true;
    }
@Override
    public Shape getBelow(){
        return below;
    }
    @Override
    public Shape getAbove(){
        return above;
    }

    @Override
    public void setAbove(Shape newAbove) {
        above = newAbove;
    }

    @Override
    public void setBelow(Shape newPrevious) {
        below = newPrevious;
    }

    @Override
    public void start(int x, int y) {
        x1 = x;
        y1 = y;
    }

    @Override
    public byte getLineThickness() {
        return thickness;
    }

    @Override
    public void setLineThickness(byte newThickness) {
        thickness = newThickness;
    }
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setFill(boolean filled) {
    }
}

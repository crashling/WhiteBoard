package whiteboard_3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * Ben Forgy
 * May 4, 2014
 */

public class Freestyle extends LinkedList<Point> implements Shape{
    
    private byte thickness;
    private Color color;
    private Shape below, above;
    
    public Freestyle(byte lineThickness, Color color){
        thickness = lineThickness;
        this.color = color;
    }
    
    public Freestyle(Collection<Point> points, byte lineThickness, 
                                            Color color)
    {
        this.addAll(points);
        thickness = lineThickness;
        this.color = color;  
    }
    public Freestyle(Freestyle free){
        this.addAll(free);
        this.thickness = free.thickness;
        this.color = free.color;
    }
    
    
    
    @Override
    public void paint(Graphics g){
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        
        Iterator<Point> it = this.iterator();
        
        Point p1, p2;
        if(it.hasNext())
            p1 = it.next();
        else
            p1 = new Point();
        
        while(it.hasNext()){
            p2 = it.next();
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            p1 = p2;
        }
    }

    @Override
    public boolean contains(int pointX, int pointY) {
        Iterator<Point> it = this.iterator();
        
        Point p1, p2;
        if(it.hasNext())
            p1 = it.next();
        else
            p1 = new Point();
        
        while(it.hasNext()){
            p2 = it.next();
            if(Line2D.ptSegDist(p1.x, p1.y, p2.x, p2.y, 
                    pointX, pointY) < thickness)
            {
                return true;
            }
            p1 = p2;
        }
        return false;
    }
    @Override
    public boolean contains(Point toTest) {        
        return contains(toTest.x, toTest.y);
    }

    @Override
    public void setCorner(int newX, int newY) {
        this.addLast(new Point(newX, newY));
    }
    
    @Override
    public boolean isResizable() {
        return false;
    }    

    @Override
    public Shape getAbove() {
        return above;
    }

    @Override
    public Shape getBelow() {
        return below;
    }

    @Override
    public void setAbove(Shape newAbove) {
        above = newAbove;

    }

    @Override
    public void setBelow(Shape newBelow) {
        below = newBelow;
    }

    @Override
    public void start(int x, int y) {
        this.add(new Point(x, y));
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

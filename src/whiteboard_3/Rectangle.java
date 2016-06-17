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

public class Rectangle implements Shape{
    protected byte lineThickness;
    protected boolean fill;
    protected Color color;
    protected int x, y, width, height;
    protected Shape below, above;
    
    protected Rectangle(){
    }
    
    public Rectangle(byte thickness, boolean isFilled, Color color){
        this.lineThickness = thickness;
        fill = isFilled;
        this.color = color;
    }
    
    public Rectangle(int x, int y, int width, int height, byte thickness,
                                  boolean isFilled, Color color)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        lineThickness = thickness;
        fill = isFilled;
        this.color = color;
    }
    public Rectangle(Rectangle rect){
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
        lineThickness = rect.lineThickness;
        fill = rect.fill;
        this.color = rect.color;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(lineThickness));
        g2.setColor(color);
        if(fill){
            g2.fillRect(x, y, width, height);
        }
        else{
            g2.drawRect(x, y, width, height);
        }
    }

    @Override
    public boolean contains(int pointX, int pointY) {
        if(pointX < x - lineThickness || pointY < y - lineThickness){
            return false;
        }
        else if(pointX > x+width + lineThickness || pointY > y+height + lineThickness){
            return false;
        }
        else if(!fill){
            if(Line2D.ptSegDist(x, y, x+width, y, pointX, pointY) < lineThickness + 1){
                return true;
            }
            else if(Line2D.ptSegDist(x, y, x, y+height, pointX, pointY) < lineThickness + 1){
                return true;
            }
            else if(Line2D.ptSegDist(x, y+height, x+width, y+height, pointX, pointY) < lineThickness + 1){
                return true;
            }
            else if(Line2D.ptSegDist(x+width, y, x+width, y+height, pointX, pointY) < lineThickness + 1){
                return true;
            }
            else{
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean contains(Point toTest) {
        return contains(toTest.x, toTest.y);
    }

    private void setX_newXGreater(int newX){
        width = newX - x;
    }
    private void setX_newXLesser(int newX) {
        width = width + (x - newX);
        x = newX;
    }
    private void setY_newYGreater(int newY) {
        height = newY - y;
    }
    private void setY_newYLesser(int newY) {
        height = height + (y - newY);
        y = newY;
    }
    
    @Override
    public void setCorner(int newX, int newY) {
        
        if(newX >= x + width/2){
            setX_newXGreater(newX);
        }
        else{
            setX_newXLesser(newX);
        }
        if(newY >= y + height/2){
            setY_newYGreater(newY);
        }
        else{
            setY_newYLesser(newY);
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
    public void setBelow(Shape newBelow) {
        below = newBelow;
    }

    @Override
    public void start(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public byte getLineThickness() {
        return lineThickness;
    }

    @Override
    public void setLineThickness(byte newThickness) {
        lineThickness = newThickness;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setFill(boolean filled) {
        this.fill = filled;
    }
}

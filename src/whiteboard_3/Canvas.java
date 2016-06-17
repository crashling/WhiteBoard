package whiteboard_3;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;

/*
 * Ben Forgy
 * May 15, 2014
 */
class Canvas implements Serializable, Iterable<Shape> {

    private Shape top, bottom;
    private int size;

    public Canvas() {
    }
    public int size(){
        return size;
    }
    
    public void drawAll(Graphics g){
        Shape current = bottom;
        while(current != null){
            current.paint(g);
            current = current.getAbove();
        }   
    }
    
    public Shape get(int index){
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        Shape current = bottom;
        for(int i = 0; i < index; i++){
            current = current.getAbove();
        }
        return current;
    }

    public Shape findShapeAtPoint(Point toFind) {
        return findShapeAtPoint(toFind.x, toFind.y);
    }
    public Shape findShapeAtPoint(int x, int y) {
        for(Shape s : this){
            if(s.contains(x, y)){
                return s;
            }
        }
        return null;
    }
    
    public void add(Shape toAdd){
        if(bottom == null){
            bottom = toAdd;
        }
        else {
            top.setAbove(toAdd);
            toAdd.setBelow(top);
        }
        size++;
        top = toAdd;
    }
  
    /**
     * This Iterator is top down.
     * @return iterator.
     */
    @Override
    public Iterator<Shape> iterator() {
        return  new Iterator<Shape>() {
            Shape current = top;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Shape next() {
                Shape toReturn = current;
                if (hasNext()) {
                    current = current.getBelow();
                }               
                return toReturn;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported."); 
            }
        };
    }
    
    /**
     * This Iterator is bottom up.
     * @param start
     * @return Iterator
     */
    public Iterator<Shape> iterator(final Shape start){
        return new Iterator<Shape>() {
            Shape current = start;
                
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Shape next() {
                Shape toReturn = current;
                if (hasNext()) {
                    current = current.getAbove();
                }               
                return toReturn;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported."); 
            }
        };
    }

    boolean isEmpty() {
        return bottom == null;
    }

    void delete(Shape currentShape) {
        if(currentShape.getAbove() != null){
            currentShape.getAbove().setBelow(currentShape.getBelow());
        }
        if(currentShape.getBelow() != null){
            currentShape.getBelow().setAbove(currentShape.getAbove());
        }
        if(currentShape == top){
            top = currentShape.getBelow();
        }
        if(currentShape == bottom){
            bottom = currentShape.getAbove();
        }
        size--;
    }

}

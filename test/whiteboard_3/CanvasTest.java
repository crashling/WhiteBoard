/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package whiteboard_3;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author BenForgy
 */
public class CanvasTest {

    /**
     * Test of get method, of class Canvas.
     */
    Canvas canvas_3;
    Canvas canvas_1;
    Canvas canvas_100;
    
    @Before
    public void setUp(){
        canvas_1 = new Canvas();
        canvas_3 = new Canvas();
        canvas_100 = new Canvas();
        
        canvas_1.add(new Line((byte)1, Color.yellow));
        for(int i = 0; i < 3; i++){
            canvas_3.add(new Rectangle((byte)1, true, Color.yellow));
        }
        for(int i = 0; i < 100; i++){
            canvas_100.add(new Rectangle(20, 20, 200, 200, (byte)4, true, Color.yellow));
        }
    }
    
    @Test
    public void get() {
        System.out.println("get");
        int index = 0;
        Canvas instance = new Canvas();
        Shape expResult = new Rectangle((byte)4, false, Color.yellow);
        instance.add(expResult);
        Shape result = instance.get(index);
        assertEquals(expResult, result);
    }
    @Test
    public void size() {
        
        assertEquals(100, canvas_100.size());
    }

    /**
     * Test of findShapeAtPoint method, of class Canvas.
     */
    @Test
    public void testFindShapeAtPoint_Point() {
        System.out.println("findShapeAtPoint");
        Point toFind = new Point(10, 10);
        Shape expResult = new Rectangle(0, 0, 300, 300, (byte)3, true, Color.green);
        canvas_100.add(expResult);
        Shape result = canvas_100.findShapeAtPoint(toFind);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class Canvas.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Shape toAdd = new Line(20, 1, 100, 2, (byte)2, Color.yellow);
        Canvas instance = new Canvas();
        instance.add(toAdd);
        assertEquals(toAdd, instance.get(instance.size()-1));
    }

    /**
     * Test of iterator method, of class Canvas.
     */
    @Test
    public void testIterator_bottomUp() {
        Canvas can = new Canvas();
        Shape exp1 = new Rectangle(10, 10, 100, 100, (byte)5, false, Color.yellow);
        Shape exp2 = new Line((byte)10, Color.yellow);
        can.add(exp1);
        can.add(exp2);
        Iterator it = canvas_100.iterator(exp1);
        
        assertEquals(exp1, it.next());
        assertEquals(exp2, it.next());  
    }

    /**
     * Test of iterator method, of class Canvas.
     */
    @Test
    public void testIterator_topDown() {
        Shape exp1 = new Rectangle(10, 10, 100, 100, (byte)5, false, Color.yellow);
        Shape exp2 = new Line((byte)10, Color.yellow);
        canvas_100.add(exp1);
        canvas_100.add(exp2);
        Iterator it = canvas_100.iterator();
        
        assertEquals(exp2, it.next());
        assertEquals(exp1, it.next());       
    }

    /**
     * Test of isEmpty method, of class Canvas.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        Canvas instance = new Canvas();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class Canvas.
     */
    @Test
    public void testDelete() {
        Shape shape = new Line((byte)5, Color.yellow);
        Canvas can = new Canvas();
        can.add(shape);
        
        can.delete(shape);
        assertTrue(can.isEmpty());
    }
    
}

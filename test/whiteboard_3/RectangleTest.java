package whiteboard_3;

import java.awt.Color;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author BenForgy
 */
public class RectangleTest {
    
    Rectangle full, outline;
    
    @Before
    public void setUp(){
        full = new Rectangle(0, 0, 100, 100, (byte)2, true, Color.yellow);
        outline = new Rectangle(0, 0, 100, 100, (byte)2, false, Color.yellow);
    }

    /**
     * Test of contains method, of class Rectangle.
     */
    @Test
    public void testContains_int_int_true() {
        System.out.println("contains");
        int pointX = 0;
        int pointY = 0;
        boolean expResult = true;
        boolean result = full.contains(pointX, pointY);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of contains method, of class Rectangle.
     */
    @Test
    public void testContains_int_int_false() {
        System.out.println("contains");
        int pointX = 200;
        int pointY = 200;
        boolean expResult = false;
        boolean result = full.contains(pointX, pointY);
        assertEquals(expResult, result);
    }
    /**
     * Test of contains method, of class Rectangle.
     */
    @Test
    public void testContains_int_int_outline_true() {
        System.out.println("contains");
        int pointX = 0;
        int pointY = 50;
        boolean expResult = true;
        boolean result = outline.contains(pointX, pointY);
        assertEquals(expResult, result);
    }
    /**
     * Test of contains method, of class Rectangle.
     */
    @Test
    public void testContains_int_int_outline_false() {
        System.out.println("contains");
        int pointX = 50;
        int pointY = 50;
        boolean expResult = false;
        boolean result = outline.contains(pointX, pointY);
        assertEquals(expResult, result);
    }

    /**
     * Test of setCorner method, of class Rectangle.
     */
    @Test
    public void testSetCorner() {
        System.out.println("setCorner");
        int newX = 200;
        int newY = 200;
        full.setCorner(newX, newY);
        assertTrue(full.contains(newX, newY));
    }
    
}

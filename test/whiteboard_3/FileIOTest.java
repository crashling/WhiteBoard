package whiteboard_3;

import org.junit.Test;

/**
 *
 * @author BenForgy
 */
public class FileIOTest {
    
    public FileIOTest() {
    }
    

    /**
     * Test of openFile method, of class FileIO.
     */
    @Test
    public void init() throws Exception {
        FileIO fio = new FileIO(new MainFrame(new Canvas()));
    }
    
}

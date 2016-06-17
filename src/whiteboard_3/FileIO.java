package whiteboard_3;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Ben Forgy
 * May 14, 2014
 */
public class FileIO implements Serializable {

    private final JFileChooser fileChooser;
    private final Component parent;

    public FileIO(Component parent) {

        fileChooser = new JFileChooser();
        this.parent = parent;
    }

    public boolean saveFile(Object toSave) throws IOException {
        int choice = fileChooser.showSaveDialog(parent);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
                ObjectOutputStream writer
                        = new ObjectOutputStream(new FileOutputStream(file));
                writer.writeObject(toSave);
                writer.close();
                return true;
        } else {
            System.out.println("Save canceled by user.");
            return false;
        }
    }

    public boolean openFile() throws IOException, ClassNotFoundException {
        int choice = fileChooser.showOpenDialog(parent);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ObjectInputStream reader
                    = new ObjectInputStream(new FileInputStream(file));
            Object input = reader.readObject();
            reader.close();
            if (input instanceof Canvas) {
                ((MainFrame) parent).addPaintFrame((Canvas) input);
            } else {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Can not open this file.",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }
}

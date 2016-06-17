package whiteboard_3;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/*
 * Ben Forgy
 * May 9, 2014
 */
public class MainFrame extends JFrame implements Serializable {

    private final ArrayList<JFrame> openFrames;
    private final ToolBar toolBar;
    private final JMenuBar menuBar;
    private final FileIO fileIO;
    protected PaintFrame focus;

    protected MainFrame(Canvas canvas) {

        fileIO = new FileIO(this);
        toolBar = new ToolBar();
        openFrames = new ArrayList<>();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createNewButton());
        fileMenu.add(createSaveButton());
        fileMenu.add(createOpenButton());
        fileMenu.add(createExitButton());
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(createHowToButton());
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

        this.add(toolBar);
        this.setPreferredSize(new Dimension(120, 500));

        this.setVisible(true);
        this.pack();
        openFrames.add(new PaintFrame(this));

    }

    protected ToolBar getToolBar() {
        return toolBar;
    }

    protected void addPaintFrame(Canvas canvas) {
        openFrames.add(new PaintFrame(this, canvas));
    }

    private JMenuItem createNewButton() {
        JMenuItem newButton = new JMenuItem("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                openFrames.add(new PaintFrame(MainFrame.this));
            }
        });
        return newButton;
    }

    private JMenuItem createSaveButton() {
        JMenuItem saveButton = new JMenuItem("Save");
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (focus != null) {
                        fileIO.saveFile(focus.getCanvas());
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "File save failed.",
                            "File I/O Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        return saveButton;
    }

    private JMenuItem createOpenButton() {
        JMenuItem openButton = new JMenuItem("Open");
        openButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    fileIO.openFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "File open failed.",
                            "File I/O Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Can not open this file type.",
                            "File I/O Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        return openButton;
    }

    private JMenuItem createExitButton() {
        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        return exitButton;
    }

    void removeFrame(PaintFrame aThis) {
        if (openFrames != null) {
            openFrames.remove(aThis);
        }
    }

    private JMenuItem createHowToButton() {
        JMenuItem howTo = new JMenuItem("How to use");
        final JFrame helpFrame = new JFrame("Help");
        helpFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setSize(400, 400);
        StringBuilder sb = new StringBuilder();
        sb.append("<html><h2>How to draw:</h2>");
        sb.append("<ul><li>Pick a shape with the buttons.</li><li>Decide how"
                + " thick the lines should be with the spinner.</li>");
        sb.append("<li>Pick a color by opening the color chooser with the "
                + "'Color Chooser' button.</li></ul>");
        
        sb.append("<h2>How to edit:</h2>");
        sb.append("<p>Note: All changes will be defined by what is currently "
                + "selected in the toolbar.</p>");
        sb.append("<ul><li>Click the Edit button to enter edit mode.</li>"
                + "<li>Left click on a shape to resize it.</li>");
        sb.append("<li>Right click on a shape to open a popup menu. <ul>");
        sb.append("<li>'Change Color' to change the color of the shape to the "
                + "currently selected color.</li>");
        sb.append("<li>'Change Line Thickness' to change the shape's line thickness "
                + "to the currently selected thickness.</li>");
        sb.append("<li>'Change solid / outline' to change the shapes fill option "
                + "to the currently selected fill option.</li>");
        sb.append("<li>'Delete' to delete the currently chosen shape."
                + "</li></ul></ul></html>");

        JTextPane text = new JTextPane();
        text.setContentType("text/html");
        text.setSize(600, 1001);
        text.setText(sb.toString());
        panel.add(text);
        helpFrame.add(panel);
        helpFrame.pack();
        howTo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                helpFrame.setVisible(true);
            }
        });
        return howTo;
    }
}

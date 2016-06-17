package whiteboard_3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/*
 * Ben Forgy
 * May 16, 2014
 */
public class PaintFrame extends JFrame {

    private final Canvas canvas;
    private final MainFrame context;
    private final PaintPanel paintPane;
    final int WIDTH = 1100;
    final int HEIGHT = 600;

    private Shape currentShape;
    private State currentState;

    public PaintFrame(MainFrame contextIn, Canvas canvasIn) {
        this.context = contextIn;
        canvas = canvasIn;
        paintPane = new PaintPanel();
        setUp();
    }

    public PaintFrame(MainFrame contextIn) {
        this.context = contextIn;
        this.canvas = new Canvas();
        paintPane = new PaintPanel();
        setUp();
    }

    private void setUp() {
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(closeWindow());
        this.setVisible(true);
        this.setLocation(250, 100);
        this.setGlassPane(new GlassPane());
        this.getGlassPane().setVisible(true);

        paintPane.setSize(WIDTH, HEIGHT);
        paintPane.setVisible(true);
        paintPane.setBackground(Color.white);
        this.add(paintPane);

        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                context.focus = PaintFrame.this;
                PaintFrame.this.getGlassPane().setVisible(true);
            }
        });
    }

    protected Object getCanvas() {
        return canvas;
    }

    private int closeWindow() {
        context.removeFrame(this);
        return HIDE_ON_CLOSE;
    }

    private class PaintPanel extends JPanel {

        public PaintPanel() {
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            canvas.drawAll(g);
        }
    }

    private class GlassPane extends JComponent {

        GlassPane() {
            this.setOpaque(false);

            this.addMouseListener(new PaintMouseListener(createPopup()));
            this.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent me) {
                    if (currentShape != null) {
                        if (currentState != State.Edit || currentShape.isResizable()) {
                            currentShape.setCorner(me.getX(), me.getY());
                        }
                    }
                    if (currentState != State.Edit) {
                        GlassPane.this.repaint();
                    } else {
                        PaintFrame.this.repaint();
                    }
                }

                @Override
                public void mouseMoved(MouseEvent me) {
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentShape != null && currentState != State.Edit) {
                currentShape.paint(g);
            }
        }

        private JPopupMenu createPopup() {
            JPopupMenu popup = new JPopupMenu();
            popup.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent me) {
                    GlassPane.this.setVisible(false);
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    GlassPane.this.setVisible(true);
                }
            });

            JMenuItem item = new JMenuItem("Change Color");
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (currentShape != null) {
                        Color c = context.getToolBar().getColor();
                        if(c == null){
                            c = Color.black;
                        }
                        currentShape.setColor(c);
                        paintPane.repaint();
                    }
                    PaintFrame.this.getGlassPane().setVisible(true);
                }
            });
            popup.add(item);

            item = new JMenuItem("Change Line Thickness");
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (currentShape != null) {
                        currentShape.setLineThickness(context.getToolBar().getThickness());
                        paintPane.repaint();
                    }
                    PaintFrame.this.getGlassPane().setVisible(true);
                }
            });
            popup.add(item);
            item = new JMenuItem("Change solid / outline");
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (currentShape != null) {
                        currentShape.setFill(context.getToolBar().isFilled());
                        paintPane.repaint();
                    }
                    PaintFrame.this.getGlassPane().setVisible(true);
                }
            });
            popup.add(item);

            item = new JMenuItem("Delete Shape");
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (currentShape != null) {
                        canvas.delete(currentShape);
                        paintPane.repaint();
                    }
                    PaintFrame.this.getGlassPane().setVisible(true);
                }
            });
            popup.add(item);
            return popup;
        }

    }

    private class PaintMouseListener extends MouseAdapter {

        int x, y, width, height;

        JPopupMenu popup;

        PaintMouseListener(JPopupMenu popup) {
            this.popup = popup;
        }

        @Override
        public void mousePressed(MouseEvent me) {
            if (me.isPopupTrigger()) {
                maybeShowPopup(me);
            } else {
                x = me.getX();
                y = me.getY();
                currentState = context.getToolBar().getState();
                if (currentState == State.Edit) {
                    currentShape = canvas.findShapeAtPoint(x, y);

                } else {
                    Color c = context.getToolBar().getColor();
                    if (c == null){
                        c = Color.BLACK;
                    }
                    if (currentState == State.Line) {
                        currentShape = new Line(context.getToolBar().getThickness(), c);
                    } else if (currentState == State.Rectangle) {
                        currentShape = new Rectangle(context.getToolBar().getThickness(), context.getToolBar().isFilled(), c);
                    } else if (currentState == State.Ellipse) {
                        currentShape = new Ellipse(context.getToolBar().getThickness(), context.getToolBar().isFilled(), c);
                    } else {
                        currentShape = new Freestyle(context.getToolBar().getThickness(), c);
                    }

                    currentShape.start(x, y);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if (me.isPopupTrigger()) {
                maybeShowPopup(me);
            } else {
                if (currentState != State.Edit) {
                    canvas.add(currentShape);
                    PaintFrame.this.repaint();
                }
            }

        }

        private void maybeShowPopup(MouseEvent e) {
            if (currentState == State.Edit) {
                Shape shape = canvas.findShapeAtPoint(e.getPoint());
                if (shape != null) {
                    PaintFrame.this.getGlassPane().setVisible(false);
                    popup.show(PaintFrame.this, e.getX(), e.getY());
                    popup.setLightWeightPopupEnabled(false);
                }
            }
        }

    }
}

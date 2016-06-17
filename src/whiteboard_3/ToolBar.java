package whiteboard_3;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Ben Forgy
 * May 13, 2014
 */


public class ToolBar extends javax.swing.JToolBar implements Serializable{
    
    private State state;
    private Color color;
    private boolean filled;
    private final int stateCount = 5;
    private final StateButton[] stateButtons;
    private final JSpinner thicknessChoser;
    private final JFrame colorChoserFrame;
    private final JButton colorButton;
    private final JToggleButton fillButton;
    StateButton oldButton;
    
    public ToolBar(){
        this.setLayout(new GridLayout(8, 0));
        this.setFloatable(false);
        thicknessChoser = createThicknessSpinner();
        stateButtons = createStateButtons();
        colorChoserFrame = createColorChooserFrame();
        colorButton = createColorButton();
        fillButton = setUpFillButton();
        setUpToolBar();
    }

    private void setUpToolBar() {
        for(JRadioButton sb : stateButtons){
            this.add(sb);
        }

        
        this.add(thicknessChoser);
        this.add(colorButton);
        this.add(fillButton);
    }

    private JToggleButton setUpFillButton() {
        JToggleButton fillButton = new JToggleButton("Solid / Outline");
        fillButton.setFont(fillButton.getFont().deriveFont(17.0f));
        fillButton.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 200)));
        fillButton.setBackground(new Color(230, 230,230));
        fillButton.setToolTipText("Toggle fill shape or only draw outline.");
        fillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                filled = !filled;
            }
        });
        return fillButton;
    }
    
    private class StateListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            StateButton thisButton = (StateButton)ae.getSource();
            thisButton.setBackground(new Color(210, 230, 230));
            state = thisButton.getState();
            if(oldButton != null){
                oldButton.setBackground(new Color(230, 250, 250));
            }
            oldButton = thisButton;
        }    
    }
    
    private StateButton[] createStateButtons(){
        
        ButtonGroup group = new ButtonGroup();
        StateButton[] buttons = new StateButton[stateCount];

        buttons[0] = new StateButton("Edit Mode", State.Edit);
        buttons[0].setToolTipText("Edit a previously drawn shape.");
        buttons[1]  = new StateButton("Freestyle", State.Freestyle);
        buttons[1].setToolTipText("Draw freely.");
        buttons[2] = new StateButton("Line", State.Line);
        buttons[2].setToolTipText("Draw a line.");
        buttons[3]  = new StateButton("Rectangle", State.Rectangle);
        buttons[3].setToolTipText("Draw a rectangle.");
        buttons[4]  = new StateButton("Ellipse", State.Ellipse);
        buttons[4].setToolTipText("Draw an ellipse.");
        
        StateListener sListener = new StateListener();
        for(StateButton srt : buttons){
            srt.setHorizontalAlignment(CENTER);
            srt.setFont(srt.getFont().deriveFont(17.0f));
            srt.addActionListener(sListener);
            srt.setBackground(new Color(230, 250, 250));
            group.add(srt);
        }
        
        buttons[1].setSelected(true);
        buttons[1].setBackground(new Color(210, 230, 230));
        oldButton = buttons[1];
        
        return buttons; 
    }
    
    private JSpinner createThicknessSpinner(){
        
        SpinnerModel model = new SpinnerNumberModel(2,1,12,1);
        final JSpinner spinner = new JSpinner(model);
        spinner.setFont(spinner.getFont().deriveFont(17.0f));
        ((DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(CENTER);
        ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        spinner.setToolTipText("Set the line thickness.");
        return spinner;
    }
    
    private JFrame createColorChooserFrame(){
        JFrame colorFrame = new JFrame();
        colorFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        final JColorChooser colorChooser = new JColorChooser(Color.black);
        colorChooser.setPreviewPanel(new JPanel());
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                color = colorChooser.getColor();      
                if((color.getRed() + color.getBlue() + color.getGreen())/3 < 120)
                    colorButton.setForeground(Color.white);
                else
                    colorButton.setForeground(Color.black);
                colorButton.setBackground(color);
            }
        });
        
        colorFrame.add(colorChooser);
        colorFrame.pack();
        colorFrame.setVisible(false);
        return colorFrame;
    }
    
    private JButton createColorButton(){
        final JButton button = new JButton("Color Chooser");
        button.setToolTipText("Open box to pick a color.");
        button.setFont(button.getFont().deriveFont(17.0f));
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 200)));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                colorChoserFrame.setVisible(!colorChoserFrame.isVisible());
                colorChoserFrame.setLocation(button.getX()+ 100,button.getY()+ 50 );
            }
        });
        return button;
    }
    
    
    public State getState(){
        return state;
    }
    public byte getThickness(){
        return ((Integer)thicknessChoser.getValue()).byteValue();
    }
    public Color getColor(){
        return color;
    }
    public boolean isFilled(){
        return filled;
    }
    
}
class StateButton extends JRadioButton{
    private final State state;
    public StateButton(String name, State state){
        super(name);
        this.state = state;
    }
    public State getState(){
        return state;
    }
}

enum State {
    Edit, Rectangle, Ellipse, Line, Freestyle;
}

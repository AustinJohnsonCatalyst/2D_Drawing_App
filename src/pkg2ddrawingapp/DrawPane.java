/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2ddrawingapp;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;



public class DrawPane extends JFrame {
    private final JPanel optionMenu;
    private final JPanel optionMenu2;
    private final JPanel paintFrame;
    private final JPanel optionComplete;
    private final JButton undo;
    private final JButton clear;
    private final JButton color1;
    private final JButton color2;
    protected final JComboBox<String> shapeChoice;
    private final JCheckBox filled;
    private final JCheckBox gradient;
    private final JCheckBox dashed;
    private final JTextField widthField;
    private final JTextField lengthField;
    private final JLabel shape;
    private final JLabel widthLabel;
    private final JLabel lengthLabel;
    protected final JLabel status;
    private static final String[] shapes =
        {"Line", "Oval", "Rectangle"};
   
    private Color paintColor1;
    private Color paintColor2;
    private  ArrayList<Canvas.Shapes> paintedShapes = new ArrayList<Canvas.Shapes>(); //implement Shapes
    private Canvas.Shapes currentShape;
    

    
    
    public DrawPane()
    {
        
    super("DrawPane");
    
    optionMenu = new JPanel(); 
    optionMenu2 = new JPanel();
    paintFrame = new Canvas();
    paintFrame.setBackground(Color.WHITE);
    optionComplete = new JPanel();
    optionMenu.setLayout(new FlowLayout());
    optionMenu2.setLayout(new FlowLayout());
    optionComplete.setLayout(new BorderLayout());
    setLayout(new BorderLayout());
    
    undo = new JButton ("Undo");
    undo.addActionListener(
            new ActionListener()
            {
              @Override
              public void actionPerformed(ActionEvent event)
              {
                  //paintedShapes.remove(paintedShapes.size() - 1);
                  repaint();
              }
            }
    );
            
    clear = new JButton ("Clear");
    clear.addActionListener(
            new ActionListener()
            {
              @Override
              public void actionPerformed(ActionEvent event)
              {
                  //paintedShapes.clear();
                  repaint();    
              }
            }
    );
    color1 = new JButton ("1st Color"); //done
    color1.addActionListener(new ActionListener()
            {
             @Override
             public void actionPerformed(ActionEvent event)
             {
                 paintColor1 = JColorChooser.showDialog(DrawPane.this, "Choose a color", getPaintColor1());
                 
                 if (getPaintColor1() == null)
                     paintColor1 = Color.RED;
             }
            }
    );
    color2 = new JButton ("2nd Color"); //done
    color2.addActionListener(new ActionListener()
            {
             @Override
             public void actionPerformed(ActionEvent event)
             {
                 paintColor2 = JColorChooser.showDialog(DrawPane.this, "Choose a color", getPaintColor2());
                 
                 if (getPaintColor2() == null)
                     paintColor2 = Color.RED;
             }
            }
    );    
    
    shapeChoice = new JComboBox<String>(shapes);
    
    filled = new JCheckBox("Filled");
    gradient = new JCheckBox("Use Gradient");
    dashed = new JCheckBox("Dashed");
    
    widthField = new JTextField(2);
    lengthField = new JTextField(2);
    
    shape = new JLabel("Shape:");
    widthLabel = new JLabel("Line Width:");
    lengthLabel = new JLabel("Dash Length:");
    status = new JLabel("TO DO");
    
    optionMenu.add(undo);
    optionMenu.add(clear);
    optionMenu.add(shape);
    optionMenu.add(shapeChoice);
    optionMenu.add(filled);
    
    optionMenu2.add(gradient);
    optionMenu2.add(color1);
    optionMenu2.add(color2);
    optionMenu2.add(widthLabel);
    optionMenu2.add(widthField);
    optionMenu2.add(lengthLabel);
    optionMenu2.add(lengthField);
    optionMenu2.add(dashed);
    
    optionComplete.add(optionMenu, BorderLayout.NORTH);
    optionComplete.add(optionMenu2,BorderLayout.SOUTH);
    add(optionComplete, BorderLayout.NORTH);
    add(paintFrame);
    add(status, BorderLayout.SOUTH);
    
    }

    public Color getPaintColor1() {
        return paintColor1;
    }
   
    public Color getPaintColor2() {
        return paintColor2;
    }

    public boolean isFilled() {
        return filled.isSelected();
    }

    public boolean isGradient() {
        return gradient.isSelected();
    }

    public boolean isDashed() {
        return dashed.isSelected();
    }

    public int getWidthField() 
    {
        int result = 0;
        try { //Try-catch block in case a user leaves the width/length field blank to avoid number format exception
            result = Integer.parseInt(widthField.getText());
        } catch(NumberFormatException nfe) {
           result = 1;
        }
        return result;
    }

    public int getLengthField() 
    {
        int result = 0;
        try { //Try-catch block in case a user leaves the width/length field blank to avoid number format exception
            result = Integer.parseInt(lengthField.getText());
        } catch(NumberFormatException nfe) {
           result = 1;
        }
        return result;
    }

    public JComboBox<String> getShapeChoice() {
        return shapeChoice;
    }
    
    public class Canvas extends JPanel
    {
        
        
        public Canvas()
        {
            addMouseListener(new MouseHandler());
            addMouseMotionListener(new MouseHandler());
        }
        
        public class State
        {
            private final Color paint1;
            private final Color paint2;
            private final boolean gradient;
            private final boolean filled;
            private final boolean dashed;
            private final int LineWidth;
            private final int DashLength;
            private final Point start;
           
            public State(Color paint1, Color paint2, boolean gradient, boolean filled, boolean dashed, int lineWidth, int dashLength, Point start) 
            {
                this.paint1 = paint1;
                this.paint2 = paint2;
                this.gradient = gradient;
                this.filled = filled;
                this.dashed = dashed;
                this.LineWidth = lineWidth;
                this.DashLength = dashLength;
                this.start = start;
            }

            public Color getPaint1() 
            {
                return paint1;
            }

            public Color getPaint2() 
            {
                return paint2;
            }

            public boolean isGradient() 
            {
                return gradient;
            }

            public boolean isFilled() 
            {
                return filled;
            }

            public boolean isDashed() 
            {
                return dashed;
            }

            public int getLineWidth() 
            {
                return LineWidth;
            }

            public int getDashLength() 
            {
                return DashLength;
            }

            public int getStartX() {
                return (int) start.getX();
            }
            
            public int getStartY() {
                return (int) start.getY();
            }      

        }
        
        @Override 
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Shapes nextShape;
            Iterator shapesIterator = paintedShapes.iterator();
            
            while ( shapesIterator.hasNext())
            {
                nextShape = (Shapes) shapesIterator.next();
                nextShape.draw(g);
            }
        }
        
        public abstract class Shapes extends Object 
        {
            public State state;
            private int endX = 0;
            private int endY = 0;

            public Shapes(State state)
            {
                this.state = state;
            }

            public State getState() 
            {
                return state;
            }       
            
            public void setEndX(int endX) {
                this.endX = endX;
            }

            public void setEndY(int endY) {
                this.endY = endY;
            }
           
            public int getEndX() {
                return endX;
            }

            public int getEndY() {
                return endY;
            }

            public abstract void draw (Graphics g);

           }

        public class Line extends Shapes
        {
            public Line(State state)
            {
                super(state);
            }

            @Override
            public void draw (Graphics g)
            {       
                g.setColor( state.getPaint1() );
                g.drawLine(state.getStartX(), state.getStartY(), getEndX(), getEndY());
            }

        }

        public class Rectangle extends Shapes
        {
            public Rectangle(Canvas.State state)
            {
                super(state);
            }

            @Override
            public void draw (Graphics g)
            {
                if (state.isGradient() == true)
                {
                    //to do
                }
                else
                {
                    g.setColor( state.getPaint1() );
                }
                if (state.isFilled() == true)
                {
                    g.fillRect(Math.min(state.getStartX(), getEndX()), Math.min(state.getStartY(), getEndY()),
                            Math.abs(state.getStartX() - getEndX()),
                            Math.abs(state.getStartY() - getEndY()));
                } 
                else
                {
                    g.drawRect(Math.min(state.getStartX(), getEndX()), Math.min(state.getStartY(), getEndY()),
                            Math.abs(state.getStartX() - getEndX()),
                            Math.abs(state.getStartY() - getEndY()));
                }
            }
        }

        public class Oval extends Shapes
        {
            public Oval(State state)
            {
                super(state);
            }

            @Override
            public void draw (Graphics g)
            {
                if (state.isGradient() == true)
                {
                    //to do
                }
                else
                {
                    g.setColor( state.getPaint1() );
                }
                if (state.isFilled() == true)
                {
                    g.fillOval(Math.min(state.getStartX(), getEndX()), Math.min(state.getStartY(), getEndY()),
                            Math.abs(state.getStartX() - getEndX()),
                            Math.abs(state.getStartY() - getEndY()));
                } 
                else
                {
                    g.drawOval(Math.min(state.getStartX(), getEndX()), Math.min(state.getStartY(), getEndY()),
                            Math.abs(state.getStartX() - getEndX()),
                            Math.abs(state.getStartY() - getEndY()));
                }
            }
        }
        private class MouseHandler extends MouseAdapter
        {
            Point start = new Point();
            Point end = new Point();

            @Override
            public void mouseDragged(MouseEvent event)
            {
  
                currentShape.setEndX(event.getX());
                currentShape.setEndY(event.getY());

                status.setText(String.format("[%d, %d]", event.getX(), event.getY()));

                repaint();
            }

            @Override
            public void mousePressed(MouseEvent event)
            {
                Point start = event.getPoint();
                State state = new State(getPaintColor1(), getPaintColor2(), isGradient(), isFilled(), 
                        isDashed(), getWidthField(), getLengthField(), start );
                if (shapeChoice.getSelectedItem() == "Line")
                    currentShape = new Line(state);
                else if (shapeChoice.getSelectedItem() == "Oval")
                    currentShape = new Oval(state);
                else if (shapeChoice.getSelectedItem() == "Rectangle")
                    currentShape = new Rectangle(state);
                 paintedShapes.add(currentShape);
                 
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                status.setText(String.format("[%d, %d]", event.getX(), event.getY())); //temp
               
            }
        }

    }

}

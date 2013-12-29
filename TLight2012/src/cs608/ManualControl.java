package cs608;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ManualControl extends JFrame {

	private static final long serialVersionUID = 1L;
	private TrafficLight tl;
	
	ButtonPanel bp;
	
	public ManualControl() {
		super("Controller-1");
		tl = new TrafficLight("Controller-1");
		initDisplay();
	}
	
	public void initDisplay() {
		
		setLayout(new BorderLayout());
		
		bp=new ButtonPanel(tl);
		add(bp, BorderLayout.NORTH);
		
		JPanel tp=new JPanel(new GridLayout(1,4));
		JLabel l=new JLabel("NORTH",SwingConstants.CENTER);
		tp.add(l);
		l=new JLabel("SOUTH",SwingConstants.CENTER);
		tp.add(l);
		l=new JLabel("WEST",SwingConstants.CENTER);
		tp.add(l);
		l=new JLabel("EAST",SwingConstants.CENTER);
		tp.add(l);
		add(tp,BorderLayout.CENTER);
		
		LightPanel lp=new LightPanel(tl);
		add(lp, BorderLayout.SOUTH);
		
		setSize( 300, 300 );
		setLocation(new Point(100,100));
		pack();
		setResizable(false);
		setVisible( true );
				
	}
	
	
	public static void main(String[] args) {
		
		ManualControl mc=new ManualControl();

	}

}

class ButtonPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private TrafficLight t;
	JButton cycleButton, lockButton, exitButton;
	boolean locked=false;
	
	ButtonPanel(TrafficLight i_t) {
		
		t=i_t;

		cycleButton=new JButton("CYCLE");
		cycleButton.addActionListener( this );
		cycleButton.setName("Cycle");
		add( cycleButton );
		
		lockButton=new JButton("LOCK");
		lockButton.addActionListener( this );
		lockButton.setName("Lock");
		add( lockButton );

		exitButton=new JButton("EXIT");
		exitButton.addActionListener( this );
		exitButton.setName("Exit");
		add( exitButton );

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if ((arg0.getSource()==cycleButton)&&(!locked))
			t.cycle();
		else if (arg0.getSource()==lockButton)
			locked = !locked;
		else if (arg0.getSource()==exitButton)
			System.exit(0);
		getParent().repaint();
	}
	
}

class LightPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private TrafficLight t;
	
	LightPanel(TrafficLight i_t) {
		
		super(new GridLayout(1,4));
		
		t=i_t;

		LampPanel lpN=new LampPanel("NORTH",t,TrafficLight.NS_LIGHTS);
		LampPanel lpS=new LampPanel("SOUTH",t,TrafficLight.NS_LIGHTS);
		LampPanel lpE=new LampPanel("EAST",t,TrafficLight.EW_LIGHTS);
		LampPanel lpW=new LampPanel("WEST",t,TrafficLight.EW_LIGHTS);
		add( lpN );
		add( lpS );
		add( lpE );
		add( lpW );
		
	}
	
}

class LampPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private TrafficLight t;
	private int dir;
	private Lamp lamps[];
	
	LampPanel(String name, TrafficLight i_t, int i_dir) {
		
		super(new GridLayout(3,1));

		t=i_t;
		dir = i_dir;
		
		// setup the panel
		
		lamps = new Lamp[3];
		lamps[0] = new Lamp(name+"RED","RED",true);
		lamps[1] = new Lamp(name+"AMBER","AMBER",false);
		lamps[2] = new Lamp(name+"GREEN","GREEN",false);
		
		setName(name);
		setPreferredSize(new Dimension(50,150));
		setBackground(Color.BLACK);
		
		//
		
		add(lamps[0]);
		add(lamps[1]);
		add(lamps[2]);
		
		// create the three bulbs
		
		repaint();
		
	}
	
	public void paint(Graphics g) {
		Lights[] l=t.getLights();
		lamps[0].turnOff();
		lamps[1].turnOff();
		lamps[2].turnOff();
		if (l[dir].get()==Lights.Color.RED)
			lamps[0].turnOn();
		if (l[dir].get()==Lights.Color.AMBER)
			lamps[1].turnOn();
		if (l[dir].get()==Lights.Color.GREEN)
			lamps[2].turnOn();
	}
	
}

class Lamp extends JPanel {
	
	private Color col;
	private boolean on;
	private int border=4;

	public Lamp(String i_name, String i_col, boolean isOn) {
		setName(i_name);
		on = isOn;
		col = Color.BLACK;
		if ("RED".equals(i_col)) col = Color.RED;
		else if ("AMBER".equals(i_col)) col = Color.ORANGE;
		else if ("GREEN".equals(i_col)) col = Color.GREEN;
		setPreferredSize(new Dimension(50,50));
		if (on)
			setBackground(col);
		else
			setBackground(Color.BLACK);
	}
	
	void turnOff() {
		on = false;
		setBackground(Color.BLACK);
		repaint();
	}

	void turnOn() {
		on = true;
		setBackground(col);
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		int w=getWidth();
		int h=getHeight();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,w,border);
		g.fillRect(0,0,border,h);
		g.fillRect(0,h-border,w,border);
		g.fillRect(w-border,0,border,h);
	}
	
}

package Game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class CoronaCurve extends Applet implements Runnable, MouseListener, KeyListener {
		
	private static int appletSize_x = 600;							//Breite des Applet-Fensters
	private static int appletSize_y = 600;							//Höhe des Applet-Fensters
	
	private Color mainColor = Color.yellow;							//Schriftfarbe
	private Color backgroundColor = Color.black;					//Hintergrundfarbe
	
	private Font mainFont = new Font("Impact", Font.PLAIN, 20);		//Schriftart	
	private Font titleFont = new Font("Impact", Font.PLAIN, 50);	//Schriftart für Titel
	
	// Variablen für die Doppelpufferung
	private Image dbImage;
	private Graphics dbg;
	
	Thread th;
	
	public static final int HEALTHY = 0;
	public static final int INFECTED = 1;
	public static final int IMMUNE = 2;
	
	private NPC[] npcs;
	
	public static void main(String[] args){
		CoronaCurve cc = new CoronaCurve();
		JFrame frame = new JFrame("CoronaCurve");
		frame.setSize(appletSize_x + 50, appletSize_y + 50);
		frame.setResizable(false);
		frame.add(cc);
		
		cc.init();
		cc.start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void init(){
		this.setSize(400, 400);
		
		setBackground(backgroundColor);
		setSize(appletSize_x, appletSize_y);		
		setFont(mainFont);
		addKeyListener(this);
		addMouseListener(this);
		
		npcs = new NPC[100];
		
		npcs[0] = new NPC(250, 250, 2);
	}
	
	public void start(){
		th = new Thread(this);
		th.start();
	}
	
	public void stop(){
		th.interrupt();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while (true) {
			npcs[0].move();
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println(e);
			} 
		}
	}
	
	public void paint(Graphics g){
		npcs[0].paintNPC(g);
	}
	

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	//Methode zur Realisierung der Doppelpufferung
	public void update (Graphics g){
		if (dbImage == null){
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		dbg.setColor (getForeground());
		paint (dbg);

		g.drawImage (dbImage, 0, 0, this);
	}
}

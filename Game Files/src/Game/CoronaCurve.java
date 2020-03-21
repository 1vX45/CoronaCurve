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
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.sun.xml.internal.ws.api.Component;

public class CoronaCurve extends Applet implements Runnable, MouseListener, KeyListener {
		
	private static final long serialVersionUID = 1L;
	
	
	public static int appletSize_x = 600;							//Breite des Applet-Fensters
	public static int appletSize_y = 600;							//Höhe des Applet-Fensters
	
	private Color mainColor = Color.yellow;							//Schriftfarbe
	private Color backgroundColor = Color.white;					//Hintergrundfarbe
	
	private Font mainFont = new Font("Impact", Font.PLAIN, 20);		//Schriftart	
	private Font titleFont = new Font("Impact", Font.PLAIN, 50);	//Schriftart für Titel
	
	private Image startImage;
	
	// Variablen für die Doppelpufferung
	private Image dbImage;
	private Graphics dbg;
	
	Thread th;
	
	Random rand = new Random();
	
	public static final int HEALTHY = 0;
	public static final int INFECTED = 1;
	public static final int IMMUNE = 2;
	
	public static final Color CLR_HEALTHY = new Color(82, 189, 81);
	public static final Color CLR_INFECTED = new Color(189, 55, 55);
	public static final Color CLR_IMMUNE = new Color(166, 166, 166);
	
	private static final int ENTITY_SIZE = 15;
	private static final int PLAYER_STARTX = appletSize_x / 2;
	private static final int PLAYER_STARTY = appletSize_y - 50;
	private static final int ENTITY_SPEED = 1;

	private int propability = 5;
	
	private NPC[] npcs;
	private int[] npcActions;
	private int actionCounter = 0;
	private Player player;
	
	private boolean leftPressed, rightPressed, upPressed, downPressed;
	
	private boolean menuStart = true;
	private boolean game = false;
	
	
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
		this.setSize(600, 600);
		
		setBackground(backgroundColor);
		setSize(appletSize_x, appletSize_y);		
		setFont(mainFont);
		addKeyListener(this);
		addMouseListener(this);
		
		try {
			startImage = (Image) ImageIO.read(new File("bin/Startbild.png")).getScaledInstance(650, 650, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		npcs = new NPC[20];
		npcActions = new int[20];
		actionSet();
		createNPCs(npcs, 20);
		
	
		player = new Player(PLAYER_STARTX, PLAYER_STARTY, ENTITY_SIZE, ENTITY_SPEED, CLR_INFECTED);
	}
	
	private void createNPCs(NPC[] npcs, int n){
		int x, y;
		double d;
		for(int i = 0; i < n; i++){
			x = Math.abs(rand.nextInt() % appletSize_x);
			y = Math.abs(rand.nextInt() % appletSize_y);
			npcs[i] = new NPC(x, y, ENTITY_SIZE, (double)(rand.nextInt() % 20) / 10, (double)(rand.nextInt() % 20) / 10, CLR_HEALTHY);
		}
	}
	
	private void actionSet(){
		for(int i = 0; i < npcs.length; i++){
			npcActions[i] = rand.nextInt()%2;
		}
	}
	
	private void checkCollisions(){
		int j = 1, jr = 1;
		for(int i = 0; i < npcs.length; i++){
			j = jr;
			while(j<npcs.length){
				if(npcs[i].collidesWith(npcs[j])){		
					int r = Math.abs((int)rand.nextInt()  % 100 + 1);
					if(npcs[j].getStatus() == INFECTED && r<propability) 
						npcs[i].infect();
					if(npcs[i].getStatus() == INFECTED && r<propability)
						npcs[j].infect();
					
				}
				j++;
			}
			jr++;
			
			int r = Math.abs((int)rand.nextInt()  % 100 + 1);
			if(player.collidesWith(npcs[i]) && r<propability){
				npcs[i].infect();
			}
		}
	}
	
	public void start(){
		th = new Thread(this);
		th.start();
	}
	
	public void stop(){
		th.interrupt();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (menuStart) {
			if (x >= 200 && x <= 450 && y >= 380 && y <= 480) {
				menuStart = false;
				game = true;
				repaint();
			} 
		}
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
			if (game) {
				repaint();
				if (leftPressed)
					player.moveLeft();
				if (rightPressed)
					player.moveRight();
				if (upPressed)
					player.moveUp();
				if (downPressed)
					player.moveDown();
				for (int i = 0; i < npcs.length; i++) {
					npcs[i].doSth(npcActions[i]);
				}
				checkCollisions();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
				if (actionCounter == 0) {
					actionSet();
					actionCounter++;
				} else if (actionCounter < 50) {
					actionCounter++;
				} else
					actionCounter = 0;
			}
			
			if(menuStart){
				System.out.println("MENU");
			}
		}
	}
	
	public void paint(Graphics g){
		if (game) {
			player.paint(g);
			for (int i = 0; i < npcs.length; i++) {
				npcs[i].paint(g);
			} 
		}else if(menuStart){
			g.drawImage(startImage, 0, 0, this);
		}
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_UP) upPressed = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_UP) upPressed = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = false;
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

package Game;

import java.awt.Color;
import java.awt.Graphics;

public class NPC {
	private int status;
	private int posX, posY;
	private int speed;
	
	public NPC (int startX, int startY, int speed){
		status = CoronaCurve.HEALTHY;
		this.posX = startX;
		this.posY = startY;
		this.speed = speed;
	}
	
	
	public int getStatus(){
		return status;
	}
	
	public int getX(){
		return posX;
	}
	
	
	public int getY(){
		return posY;
	}
	
	public void move(){
		posX += speed;
		posY += speed;
	}
	
	public void paintNPC(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(posX, posY, 50, 50);
	}
}


package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	private int posX, posY;
	private int size;
	private int speed;
	private Color color;
	
	public Player(int startX, int startY, int size, int initSpeed, Color initColor){
		posX = startX;
		posY = startY;
		this.size = size;
		speed = initSpeed;
		color = initColor;
	}
	
	public void moveRight(){
		posX += speed;
	}
	
	public void moveLeft(){
		posX -= speed;
	}
	
	public void moveUp(){
		posY -= speed;
	}
	
	public void moveDown(){
		posY += speed;
	}
	
	public void paint(Graphics g){
		g.setColor(color);
		g.fillOval(posX, posY, size, size);
	}
}

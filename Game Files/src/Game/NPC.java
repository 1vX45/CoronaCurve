package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class NPC {
	private int status;
	private int duration; //wie lange es dauert bis person wieder gesundet //12345 bedeutet gesund
	private int posX, posY, size;
	private int speedX, speedY;
	private Color color;
	
	public NPC (int startX, int startY, int size, int initSpeedX, int initSpeedY, Color initColor){
		status = CoronaCurve.HEALTHY;
		posX = startX;
		posY = startY;
		this.size = size;
		speedX = initSpeedX;
		speedY = initSpeedY;
		color = initColor;
		duration = 12345;
	}
	Random rand = new Random();
	public int getStatus(){
		return status;
	}
	
	public int getX(){
		return posX;
	}
	
	public int getY(){
		return posY;
	}
	
	/*
	 *  0: no collision
	 *  1: collision left or right
	 *  2: collision above or below
	 */
	public int isBorder(int sizeX, int sizeY){
		if(posX < 0 || posX > sizeX - size)
			return 1;
		else if(posY < 0 || posY > sizeY - size)
			return 2;
		else return 0;
	}
	
	public void move(){
		int x = CoronaCurve.appletSize_x;
		int y = CoronaCurve.appletSize_y;
		if(isBorder(x, y) == 0){
			posX += speedX;
			posY += speedY;
		}else if(isBorder(x, y) == 1){
			speedX *= -1;
			posX += speedX;
			posY += speedY;
		}else if(isBorder(x, y) == 2){
			speedY *= -1;
			posX += speedX;
			posY += speedY;
		}
	}
	
	public void infect(){
		status = CoronaCurve.INFECTED;
		color = CoronaCurve.CLR_INFECTED;
		setDuration(Math.abs(rand.nextInt() % 300) + 100; //zufällig?
	}
	
	public int isHealed(){
		if(getDuration() == 1) {
			return 1;
		}
		else if (getDuration() < 0) {
			System.out.println("ERROR: HEALING DOESN'T WORK AS EXPECTED");
			return 1;
		}
		else {
			setDuration(getDuration()-1);
			return 0;
		}
	}
	
	public void heal(){
		status = CoronaCurve.IMMUNE;
		color = CoronaCurve.CLR_IMMUNE;
	}
	
	public boolean collidesWith(NPC other){
		int diff_x = Math.abs(posX - other.getX());
		int diff_y = Math.abs(posY - other.getY());
		double dist = Math.sqrt(diff_x*diff_x + diff_y*diff_y);
		if(dist < size) 
			return true;
		else return false;
	}
	
	public void paint(Graphics g){
		g.setColor(color);
		g.fillOval(posX, posY, size, size);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}



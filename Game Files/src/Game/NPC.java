package Game;

import java.awt.Color;
import java.awt.Graphics;

public class NPC {
	private int status;
	private double posX, posY;
	private int size;
	private double speedX, speedY;
	private Color color;
	
	public NPC (int startX, int startY, int size, double initSpeedX, double initSpeedY, Color initColor){
		status = CoronaCurve.HEALTHY;
		posX = startX;
		posY = startY;
		this.size = size;
		speedX = initSpeedX;
		speedY = initSpeedY;
		color = initColor;
	}
	
	public int getStatus(){
		return status;
	}
	
	public double getX(){
		return posX;
	}
	
	public double getY(){
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
	
	public void doSth(int r){
			switch (r) {
			case 0:
				move();
				break;
			case 1:
				break;
			default:
				break;
			}
	}
	
	public void infect(){
		status = CoronaCurve.INFECTED;
		color = CoronaCurve.CLR_INFECTED;
	}
	
	public void heal(){
		status = CoronaCurve.IMMUNE;
		color = CoronaCurve.CLR_IMMUNE;
	}
	
	public boolean collidesWith(NPC other){
		double diff_x = Math.abs(posX - other.getX());
		double diff_y = Math.abs(posY - other.getY());
		double dist = Math.sqrt(diff_x*diff_x + diff_y*diff_y);
		if(dist < size) 
			return true;
		else return false;
	}
	
	public void paint(Graphics g){
		g.setColor(color);
		g.fillOval((int)posX, (int)posY, size, size);
	}
}


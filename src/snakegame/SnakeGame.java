package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
	
	private class Tile{
		int x,y;
		Tile(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	
	int boardWidth;
	int boardHeight;
	int tileSize=25;
	
	Tile snakeHead;
	
	ArrayList<Tile> snakeBody;
	Tile food;
	Random random;
	
	//game logic
	Timer gameLoop;
	
	int velocityx;
	int velocityy;
	boolean gameOver=false;
	
	
	public SnakeGame(int boardWidth, int boardHeight) {
		this.boardHeight=boardHeight;
		this.boardWidth=boardWidth;
		setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
		setBackground(Color.gray);
		addKeyListener(this);
		setFocusable(true);
		
		snakeHead=new Tile(5,5);
		snakeBody=new ArrayList<Tile>();
		food=new Tile(10,10);
		
		random=new Random();
		placeFood();
		velocityx=0;
		velocityy=0;
		gameLoop=new Timer(200,this);
		gameLoop.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		//Food
		g.setColor(Color.RED);
		g.fillRect(food.x*tileSize, food.y*tileSize,tileSize , tileSize);
		
		
		//snakeHead
		g.setColor(Color.black);
		g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
		
		//SnakeBody 
		for(int i=0;i<snakeBody.size();i++) {
			Tile snakePart=snakeBody.get(i);
			g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize,tileSize, tileSize);
		}
		//Score
		g.setFont(new Font("Arial",Font.CENTER_BASELINE,16));
		if(gameOver) {
			g.setColor(Color.GREEN);
			g.drawString("GAME OVER: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
			
		}else {
			g.drawString("Score: "+String.valueOf(snakeBody.size()), tileSize-16,tileSize);
		}
		
	}

	public void placeFood() {
		food.x=random.nextInt(boardWidth/tileSize);
		food.y=random.nextInt(boardHeight/tileSize);	
		
	}

	public boolean collision(Tile tile1,Tile tile2) {
		return tile1.x==tile2.x && tile1.y == tile2.y;
	}
	
	
	public void move() {
		//eat food 
		if(collision(snakeHead, food)) {
			snakeBody.add(new Tile(food.x,food.y));
			placeFood();
		}
		//snakebody
		for(int i=snakeBody.size()-1;i>=0;i--) {
			Tile snakePart=snakeBody.get(i);
			if(i==0) {
				snakePart.x=snakeHead.x;
				snakePart.y=snakeHead.y;
			}else {
				Tile prevSnakePart=snakeBody.get(i-1);
				snakePart.x=prevSnakePart.x;
				snakePart.y=prevSnakePart.y;
			}
		}
		
		//snake head
		snakeHead.x+=velocityx;
		snakeHead.y+=velocityy;
		//gameOver Conditions
		for(int i=0;i<snakeBody.size();i++) {
			Tile snakePart=snakeBody.get(i);
			if(collision(snakeHead, snakePart)) {
				gameOver=true;
			}
		}
		if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize>boardWidth ||
			snakeHead.y*tileSize<0 || snakeHead.y*tileSize>boardWidth) {
			gameOver=true;
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if(gameOver) {
			gameLoop.stop();
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP && velocityy !=1) {
			velocityx=0;
			velocityy=-1;		
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityy !=-1) {
			velocityx=0;
			velocityy=1;		
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityx !=1) {
			velocityx=-1;
			velocityy=0;		
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityx !=-1) {
			velocityx=1;
			velocityy=0;		
		}
		
	}

	
	//Do not need
	@Override
	public void keyTyped(KeyEvent e) {
			
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	
}

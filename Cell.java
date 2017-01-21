//an individual cell of the minefield
package minesweeper;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Insets;

public class Cell extends JButton{
	//says the location of the cell in the grid, whether or not it contains a mine, and the number of surrounding mines
	private int xPos;
	private int yPos;
	private boolean mine = false;
	private int adjacentMines;
	private boolean isCleared = false;
	private boolean flagged = false;
	
	public Cell(int x, int y) {
		xPos = x;
		yPos = y;
		this.setMargin(new Insets(0, 0, 0, 0));
	}
	
	public void setMine() {
		mine = true;
	}
	
	public boolean isMine() {
		return mine;
	}
	
	public void setAdjacentMines(int adjacentMines) {
		this.adjacentMines = adjacentMines;
	}
	
	public int getAdjacentMines() {
		return adjacentMines;
	}
	
	public boolean isCleared() {
		return isCleared;
	}
	
	public void setCleared() {
		isCleared = true;
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		if (adjacentMines > 0) {
			this.setText(Integer.toString(adjacentMines));
		}
		switch (adjacentMines) {
		case 1: this.setForeground(Color.BLUE);
			break;
		case 2: this.setForeground(Color.decode("0x008000"));
			break;
		case 3: this.setForeground(Color.RED);
			break;
		default: this.setForeground(Color.BLACK);
			break;
		}
	}
	
	public void reset() {
		isCleared = false;
		mine = false;
		flagged = false;
		setBorderPainted(true);
		setFocusPainted(true);
		setContentAreaFilled(true);
		this.setText("");
	}
	
	public void toggleFlag() {
		if (isCleared == false) { //can't set an already cleared spot
			//toggle flagged state when clicked
			if (flagged == false) {
				this.setText("F");
				this.setForeground(Color.RED);
				flagged = true;
			}
			else if (flagged == true) {
				this.setText("");
				flagged = false;
			}
		}
	}
	
	public boolean isFlagged() {
		return flagged;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
}
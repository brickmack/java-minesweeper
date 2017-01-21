//contains the entire minefield
package minesweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Grid extends JPanel{
	private int width;
	private int height;
	private int mines;
	private Cell[][] cells;
	private boolean initialized = false;
	private long startTime;
	
	public Grid() {
	}
	
	public void makeGrid(int width, int height, int mines) {
		this.width = width;
		this.height = height;
		this.mines = mines;
		
		//scale the grid and the window with the number of cells
		setLayout(new GridLayout(height, width));
		setPreferredSize(new Dimension(30*width, 30*height));
		
		cells = new Cell[width][height];
		for (int yPos=0; yPos<height; yPos++) {
			for (int xPos=0; xPos<width; xPos++) {
				cells[xPos][yPos] = new Cell(xPos, yPos);
				add(cells[xPos][yPos]);
				cells[xPos][yPos].addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						//get the individual cell clicked
						Cell cell = (Cell) e.getSource();
						
						//determine if it was a left or right click
						if (SwingUtilities.isLeftMouseButton(e)) {
							//we don't generate the minefield until after the first click, the ensure that the user doesnt hit a mine on the first try
							if (initialized == false) {
								//set mines at random positions and count how many are next to each cell
								generateMines();
								initialized = true;
							}
							//either clear the cell or set off a mine
							leftClick(cell);
						}
						else if (SwingUtilities.isRightMouseButton(e)) {
							//flag/unflag the cell
							cell.toggleFlag();
						}
		            }
						
					//these don't do anything, just to appease the compiler gods
					public void mousePressed(MouseEvent e) {
		            }
		            public void mouseReleased(MouseEvent e) {
		            }
		            public void mouseEntered(MouseEvent e) {
		            }
		            public void mouseExited(MouseEvent e) {
		            }
				});
			}
		}
	}
	
	public void reset() {
		//resets the grid to its starting condition
		for (int yPos=0; yPos<height; yPos++) {
			for (int xPos=0; xPos<width; xPos++) {
				cells[xPos][yPos].reset();
			}
		}
		startTime = 0;
		initialized = false;
	}
	
	public void generateMines() {
		startTime = System.currentTimeMillis();
		
		Random rand = new Random();
		int counter = 0;
		while (counter < mines) {
			int xPos = rand.nextInt(width);
			int yPos = rand.nextInt(height);
			
			//verify that the cell isn't already filled, or the one the user hit on the first click
			if (cells[xPos][yPos].isMine() == false && cells[xPos][yPos].isCleared() == false) {
				cells[xPos][yPos].setMine();
				counter++;
			}
		}
		//count adjacent mines for each cell
		for (int yPos=0; yPos<height; yPos++) {
			for (int xPos=0; xPos<width; xPos++) {
				int adjacentMines = 0;
				int[] xVals = {xPos-1, xPos, xPos+1};
				int[] yVals = {yPos-1, yPos, yPos+1};
				for (int xVal : xVals) {
					for (int yVal : yVals) {
						if (xVal>=0 && yVal>=0 && xVal<width && yVal<height && cells[xVal][yVal].isMine() == true) {
							adjacentMines++;
						}
					}
				}
				cells[xPos][yPos].setAdjacentMines(adjacentMines);
			}
		}
	}
	
	public void leftClick (Cell cell) {
		//either set off a mine, or clear the cell. in a separate method even though only used once, because readability
		if (cell.isFlagged() == false) {//dont want to let the user hit a flagged mine accidentally
			if (cell.isMine() == true) {
				int playAgain = JOptionPane.showConfirmDialog(null, "Play again?", "You hit a mine!", JOptionPane.YES_NO_OPTION);
				if (playAgain == JOptionPane.YES_OPTION) {
					reset();
				}
				else {
					System.exit(0);
				}
			}
			else {
				//clear cell, clear adjacent cells that don't have any adjacent mines
				int cellX = cell.getXPos();
				int cellY = cell.getYPos();
				
				cells[cellX][cellY].setCleared();
				
				for (int r=0; r<10; r++) { //really hacky solution, will try to find a better algorithm to find an endpoint
					for (int yPos=0; yPos<height; yPos++) {
						for (int xPos=0; xPos<width; xPos++) {
							if (cells[xPos][yPos].getAdjacentMines() == 0 && cells[xPos][yPos].isCleared() == true) {
								int[] xVals = {xPos-1, xPos, xPos+1};
								int[] yVals = {yPos-1, yPos, yPos+1};
								for (int x : xVals) {
									for (int y : yVals) {
										if (x>=0 && y>=0 && x<width && y<height) {
											cells[x][y].setCleared();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public int getCorrect() {
		//count the total number of correctly flagged mines on the field
		int correct = 0;
		for (int yPos=0; yPos<height; yPos++) {
			for (int xPos=0; xPos<width; xPos++) {
				if (cells[xPos][yPos].isFlagged() == true && cells[xPos][yPos].isMine()) {
					correct++;
				}
			}
		}
		return correct;
	}
	
	public int getFlaggedMines() {
		//count the number of flagged mines
		int flagged = 0;
		for (int yPos=0; yPos<height; yPos++) {
			for (int xPos=0; xPos<width; xPos++) {
				if (cells[xPos][yPos].isFlagged()) {
					flagged++;
				}
			}
		}
		return flagged;
	}
	
	public int getGridSize() {
		return width*height;
	}
	
	public String getPlayTime() {
		long playTime = 0;
		if (startTime != 0) {
			playTime = (System.currentTimeMillis() - startTime)/1000;
		}
		return Long.toString(playTime);
	}
}
package minesweeper;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JMenuItem;

public class GameWindow extends JFrame{
	private Grid grid = new Grid();
	private JLabel mineCounter = new JLabel();
	
	public GameWindow(int width, int height, int mines) {
		grid.makeGrid(width, height, mines);
		
		JPanel header = new JPanel();
		header.setLayout(new GridLayout(1,3));
		
		header.add(mineCounter);
		
		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.reset();
			}
		});
		header.add(reset);
		
		JLabel timerDisplay = new JLabel("00:00");
		header.add(timerDisplay);
		
		add(grid);
		add(header, BorderLayout.PAGE_START);
		pack();
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		while (true) {
			//update the numbers in the header
			mineCounter.setText(Integer.toString(grid.getFlaggedMines()) + "/" + Integer.toString(mines));
			timerDisplay.setText(grid.getPlayTime());
			
			//win conditions
			if (grid.getFlaggedMines() == mines) {
				if (grid.getCorrect() == mines) {
					int playAgain = JOptionPane.showConfirmDialog(null, "Play again?", "You won!", JOptionPane.YES_NO_OPTION);
					if (playAgain == JOptionPane.YES_OPTION) {
						grid.reset();
					}
					else {
						System.exit(0);
					}
				}
			}
		}
	}
}
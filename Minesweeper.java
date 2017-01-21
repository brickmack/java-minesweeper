package minesweeper;

import javax.swing.*;
import java.util.*;

public class Minesweeper {
	public static void main(String[] args) {
		JTextField widthField = new JTextField("10     "); //extra spaces at the end are neccesary to make the box long enough
		JTextField heightField = new JTextField("10     ");
		JTextField mineField = new JTextField("10     ");

		JPanel dialogWindow = new JPanel();
		dialogWindow.add(new JLabel("Width:"));
		dialogWindow.add(widthField);
		dialogWindow.add(Box.createHorizontalStrut(15));
		dialogWindow.add(new JLabel("Height:"));
		dialogWindow.add(heightField);
		dialogWindow.add(Box.createHorizontalStrut(15));
		dialogWindow.add(new JLabel("Mines:"));
		dialogWindow.add(mineField);

		while (true) {
			//keep looping over the dialog until the user enters something valid
			int result = JOptionPane.showConfirmDialog(null, dialogWindow, "Minesweeper", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				int width = Integer.parseInt(widthField.getText().replace(" ", ""));
				int height = Integer.parseInt(heightField.getText().replace(" ", ""));
				int mines = Integer.parseInt(mineField.getText().replace(" ", ""));
				
				if (mines < (width*height) && width > 3 && height > 3) { //cant have more mines than there are cells, also can't have grid be too small
					new GameWindow(width, height, mines);
					break;
				}
			}
			else {
				System.exit(0);
			}
		}
	}
}
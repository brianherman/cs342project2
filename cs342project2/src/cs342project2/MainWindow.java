package cs342project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
public class MainWindow extends JFrame {
	private JButton[][] buttonGrid = new JButton[32][32];
	public MainWindow() throws IOException{
		setLayout(new GridLayout(8,8));
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				buttonGrid[i][j]= new JButton();
				add(buttonGrid[i][j]);
			}
		}
		pack();
		setVisible(true);
	}
}

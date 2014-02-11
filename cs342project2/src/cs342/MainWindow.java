package cs342;

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
				buttonGrid[i][j].addActionListener(new buttonGridListener());
			}
		}
	
		pack();
		setVisible(true);
	}
	private class buttonGridListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
						if(buttonGrid[i][j]==e.getSource())
							System.out.println("Clicked:"+i+" "+ j);
				}
			}
		}
		
	}
}

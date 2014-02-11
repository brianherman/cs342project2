package cs342;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
public class MainWindow extends JFrame {
	private MinesweeperBoard board;
	private JButton[][] buttonGrid;
	
	public MainWindow(int x, int y, int numberOfMines) throws IOException{
		JMenuBar menuBar;
		JMenu file, help;
		JMenuItem newGame, F1;
		board = new MinesweeperBoard(x,y,numberOfMines);
		buttonGrid = new JButton[x][y];
		menuBar = new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");
		menuBar.add(file);
		menuBar.add(help);
		newGame = new JMenuItem("New Game");
		
		F1 = new JMenuItem("Help");
		file.add(newGame);
		help.add(F1);
		setJMenuBar(menuBar);
		setLayout(new GridLayout(x,y));
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
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
						if(buttonGrid[i][j] == e.getSource())
						{
							reveal(i,j);
						}
				}
			}
		}
		public void reveal(int i, int j){
			if(i==-1)
				return;
			if(j==-1)
				return;
			if(i>=8)
				return;
			if(j>=8)
				return;
			
			if(board.get(i+1,j)==0){
				board.setVisible(i+1, j);
				buttonGrid[i+1][j].setText(Integer.toString(board.get(i+1,j)));
			}else if(board.get(i-1,j)==0){
				board.setVisible(i-1, j);
				buttonGrid[i-1][j].setText(Integer.toString(board.get(i-1,j)));
			}else if(board.get(i, j+1)==0){
				board.setVisible(i, j+1);
				buttonGrid[i][j+1].setText(Integer.toString(board.get(i,j+1)));
			}else if(board.get(i, j-1)==0){
				board.setVisible(i, j-1);
				buttonGrid[i][j-1].setText(Integer.toString(board.get(i,j-1)));
			}
			board.setVisible(i,j);
			buttonGrid[i][j].setText(Integer.toString(board.get(i,j)));
			if(board.revealed(i,j)==false && board.get(i,j)>=0){
				reveal(i+1,j);
				reveal(i-1,j);
				reveal(i,j+1);
				reveal(i,j-1);
			}
		}
	}
}

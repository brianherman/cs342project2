package cs342;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MainWindow extends JFrame {
	private MinesweeperBoard board;
	private JButton[][] buttonGrid;
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem newGame, F1;

	public MainWindow(int x, int y, int numberOfMines) throws IOException{

		board = new MinesweeperBoard(x,y,numberOfMines);
		buttonGrid = new JButton[x][y];
		menuBar = new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");
		menuBar.add(file);
		menuBar.add(help);
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(new MainWindowListener());
		F1 = new JMenuItem("Help");
		file.add(newGame);
		help.add(F1);
		setJMenuBar(menuBar);
		BorderLayout bl = new BorderLayout();
		setLayout(bl);

		setLayout(new GridLayout(x,y));
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				buttonGrid[i][j]= new JButton();
				add(buttonGrid[i][j]);
				buttonGrid[i][j].addActionListener(new MainWindowListener());
			}
		}
		pack();
		setVisible(true);
	}

	private class MainWindowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			for(int i=0; i<8; i++)
			{
				for(int j=0; j<8; j++)
				{
					if(buttonGrid[i][j] == e.getSource())
					{
						reveal(i,j);
						if(board.get(i, j)<0)
							System.out.println("boom");
					}
				}
			}

			if(newGame==e.getSource()){
				board = new MinesweeperBoard(board.length(),board.width(),board.getMines());
				for(int i=0; i<board.length(); i++){
					for(int j=0; j<board.width(); j++){
						buttonGrid[i][j].setText(" ");
					}
				}
			}
		}
		public void reveal(int i, int j)
		{
			if(i>=0 && j>=0 || i<=board.length() && j<=board.width()){
				if(board.get(i, j)>=0 && board.revealed(i, j)==false)
				{
					board.setVisible(i, j);
					buttonGrid[i][j].setText(Integer.toString(board.get(i,j)));
					if((i-1) >0)
						reveal(i-1,j);
					if((i+1) < board.length())
						reveal(i+1,j);
					if((j-1) > 0)
						reveal(i,j-1);
					if((j+1) < board.width())
						reveal(i,j+1);
				}else{
					return;
				}
			}
		}
	}
}
//		public void reveal(int i, int j){
//			Queue<Cell> q = new LinkedList<Cell>();
//			q.add(new Cell(i,j));
//			if(board.get(i,j)<0){
//			}
//			while(!q.isEmpty()){
//				Cell current = q.poll();
//				if(board.get(current.getX(),current.getY())==0 &&
//						board.revealed(current.getX(),current.getY())==false)
//				{
//					if(i+1 < board.length())
//						q.add(new Cell(i+1,j));
//					if(i-1 > 0)
//						q.add(new Cell(i-1,j));
//					if(j-1 > 0)
//						q.add(new Cell(i,j-1));
//					if(j+1 < board.width())
//						q.add(new Cell(i,j+1));
//					if(i+1<board.length() && j+1<board.width())
//						q.add(new Cell(i+1,j+1));
//					if(i-1>0 && j-1>0)
//						q.add(new Cell(i-1,j-1));
//					if(i+1<board.length() && j-1>0)
//						q.add(new Cell(i+1,j-1));
//					if(i-1>0 && j+1<board.width())
//						q.add(new Cell(i-1,j+1));
//				}
//				board.setVisible(current.getX(),current.getY());
//				buttonGrid[current.getX()][current.getY()].setText(Integer.toString(board.get(current.getX(),current.getY())));
//			}
//		}
//	}

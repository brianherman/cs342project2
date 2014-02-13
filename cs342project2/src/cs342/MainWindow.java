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
	private JPanel top;
	private JPanel bottom;
	private JLabel score;
	private JLabel timer;
	public MainWindow(int x, int y, int numberOfMines){
		super("Minesweeper");
		MainWindowListener mwl = new MainWindowListener();
		MainMouseListener mml = new MainMouseListener();

		board = new MinesweeperBoard(x,y,numberOfMines);
		buttonGrid = new JButton[x][y];
		menuBar = new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");
		menuBar.add(file);
		menuBar.add(help);
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(mwl);
		F1 = new JMenuItem("Help");
		file.add(newGame);
		help.add(F1);
		setJMenuBar(menuBar);
		top = new JPanel(new BorderLayout());
		bottom = new JPanel(new GridLayout(x,y));
		setLayout(new BorderLayout());
		
		score = new JLabel("010");
		timer = new JLabel("000");
		score.setFont(new Font("Courier",Font.BOLD,32));
		timer.setFont(new Font("Courier",Font.BOLD,32));

		top.add(score,BorderLayout.LINE_START);
		
		top.add(timer,BorderLayout.LINE_END);
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				buttonGrid[i][j]= new JButton();
				bottom.add(buttonGrid[i][j]);
				buttonGrid[i][j].addActionListener(mwl);
				buttonGrid[i][j].addMouseListener(mml);
			}
		}
		add(top,BorderLayout.PAGE_START);
		add(bottom, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	private class MainMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			for(int i=0; i<10; i++)
			{
				for(int j=0; j<10; j++)
				{
					if(buttonGrid[i][j] == e.getSource())
					{
						if(SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1){
							if(buttonGrid[i][j].isEnabled()){
								buttonGrid[i][j].setEnabled(false);
							}else{
								buttonGrid[i][j].setEnabled(true);
							}
						}
					}

				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {		}

		@Override
		public void mouseExited(MouseEvent e) {		 }
		
		@Override
		public void mousePressed(MouseEvent e) {		}

		@Override
		public void mouseReleased(MouseEvent e) {		}
	}
	private class MainWindowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			for(int i=0; i<10; i++)
			{
				for(int j=0; j<10; j++)
				{
					if(buttonGrid[i][j] == e.getSource())
					{
						System.out.println(board.get(i, j));
						if(board.get(i, j)<0)
						{	
							JOptionPane.showMessageDialog(null, "You Lose.");
							buttonGrid[i][j].setText(Integer.toString(board.get(i,j)));
						}else if(board.get(i, j)>0){
							buttonGrid[i][j].setText(Integer.toString(board.get(i,j)));
						}else{
							reveal(i,j);
						}
						
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
			if(i>=0 && j>=0 || i<board.length() && j<board.width()){
				if(board.get(i, j)==0 && board.revealed(i, j)==false)
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
				}else if(board.get(i, j)>0 && board.revealed(i, j)==false){
					board.setVisible(i, j);
					buttonGrid[i][j].setText(Integer.toString(board.get(i,j)));
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

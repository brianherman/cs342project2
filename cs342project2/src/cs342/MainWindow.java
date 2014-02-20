package cs342;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainWindow extends JFrame {

	private MinesweeperBoard board;
	private JButton[][] buttonGrid;
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem newGame, exit, topTen, helpMenu;
	private JPanel top;
	private JPanel bottom;
	private JLabel flags;
	private JLabel timer;
	private ArrayList<Player> topTenScores = new ArrayList<Player>();
	private boolean gameEnded = false;
	private Timer Jtimer;
	private int time=0;
	private int flagsCounter = 10;

	public static void main(String args[])
	{
		MainWindow mw = new MainWindow(10,10,10);
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public MainWindow(int x, int y, int numberOfMines){
		super("Minesweeper");
		//Create the listeners
		MainWindowListener mwl = new MainWindowListener();
		MainMouseListener mml = new MainMouseListener();

		board = new MinesweeperBoard(x,y,numberOfMines);
		buttonGrid = new JButton[x][y];
		//Setup the menu bar
		menuBar = new JMenuBar();
		file = new JMenu("Game");
		help = new JMenu("Help");
		file.setMnemonic('G');
		menuBar.add(file);
		menuBar.add(help);

		newGame = new JMenuItem("Reset");
		newGame.setMnemonic('R');
		topTen = new JMenuItem("Top Ten Scores");
		helpMenu = new JMenuItem("Minesweeper Help");
		exit = new JMenuItem("Exit");

		topTen.addActionListener(mwl);
		newGame.addActionListener(mwl);
		exit.addActionListener(mwl);
		helpMenu.addActionListener(mwl);

		file.add(newGame);
		file.add(topTen);
		file.add(exit);

		help.add(helpMenu);

		setJMenuBar(menuBar);
		//end setup of the menubar
		//setup the layouts one will be 
		top = new JPanel(new BorderLayout());
		bottom = new JPanel(new GridLayout(x,y));
		setLayout(new BorderLayout());

		flags = new JLabel("010");
		timer = new JLabel("000");
		flags.setFont(new Font("Courier",Font.BOLD,32));
		timer.setFont(new Font("Courier",Font.BOLD,32));

		top.add(flags,BorderLayout.LINE_START);

		top.add(timer,BorderLayout.LINE_END);

		//Add the buttons
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				buttonGrid[i][j]= new JButton();
				bottom.add(buttonGrid[i][j]);
				buttonGrid[i][j].addActionListener(mwl);
				buttonGrid[i][j].addMouseListener(mml);
				//buttonGrid[i][j].setText(String.valueOf(board.get(i, j)));
			}
		}
		//add the layouts to the main layout
		add(top,BorderLayout.PAGE_START);
		add(bottom, BorderLayout.CENTER);
		//Start the timer
		int delay = 1000; //milliseconds
		ActionListener taskPerformer = new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				timer.setText(String.valueOf(time++));
			}

		};
		Jtimer = new Timer(delay, taskPerformer);

		pack();
		setVisible(true);
	}
	private class MainMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			//This creates support for flags
			for(int i=0; i<10; i++)
			{
				for(int j=0; j<10; j++)
				{
					if(buttonGrid[i][j] == e.getSource())
					{
						if(SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1){
							if(buttonGrid[i][j].isEnabled()){
								buttonGrid[i][j].setEnabled(false);
								flagsCounter--;
							}else{
								flagsCounter++;
								buttonGrid[i][j].setEnabled(true);
							}
							flags.setText(String.valueOf(flagsCounter));

						}
					}

				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {		}

		@Override
		public void mouseExited(MouseEvent e) {		    }

		@Override
		public void mousePressed(MouseEvent e) {		}

		@Override
		public void mouseReleased(MouseEvent e) {		}
	}


	private class MainWindowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			//If the lost flag is false then preform the action
			if(!gameEnded){
				for(int i=0; i<10; i++)
				{
					for(int j=0; j<10; j++)
					{
						if(buttonGrid[i][j] == e.getSource())
						{
							if(board.get(i, j)<0)
							{	
								Jtimer.stop();
								JOptionPane.showMessageDialog(null, "You Lose.");
								gameEnded=true;
								//read in the image and set the icon of the bomb
								try {
									Image img = ImageIO.read(getClass().getResource("/bombrevealed.gif"));
									buttonGrid[i][j].setIcon(new ImageIcon(img));
								} catch (IOException ex) {
									ex.printStackTrace();
								}
							}else if(board.get(i, j)>0){
								// if there isnt a bomb display the number corresponding to the
								// location of the bomb
								Jtimer.start();
								buttonGrid[i][j].setText(Integer.toString(board.get(i,j)));
								board.setVisible(i, j);
							}else{
								//if the grid is 0 then reveal the buttons
								Jtimer.start();
								reveal(i,j);
							}

							if(board.isWon()){
								while(true){
									String playerName = (String)JOptionPane.showInputDialog(
											null,
											"Enter Your Name:\n",
											"Customized Dialog",
											JOptionPane.PLAIN_MESSAGE,
											null,
											null,
											"");

									//If a string was returned, say so.
									if ((playerName != null) && (playerName.length() > 0)) {
										topTenScores.add(new Player(playerName,time));
										break;
										//exit the loop
									}
									gameEnded=true;
								}
							}
						}
					}
				}
				

			}
			if(newGame==e.getSource()){
				//reset all variables
				gameEnded=false;
				time=0;
				timer.setText("0");
				//create a new board
				board = new MinesweeperBoard(board.length(),board.width(),board.getMines());
				//clear the board.
				for(int i=0; i<board.length(); i++){
					for(int j=0; j<board.width(); j++){
						buttonGrid[i][j].setText(" ");
						buttonGrid[i][j].setIcon(null);
					}
				}
				
			}
			if(topTen==e.getSource()){
				Collections.sort(topTenScores);
				String players= "";
				for(int i=0; i<topTenScores.size(); i++){
					players=players+" " + i + ": "+ topTenScores.get(i).toString();
				}
				if(topTenScores.size()==0){
					players="No scores.";
				}
				JOptionPane.showMessageDialog(null, players);
			}
			if(exit==e.getSource()){
				System.exit(0);
			}
			if(helpMenu == e.getSource())
			{
				JOptionPane.showMessageDialog(null, "The purpose of the game is to reveal all the squares excluding the mines.\n"
						+ " Left click to reveal a square/bomb. Right click to mark a bomb. Good Luck!");
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

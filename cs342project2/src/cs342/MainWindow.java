package cs342;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class MainWindow extends JFrame {
/*Declaring the objects*/
	private MinesweeperBoard board;
	private JButton[][] buttonGrid;
	private JMenuBar menuBar;
	private JMenu game, help;
	private JMenuItem newGame, exit, topTen, helpMenu, about, resetScores;
	private JPanel top;
	private JPanel bottom;
	private JLabel flags;
	private JLabel timer;
	private ArrayList<Player> topTenScores = new ArrayList<Player>();
	private boolean gameEnded = false;
	private Timer Jtimer;
	private int time = 0;
	private int flagsCounter = 10;
	private int counter[][] = new int[10][10];

/*****************************************/
	
	public static void main(String args[])
	{
		/*For Mac Users, sets the menu bar on the top*/
		if (System.getProperty("os.name").contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		/*Creates the window*/
		MainWindow mw = new MainWindow(10,10,1);
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/*****************************************/
	/*
	 * Making the main window functional
	 */
	public MainWindow(int x, int y, int numberOfMines){
		/*Puts the name on top of the window*/
		super("Minesweeper");
		
		/*Create the listeners*/
		MainWindowListener mwl = new MainWindowListener();
		MainMouseListener mml = new MainMouseListener();
		
		/*Creates the board data*/
		board = new MinesweeperBoard(x,y,numberOfMines);
		buttonGrid = new JButton[x][y];
		/*Setup the menu bar*/
		menuBar = new JMenuBar();
		/*Top Level Menu-Game options, Help Options*/
		game = new JMenu("Game");
		help = new JMenu("Help");
		help.setMnemonic('H');
		game.setMnemonic('G');
		menuBar.add(game);
		menuBar.add(help);
		
		/*The Game Menu*/
		newGame = new JMenuItem("Reset");
		newGame.setMnemonic('R');
		topTen = new JMenuItem("Top Ten Scores");
		topTen.setMnemonic('T');
		resetScores = new JMenuItem("Reset Top Ten Scores");
		topTen.setMnemonic('S');
		helpMenu = new JMenuItem("Minesweeper Help");
		helpMenu.setMnemonic('S');
		about= new JMenuItem("About");
		about.setMnemonic('A');
		exit = new JMenuItem("Exit");
		exit.setMnemonic('X');
		
		/*Listeners for all the menus*/
		topTen.addActionListener(mwl);
		newGame.addActionListener(mwl);
		exit.addActionListener(mwl);
		helpMenu.addActionListener(mwl);
		about.addActionListener(mwl);
		resetScores.addActionListener(mwl);

		/*Adding them to the game menu*/
		game.add(newGame);
		game.add(topTen);
		game.add(resetScores);
		game.add(exit);
		
		/*Adding them to the help menu*/
		help.add(helpMenu);
		help.add(about);

		setJMenuBar(menuBar);
		/*end setup of the menubar*/
		
		/*setup the layouts*/ 
		top = new JPanel(new BorderLayout());
		bottom = new JPanel(new GridLayout(x,y));
		setLayout(new BorderLayout());

		/*Shows the number of flags and the timer*/
		flags = new JLabel("010");
		timer = new JLabel("000");
		flags.setFont(new Font("Courier",Font.BOLD,32));
		timer.setFont(new Font("Courier",Font.BOLD,32));
		/*Adds them to the Jpanel*/
		top.add(flags,BorderLayout.LINE_START);
		top.add(timer,BorderLayout.LINE_END);

		/*Creates all the buttons*/ 
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				buttonGrid[i][j]= new JButton();
				bottom.add(buttonGrid[i][j]);
				buttonGrid[i][j].addActionListener(mwl);
				buttonGrid[i][j].addMouseListener(mml);
				buttonGrid[i][j].setText(String.valueOf(board.get(i, j)));
			}
		}
		
		/*Adds the layout to the Jframe*/
		add(top,BorderLayout.PAGE_START);
		add(bottom, BorderLayout.CENTER);
		/*Start the timer*/
		int delay = 1000; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
		/*Anonymous Class that incremements the timer*/
			public void actionPerformed(ActionEvent evt) {
				timer.setText(String.valueOf(time++));
			}

		};
		/*Initializes the timer*/
		Jtimer = new Timer(delay, taskPerformer);
		try {
			/* Read the scores from the file */
			BufferedReader b = new BufferedReader(new FileReader("scores.dat"));
			String line = null;
			while((line = b.readLine()) != null){
				String split[] = line.split(",");
				System.out.println("Adding " +split[0]+split[1]);
				topTenScores.add(new Player(split[0],Integer.parseInt(split[1])));
			}
			b.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		/*Shrinks it to the smallest size*/
		pack();
		setVisible(true);
	}
	/*****************************************/
	
	/*
	 * Controls the events from the mouse 
	 */
	private class MainMouseListener implements MouseListener{
		/*Counter for each button*/
		@Override
		public void mouseClicked(MouseEvent e) {
			char state[] ={'M', '?',' '};
			if(!gameEnded){
				/*This allows the user to enable the flags*/
				for(int i=0; i<10; i++) {
					for(int j=0; j<10; j++) {
						if(buttonGrid[i][j] == e.getSource()) {
							if(SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1){

								if(counter[i][j]%3==0)
								{
									buttonGrid[i][j].setEnabled(false);
									buttonGrid[i][j].setText(String.valueOf(state[counter[i][j]%3]));
									counter[i][j]++;
									flagsCounter--;
								}else if(counter[i][j]%3==1){
									buttonGrid[i][j].setEnabled(false);
									buttonGrid[i][j].setText(String.valueOf(state[counter[i][j]%3]));
									counter[i][j]++;
								}else if(counter[i][j]%3==2){
									buttonGrid[i][j].setEnabled(true);
									buttonGrid[i][j].setText(String.valueOf(state[counter[i][j]%3]));
									counter[i][j]++;
									flagsCounter++;
								}

								flags.setText(String.valueOf(flagsCounter));

							}
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

	/*
	 * Controls the menu and buttons 
	 */
	private class MainWindowListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			/*If the games over, don't do anything else*/
			if(!gameEnded){
				for(int i=0; i<10; i++) {
					for(int j=0; j<10; j++) {
						if(buttonGrid[i][j] == e.getSource()) {
							if(board.get(i, j)<0) {	
								Jtimer.stop();
								JOptionPane.showMessageDialog(null, "You Lose.");
								gameEnded=true;
								/*Read in the image and set the icon of the bomb*/
								try {
									Image img = ImageIO.read(getClass().getResource("/bombrevealed.gif"));
									buttonGrid[i][j].setIcon(new ImageIcon(img));
								} catch (IOException ex) {
									ex.printStackTrace();
								}
							}else if(board.get(i, j)>0){
								/*This shows the number of bombs that are surrounding that button*/
								Jtimer.start();
								buttonGrid[i][j].setText(Integer.toString(board.get(i,j)));
								board.setVisible(i, j);
							}else{
								/*if the grid is 0 then reveal the buttons*/
								Jtimer.start();
								reveal(i,j);
							}
							
							/*If the user won the game, give them option to enter their name*/
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

									/*Save the string that was entered*/
									if ((playerName != null) && (playerName.length() > 0)) {
										topTenScores.add(new Player(playerName,time));
										break;
										/*exit the loop*/
									}
									
									gameEnded=true;
								}
								try{
									/* Write the scores to the file */
									PrintWriter writer = new PrintWriter("scores.dat", "UTF-8");
									for(Player p : topTenScores){
										writer.println(p.toString());
									}
									writer.close();
								}catch (IOException ex){
									ex.printStackTrace();
								}
							}
						}
					}
				}


			}
			/*Asking for a new game*/
			if(newGame==e.getSource()){
				int n = JOptionPane.showConfirmDialog(
						null,
						"Are you sure you want to start a new game?",
						null,
						JOptionPane.YES_NO_OPTION);
				if(n==0){

					/*reset all variables*/
					gameEnded=false;
					time=0;
					timer.setText("0");
					/*create a new board*/
					board = new MinesweeperBoard(board.length(),board.width(),board.getMines());
					/*clear the board.*/
					for(int i=0; i<board.length(); i++){
						for(int j=0; j<board.width(); j++){
							buttonGrid[i][j].setText(" ");
							buttonGrid[i][j].setIcon(null);
							counter[i][j]=0;
						}
					}
				}

			}
			/*Displays the top ten scores*/
			if(topTen==e.getSource()){
				Collections.sort(topTenScores);
				String players= "";
				for(int i=0; i<topTenScores.size(); i++){
					players=players+" " + i+1 + ": "+ topTenScores.get(i).toString() + "\n";
				}
				if(topTenScores.size()==0){
					players="No scores.";
				}
				JOptionPane.showMessageDialog(null, players);
			}
			/*exit*/
			if(exit==e.getSource()){
				System.exit(0);
			}
			/*Display the rules of the game*/
			if(helpMenu == e.getSource())
			{
				JOptionPane.showMessageDialog(null, "The purpose of the game is to reveal all the squares excluding the mines.\n"
						+ " Left click to reveal a square/bomb. Right click to mark a bomb. Good Luck!");
			}
			/*Give user option to clear top scores*/
			if(resetScores == e.getSource())
			{
				int n = JOptionPane.showConfirmDialog(
						null,
						"Are you sure you want to reset the scores?",
						null,
						JOptionPane.YES_NO_OPTION);
				if(n==0){
					topTenScores.clear();
				}
			}
			/*"About" section*/
			if(about == e.getSource())
			{
				JOptionPane.showMessageDialog(null, "Made by:\n"
						+ "Brian Herman and Diana Carrillo.");
			}
		}
		/*Flood fill algorithm to reveal the squares*/
		/**
		 * Flood Fill Algorithm to reveal the squares
		 * @param i
		 * @param j
		 */
		public void reveal(int i, int j)
		{
			if(i>=0 && j>=0 || i<board.length() && j<board.width()){
				if(board.get(i, j)==0 && board.revealed(i, j)==false && buttonGrid[i][j].isEnabled())
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

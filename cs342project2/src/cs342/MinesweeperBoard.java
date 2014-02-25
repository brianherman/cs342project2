package cs342;
import java.util.Random;
public class MinesweeperBoard {
	private int[][] board;
	private boolean[][] revealed;
	private int length=0;
	private int width=0;
	private int mines;
	private int numOfRevealed;
	/*
	 * Pseudo code to generate the board.
	 *	grid = [n,m]   // initialize all cells to 0
	 *	for k = 1 to number_of_mines
	 *	   get random mine_x and mine_y where grid(mine_x, mine_y) is not a mine
	 *	   for x = -1 to 1
	 *	      for y = -1 to 1
	 *	         if x = 0 and y = 0 then
	 *	            grid[mine_x, mine_y] = -number_of_mines  // negative value = mine
	 *	         else 
	 *	            increment grid[mine_x + x, mine_y + y] by 1
	 */
	public MinesweeperBoard(int x, int y, int m){
		board = new int[x][y];
		revealed = new boolean[x][y];
		numOfRevealed=0;
		length=x;
		width=y;
		mines=m;
		Random rn = new Random();
		
		/*For loop to set the mines*/
		for(int k=1; k<=mines; k++) {
			int mine_x, mine_y;
			while(true){
				mine_x = rn.nextInt(x);
				mine_y = rn.nextInt(y);
				if(board[mine_x][mine_y] <= 0)
					break;
			}
			//System.out.println(mine_x +" " + mine_y);
			
			/*Sets the numbers on the board*/
			for(int i=-1; i<2; i++) {
				//System.out.println(i);
				for(int j=-1; j<2; j++){
					if (i==0 && j==0){
						board[mine_x][mine_y]= -mines;
					}else{
						if(mine_x+i < 0 || mine_x+i >= x ||
								mine_y+j < 0 || mine_y+j >= y)
							continue;
						board[mine_x+i][mine_y+j]++;
					}
					//System.out.println(i + " "+ j);
				}

			}
		}
	}
/*************************************/
	
	/*Gets the cell from the board*/
	public int get(int x, int y){
		if(x<0 || y<0)
			return -1;
		if(x > length || y > width)
			return -1;
		return board[x][y];
	}
	
	/*Checks to see if the cell is revealed*/
	public boolean revealed(int x, int y){
		if(x<0 || y<0)
			return false;
		if(x>length || y > width)
			return false;
		return revealed[x][y];
	}
	/*Gets the length & width & mines of the board*/
	public int length(){
		return length;
	}
	public int width(){
		return width;
	}
	public int getMines(){
		return mines;
	}
	
	/*Checks to see if the user won*/
	public boolean isWon(){
		System.out.println(numOfRevealed);
		return (length*width-numOfRevealed) == mines;
	}
	/*Makes a cell visible*/
	public void setVisible(int x, int y){
		if(x<0 || y<0)
			return;
		if(x>length || y > width)
			return;
		numOfRevealed++;
		revealed[x][y]=true;
	}
}

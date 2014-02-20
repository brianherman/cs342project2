package cs342;

public class Player implements Comparable<Player>{
	private int score;
	private String name;
	public Player(String n, int s){
		name = n;
		score = s;
	}
	@Override
	public int compareTo(Player p) {
		return p.score - score;
	}
	public String toString(){
		return name +" " + score;
	}
}

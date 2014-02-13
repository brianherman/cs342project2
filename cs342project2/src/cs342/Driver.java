package cs342;

import java.io.IOException;

import javax.swing.JFrame;

public class Driver {
	public static void main(String args[])
	{
		MainWindow mw = new MainWindow(10,10,10);
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

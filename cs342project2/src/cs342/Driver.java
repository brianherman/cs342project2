package cs342;

import java.io.IOException;

import javax.swing.JFrame;

public class Driver {
	public static void main(String args[])
	{
		try {
			MainWindow mw = new MainWindow(8,8,8);
			mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

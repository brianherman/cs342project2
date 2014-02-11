package cs342;

import java.io.IOException;

public class Driver {
	public static void main(String args[])
	{
		try {
			MainWindow mw = new MainWindow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
